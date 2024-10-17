import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/tickets")
@MultipartConfig
public class TicketServlet extends HttpServlet {

    private static final Map<Integer, Ticket> ticketMap = new HashMap<>();
    private static final AtomicInteger ticketCounter = new AtomicInteger(1);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            listTickets(request, response);
        } else {
            switch (action) {
                case "view":
                    viewTicket(request, response);
                    break;
                case "download":
                    downloadAttachment(request, response);
                    break;
                case "new":
                    showTicketForm(request, response);
                    break;
                default:
                    listTickets(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createTicket(request, response);
        } else {
            response.sendRedirect("tickets");
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tickets", ticketMap.values());
        request.getRequestDispatcher("/listTickets.jsp").forward(request, response);
    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Ticket ticket = getTicket(id);

        if (ticket != null) {
            request.setAttribute("ticket", ticket);
            request.getRequestDispatcher("/viewTicket.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
        }
    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newTicketForm.jsp").forward(request, response);
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        Ticket ticket = new Ticket(customerName, subject, body, new ArrayList<>());
        processAttachment(request, ticket);

        int id = ticketCounter.getAndIncrement();
        ticketMap.put(id, ticket);

        response.sendRedirect("tickets");
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int attachmentIndex = Integer.parseInt(request.getParameter("attachmentIndex"));

        Ticket ticket = getTicket(ticketId);
        if (ticket != null) {
            Attachment attachment = ticket.getAttachment(attachmentIndex);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() + "\"");

            try (OutputStream out = response.getOutputStream()) {
                out.write(attachment.getContents());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attachment not found");
        }
    }

    private void processAttachment(HttpServletRequest request, Ticket ticket)
            throws IOException, ServletException {
        Part filePart = request.getPart("attachment");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            byte[] fileContents = filePart.getInputStream().readAllBytes();

            Attachment attachment = new Attachment();
            attachment.setName(fileName);
            attachment.setContents(fileContents);

            ticket.addAttachment(attachment);
        }
    }

    private Ticket getTicket(int id) {
        return ticketMap.get(id);
    }
}
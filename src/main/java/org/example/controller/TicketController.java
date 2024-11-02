package org.example.controller;
import javax.servlet.http.HttpServletResponse;
import org.example.model.Ticket;

import org.example.model.Attachment;
import org.example.model.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final Map<Integer, Ticket> ticketMap = new HashMap<>();
    private int ticketCounter = 1;

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketMap.values());
        return "listTickets.jsp";
    }

    @GetMapping("/new")
    public String showTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "newTicketForm";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket,
                               @RequestParam("attachment") MultipartFile attachment) throws IOException {
        if (!attachment.isEmpty()) {
            Attachment att = new Attachment();
            att.setName(attachment.getOriginalFilename());
            att.setContents(attachment.getBytes());
            ticket.addAttachment(att);
        }
        ticketMap.put(ticketCounter++, ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}")
    public String viewTicket(@PathVariable int id, Model model) {
        Ticket ticket = ticketMap.get(id);
        if (ticket == null) {
            return "error";
        }
        model.addAttribute("ticket", ticket);
        return "viewTicket";
    }

    @GetMapping("/{id}/download/{index}")
    public void downloadAttachment(@PathVariable int id,
                                   @PathVariable int index,
                                   HttpServletResponse response) throws IOException {
        Ticket ticket = ticketMap.get(id);
        if (ticket != null && index < ticket.getAttachments().size()) {
            Attachment attachment = ticket.getAttachment(index);
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + attachment.getName() + "\"");
            response.getOutputStream().write(attachment.getContents());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
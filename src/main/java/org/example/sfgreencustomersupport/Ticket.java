import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String customerName;
    private String subject;
    private String body;
    private List<Attachment> attachments;

    public Ticket() {
        this.attachments = new ArrayList<>();
    }

    public Ticket(String customerName, String subject, String body, List<Attachment> attachments) {
        this.customerName = customerName;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    // Helper Methods
    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public int getNumberOfAttachments() {
        return attachments.size();
    }

    public Attachment getAttachment(int index) {
        if (index >= 0 && index < attachments.size()) {
            return attachments.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid attachment index.");
        }
    }

    public List<Attachment> getAllAttachments() {
        return new ArrayList<>(attachments);
    }
}
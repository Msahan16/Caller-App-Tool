package CallerApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private Contact contact;
    private LocalDateTime dateTime;
    private String note;


    public Schedule(Contact contact, LocalDateTime dateTime, String note) {
        this.contact = contact;
        this.dateTime = dateTime;
        this.note = note;    
    }
    

    public Contact getContact() {
        return contact;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getNote() {
        return note;
    }
    
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Contact: " + contact.getName() + ", DateTime: " + dateTime.format(formatter) + ", Note: " + note;
    }
}
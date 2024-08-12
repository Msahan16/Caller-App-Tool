package CallerApp;
import java.util.Objects;

public class Contact { //contructor initialze name,num
    private String name;
    private String phoneNumber;
    private boolean isFavorite;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isFavorite = false;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
    this.name = name;
}

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void SetPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}
//checck fav marked
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
 // Override toString method for easy contact information displa
    @Override
    public String toString() {
        return "Name: " + name + "   Phone: " + phoneNumber + (isFavorite ? " (Favorite)" : "");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return name.equals(contact.name) && phoneNumber.equals(contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }

    
}
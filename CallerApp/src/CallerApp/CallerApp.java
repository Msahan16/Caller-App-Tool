package CallerApp;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CallerApp {
     // List to store contacts
    private List<Contact> contacts;
    private List<ContactGroup> groups;
    private List<Schedule> schedules;
    private List<Contact> blockedContacts;
    private Map<String, ContactGroup> groupsMap;
    
// Constructor to initialize all lists
    public CallerApp() {
        this.contacts = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.groupsMap = new HashMap<>();
        this.schedules = new ArrayList<>();
        this.blockedContacts = new ArrayList<>();
    }

    //methds
    public boolean addContact(Contact contact) {
        if (!contacts.contains(contact)) {
            return contacts.add(contact);
        }
        return false; //already contac have
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }

    public boolean updateContact(Contact oldContact, Contact newContact) {
        int index = contacts.indexOf(oldContact);
        if (index != -1) {
            contacts.set(index, newContact);
            return true;
        }
        return false; 
    }

    public List<Contact> getContacts() { 
        return contacts;
    }
//adding grp
    public boolean addGroup(ContactGroup group) {
        if (!groupsMap.containsKey(group.getName().toLowerCase())) {
            groups.add(group);
            groupsMap.put(group.getName().toLowerCase(), group);
            return true;
        }
        return false;
    }

    public boolean removeGroup(ContactGroup group) {
        if (groupsMap.remove(group.getName().toLowerCase()) != null) {
            groups.remove(group);
            return true;
        }
        return false;
    }

    public ContactGroup getGroupByName(String name) {
        return groupsMap.get(name.toLowerCase());
    }

    public List<ContactGroup> getGroups() {
        return groups;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void blockContact(Contact contact) {
        blockedContacts.add(contact);
    }

    public List<Contact> getBlockedContacts() {
        return blockedContacts;
    }

    public List<Contact> getFavoriteContacts() {
        List<Contact> favoriteContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.isFavorite()) {
                favoriteContacts.add(contact);
            }
        }
        return favoriteContacts;
    }
}

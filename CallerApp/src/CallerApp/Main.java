package CallerApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CallerApp callerApp = new CallerApp();
    private static Scanner scanner = new Scanner(System.in);

    //its working loop
    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }
//handle user inputs for this methods
            switch (choice) {
                case 1:
                    callContact();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    removeContact();
                    break;
                case 4:
                    updateContact();
                    break;
                case 5:
                    manageFavoriteContacts();
                    break;
                case 6:
                    manageBlockedContacts();
                    break;
                case 7:
                    addGroup();
                    break;
                case 8:
                    updateGroup();
                    break;
                case 9:
                    manageSchedule();
                    break;
                case 10:
                    displayContacts();
                    break;
                case 11:
                    displayGroups();
                    break;
                case 12:
                    displayFavoriteContacts();
                    break;
                case 13:
                    displayScheduledContacts();
                    break;
                case 14:
                    displayBlockedContacts();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
            addSpacing();
        }
    }

    private static void displayMenu() {
        System.out.println("Caller App");
        System.out.println("Menu:");
        System.out.println("1. Call Contact");
        System.out.println("2. Add Contact");
        System.out.println("3. Remove Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Manage Favorite Contacts");
        System.out.println("6. Manage Blocked Contacts");
        System.out.println("7. Add Group");
        System.out.println("8. Update Group");
        System.out.println("9. Manage Schedule");
        System.out.println("10.Display Contacts");
        System.out.println("11.Display Groups");
        System.out.println("12.Display Favorite Contacts");
        System.out.println("13.Display Scheduled Contacts");
        System.out.println("14.Display Blocked Contacts");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        
    }

    private static void callContact() {
    System.out.println();
    System.out.print("Enter the name of the contact to call: ");
    String name = scanner.nextLine();
    Contact contact = findContactByName(name);
    
    if (contact != null) {
        System.out.println("Calling " + contact.getName() + ".....");
    } else {
        System.out.println("Contact not found.");
    }
    while (true) {
    System.out.println("Press '0' to return to the Main Menu.");
    int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                break;
            }
}
    }

   private static void addContact() {
    System.out.println();
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    if (name.isEmpty()) {
        System.out.println("Name cannot be empty.");
        return;
    }

    // Check if the name already exists
    for (Contact contact : callerApp.getContacts()) {
        if (contact.getName().equalsIgnoreCase(name)) { // Using equalsIgnoreCase to make the check caseinsensitive
            System.out.println("A contact with the name '" + name + "' already exists.");
            return;
        }
    }

    String phone;
    while (true) {
        System.out.print("Enter phone number (10 digits): ");
        phone = scanner.nextLine();
        if (phone.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
        } else if (phone.length() != 10 ) {
            System.out.println("Phone number must be exactly 10 digits.");
        } else {
            // Check if the phone number already exists
            for (Contact contact : callerApp.getContacts()) {
                if (contact.getPhoneNumber().equals(phone)) {
                    System.out.println("Phone number already exists for another contact: " + contact.getName());
                    return;
                }
            }
            break; // Exit the loop if the phone number is unique
        }
    }
//add conatct to list
    Contact contact = new Contact(name, phone);//if both details want to run
    if (callerApp.addContact(contact)) {
        System.out.println("Contact added successfully.");
    } else {
        System.out.println("Failed to add contact.");
    }
}



    private static void removeContact() {
        System.out.print("Enter contact name to remove: ");
        String name = scanner.nextLine();
        Contact contact = findContactByName(name);//  inthe contact class contact list finding the given name
        if (contact != null) {
            if (callerApp.removeContact(contact)) {
                // Remove from favorites if its s fav contact 
                if (contact.isFavorite()) {
                    contact.setFavorite(false);//if in the favorite list removed
                    
                }

                // Remove from blocked contacts if present
                if (callerApp.getBlockedContacts().contains(contact)) {
                    callerApp.getBlockedContacts().remove(contact);
                    
                }

                // Remove from groups if present
                for (ContactGroup group : callerApp.getGroups()) {
                    group.removeContact(contact);
                }
                

                // Remove from schedules if present
                List<Schedule> schedules = new ArrayList<>(callerApp.getSchedules());
                for (Schedule schedule : schedules) {
                    if (schedule.getContact().equals(contact)) {
                        callerApp.removeSchedule(schedule);
                    }
                }
                

                System.out.println("Contact removed successfully.");
            } else {
                System.out.println("Failed to remove contact.");
            }
        } else {
            System.out.println("Contact not found.");
        }
    }
    
      private static void updateContact() {
    System.out.println();
    System.out.print("Enter the name of the contact to update: ");
    String oldName = scanner.nextLine();
    Contact oldContact = findContactByName(oldName);
    if (oldContact != null) {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        if (newName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        String newPhone;
        while (true) {
            System.out.print("Enter new phone number (10 digits): ");
            newPhone = scanner.nextLine();
            if (newPhone.isEmpty()) {
                System.out.println("Phone number cannot be empty.");
            } else if (newPhone.length() != 10) {
                System.out.println("Phone number must be exactly 10 digits.");
            } else {
                break; // Exit the loop if the phone number is valid
            }
        }
        Contact newContact = new Contact(newName, newPhone);
        if (callerApp.updateContact(oldContact, newContact)) {
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Failed to update contact.");
        }
    } else {
        System.out.println("Contact not found.");
    }
}


    private static void manageFavoriteContacts() {
        System.out.println();
        while (true) {
            System.out.println("Manage Favorite Contacts:");
            System.out.println("1. Add to Favorite");
            System.out.println("2. Remove from Favorite");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }

        if (choice == 0) {
            break;
        }

        if (choice != 1 && choice != 2) {
            System.out.println();
            System.out.println("Invalid choice");
            System.out.println();
            continue;
        
            }
            System.out.print("Enter the name of the contact: ");
            String name = scanner.nextLine();
            Contact contact = findContactByName(name);
            if (contact != null) {
                if (choice == 1) {
                    contact.setFavorite(true);
                    System.out.println("Contact added to favorites.");
                    System.out.println();
                   
                } else if (choice == 2) {
                    contact.setFavorite(false);
                    System.out.println("Contact removed from favorites.");
                     System.out.println();
               
                }
                else {
                System.out.println("Contact not found.");
                System.out.println();
            }
        }
        }
    }
    

    private static void manageBlockedContacts() {
    System.out.println();
    while (true) {
        System.out.println("Manage Blocked Contacts:");
        System.out.println("1. Block Contact");
        System.out.println("2. Unblock Contact");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("Invalid input. Please enter a number.");
            continue;
        }

        if (choice == 0) {
            break;
        }

        if (choice != 1 && choice != 2) {
            System.out.println();
            System.out.println("Invalid choice");
            System.out.println();
            continue;
        }

        System.out.print("Enter the name of the contact: ");
        String name = scanner.nextLine();
        Contact contact = findContactByName(name);

        if (contact == null) {
            System.out.println("Contact not found.");
            continue;
        }

        if (choice == 1) { // Block Contact
            if (callerApp.getBlockedContacts().contains(contact)) {
                System.out.println("Contact is already blocked.");
            } else {
                callerApp.blockContact(contact);
                System.out.println("Contact blocked.");
            }
        } else if (choice == 2) { // Unblock Contact
            if (callerApp.getBlockedContacts().contains(contact)) {
                callerApp.getBlockedContacts().remove(contact);
                System.out.println("Contact unblocked.");
            } else {
                System.out.println("Contact is not blocked.");
            }
        }
        System.out.println();
    }
}


    private static void addGroup() {
        System.out.println();
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine();
        if (groupName.isEmpty()) {
            System.out.println("Group name cannot be empty.");
            return;
        }
        ContactGroup group = new ContactGroup(groupName);
        if (callerApp.addGroup(group)) {
            System.out.println("Group added successfully.");
        } else {
            System.out.println("Group already exists.");
            return;
        }
        while (true) {
            System.out.print("Enter contact name to add to group (or '99' to stop): ");
            String contactName = scanner.nextLine();
            if (contactName.equals("99")) {
                System.out.println("Group created successfully.");
                return;
            }
            Contact contact = findContactByName(contactName);
            if (contact != null) {
                group.addContact(contact);
                System.out.println("Contact added to group.");
            } else {
                System.out.println("Contact not found.");
            }
        }
    }

    private static void updateGroup() {
    System.out.println();
    while (true) {
        System.out.println("Update Group:");
        System.out.println("1. Add Contact to Group");
        System.out.println("2. Remove Contact from Group");
        System.out.println("3. Remove Group");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
         int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("Invalid input. Please enter a number.");
            System.out.println();
            continue;
        }

        if (choice == 0) {
            break;
        }

        if (choice != 1 && choice != 2) {
            System.out.println();
            System.out.println("Invalid choice");
            System.out.println();
            continue;
        }
        System.out.print("Enter the name of the group: ");
        String groupName = scanner.nextLine();
        ContactGroup group = callerApp.getGroupByName(groupName);
        if (group == null) {
            System.out.println("Group not found.");
            System.out.println();
            continue;
        
        }
        switch (choice) {
            case 1:
                while (true) {
                    System.out.print("Enter contact name to add to group (or '99' to stop): ");
                    String contactName = scanner.nextLine();
                    if (contactName.equals("99")) {
                        System.out.println("Group updated successfully.");
                        System.out.println();
                        return;
                    }
                    Contact contact = findContactByName(contactName);
                    if (contact != null) {
                        group.addContact(contact);
                        System.out.println("Contact added to group.");
                        System.out.println();
                    } else {
                        System.out.println("Contact not found in contact list.");
                        System.out.println();
                    }
                }
            case 2:
                while (true) {
                    System.out.print("Enter contact name to remove from group (or '99' to stop): ");
                    String contactName = scanner.nextLine();
                    if (contactName.equals("99")) {
                        System.out.println("Group updated successfully.");
                        System.out.println();
                        return;
                    }
                    Contact contact = findContactByName(contactName);
                    if (contact != null) {
                        group.removeContact(contact);
                        System.out.println("Contact removed from group.");
                        System.out.println();
                    } else {
                        System.out.println("Contact not found in contact list.");
                        System.out.println();
                    }
                }
            case 3:
                callerApp.removeGroup(group);
                System.out.println("Group removed successfully.");
                System.out.println();
                break;
        }
    }
}

    private static void manageSchedule() {
        System.out.println();
        while (true) {
            System.out.println("Manage Schedule:");
            System.out.println("1. Add Schedule");
            System.out.println("2. Remove Schedule");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("Invalid input. Please enter a number.");
            System.out.println();
            continue;
        }

        if (choice == 0) {
            break;
        }

        if (choice != 1 && choice != 2) {
            System.out.println();
            System.out.println("Invalid choice");
            System.out.println();
            continue;
        
            }
            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                    removeSchedule();
                    break;
            }
        }
    }

    private static void addSchedule() {
    System.out.println();
    System.out.print("Enter the name of the contact to schedule: ");
    String name = scanner.nextLine();
    Contact contact = findContactByName(name);
    if (contact == null) {
        System.out.println("Contact not found.");
        System.out.println();
        return;
    }

    LocalDateTime dateTime = null;
    while (dateTime == null) {
        System.out.print("Enter the schedule date and time (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        try {
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Enter the correct format (yyyy-MM-dd HH:mm).");
        }
    }

    System.out.print("Enter note: ");
    String note = scanner.nextLine();
    
    Schedule schedule = new Schedule(contact, dateTime, note);
    callerApp.addSchedule(schedule);
    System.out.println("Schedule added successfully.");
    System.out.println();
}

    private static void removeSchedule() {
        System.out.println();
        System.out.print("Enter the name of the contact to remove schedule: ");
        String name = scanner.nextLine();
        Contact contact = findContactByName(name);
        if (contact == null) {
            System.out.println("Contact not found.");
           System.out.println();
            return;
        }
        List<Schedule> schedules = callerApp.getSchedules();
        for (Schedule schedule : schedules) {
            if (schedule.getContact().equals(contact)) {
                callerApp.removeSchedule(schedule);
                System.out.println("Schedule removed successfully.");
                System.out.println();
                return;
            }
        }
        System.out.println("Schedule not found.");
        System.out.println();
    }

    private static void displayContacts() {
    System.out.println();
    List<Contact> contacts = new ArrayList<>(callerApp.getContacts());

    if (contacts.isEmpty()) {
        System.out.println("No contacts available.");
    } else {
        // Sort contacts using the separate selectionSort method
        selectionSort(contacts);

        // Display all contacts
        System.out.println("All Contacts:");
        for (Contact contact : contacts) {
            boolean isScheduled = callerApp.getSchedules().stream()
                    .anyMatch(schedule -> schedule.getContact().equals(contact));
            String scheduleNote = isScheduled ? " [Scheduled]" : "";
            System.out.println(contact + scheduleNote);
        }

        // Display contacts by group
        System.out.println("\nContacts by Group:");
        for (ContactGroup group : callerApp.getGroups()) {
            System.out.println("Group: " + group.getName());
            for (Contact contact : group.getContacts()) {
                System.out.println(" - " + contact);
            }
        }
    }
}


     private static void displayGroups() {
           System.out.println();
    List<ContactGroup> groups = callerApp.getGroups();
    if (groups.isEmpty()) {
        System.out.println("No groups available.");
    } else {
        for (ContactGroup group : groups) {
            System.out.println(group);
            for (Contact contact : group.getContacts()) {
                System.out.println("  " + contact);
            }
        }
    }
    }

    private static void displayFavoriteContacts() {
        System.out.println();
    List<Contact> contacts = callerApp.getFavoriteContacts();
    if (contacts.isEmpty()) {
        System.out.println("No favorite contacts available.");
    } else {
        selectionSort(contacts); // Sort using selection sort
        System.out.println(" Favourite Contacts:");
        System.out.println();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
    }

    private static void displayScheduledContacts() {
      System.out.println();
    List<Schedule> schedules = callerApp.getSchedules();
    if (schedules.isEmpty()) {
        System.out.println("No scheduled contacts available.");
    } else {
        schedules.sort(Comparator.comparing(Schedule::getDateTime));
        System.out.println("Sheduled Contacts:");
        System.out.println();
        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
    }
    }


    private static void displayBlockedContacts() {
    System.out.println();
    List<Contact> contacts = callerApp.getBlockedContacts();
    if (contacts.isEmpty()) {
        System.out.println("No blocked contacts available.");
    } else {
        selectionSort(contacts); // Sort using selection sort
        System.out.println("Blocked Contacts:");
        System.out.println();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}

    private static Contact findContactByName(String name) {
        System.out.println();
        List<Contact> contacts = callerApp.getContacts();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
    private static void selectionSort(List<Contact> contacts) {
            int n = contacts.size();
    
    // Traverse through all but the last element
    for (int i = 0; i < n - 1; i++) {
        // Find the minimum element of the list
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (contacts.get(j).getName().compareTo(contacts.get(minIndex).getName()) < 0) {
                minIndex = j;
            }
        }
        
        // Swap  minimum element with the first unsorted element
        if (minIndex != i) {
            Contact temp = contacts.get(i);
            contacts.set(i, contacts.get(minIndex));
            contacts.set(minIndex, temp);
        }
    }   
    
   }

    private static void addSpacing() {
        System.out.println();
        System.out.println("===================================================");
        System.out.println();
    }


    


}






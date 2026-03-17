package volunteer;

/*
 Custom Linked List implementation.
 This class manages all volunteer nodes in the system.
 */
public class VolunteerLinkedList {

    // Head node of the linked list
    Node head;

    /*
     Method to add a volunteer to the linked list
     */
    public void addVolunteer(Volunteer volunteer) {

        // Create a new node containing the volunteer
        Node newNode = new Node(volunteer);

        // If the list is empty, new node becomes the head
        if (head == null) {
            head = newNode;
            return;
        }

        // Traverse to the end of the list
        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        // Add the new node at the end
        temp.next = newNode;
    }

    /*
     Method to search for a volunteer by ID
     Returns the volunteer if found, otherwise returns null
     */
    public Volunteer searchById(int id) {

        Node temp = head;

        // Traverse through the linked list
        while (temp != null) {

            if (temp.volunteer.volunteerId == id) {
                return temp.volunteer;
            }

            temp = temp.next;
        }

        // Volunteer not found
        return null;
    }

    /*
     Method to remove a volunteer by ID
     Returns true if removal was successful
     */
    public boolean removeVolunteer(int id) {

        // If list is empty
        if (head == null) {
            return false;
        }

        // If the first node should be removed
        if (head.volunteer.volunteerId == id) {
            head = head.next;
            return true;
        }

        // Traverse the list
        Node current = head;
        Node previous = null;

        while (current != null) {

            // Volunteer found
            if (current.volunteer.volunteerId == id) {

                // Remove node by linking previous node to next node
                previous.next = current.next;

                return true;
            }

            previous = current;
            current = current.next;
        }

        // Volunteer not found
        return false;
    }

    /*
     Method to display all volunteers in the list
     */
    public void displayVolunteers() {

        Node temp = head;

        while (temp != null) {

            temp.volunteer.displayVolunteer();
            temp = temp.next;
        }
    }
    /*
 Method to search volunteer by name
 Displays all volunteers with the matching name
 */
    public void searchByName(String name) {

        Node temp = head;

        boolean found = false;

        // Traverse through the linked list
        while (temp != null) {

            // Compare names ignoring case
            if (temp.volunteer.name.equalsIgnoreCase(name)) {

                temp.volunteer.displayVolunteer();
                found = true;

            }

            temp = temp.next;
        }

        // If no volunteer found
        if (!found) {
            System.out.println("No volunteer found with this name.");
        }
    }
}
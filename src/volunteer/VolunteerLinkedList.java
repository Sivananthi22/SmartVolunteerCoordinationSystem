package volunteer;
/*
 Custom Linked List implementation.
 This class manages all volunteer nodes in the system.
 It allows adding, removing, searching, displaying, and sorting volunteers.
 */
public class VolunteerLinkedList {
    // Head node of the linked list (starting point)
    private Node head;
    //Getter for head (used in other classes like system)
    public Node getHead() {
        return head;
    }
    /*
     Method to add a volunteer to the linked list
     New volunteers are added at the end of the list
     */
    public void addVolunteer(Volunteer volunteer) {
        Node newNode = new Node(volunteer);
        // If list is empty
        if (head == null) {
            head = newNode;
            return;
        }
        Node temp = head;
        // Traverse to last node
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(newNode);
    }

     // Method to search for a volunteer by ID
    public Volunteer searchById(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.getVolunteer().getVolunteerId() == id) {
                return temp.getVolunteer();
            }
            temp = temp.getNext();
        }
        return null;
    }

     //Method to remove a volunteer by ID
    public boolean removeVolunteer(int id) {
        if (head == null) return false;
        // If head needs to be removed
        if (head.getVolunteer().getVolunteerId() == id) {
            head = head.getNext();
            return true;
        }
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.getVolunteer().getVolunteerId() == id) {
                previous.setNext(current.getNext());
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        return false;
    }


    // Method to display all volunteers
    public void displayVolunteers() {
        Node temp = head;
        while (temp != null) {
            temp.getVolunteer().displayVolunteer();
            temp = temp.getNext();
        }
    }


    // Method to search volunteers by name
    public void searchByName(String name) {
        Node temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.getVolunteer().getName().equalsIgnoreCase(name)) {
                temp.getVolunteer().displayVolunteer();
                found = true;
            }
            temp = temp.getNext();
        }
        if (!found) {
            System.out.println("No volunteer found with this name.");
        }
    }


    /*
 Method to sort volunteers by priority level
 High => Medium => Low
 Using Insertion Sort (suitable for linked lists)
*/
    public void sortByPriority() {
        // If list is empty or has only one node
        if (head == null || head.getNext() == null) {
            return;
        }
        Node sorted = null; // New sorted list
        Node current = head;
        // Traverse original list
        while (current != null) {
            Node next = current.getNext(); // Store next node
            // Insert current node into sorted list
            sorted = insertInSortedOrder(sorted, current);
            current = next;
        }
        // Update head to sorted list
        head = sorted;
    }

    /*
     Helper method to insert a node into the sorted list
     based on priority (High => Medium => Low)
    */
    private Node insertInSortedOrder(Node sorted, Node newNode) {

        int newPriority = getPriorityValue(
                newNode.getVolunteer().getPriorityLevel());
        // Case 1: Insert at beginning
        if (sorted == null ||
                newPriority > getPriorityValue(
                        sorted.getVolunteer().getPriorityLevel())) {

            newNode.setNext(sorted);
            return newNode;
        }

        Node current = sorted;
        // Traverse sorted list to find correct position
        while (current.getNext() != null &&
                getPriorityValue(current.getNext()
                        .getVolunteer().getPriorityLevel()) >= newPriority) {

            current = current.getNext();
        }

        // Insert node
        newNode.setNext(current.getNext());
        current.setNext(newNode);

        return sorted;
    }
    /*
     Helper method to convert priority to numeric value
     High = 3, Medium = 2, Low = 1
    */
    private int getPriorityValue(String priority) {

        if (priority.equalsIgnoreCase("high")) {
            return 3;
        } else if (priority.equalsIgnoreCase("medium")) {
            return 2;
        } else {
            return 1;
        }
    }
}
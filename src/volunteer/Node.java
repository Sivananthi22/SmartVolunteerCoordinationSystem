package volunteer;
/*
 Node class used in Linked List and Queue.
 Each node stores a Volunteer object and a reference
 to the next node in the structure.
 */
public class Node {
    // Volunteer object stored in this node
    private Volunteer volunteer;
    // Reference to the next node in the linked structure
    private Node next;

     //Constructor to create a node
    public Node(Volunteer volunteer) {
        this.volunteer = volunteer;
        this.next = null;
    }

     //Getter for volunteer
    public Volunteer getVolunteer() {
        return volunteer;
    }

    // Setter for volunteer
    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

     //Getter for next node
    public Node getNext() {
        return next;
    }

     //Setter for next node
    public void setNext(Node next) {
        this.next = next;
    }
}
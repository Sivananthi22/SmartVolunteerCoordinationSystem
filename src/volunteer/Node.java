package volunteer;

/*
 Node class used in Linked List, Queue and Stack.
 Each node stores a Volunteer object and a reference
 to the next node.
 */
public class Node {

    // Volunteer object stored in this node
    Volunteer volunteer;

    // Reference to next node
    Node next;

    /*
     Constructor to create a node
     */
    public Node(Volunteer volunteer) {

        this.volunteer = volunteer;
        this.next = null;

    }
}

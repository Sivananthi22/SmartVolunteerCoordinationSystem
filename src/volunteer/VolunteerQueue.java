package volunteer;

/*
 Custom Queue Implementation
 This class represents a Queue data structure implemented using
 a linked list approach.
 The queue is used to manage volunteers waiting for task assignment.
 It follows the FIFO principle (First In First Out), meaning:
 - The first volunteer added to the queue will be the first one removed.
 This is important in our system because volunteers should be processed
 in the order they register, unless priority overrides it elsewhere.
*/
public class VolunteerQueue {
    // Reference to the first element (front) of the queue
    private Node front;
    // Reference to the last element (rear) of the queue
    private Node rear;

    /*
     Method: enqueue()
     Description:
     Adds a new volunteer to the queue.
     - A new node is created to store the volunteer.
     - If the queue is empty, both front and rear point to the new node.
     - Otherwise, the new node is added at the rear.
     This maintains FIFO order.
    */
    public void enqueue(Volunteer volunteer) {

        // Create a new node containing the volunteer
        Node newNode = new Node(volunteer);

        // If queue is empty
        if (rear == null) {

            // Both front and rear point to the new node
            front = rear = newNode;
            return;
        }
        // Link new node at the end of the queue
        rear.setNext(newNode);

        // Update rear pointer
        rear = newNode;
    }

    /*
     Method: dequeue()
     Description:
     Removes and returns the volunteer at the front of the queue.
     - If the queue is empty, return null.
     - Otherwise, remove the front node.
     - Move front pointer to next node.
     If the queue becomes empty after removal, rear is also reset.
     This ensures FIFO behaviour.
    */
    public Volunteer dequeue() {
        // If queue is empty
        if (front == null) {
            System.out.println("Queue is empty. No volunteer to dequeue.");
            return null;
        }
        // Get the volunteer at the front
        Volunteer volunteer = front.getVolunteer();
        // Move front pointer to next node
        front = front.getNext();
        // If queue becomes empty, reset rear
        if (front == null) {
            rear = null;
        }
        return volunteer;
    }

    /*
     Method: isEmpty()
     Description:
     Checks whether the queue is empty.
     Returns:
     - true if queue has no elements
     - false otherwise
    */
    public boolean isEmpty() {
        return front == null;
    }
    /*
     Method: peek()
     Description:
     Returns the volunteer at the front of the queue WITHOUT removing it.
     - Useful when we want to see who is next in line
     - Does not modify the queue
     Returns:
     - Volunteer at front
     - null if queue is empty
    */
    public Volunteer peek() {
        if (front == null) {
            System.out.println("Queue is empty.");
            return null;
        }
        return front.getVolunteer();
    }

    /*
     Method: displayQueue()
     Description:
     Displays all volunteers currently in the queue.
     - Traverses from front to rear
     - Useful for debugging and testing
     This helps demonstrate queue behaviour during viva.
    */
    public void displayQueue() {
        Node temp = front;
        // If queue is empty
        if (temp == null) {
            System.out.println("Queue is empty.");
            return;
        }

        // Traverse and display each volunteer
        while (temp != null) {
            temp.getVolunteer().displayVolunteer();
            temp = temp.getNext();
        }
    }
}
package volunteer;

/*
 Custom Queue implementation.
 Used to manage volunteers waiting for task assignment.
 Queue follows FIFO (First In First Out).
 */
public class VolunteerQueue {

    Node front;
    Node rear;

    /*
     Add volunteer to queue
     */
    public void enqueue(Volunteer volunteer) {

        Node newNode = new Node(volunteer);

        // If queue is empty
        if (rear == null) {

            front = rear = newNode;
            return;

        }

        // Add node at rear
        rear.next = newNode;
        rear = newNode;

    }

    /*
     Remove volunteer from queue
     */
    public Volunteer dequeue() {

        if (front == null) {

            return null;

        }

        Volunteer volunteer = front.volunteer;

        front = front.next;

        if (front == null) {

            rear = null;

        }

        return volunteer;
    }

    /*
     Check if queue is empty
     */
    public boolean isEmpty() {

        return front == null;

    }
}

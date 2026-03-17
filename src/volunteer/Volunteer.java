package volunteer;
/*
 This class represents a Volunteer object.
 It stores all information related to a volunteer.
 */
public class Volunteer {
    // Unique ID of the volunteer
    int volunteerId;

    // Name of the volunteer
    String name;

    // Task assigned to the volunteer
    String taskAssigned;

    // Priority level (Low / Medium / High)
    String priorityLevel;

    // Indicates whether the volunteer has been assigned a task
    boolean assigned;

    /*
     Constructor to create a volunteer object
     */
    public Volunteer(int volunteerId, String name, String priorityLevel) {

        this.volunteerId = volunteerId;
        this.name = name;
        this.priorityLevel = priorityLevel;

        // Initially volunteer has no task
        this.taskAssigned = "";

        // Initially volunteer is not assigned
        this.assigned = false;
    }

    /*
     Method to display volunteer details
     */
    public void displayVolunteer() {

        System.out.println("Volunteer ID: " + volunteerId);
        System.out.println("Name: " + name);
        System.out.println("Priority Level: " + priorityLevel);
        System.out.println("Task Assigned: " + taskAssigned);
        System.out.println("Assigned Status: " + assigned);
        System.out.println("---------------------------------");

    }
}

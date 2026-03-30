package volunteer;

/*
 This class represents a Volunteer object.
 It stores all information related to a volunteer.
 */
public class Volunteer {

    // Unique ID of the volunteer
    private int volunteerId;

    // Name of the volunteer
    private String name;

    // Task assigned to the volunteer
    private String taskAssigned;

    // Priority level (Low / Medium / High)
    private String priorityLevel;

    // Indicates whether the volunteer has been assigned a task (true/false)
    private boolean assigned;


    // Constructor to create a volunteer object
    public Volunteer(int volunteerId, String name, String priorityLevel) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.priorityLevel = priorityLevel;
        // Initially volunteer has no task
        this.taskAssigned = "Not Assigned";
        // Initially volunteer is not assigned
        this.assigned = false;
    }

    // Getter for volunteer ID
    public int getVolunteerId() {
        return volunteerId;
    }
    // Getter for name
    public String getName() {
        return name;
    }
    // Getter for task assigned
    public String getTaskAssigned() {
        return taskAssigned;
    }
    // Setter for task assigned
    public void setTaskAssigned(String taskAssigned) {
        this.taskAssigned = taskAssigned;
    }
    // Getter for priority level
    public String getPriorityLevel() {
        return priorityLevel;
    }
    // Getter for assigned status
    public boolean isAssigned() {
        return assigned;
    }
    // Setter for assigned status
    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

     //Method to display volunteer details
    public void displayVolunteer() {
        System.out.println("Volunteer ID: VID" + String.format("%04d", volunteerId));
        System.out.println("Name: " + name);
        System.out.println("Priority Level: " + priorityLevel);
        System.out.println("Task Assigned: " + taskAssigned);
        System.out.println("Assigned Status: " + assigned);
        System.out.println("---------------------------------");
    }
}
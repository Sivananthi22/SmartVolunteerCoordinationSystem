package volunteer;
/*
 This class represents an action performed in the system.
 It is used to store operations for undo functionality.
 */
public class Action {
    // Constants for action types
    public static final String ASSIGN = "ASSIGN";
    public static final String REMOVE = "REMOVE";
    // Type of action performed
    // Used to determine how to undo the action
    private String actionType;
    // Volunteer involved in the action
    private  Volunteer volunteer;
    private String previousTask;
    private boolean previousAssigned;

     //Constructor to initialize action details
     public Action(String actionType, Volunteer volunteer) {
         this.actionType = actionType;
         this.volunteer = volunteer;

         // SAVE CURRENT STATE
         this.previousTask = volunteer.getTaskAssigned();
         this.previousAssigned = volunteer.isAssigned();
     }

    //Getter for action type
    public String getActionType() {
        return actionType;
    }
     // Getter for volunteer
    public Volunteer getVolunteer() {
        return volunteer;
    }
    public String getPreviousTask() {
        return previousTask;
    }

    public boolean getPreviousAssigned() {
        return previousAssigned;
    }
}
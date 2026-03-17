package volunteer;

/*
 This class represents an action performed in the system.
 It is used for the undo functionality.
*/
public class Action {

    // Type of action performed (ASSIGN or REMOVE)
    String actionType;

    // Volunteer involved in the action
    Volunteer volunteer;

    // Constructor
    public Action(String actionType, Volunteer volunteer) {
        this.actionType = actionType;
        this.volunteer = volunteer;
    }
}
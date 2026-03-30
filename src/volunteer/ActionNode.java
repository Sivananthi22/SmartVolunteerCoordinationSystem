package volunteer;
/*
 Node class used in ActionStack.
 Each node stores an Action object and a reference to the next node.
 */
public class ActionNode {
    // Action stored in this node
    private Action action;
    // Reference to the next node in the stack
    private ActionNode next;
     // Constructor to create a node
    public ActionNode(Action action) {
        this.action = action;
        this.next = null;
    }
     // Getter for action
    public Action getAction() {
        return action;
    }
     // Getter for next node
    public ActionNode getNext() {
        return next;
    }
     // Setter for next node
    public void setNext(ActionNode next) {
        this.next = next;
    }
}
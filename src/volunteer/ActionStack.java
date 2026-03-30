package volunteer;
/*
 Custom stack used for undo operations.
 Stack follows LIFO (Last In First Out) principle.
 */
public class ActionStack {
    // Top of the stack
    private ActionNode top;

     // Method to push an action onto the stack
    public void push(Action action) {
        // Create a new node
        ActionNode newNode = new ActionNode(action);
        // Link new node to current top
        newNode.setNext(top);
        // Update top to new node
        top = newNode;
    }

    // Method to pop the last action from the stack
    public Action pop() {
        // If stack is empty
        if (top == null) {
            System.out.println("No actions to undo.");
            return null;
        }
        // Get action from top
        Action action = top.getAction();
        // Move top pointer to next node
        top = top.getNext();
        return action;
    }
    // Method to check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }
}
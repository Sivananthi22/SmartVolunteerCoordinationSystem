package volunteer;

/*
 Custom stack used for undo operations.
 Stack follows LIFO (Last In First Out).
 */
public class ActionStack {

    ActionNode top;

    /*
     Push action to stack
     */
    public void push(Action action) {

        ActionNode newNode = new ActionNode(action);

        newNode.next = top;
        top = newNode;

    }

    /*
     Pop last action
     */
    public Action pop() {

        if (top == null) {
            return null;
        }

        Action action = top.action;
        top = top.next;

        return action;
    }

    /*
     Check if stack is empty
     */
    public boolean isEmpty() {
        return top == null;
    }
}
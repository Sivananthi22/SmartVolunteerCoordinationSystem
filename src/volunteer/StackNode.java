package volunteer;

/*
 Node class used in ActionStack
 */
public class ActionNode {

    Action action;
    ActionNode next;

    public ActionNode(Action action) {

        this.action = action;
        this.next = null;

    }
}
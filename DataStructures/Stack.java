
/**
 * Methods for a stack
 *
 * @author Kanya Farley
 * @version 23/04
 */
public class Stack
{
    private Node top;
    /**
     * Constructor for objects of class Stack
     * 
     * Empty since only top element must be set, which is set in "push" method
     */
    public Stack()
    {
        //
    }
    
    /* checks if stack is empty */
    public boolean emptyStack() {
        if (this.top == null) {
            return(true);
        } else {
            return(false);
        }
    }
    
    /* add a node to the top of the list */
    public void push(String data) {
        Node node = new Node();
        node.setData(data);
        node.setNextNode(this.top); // current top becomes next node
        this.top = node; // this new node becomes the new top
    }
    
    /* pop a node off top of stack */
    public String pop() {
        String data = top.getData();
        top = top.getNextNode(); // new top is the next node
        return(data);
    }
    
    public Node getTop() {
        return(this.top);
    }
}

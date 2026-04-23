
/**
 * Linked list with nodes
 *
 * @author Kanya Farley
 * @version 23/04
 */
public class Node
{
    private String data;
    private Node next;
    
    /** Constructor for nodes with no data*/
    public Node() {
        this.data = "";
    }
    
    /** Constructor for nodes with data*/
    public Node(String data) {
        this.data = data;
    }
    
    /* setters */
    public void setData(String data) {
        this.data = data;
    }
    
    public void setNextNode(Node n) {
        this.next = n;
    }
    
    /* getters */
    public String getData() {
        return(this.data);
    }
    
    public Node getNextNode() {
        return(this.next);
    }
}

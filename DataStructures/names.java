
/**
 * Creates a stack of names and prints them
 *
 * @author Kanya Farley
 * @version 23/04
 */
public class names
{
    /**
     * Constructor for objects of class names
     */
    public static void main(String[] args)
    {
        Stack stackOfNames = new Stack();
        /* add names */
        stackOfNames.push("Aaron");
        stackOfNames.push("Bill");
        stackOfNames.push("Charlie");
        
        /* print names from stack (top to bottom) */
        System.out.println(stackOfNames.pop());
        System.out.println(stackOfNames.pop());
        System.out.println(stackOfNames.pop());
    }
}

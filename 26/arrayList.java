
/**
 * Quick array list test
 *
 * @author Kanya Farley
 * @version 19/2/26
 */
import java.util.ArrayList;
public class arrayList
{
    // instance variables
    //String names[] = {"Bob", "Terry", "Sally", "Cat", "Grug", "Bert"};
    ArrayList<String> names = new ArrayList<String>();
    /**
     * Constructor for objects of class arrayList
     */
    public arrayList()
    {
        names.add("Bob");
        names.add("Bert");
        names.add("Sally");
        names.add("Cat");
        names.add("Grug");
        for (String currentName : names) {
            System.out.println(currentName);
        }
    }
}

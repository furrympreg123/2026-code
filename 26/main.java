
/**
 * Provides objects using the Car class.
 *
 * @author Kanya Farley
 * @version 19/2/26
 */
import java.util.Scanner;
public class main
{
    // instance variables
    Scanner kb = new Scanner(System.in);
    /**
     * Constructor for objects of class main
     */
    public static void main(String[] args)
    {
        Car car1 = new Car("Volkswagen", "Golf", 2009, 219, 1271, "Black", "Hatchback");
        Car car2 = new Car("Toyota", "Aqua", 2018, 112, 1003, "Blue", "Hatchback");
        System.out.println(car1.getMake());
        System.out.println(car1.getModel());
        System.out.println(car1.getYear());
        System.out.println(car1.getHorsepower());
        System.out.println(car1.getWeight());
        System.out.println(car1.getColour());
        System.out.println(car1.getShape());
    }
}

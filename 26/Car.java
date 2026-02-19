
/**
 * This class sets a constructor for creating car objects.
 *
 * @author Kanya Farley
 * @version 19/2/26
 */
public class Car
{
    private String make;
    private String model;
    private int year;
    private int horsepower;
    private double weight; // in kgs
    private String colour;
    private String shape;
    /**
     * Constructor for objects of class car
     */
    public Car(String make, String model, int year, int horsepower, double weight, String colour, String shape)
    {
        this.make = make;
        this.model = model;
        this.year = year;
        this.horsepower = horsepower;
        this.weight = weight;
        this.colour = colour;
        this.shape = shape;
    }
    
    public String getMake() {
        return(this.make);
    }
    
    public String getModel() {
        return(this.model);
    }
    
    public int getYear() {
        return(this.year);
    }
    
    public int getHorsepower() {
        return(this.horsepower);
    }
    
    public double getWeight() {
        return(this.weight);
    }
    
    public String getColour() {
        return(this.colour);
    }
    
    public String getShape() {
        return(this.shape);
    }
}

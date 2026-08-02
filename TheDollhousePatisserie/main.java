
/**
 * This is the class where the actual game actions occur
 * 
 * !! For the ecs100 library to work (and not cause compile error), BlueJ must be version 5.5.0
 * 
 * Background needed for kitchen method GUI
 * Kitchen method animation needs to be completed
 *
 * @author Kanya Farley
 * @version 31/7
 */
import java.util.Random;
import ecs100.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class main
{
    boolean active = true;

    String[] recipe = {"Parfait", "Fruit Tart", "Cinnamon Roll", "Cake", "Ube Cupcake", "Coffee Jelly", "Melon Float"};
    String[] sprite = {"girl1", "boy1", "girl2", "boy2", "mascot1", "mascot2"};
    ArrayList<String> step = new ArrayList<String>(); // tracks recipe steps
    String actual; // actual step being executed

    double pressedX = 0;
    double pressedY = 0;
    double releasedX = 0;
    double releasedY = 0;

    OrderQueue oQueue = new OrderQueue();
    WaitingQueue wQueue = new WaitingQueue();
    String currentSprite;
    String currentRecipe;
    Customer cust1;
    Customer cust2;
    Customer cust3;
    Customer cust4;
    boolean orderComplete = true; // turn into method??

    /* GUI */
    final int oQueueX = 252;
    final int oQueueY = 375;

    final int wQueueX = 628;
    final int wQueueY = 375;

    final int custWidth = 220;
    final int custHeight = 300;

    final int custXGap = 180;

    /**
     * Constructor for objects of class CopyOfmainECSver
     */
    public void main()
    {
        /* background GUI */
        UI.setWindowSize(1098, 672);
        UI.drawImage("kitchen_DHP.jpeg", 0, 0);
        UI.setMouseListener(this::doMouse);
        
        UI.printf("Welcome to the Dollhouse \nPatisserie!");
        UI.printf("\nClick the bright speech bubble to \ntake an order, and the customer \nwill wait by the other side of \nthe counter.");
        UI.printf("\nUse the various kitchen tools to \nfind the correct steps for making \nthe recipe.");
        UI.printf("\nOnce the message 'Order Complete!' \nappears, you may serve the waiting \ncustomer!");
        
        while (active) {
            addOrder(oQueue);
        }

    }

    public void doMouse(String action, double x, double y) {
        switch (action) {
            case "pressed" -> {
                    this.pressedX = x;
                    this.pressedY = y;
                }
            case "released" -> {
                    this.releasedX = x;
                    this.releasedY = y;
                    if (this.active && releasedX >=oQueueX+140 && releasedX <= oQueueX+240 && releasedY >= oQueueY-50 && releasedY <= oQueueY+50 && !oQueue.orderQueueEmpty() && wQueue.waitingQueueEmpty()) {
                        wQueue.waitingEnqueue(orderTaken(oQueue));
                        if (!wQueue.waitingQueueEmpty()) {
                            redrawAll();
                        }
                    } else if (releasedX >=oQueueX+140 && releasedX <= oQueueX+240 && releasedY >= oQueueY && releasedY <= oQueueY+100 && !orderComplete) {
                        UI.println("Sorry, you can't take an order right now.");
                    }

                    /* serve waiting customer */
                    if (this.active && releasedX >= wQueueX-10 && releasedX <= wQueueX+90 && releasedY >= wQueueY-50 && releasedY <= wQueueY+50 && orderComplete) {
                        if (orderComplete) {
                            wQueue.waitingDequeue();
                            redrawAll();
                        }
                    }

                    // step actions per recipe

                    /* refridgerate */
                    if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 70 && releasedX <= 220 && releasedY >= 20 && releasedY <= 240) {
                        actual = "refridgerate";
                        if (step.size() > 0 && actual == step.get(0)) {
                            refridgerate(currentRecipe);
                        } else {
                            UI.println("Wrong step!");
                            UI.println("Required step: " + step.get(0));
                        }
                    }
                    /* chop */
                    if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 268 && releasedX <= 318 && releasedY >= 50 && releasedY <= 190) {
                        actual = "chop";
                        if (step.size() > 0 && actual == step.get(0)) {
                            chop(currentRecipe);
                        } else {
                            UI.println("Wrong step!");
                            UI.println("Required step: " + step.get(0));
                        }
                    }
                    /* mix */
                    if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 392 && releasedX <= 482 && releasedY >= 68 && releasedY <= 143) {
                        actual = "mix";
                        if (step.size() > 0 && actual == step.get(0)) {
                            mix(currentRecipe);
                        } else {
                            UI.println("Wrong step!");
                            UI.println("Required step: " + step.get(0));
                        }
                    }
                    /* oven */
                    if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 516 && releasedX <=684 && releasedY >= 85 && releasedY <= 250) {
                        actual = "oven";
                        if (step.size() > 0 && actual == step.get(0)) {
                            oven(currentRecipe);
                        } else {
                            UI.println("Wrong step!");
                            UI.println("Required step: " + step.get(0));
                        }
                    }
                    /* decorate */
                    if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 700 && releasedX <= 770 && releasedY >= 94 && releasedY <= 159) {
                        actual = "decorate";
                        if (step.size() > 0 && actual == step.get(0)) {
                            decorate(currentRecipe);
                        } else {
                            UI.println("Wrong step!");
                            UI.println("Required step: " + step.get(0));
                        }
                    }

                }
        }
    }

    /* random generators */
    public Customer newRandomCustomer() {
        String recipe = randomRecipe();
        String sprite = randomCustomer();
        Customer newCustomer = new Customer(sprite, recipe); // make new customer
        drawOrderingCustomer(sprite, recipe, oQueue);
        return newCustomer;
    }

    /**
     * Generates a random recipe selected from array options
     */
    public String randomRecipe() {
        Random ranRecipe = new Random();
        int randomRecipe = ranRecipe.nextInt(recipe.length);
        return recipe[randomRecipe];
    }

    /**
     * Generates a random customer sprite selected from array options
     */
    public String randomCustomer() {
        Random ranSprite = new Random();
        int randomSprite = ranSprite.nextInt(sprite.length);
        return sprite[randomSprite];
    }

    /**
     * Draws each new ordering customer in correct position of the order queue
     */
    public void drawOrderingCustomer(String sprite, String recipe, OrderQueue oQueue) {
        if (oQueue.getFront() == null) {
            UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX, oQueueY, custWidth, custHeight); // sprite
            UI.drawImage("speaking_DHP.png", oQueueX+140, oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165, oQueueY-35, 65, 70);
            cust1 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 0) {
            UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-custXGap, oQueueY, custWidth, custHeight);
            UI.drawImage("speaking_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-custXGap, oQueueY-35, 65, 70);
            UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
            cust2 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 1) {
            UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-(custXGap*2), oQueueY, custWidth, custHeight);
            UI.drawImage("speaking_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-(custXGap*2), oQueueY-35, 65, 70);
            UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
            cust3 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 2) {
            UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-(custXGap*3), oQueueY, custWidth, custHeight);
            UI.drawImage("speaking_DHP.png", oQueueX+140-(custXGap*3), oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-(custXGap*3), oQueueY-35, 65, 70);
            UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-(custXGap*3), oQueueY-50, 100, 100);
            cust4 = new Customer(sprite, recipe);
        }
    }

    /* check status of queues */
    public void orderQueueStatus(OrderQueue queue) {
        if (queue.orderQueueEmpty() == true) {
            UI.println("No customers.");
        } else if (queue.orderQueueEmpty() == false) {
            UI.println("Someone wants to order!");
        }
    }

    public void waitingQueueStatus(WaitingQueue queue) {
        if (queue.waitingQueueEmpty() == true) {
            UI.println("No current orders.");
        } else if (queue.waitingQueueEmpty() == false) {
            UI.println("Someone is waiting for an order!");
        }
    }

    public void addOrder(OrderQueue oQueue) {
        while (oQueue.getSize() < 3) {
            oQueue.orderEnqueue(newRandomCustomer());
            //UI.sleep(30000); // adds customer every 30 seconds
            UI.sleep(5000);
        }
    }

    /**
     * Transfers customer to waiting queue, erases old order queue and redraws in correct order
     */
    public Customer orderTaken(OrderQueue oQueue) {
        WaitingQueue wQueue = new WaitingQueue();
        String [] next = (oQueue.orderDequeue()).split("-"); // is dequeued but not erased
        currentSprite = next[0];
        currentRecipe = next[1];
        Customer waitingCustomer = new Customer(next[0], next[1]);
        
        /* shifting queue up */
        cust1 = cust2;
        cust2 = cust3;
        cust3 = cust4;
        cust4 = null;
        
        redrawAll();
        orderComplete = false;
        recipeStart(currentRecipe, wQueue);
        return waitingCustomer;
    }

    public void redrawAll() {
        UI.eraseImage("kitchen_DHP.jpeg", 0, 0); // erases everything to avoid layers
        
        /* background */
        UI.drawImage("kitchen_DHP.jpeg", 0, 0);

        /* waiting queue */
        if (!wQueue.waitingQueueEmpty()) {
            UI.drawImage("customerGUI/" + wQueue.getWFront().getSprite() + "_DHP.png", wQueueX, wQueueY, custWidth, custHeight); // sprite
            UI.drawImage("thinking_DHP.png", wQueueX-10, wQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + wQueue.getWFront().getRecipe() + "_DHP.png", wQueueX+5, wQueueY-35, 65, 70); // PLACED POORLY!
        }

        /* ordering queue */
        if (cust1 != null) {
            UI.drawImage("customerGUI/" + cust1.getSprite() + "_DHP.png", oQueueX, oQueueY, custWidth, custHeight); // sprite
            UI.drawImage("speaking_DHP.png", oQueueX+140, oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + cust1.getRecipe() + "_DHP.png", oQueueX+165, oQueueY-35, 65, 70);
        }
        if (cust2 != null) {
            UI.drawImage("customerGUI/" + cust2.getSprite() + "_DHP.png", oQueueX-custXGap, oQueueY, custWidth, custHeight);
            UI.drawImage("speaking_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + cust2.getRecipe() + "_DHP.png", oQueueX+165-custXGap, oQueueY-35, 65, 70);
            UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
        }
        if (cust3 != null) {
            UI.drawImage("customerGUI/" + cust3.getSprite() + "_DHP.png", oQueueX-(custXGap*2), oQueueY, custWidth, custHeight);
            UI.drawImage("speaking_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
            UI.drawImage("recipeGUI/" + cust2.getRecipe() + "_DHP.png", oQueueX+165-(custXGap*2), oQueueY-35, 65, 70);
            UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
        }
    }

    /* recipe handler */
    public void recipeStart(String recipe, WaitingQueue wQueue) {
        switch (recipe) {
            case "Parfait" :
                step.add("refridgerate");
                step.add("chop");
                step.add("decorate");
                break;
            case "Fruit Tart" :
                step.add("mix");
                step.add("oven");
                step.add("chop");
                step.add("decorate");
                step.add("refridgerate");
                break;
            case "Cinnamon Roll":
                step.add("mix");
                step.add("chop");
                step.add("oven");
                break;
            case "Cake":
                step.add("mix");
                step.add("oven");
                step.add("decorate");
                break;
            case "Ube Cupcake":
                step.add("mix");
                step.add("oven");
                step.add("decorate");
                break;
            case "Coffee Jelly":
                step.add("mix");
                step.add("refridgerate");
                step.add("decorate");
                break;
            case "Melon Float":
                step.add("mix");
                step.add("refridgerate");
                step.add("decorate");
                break;
        }
    }

    /**
     * Order is set to complete when there are no more steps for the recipe (not functioning as intended...)
     */
    public void checkRecipeCompletion() {
        if (step.size() > 0) {
            UI.println("Next step: " + step.get(0));
        } else if (step.size() == 0) {
            orderComplete = true;
            UI.println("Order complete!");
        }
    }

    /* recipe methods */
    public void refridgerate (String recipe) {
        /*placeholder*/
        UI.println("Refridgerating...");
        UI.sleep(5000);
        UI.println("Done!");
        step.remove(0);
        checkRecipeCompletion();
    }

    public void chop (String recipe) {
        if (recipe.equals("Cinnamon Roll")) {
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 2; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 60, 60, 500, 500);
                    UI.drawImage("kitchenGUI/chopping1_DHP.png", 185, 200, 500, 221);
                    UI.drawImage("kitchenGUI/chopping_knife" + j + "_DHP.png", 500, 150, 150, 100);
                    UI.sleep(700);
                }
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
            UI.drawImage("kitchenGUI/chopping2_DHP.png", 185, 200, 500, 221);
            UI.sleep(1500);
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        } else if (recipe.equals("Parfait") || recipe.equals("Fruit Tart")) {
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 2; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 60, 60, 500, 500);
                    UI.drawImage("kitchenGUI/chopping3_DHP.png", 185, 200, 500, 221);
                    UI.drawImage("kitchenGUI/chopping_knife" + j + "_DHP.png", 500, 150, 150, 100);
                    UI.sleep(700);
                }
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
            UI.drawImage("kitchenGUI/chopping4_DHP.png", 185, 200, 500, 221);
            UI.sleep(1500);
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 2; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 60, 60, 500, 500);
                    UI.drawImage("kitchenGUI/chopping5_DHP.png", 185, 200, 500, 221);
                    UI.drawImage("kitchenGUI/chopping_knife" + j + "_DHP.png", 500, 150, 150, 100);
                    UI.sleep(700);
                }
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
            UI.drawImage("kitchenGUI/chopping6_DHP.png", 185, 200, 500, 221);
            UI.sleep(1500);
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        }
        step.remove(0);
        checkRecipeCompletion();
    }

    public void mix (String recipe) {
        if (recipe.equals("Melon Float")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j < 6; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
                    UI.drawImage("kitchenGUI/melon_mix" + j + "_DHP.png", 185, 200, 500, 221);
                    UI.sleep(800);
                }
            }
            UI.println("Done!");
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j < 6; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
                    UI.drawImage("kitchenGUI/mix" + j + "_DHP.png", 185, 200, 500, 221);
                    UI.sleep(600);
                }
            }
            UI.println("Done!");
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        }
    }

    public void oven (String recipe) {
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
        UI.drawImage("kitchenGUI/oven_empty_DHP.png", 200, 200, 450, 221);
        UI.sleep(2000);
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
        UI.drawImage("kitchenGUI/oven_prebaked_DHP.png", 200, 200, 450, 221);
        UI.sleep(2000);
        for (int i = 1; i <= 3; i++) {
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
            UI.drawImage("kitchenGUI/oven_bake" + i + "_DHP.png", 200, 200, 450, 221);
            UI.sleep(1500);
        }
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", -100, 60, 1050, 500);
        UI.drawImage("kitchenGUI/oven_baked_DHP.png", 200, 200, 450, 221);
        UI.sleep(3000);
        redrawAll();
        step.remove(0);
        checkRecipeCompletion();
    }

    public void decorate (String recipe) {
        /*placeholder*/
        UI.println("Decorating...");
        UI.sleep(5000);
        UI.println("Done!");
        step.remove(0);
        checkRecipeCompletion();
    }

}


/**
 * This is the class where the actual game actions occur
 * 
 * !! For the ecs100 library to work (and not cause compile error), BlueJ must be version 5.5.0 or later
 *
 * @author Kanya Farley
 * @version 7/8
 */
import java.util.Random;
import ecs100.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class main
{
    boolean active = false;

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
    boolean orderComplete = true;

    /* GUI */
    final int oQueueX = 252;
    final int oQueueY = 375;

    final int wQueueX = 628;
    final int wQueueY = 375;

    final int custWidth = 220;
    final int custHeight = 300;

    final int custXGap = 180;

    boolean actionActive = false;

    /**
     * Constructor for objects of class CopyOfmainECSver
     */
    public void main()
    {
        UI.setWindowSize(1080, 667);
        UI.setMouseListener(this::doMouse);
        UI.drawImage("kitchen_DHP.jpg", 0, 0);
        
        // TUTORIAL
        active = false;
        UI.println("(Tutorial not interactable)");
        
        UI.drawImage("popup_DHP.png", 140, 270, 550, 320);
        UI.setColor(Color.decode("#FFA8C7"));
        UI.setFontSize(20);

        UI.drawString("Welcome to the Dollhouse Patisserie!", 230, 365);
        UI.drawString("Let's get started with the basics.", 210, 390);
        UI.drawString("<-- To the left is the queue for ordering customers.", 195, 415);
        UI.drawString("Let's serve somebody!", 200, 440);
        UI.sleep(5000);

        redrawAll();
        oQueue.orderEnqueue(newRandomCustomer());
        UI.setFontSize(17);
        UI.drawImage("popup_DHP.png", 240, 120, 367, 213);
        UI.drawString("This customer wants a " + oQueue.getFront().getRecipe() + ".", 285, 195);
        UI.drawString("To take the order, we click the", 285, 215);
        UI.drawString("speech bubble.", 285, 235);
        UI.sleep(5000);

        wQueue.waitingEnqueue(orderTaken(oQueue));
        UI.drawImage("popup_DHP.png", 140, 270, 550, 320);
        UI.setColor(Color.decode("#FFA8C7"));
        UI.setFontSize(20);
        UI.drawString("Once the order is taken, the above", 230, 365);
        UI.drawString("kitchen tools can be used in accordance with", 210, 390);
        UI.drawString("the next step on the side panel.", 200, 415);
        UI.drawString("The side panel says to " + step.get(0).toLowerCase() + ", so", 200, 440);
        UI.drawString("we click the " + step.get(0).toLowerCase() + " tool!", 200, 465);
        UI.sleep(7000);

        int initialSize = step.size();

        switch (step.get(0)) {
            case "Refridgerate" :
                refridgerate(wQueue.getWFront().getRecipe());
                break;
            case "Chop" : 
                chop(wQueue.getWFront().getRecipe());
                break;
            case "Mix" :
                mix(wQueue.getWFront().getRecipe());
                break;
            case "Bake" :
                oven(wQueue.getWFront().getRecipe());
                break;
            case "Decorate" :
                decorate(wQueue.getWFront().getRecipe());
                break;
        }
        if (step.size() < initialSize) {
            UI.println("(not actually)"); // avoid confusing the user about "next step" in tutorial
            UI.setFontSize(17);
            UI.drawImage("popup_DHP.png", 240, 120, 367, 213);
            step.clear();
            orderComplete = true;
            UI.drawString("Once the recipe is complete, we can", 285, 195);
            UI.drawString("serve the customer in the waiting", 285, 215);
            UI.drawString("queue by clicking the speech bubble.", 285, 235);
            UI.drawString("Now it's your turn!", 285, 255);
            UI.sleep(6000);
            wQueue.waitingDequeue();
            redrawAll();
            active = true;
        }
        // TUTORIAL END
        
        while (active) {
            if (oQueue.getSize() < 3) {
                addOrder(oQueue);
            } else {
                UI.sleep(500); // prevents thread lock
            }
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
                    if (this.active) {
                        if (releasedX >=oQueueX+140 && releasedX <= oQueueX+240 && releasedY >= oQueueY-50 && releasedY <= oQueueY+50 && !oQueue.orderQueueEmpty() && wQueue.waitingQueueEmpty()) {
                            wQueue.waitingEnqueue(orderTaken(oQueue));
                            if (!wQueue.waitingQueueEmpty()) {
                                redrawAll();
                            }
                        } else if (releasedX >=oQueueX+140 && releasedX <= oQueueX+240 && releasedY >= oQueueY && releasedY <= oQueueY+100 && !orderComplete) {
                            UI.println("Sorry, you can't take an order right now.");
                        }

                        /* serve waiting customer */
                        if (releasedX >= wQueueX-10 && releasedX <= wQueueX+90 && releasedY >= wQueueY-50 && releasedY <= wQueueY+50 && orderComplete) {
                            if (orderComplete) {
                                wQueue.waitingDequeue();
                                redrawAll();
                            } else {
                                UI.println("Recipe incomplete!");
                            }
                        }

                        // step actions per recipe

                        /* refridgerate */
                        if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 70 && releasedX <= 220 && releasedY >= 20 && releasedY <= 240) {
                            actual = "Refridgerate";
                            if (step.size() > 0 && actual == step.get(0)) {
                                refridgerate(currentRecipe);
                            } else {
                                UI.println("Wrong step!");
                                UI.println("Required step: " + step.get(0));
                            }
                        }
                        /* chop */
                        if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 268 && releasedX <= 318 && releasedY >= 50 && releasedY <= 190) {
                            actual = "Chop";
                            if (step.size() > 0 && actual == step.get(0)) {
                                chop(currentRecipe);
                            } else {
                                UI.println("Wrong step!");
                                UI.println("Required step: " + step.get(0));
                            }
                        }
                        /* mix */
                        if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 392 && releasedX <= 482 && releasedY >= 68 && releasedY <= 143) {
                            actual = "Mix";
                            if (step.size() > 0 && actual == step.get(0)) {
                                mix(currentRecipe);
                            } else {
                                UI.println("Wrong step!");
                                UI.println("Required step: " + step.get(0));
                            }
                        }
                        /* oven */
                        if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 516 && releasedX <=684 && releasedY >= 85 && releasedY <= 250) {
                            actual = "Bake";
                            if (step.size() > 0 && actual == step.get(0)) {
                                oven(currentRecipe);
                            } else {
                                UI.println("Wrong step!");
                                UI.println("Required step: " + step.get(0));
                            }
                        }
                        /* decorate */
                        if (!wQueue.waitingQueueEmpty() && !orderComplete && releasedX >= 700 && releasedX <= 770 && releasedY >= 94 && releasedY <= 159) {
                            actual = "Decorate";
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
            if (!actionActive) {
                UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX, oQueueY, custWidth, custHeight); // sprite
                UI.drawImage("speaking_DHP.png", oQueueX+140, oQueueY-50, 100, 100);
                UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165, oQueueY-35, 65, 70);
            }
            cust1 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 0) {
            if (!actionActive) {
                UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-custXGap, oQueueY, custWidth, custHeight);
                UI.drawImage("speaking_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
                UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-custXGap, oQueueY-35, 65, 70);
                UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-custXGap, oQueueY-50, 100, 100);
            }
            cust2 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 1) {
            if (!actionActive) {
                UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-(custXGap*2), oQueueY, custWidth, custHeight);
                UI.drawImage("speaking_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
                UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-(custXGap*2), oQueueY-35, 65, 70);
                UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-(custXGap*2), oQueueY-50, 100, 100);
            }
            cust3 = new Customer(sprite, recipe);
        } else if (oQueue.getSize() == 2) {
            if (!actionActive) {
                UI.drawImage("customerGUI/" + sprite + "_DHP.png", oQueueX-(custXGap*3), oQueueY, custWidth, custHeight);
                UI.drawImage("speaking_DHP.png", oQueueX+140-(custXGap*3), oQueueY-50, 100, 100);
                UI.drawImage("recipeGUI/" + recipe + "_DHP.png", oQueueX+165-(custXGap*3), oQueueY-35, 65, 70);
                UI.drawImage("speakingoverlay_DHP.png", oQueueX+140-(custXGap*3), oQueueY-50, 100, 100);
            }
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
        oQueue.orderEnqueue(newRandomCustomer());
        UI.sleep(7500);
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
        UI.eraseImage("kitchen_DHP.jpg", 0, 0); // erases everything to avoid layers

        /* background */
        UI.drawImage("kitchen_DHP.jpg", 0, 0);

        /* waiting queue */
        if (!wQueue.waitingQueueEmpty()) {
            UI.drawImage("customerGUI/" + wQueue.getWFront().getSprite() + "_DHP.png", wQueueX, wQueueY, custWidth, custHeight); // sprite
            UI.drawImage("thinking_DHP.png", wQueueX+90, wQueueY-50, -100, 100);
            UI.drawImage("recipeGUI/" + wQueue.getWFront().getRecipe() + "_DHP.png", wQueueX, wQueueY-35, 70, 70);
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
                step.add("Refridgerate");
                step.add("Chop");
                step.add("Decorate");
                break;
            case "Fruit Tart" :
                step.add("Mix");
                step.add("Bake");
                step.add("Chop");
                step.add("Decorate");
                step.add("Refridgerate");
                break;
            case "Cinnamon Roll":
                step.add("Mix");
                step.add("Chop");
                step.add("Bake");
                break;
            case "Cake":
                step.add("Mix");
                step.add("Bake");
                step.add("Decorate");
                break;
            case "Ube Cupcake":
                step.add("Mix");
                step.add("Bake");
                step.add("Decorate");
                break;
            case "Coffee Jelly":
                step.add("Mix");
                step.add("Refridgerate");
                step.add("Decorate");
                break;
            case "Melon Float":
                step.add("Mix");
                step.add("Refridgerate");
                step.add("Decorate");
                break;
        }
        if (active) {UI.clearText();}
        UI.println("First step: " + step.get(0));
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
        actionActive = true;

        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("kitchenGUI/fridge_open_DHP.png", 180, 50, 470, 600);
        UI.sleep(2000);
        if (recipe.equals("Fruit Tart")) {
            UI.drawImage("recipeGUI/Fruit Tart_DHP.png", 230, 250, 80, 80);
        } else {
            UI.drawImage("kitchenGUI/" + recipe + "_decor1_DHP.png", 310, 280, 140, 140);
        }
        UI.sleep(2000);
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("kitchenGUI/fridge_closed_DHP.png", 185, 55, 470, 560);
        UI.sleep(2000);
        redrawAll();
        step.remove(0);
        checkRecipeCompletion();
        actionActive = false;
    }

    public void chop (String recipe) {
        actionActive = true;
        if (recipe.equals("Cinnamon Roll")) {
            for (int i = 0; i < 5; i++) {
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping1_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife1_DHP.png", 517, 233, 204, 130);
                UI.sleep(700);
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping1_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife2_DHP.png", 525, 183, 180, 180);
                UI.sleep(700);
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
            UI.drawImage("kitchenGUI/chopping2_DHP.png", 144, 250, 600, 265);
            UI.sleep(1500);
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        } else if (recipe.equals("Parfait") || recipe.equals("Fruit Tart")) {
            for (int i = 0; i < 5; i++) {
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping3_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife1_DHP.png", 517, 233, 204, 130);
                UI.sleep(700);
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping3_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife2_DHP.png", 525, 183, 180, 180);
                UI.sleep(700);
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
            UI.drawImage("kitchenGUI/chopping4_DHP.png", 144, 250, 600, 265);
            UI.sleep(1500);
            for (int i = 0; i < 5; i++) {
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping5_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife1_DHP.png", 517, 233, 204, 130);
                UI.sleep(700);
                UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                UI.drawImage("kitchenGUI/chopping5_DHP.png", 144, 250, 600, 265);
                UI.drawImage("kitchenGUI/chopping_knife2_DHP.png", 525, 183, 180, 180);
                UI.sleep(700);
            }
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
            UI.drawImage("kitchenGUI/chopping6_DHP.png", 144, 250, 600, 265);
            UI.sleep(1500);
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        }
        actionActive = false;
    }

    public void mix (String recipe) {
        actionActive = true;
        if (recipe.equals("Melon Float")) {
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j < 6; j++) {
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                    UI.drawImage("kitchenGUI/melon_mix" + j + "_DHP.png", 175, 220, 530, 251);
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
                    UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
                    UI.drawImage("kitchenGUI/mix" + j + "_DHP.png", 175, 220, 530, 251);
                    UI.sleep(600);
                }
            }
            UI.println("Done!");
            redrawAll();
            step.remove(0);
            checkRecipeCompletion();
        }
        actionActive = false;
    }

    public void oven (String recipe) {
        actionActive = true;
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("kitchenGUI/oven_empty_DHP.png", 170, 180, 505, 330);
        UI.sleep(2000);
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("kitchenGUI/oven_prebaked_DHP.png", 170, 180, 505, 330);
        UI.sleep(2000);
        for (int i = 1; i <= 3; i++) {
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
            UI.drawImage("kitchenGUI/oven_bake" + i + "_DHP.png", 170, 180, 505, 330);
            UI.sleep(1500);
        }
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("kitchenGUI/oven_baked_DHP.png", 170, 180, 505, 330);
        UI.sleep(3000);
        redrawAll();
        step.remove(0);
        checkRecipeCompletion();
        actionActive = false;
    }

    /** draws decorating process based on recipe! */
    public void decorate (String recipe) {
        actionActive = true;
        int frames = 0;
        switch (recipe) {
            case "Parfait" :
                frames = 3;
                break;
            case "Fruit Tart" :
                frames = 5;
                break;
            case "Cake" :
                frames = 2;
                break;
            case "Ube Cupcake" :
                frames = 1;
                break;
            case "Coffee Jelly" :
                frames = 2;
                break;
            case "Melon Float" :
                frames = 2;
                break;
        }
        for (int i = 1; i < frames+1; i++) {
            UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
            UI.drawImage("kitchenGUI/" + recipe + "_decor" + i + "_DHP.png", 260, 170, 350, 350);
            UI.sleep(800);
        }
        UI.drawImage("kitchenGUI/kitchenactionBG_DHP.png", 0, 0, 1080, 667);
        UI.drawImage("recipeGUI/" + recipe + "_DHP.png", 313, 163, 250, 320);
        UI.sleep(1000);
        step.remove(0);
        redrawAll();
        checkRecipeCompletion();
        actionActive = false;
    }

}

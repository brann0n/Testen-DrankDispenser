package com.testen.drankdispenser.glas;

import com.testen.drankdispenser.DrinkTypes;

public abstract class Glass {
    private String name; //sinas of cola ofzo
    private int size; //in milliliter (now also used for loading time)
    private DrinkTypes drink;
    private boolean glassDetected;
    private int filledPercentage;

    public Glass(String Name, int Size, DrinkTypes type) {
        size = Size;
        name = Name;
        drink = type;
        glassDetected = true;
    }

    public void glassDetected(boolean d) {
        glassDetected = d;
    }

    public int getFilledPercentage() {
        return filledPercentage;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public DrinkTypes getDrinkType() {
        return drink;
    }

    public void fillGlass() throws InterruptedException {
        System.out.println("Attempting to fill the glass...");
        while (glassDetected && filledPercentage < 100) {
            filledPercentage++;
            if (filledPercentage % 10 == 0) {
                //iedere 10 stappen
                String stars = "";
                int factor = filledPercentage / 10;
                for (int i = 0; i < factor; i++) {
                    stars += "*";
                }
                for (int i = 0; i < 10 - factor; i++) {
                    stars += " ";
                }
                System.out.print("\rProgress: [" + stars + "] Drink: " + drink.name());
            }
            Thread.sleep(size);
        }

        //check if the glass is filled completely
        if (filledPercentage == 100) {
            System.out.println("\n\rGlass has been filled with " + drink.name());
        } else {
            System.out.println("Glass was removed from dispenser before filled completely");
        }
    }
}

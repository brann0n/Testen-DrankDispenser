package com.testen.drankdispenser.glas;

import com.testen.drankdispenser.DrinkTypes;

public abstract class Glass {
    private String name; //sinas of cola ofzo
    private int size; //in milliliter
    private DrinkTypes drink;

    public Glass(String Name, int Size, DrinkTypes type){
        size = Size;
        name = Name;
        drink = type;
    }

    public String getName(){
        return name;
    }

    public int getSize(){
        return size;
    }

    public DrinkTypes getDrinkType(){
        return drink;
    }

    public void fillGlass(){

    }
}

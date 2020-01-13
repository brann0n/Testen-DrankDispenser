package com.testen.drankdispenser;

import com.testen.drankdispenser.glas.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Dispenser {

    private String name; //the name of the dispenser
    private HashMap<String, Glass> glasses;

    public static void main(String args[]) {
        System.out.println("Drankdispenser wordt gestart");
        Dispenser testen = new Dispenser("Testen dingetje");
        testen.addGlass(DrinkTypes.BEER);
        testen.addGlass(DrinkTypes.COLA);
        testen.printGlasses();


        try {
            testen.serveGlass(DrinkTypes.BEER);
            testen.serveGlass(DrinkTypes.WINE);
        } catch (GlassNotAcceptedException e) {
            e.printStackTrace();
        }

    }

    public Dispenser(String Name){
        name = Name;
        glasses = new HashMap<>();
    }

    public String getName(){
        return name;
    }

    public void addGlass(DrinkTypes type) {
        switch(type){
            case COLA:
                glasses.put("Cola", new ColaGlass());
                break;
            case FANTA:
                glasses.put("Sinas", new FantaGlass());
                break;
            case CASSIS:
                glasses.put("Cassis", new CassisGlass());
                break;
            case BEER:
                glasses.put("Beer", new BeerGlass());
                break;
            case WINE:
                glasses.put("Wine", new WineGlass());
                break;
            case COFFEE:
                glasses.put("Coffee", new CoffeeGlass());
                break;
        }
    }

    public void printGlasses(){
        System.out.println("Glazen die herkent worden door de dispenser:");
        for(Glass item: glasses.values()){
            System.out.println("    - " + item.getName() + " Inhoud: " + item.getSize() + "ml.");
        }
    }

    public void serveGlass(DrinkTypes glass) throws GlassNotAcceptedException {
        for (Glass item : glasses.values()) {
            if (item.getDrinkType() == glass) {
                item.fillGlass(); //the actual served glass is no object because we turn on the dispenser
                System.out.println("Glass was filled with " + item.getName());
                return;
            }
        }

        //if the code reaches here the provided glass is not allowed by the dispenser
        System.out.println("Provided glass is not allowed by the dispenser");
        throw new GlassNotAcceptedException(glass, "Provided glass is not allowed by the dispenser");
    }
}

package com.testen.drankdispenser;

import com.testen.drankdispenser.glas.*;
import com.testen.drankdispenser.reservoir.DrinkReservoir;
import com.testen.drankdispenser.reservoir.Reservoir;
import com.testen.drankdispenser.reservoir.WasteReservoir;

import java.util.HashMap;

public class Dispenser {

    private String name; //the name of the dispenser
    private HashMap<String, Glass> glasses;
    private HashMap<String, Reservoir> reservoirs;
    private WasteReservoir wasteReservoir;

    public static void main(String args[]) {
        System.out.println("Drankdispenser wordt gestart");
        Dispenser testen = new Dispenser("Testen dingetje");

        //set the allowed drinks (set these before filling reservoirs ;) )
        testen.addAllowedDrinkType(DrinkTypes.BEER);
        testen.addAllowedDrinkType(DrinkTypes.COLA);

        //perform maintenance
        testen.emptyWasteReservoir();
        testen.fillDrinkReservoirs();

        //show the available stuff to the customer
        testen.printGlasses();

        //pour some glasses
        try {
            testen.serveGlass(DrinkTypes.BEER);
            testen.serveGlass(DrinkTypes.COLA);
        } catch (GlassNotAcceptedException e) {
            System.out.println("Error: That glass is not supported");
        }

    }

    public Dispenser(String Name) {
        name = Name;
        glasses = new HashMap<>();
        reservoirs = new HashMap<>();
        wasteReservoir = new WasteReservoir(1000);
    }

    public String getName() {
        return name;
    }

    public void addAllowedDrinkType(DrinkTypes type) {
        String typeName = type.name();
        int resSize = 1500;
        switch (type) {
            case COLA:
                glasses.put(typeName, new ColaGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
            case FANTA:
                glasses.put(typeName, new FantaGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
            case CASSIS:
                glasses.put(typeName, new CassisGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
            case BEER:
                glasses.put(typeName, new BeerGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
            case WINE:
                glasses.put(typeName, new WineGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
            case COFFEE:
                glasses.put(typeName, new CoffeeGlass());
                reservoirs.put(typeName, new DrinkReservoir(resSize));
                break;
        }
    }

    public void printGlasses() {
        System.out.println("Glazen die herkent worden door de dispenser:");
        for (Glass item : glasses.values()) {
            System.out.println("    - " + item.getName() + " Inhoud: " + item.getSize() + "ml.");
        }
    }

    public void serveGlass(DrinkTypes glass) throws GlassNotAcceptedException {
        for (Glass item : glasses.values()) {
            if (item.getDrinkType() == glass) {
                item.glassDetected(true); //you could set this to false half way through pouring except we don't do multithreading
                try {
                    item.fillGlass(); //the actual served glass is no object because we turn on the dispenser
                } catch (InterruptedException e) {
                    //let is slide
                }
                item.glassDetected(false);
                return;
            }
        }
        throw new GlassNotAcceptedException(glass, "Provided glass is not allowed by the dispenser");
    }

    public void emptyWasteReservoir() {
        wasteReservoir.setEmpty();
    }

    public void fillDrinkReservoirs() {
        //TODO: fill the reservoirs
    }
}

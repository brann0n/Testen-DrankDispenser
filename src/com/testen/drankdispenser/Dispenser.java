package com.testen.drankdispenser;

import com.testen.drankdispenser.glas.*;
import com.testen.drankdispenser.reservoir.DrinkReservoir;
import com.testen.drankdispenser.reservoir.DrinkReservoirEmptyException;
import com.testen.drankdispenser.reservoir.WasteReservoir;
import com.testen.drankdispenser.reservoir.WasteReservoirFullException;

import java.util.HashMap;
import java.util.Map;

public class Dispenser {

    private String name; //the name of the dispenser
    private HashMap<String, Glass> glasses;
    private HashMap<String, DrinkReservoir> reservoirs;
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

        if (testen.isMaintenanceRequired()) {
            testen.printMaintenanceStatus();
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
        int resSize = 5000;
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
                    item.fillGlass(reservoirs.get(glass.name()), wasteReservoir); //the actual served glass is no object because we turn on the dispenser
                } catch (InterruptedException e) {
                    //let is slide
                } catch (WasteReservoirFullException e2) {
                    //tell the user that the waste reservoir is full
                    System.out.println("Could not fill glass, the waste reservoir is full.");
                } catch (DrinkReservoirEmptyException e3) {
                    System.out.println("Could not fill glass, the " + glass.name() + " reservoir is empty.");
                }
                item.glassDetected(false);
                return;
            }
        }
        throw new GlassNotAcceptedException(glass, "Provided glass is not allowed by the dispenser.");
    }

    public void emptyWasteReservoir() {
        wasteReservoir.setEmpty();
    }

    public void fillDrinkReservoirs() {
        for (DrinkReservoir r : reservoirs.values()) {
            r.setFull(); //now all the reservoirs are going to be filled
        }
    }

    public boolean isMaintenanceRequired() {
        boolean required = false;

        if (wasteReservoir.isFull()) { //checks if its above the set margin (80%)
            required = true;
            System.out.println("Empty the waste reservoir");
        }

        for (Map.Entry<String, DrinkReservoir> r2 : reservoirs.entrySet()) {
            if (r2.getValue().isEmpty()) { //checks if its below the set margin (15%)
                required = true;
                System.out.println("Fill the " + r2.getKey() + " reservoir");
            }
        }

        return required;
    }

    public void printMaintenanceStatus() {
        System.out.println("Waste reservoir is filled for " + wasteReservoir.calcPercentageFilled() + "%");
        for (Map.Entry<String, DrinkReservoir> r2 : reservoirs.entrySet()) {
            System.out.println(r2.getKey() + " reservoir is filled for " + r2.getValue().calcPercentageFilled() + "%");
        }
    }
}

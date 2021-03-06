package com.testen.drankdispenser.reservoir;

public abstract class Reservoir {
    private int filled; //in milliliters
    private int size; //in milliliters

    public Reservoir(int Size) {
        filled = 0;
        size = Size;
    }

    public boolean isFull() {
        return calcPercentageFilled() > 80;
    }

    public boolean isEmpty() {
        return calcPercentageFilled() < 15;
    }

    public void setFull() {
        filled = size;
    }

    public void setEmpty() {
        filled = 0;
    }

    protected void setFilled(int milliliters) {
        if (milliliters < size) {
            filled = milliliters;
        } else {
            filled = size;
        }
    }

    public int getFilled() {
        return filled;
    }

    public int getSize() {
        return size;
    }

    public abstract void drain(int amount) throws DrinkReservoirEmptyException;

    public abstract void fill(int amount) throws WasteReservoirFullException;

    public double calcPercentageFilled() {
        return 100 * ((double) filled / size);
    }
}

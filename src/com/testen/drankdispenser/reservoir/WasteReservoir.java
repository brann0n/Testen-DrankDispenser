package com.testen.drankdispenser.reservoir;

public class WasteReservoir extends Reservoir {

    public WasteReservoir(int Size) {
        super(Size);
    }

    @Override
    public void drain(int amount) {
        if (getFilled() - amount >= 0) {
            setFilled(getFilled() - amount);
        } else {
            //just set it to 0
            setFilled(0);
        }
    }

    @Override
    public void fill(int amount) throws WasteReservoirFullException {
        if (getFilled() + amount < getSize()) {
            setFilled(getFilled() + amount);
        } else {
            //if the waste reservoir is filled to the max throw an exception
            throw new WasteReservoirFullException();
        }
    }
}

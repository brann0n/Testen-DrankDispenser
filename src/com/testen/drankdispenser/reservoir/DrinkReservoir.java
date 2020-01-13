package com.testen.drankdispenser.reservoir;

public class DrinkReservoir extends Reservoir {

    public DrinkReservoir(int Size) {
        super(Size);
    }

    @Override
    public void drain(int amount) throws DrinkReservoirEmptyException {
        if (getFilled() - amount >= 0) {
            setFilled(getFilled() - amount);
        } else {
            //cannot fill the glass completely, throw a fucking exception
            throw new DrinkReservoirEmptyException();
        }
    }

    @Override
    public void fill(int amount) {
        //fill the reservoir to the brim, don't care if its spilled (this is a user error, don't be stupid :))
        if (getFilled() + amount < getSize()) {
            setFilled(getFilled() + amount);
        } else {
            setFilled(getSize());
        }
    }
}

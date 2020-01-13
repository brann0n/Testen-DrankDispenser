package com.testen.drankdispenser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DispenserTest {

    Dispenser testen;

    @BeforeEach
    void setUp() {
        testen = new Dispenser("Testen dingetje");
    }

    @AfterEach
    void tearDown() {
        testen = null;
    }

    @Test
    void addGlass() {
        testen.addGlass(DrinkTypes.BEER);
    }

    @Test
    void testServeNonExistentGlass() {
        //should throw exception
        Assertions.assertThrows(GlassNotAcceptedException.class, () -> {
            testen.serveGlass(DrinkTypes.COLA);
        });
    }

    @Test
    void testServeExistentGlass() {
        testen.addGlass(DrinkTypes.BEER);
        Assertions.assertDoesNotThrow(() -> {
            testen.serveGlass(DrinkTypes.BEER);
        });
    }
}
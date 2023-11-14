package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

class TestDeathNote {

    private static final int NUMBER_OF_RULES=13;
    private DeathNote dNote;
    private String testName = "Hanzo Hasashi";
    private String testName2 = "Jonathan Joestar";

    @BeforeEach
    public void obtainBook(){
        this.dNote = new DeathNoteImpl();
    }

    @Test 
    public void testRules(){
        try {
            this.dNote.getRule(0);
            Assertions.fail("There is no 0 rule");
        } catch (IllegalArgumentException e) {
            assertEquals("Rules go from 1 to 13", e.getMessage());
        }

        try {
            this.dNote.getRule(-1);
            Assertions.fail("There are no negative rules");
        } catch (IllegalArgumentException e) {
            assertEquals("Rules go from 1 to 13", e.getMessage());
        }

        //assertFalse(this.dNote.getRule(1).isBlank());
    }

    @Test 
    public void testEmptyRules(){
        int i;
        for (i=1; i<=NUMBER_OF_RULES && !this.dNote.getRule(i).isBlank(); i++){
        }
        if (i<NUMBER_OF_RULES){
            Assertions.fail("There should be no blank rules!");
        }
    }

    @Test
    public void testWriteName(){
        assertFalse(this.dNote.isNameWritten(testName));

        try {
            this.dNote.writeName(testName);
        } catch (NullPointerException e) {
            Assertions.fail("A name is indeed used here!");
        }

        assertTrue(this.dNote.isNameWritten(testName));

        assertFalse(this.dNote.isNameWritten(testName2));

        assertFalse(this.dNote.isNameWritten(""));
    }
}
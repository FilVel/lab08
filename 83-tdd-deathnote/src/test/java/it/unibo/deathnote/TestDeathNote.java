package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

class TestDeathNote {

    private static final int NUMBER_OF_RULES=13;
    private DeathNote dNote;
    private static final String TEST_NAME = "Hanzo Hasashi";
    private static final String TEST_NAME_2 = "Jonathan Joestar";
    private static final int INVALID_DETAILS_TIME = 6000 + 100;

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
        assertFalse(this.dNote.isNameWritten(TEST_NAME));

        try {
            this.dNote.writeName(TEST_NAME);
        } catch (NullPointerException e) {
            Assertions.fail("A name is indeed used here!");
        }

        assertTrue(this.dNote.isNameWritten(TEST_NAME));

        assertFalse(this.dNote.isNameWritten(TEST_NAME_2));

        assertFalse(this.dNote.isNameWritten(""));
    }


    @Test
    void testDeathCause() throws InterruptedException {
        dNote.writeName(TEST_NAME);
        assertEquals("", dNote.getDeathCause(TEST_NAME));
        dNote.writeName(TEST_NAME_2);
        assertTrue(dNote.writeDeathCause("Vampire attack"));
        assertEquals("Vampire attack", dNote.getDeathCause(TEST_NAME_2));
        sleep(50);
        assertFalse(dNote.writeDeathCause("Spontaneous human combustion"));
        assertEquals("Vampire attack", dNote.getDeathCause(TEST_NAME_2));
    }

    @Test
    void testDeathDetails() throws InterruptedException {
        dNote.writeName(TEST_NAME);
        assertEquals("", dNote.getDeathDetails(TEST_NAME));
        assertTrue(dNote.writeDetails("Killed by Sub-Zero (Bi-Han)"));
        assertEquals("Killed by Sub-Zero (Bi-Han)", dNote.getDeathDetails(TEST_NAME));

        try {
            dNote.writeDetails("Killed by D'Vorah");
            Assertions.fail("Details have already been entered!");
        } catch (IllegalStateException e) {
            assertEquals("Details have already been entered!", e.getMessage());
        }

        dNote.writeName(TEST_NAME_2);
        sleep(INVALID_DETAILS_TIME);
        assertFalse(dNote.writeDetails("Killed by his own 'brother'"));
        assertEquals("", dNote.getDeathDetails(TEST_NAME_2));
    }
    
}
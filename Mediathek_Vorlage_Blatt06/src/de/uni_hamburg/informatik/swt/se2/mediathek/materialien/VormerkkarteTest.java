package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;


public class VormerkkarteTest 
{
    VormerkKarte TEST_Vormerkkarte;
    Kunde kunde1;
    Kunde kunde2;
    Kunde kunde3;
    Kunde kunde4;
    Medium TEST_medium;
    ArrayBlockingQueue<Kunde> TEST_vormerkerListe;
    
    public VormerkkarteTest()
    {        
        Kunde kunde1 = new Kunde(new Kundennummer(123451), "ich1", "du1");
        Kunde kunde2 = new Kunde(new Kundennummer(123452), "ich2", "du2");
        Kunde kunde3 = new Kunde(new Kundennummer(123453), "ich3", "du3");
        Kunde kunde4 = new Kunde(new Kundennummer(123454), "ich4", "du4");
        VormerkKarte TEST_Vormerkkarte = new VormerkKarte(TEST_medium, kunde1);
    }
    
    public void TESTEgetMedium()
    {
        assertEquals(TEST_medium, TEST_Vormerkkarte.get_medium());
    }
    
    public void TESTEaddVormerker()
    {
        TEST_Vormerkkarte.addVormerker(kunde1);
        TEST_vormerkerListe = TEST_Vormerkkarte.get_vormerkerListe();
        assertEquals(kunde1,TEST_vormerkerListe.peek());
    }
    
    public void TESTEverleiheAnVormerker()
    {
        TEST_Vormerkkarte.verleiheAnVormerker();
        
    }
}

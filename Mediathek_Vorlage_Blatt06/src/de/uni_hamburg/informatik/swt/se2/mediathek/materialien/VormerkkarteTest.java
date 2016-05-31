package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken.VormerkException;
import static org.junit.Assert.assertEquals;
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
    
    //TODO fix tests !
    public VormerkkarteTest()
    {        
        Kunde kunde1 = new Kunde(new Kundennummer(123451), "ich1", "du1");
        //TODO unused
        Kunde kunde2 = new Kunde(new Kundennummer(123452), "ich2", "du2");
        Kunde kunde3 = new Kunde(new Kundennummer(123453), "ich3", "du3");
        Kunde kunde4 = new Kunde(new Kundennummer(123454), "ich4", "du4");
        VormerkKarte TEST_Vormerkkarte = new VormerkKarte(TEST_medium, kunde1);
    }
    
    @Test
    public void TESTEgetMedium()
    {
        assertEquals(TEST_medium, TEST_Vormerkkarte.get_medium());
    }
    
    @Test
    public void TESTEaddVormerker()
    {

        TEST_vormerkerListe = TEST_Vormerkkarte.get_vormerkerListe();
        assertEquals(kunde1,TEST_vormerkerListe.peek());
    }
    
    @Test
    public void TESTEverleiheAnVormerker()
    {
        try
        {
            TEST_Vormerkkarte.addVormerker(kunde2);
        }
        catch(VormerkException e)
        {
            //Testing
        }
        TEST_Vormerkkarte.verleiheAnVormerker();
        TEST_vormerkerListe = TEST_Vormerkkarte.get_vormerkerListe();
        assertEquals(kunde2,TEST_vormerkerListe.peek());               
    }
}

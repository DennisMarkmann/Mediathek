package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken.VormerkException;
import static org.junit.Assert.assertEquals;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;
import org.junit.Test;

public class VormerkkarteTest {

    private ArrayBlockingQueue<Kunde> vormerkerListe;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Kunde _kunde3;
    private Kunde _kunde4;
    private Medium _medium;
    private VormerkKarte _vormerkkarte;

    @Before
    public void generateTestDate() throws VormerkException {
        _kunde1 = new Kunde(new Kundennummer(123451), "ich1", "du1");
        _kunde2 = new Kunde(new Kundennummer(123452), "ich2", "du2");
        _kunde3 = new Kunde(new Kundennummer(123453), "ich3", "du3");
        _kunde4 = new Kunde(new Kundennummer(123454), "ich4", "du4");
        _vormerkkarte = new VormerkKarte(_medium, _kunde1);
    }

    @Test
    public void testGetMedium() {
        assertEquals(_medium, _vormerkkarte.get_medium());
    }

    @Test
    public void testAddVormerker() {

        vormerkerListe = _vormerkkarte.get_vormerkerListe();
        assertEquals(_kunde1, vormerkerListe.peek());
    }

    @Test
    public void testeVerleiheAnVormerker() {
        try {
            _vormerkkarte.addVormerker(_kunde2);
        } catch (VormerkException e) {
            // TODO Exception handling
        }
        _vormerkkarte.verleiheAnVormerker();
        vormerkerListe = _vormerkkarte.get_vormerkerListe();
        assertEquals(_kunde2, vormerkerListe.peek());
    }
}

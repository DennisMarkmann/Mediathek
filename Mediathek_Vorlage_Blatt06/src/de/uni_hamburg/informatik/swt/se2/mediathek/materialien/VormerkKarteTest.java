package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken.VormerkException;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;
import org.junit.Test;

public class VormerkKarteTest
{

    private ArrayBlockingQueue<Kunde> vormerkerListe;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Kunde _kunde3;
    private Medium _medium;
    private VormerkKarte _vormerkkarte;

    @Before
    public void generateTestDate() throws VormerkException
    {
        _kunde1 = new Kunde(new Kundennummer(123451), "ich1", "du1");
        _kunde2 = new Kunde(new Kundennummer(123452), "ich2", "du2");
        _kunde3 = new Kunde(new Kundennummer(123453), "ich3", "du3");
        _vormerkkarte = new VormerkKarte(_medium, _kunde1);
    }

    @Test
    public void testGetMedium()
    {
        assertEquals(_medium, _vormerkkarte.getMedium());
    }

    @Test
    public void testKonstruktor()
    {

        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde1, vormerkerListe.peek());
        assertEquals(_medium, _vormerkkarte.getMedium());
    }

    @Test
    public void testZweiterHinzugefügterKundeIstVorhanden()
            throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde1, vormerkerListe.poll());
        assertEquals(_kunde2, vormerkerListe.poll());
    }

    @Test
    public void testDritterHinzugefügterKundeIstVorhanden()
            throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.addVormerker(_kunde3);
        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde1, vormerkerListe.poll());
        assertEquals(_kunde2, vormerkerListe.poll());
        assertEquals(_kunde3, vormerkerListe.poll());
    }

    @Test
    public void testSelberKundeZweimalVormerkenNichtMöglich()
            throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        //TODO fix test
        _vormerkkarte.addVormerker(_kunde2);
        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde1, vormerkerListe.poll());
        assertEquals(_kunde2, vormerkerListe.poll());
        assertEquals(null, vormerkerListe.poll());
    }

    @Test
    public void testAddVormerker()
    {

        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde1, vormerkerListe.peek());
    }

    @Test
    public void testVerleiheAnVormerker() throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.verleiheAnVormerker();
        vormerkerListe = _vormerkkarte.getVormerkerListe();
        assertEquals(_kunde2, vormerkerListe.peek());
    }

    @Test
    public void testGibAlleKunden() throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.addVormerker(_kunde3);

        assertEquals(_kunde1, _vormerkkarte.gibKundeFuerIndex(0));
        assertEquals(_kunde2, _vormerkkarte.gibKundeFuerIndex(1));
        assertEquals(_kunde3, _vormerkkarte.gibKundeFuerIndex(2));
    }

    @Test
    public void testIstListeEmpty() throws VormerkException
    {
        _vormerkkarte.verleiheAnVormerker();
        assertTrue(_vormerkkarte.istListeEmpty());
    }

    @Test
    public void testIstVorgemerktVonKunde()
    {
        assertTrue(_vormerkkarte.istVorgemerktVonKunde(_kunde1));
        assertFalse(_vormerkkarte.istVorgemerktVonKunde(_kunde2));
    }

    @Test
    public void testIstKomplettVorgemerkt() throws VormerkException
    {
        assertFalse(_vormerkkarte.istKomplettVorgemerkt());
        _vormerkkarte.addVormerker(_kunde2);
        assertFalse(_vormerkkarte.istKomplettVorgemerkt());
        _vormerkkarte.addVormerker(_kunde3);
        assertTrue(_vormerkkarte.istKomplettVorgemerkt());
    }

    @Test
    public void testGibKundeFuerIndex() throws VormerkException
    {
        _vormerkkarte.addVormerker(_kunde2);
        _vormerkkarte.addVormerker(_kunde3);
        assertEquals(_kunde1, _vormerkkarte.gibKundeFuerIndex(0));
        assertEquals(_kunde2, _vormerkkarte.gibKundeFuerIndex(1));
        assertEquals(_kunde3, _vormerkkarte.gibKundeFuerIndex(2));
    }

}

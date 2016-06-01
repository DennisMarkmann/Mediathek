package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken.VormerkException;

/**
 * Vormerkkarte als Material für Vormerkungen in der Mediathek
 *
 * @Author SE2 4er-Team
 * @Date 25/05/2016
 *
 */
public class VormerkKarte {

    private final Medium _medium;
    private ArrayBlockingQueue<Kunde> _vormerkerListe;

    /**
     *
     * Konstruktor für die Vormerkkarte Erstellen einer Queue mit der Kapazität von 3. Nur 3 Kunden können gleichzeitig
     * vormerken.
     *
     * @param kunde
     * @param das zur Vormerkkarte gehörige Medium.
     *
     */
    public VormerkKarte(Medium medium, Kunde kunde) {
        _medium = medium;
        _vormerkerListe = new ArrayBlockingQueue<Kunde>(3);
        try {
            this.addVormerker(kunde);
        } catch (VormerkException e) {
            System.out.println("Erstellen der Liste fehlgeschlagen");
        }
    }

    /**
     * Hinzufügen eines Vormerkers nach dem FIFO-Prinzip.
     *
     * @param
     * @throws VormerkException
     */
    public void addVormerker(Kunde vormerker) throws VormerkException {
        if (istVerliehenAnKunden(vormerker)) {
            return;
        }

        try {
            _vormerkerListe.put(vormerker);
        } catch (IllegalStateException | InterruptedException e) {
            System.out.println("ERROR");
            // TODO Exception handling
            VormerkException ex = new VormerkException("Vormerken fehlgeschlagen: Liste voll!");
            throw ex;

        }
    }

    public boolean equalsErsterVormerker(Kunde kunde) {
        return this.get_vormerkerListe().peek().equals(kunde);
    }

    public Medium get_medium() {
        return _medium;
    }

    public ArrayBlockingQueue<Kunde> get_vormerkerListe() {
        return _vormerkerListe;
    }

    public String getFormatiertenString() {
        return "Kunde: \"" + _vormerkerListe.peek() + "\" Medium: \"" + _medium + "\"";
    }

    public ArrayList<Kunde> gibAlleKunden() {
        ArrayList<Kunde> kunden = new ArrayList<Kunde>();
        Iterator<Kunde> iterator = _vormerkerListe.iterator();
        for (int i = 0; i < 3; i++) {
            if (iterator.hasNext()) {
                kunden.add(iterator.next());
            } else {
                kunden.add(null);
            }
        }
        return kunden;
    }

    // unused
    public Kunde gibKundeFuerIndex(int index) {
        Iterator<Kunde> iterator = _vormerkerListe.iterator();
        Kunde kunde = null;
        for (int i = 0; i < index; i++) {
            kunde = iterator.next();
        }
        return kunde;
    }

    public boolean istKomplettVorgemerkt() {
        return 3 <= this.get_vormerkerListe().size();
    }

    public boolean istListeEmpty() {
        return 0 == this.get_vormerkerListe().size();
    }

    public boolean istVerliehenAnKunden(Kunde kunde) {
        Iterator<Kunde> iterator = _vormerkerListe.iterator();
        for (int i = 0; i < 3; i++) {
            if (iterator.hasNext() && iterator.next().equals(kunde)) {
                return true;
            }
        }
        return false;
    }

    public void set_vormerkerListe(ArrayBlockingQueue<Kunde> _vormerkerListe) {
        this._vormerkerListe = _vormerkerListe;
    }

    /**
     * Gibt und entfernt den Vormerker an Index:1.
     */
    public void verleiheAnVormerker() {
        _vormerkerListe.poll();
    }
}

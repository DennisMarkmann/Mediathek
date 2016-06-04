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

    /**
     * Das Medium zu dem die Vormerkungen gespeichert werden sollen.
     */
    private final Medium _medium;
    
    /**
     * Queue in der maximal drei Vormerker gespeichert werden können.
     */
    private ArrayBlockingQueue<Kunde> _vormerkerListe;

    /**
     *
     * Konstruktor für die Vormerkkarte Erstellen einer Queue mit der Kapazität von 3. Nur 3 Kunden können gleichzeitig
     * vormerken.
     *
     * @param kunde für den die erste Vormerkung durchgeführt werden soll.
     * @param medium zu dem die Vormerkungen gespeichert werden sollen.
     * @throws VormerkException Exception die geworfen wird, sollte beim Hinzufuegen eines Vormerkers etwas schief gehen.
     *
     */
    public VormerkKarte(Medium medium, Kunde kunde) throws VormerkException {
        _medium = medium;
        _vormerkerListe = new ArrayBlockingQueue<Kunde>(3);
        this.addVormerker(kunde);
    }

    /**
     * Hinzufügen eines Vormerkers nach dem FIFO-Prinzip.
     *
     * @param vormerker der in die Liste der vormerker hinzugefuegt werden soll.
     * @throws VormerkException Exception die geworfen wird, sollte beim Hinzufuegen eines Vormerkers etwas schief gehen.
     */
    public void addVormerker(Kunde vormerker) throws VormerkException {
        if (istVerliehenAnKunden(vormerker) || _vormerkerListe.size() >= 3) {
            return;
        }

        try {
            _vormerkerListe.put(vormerker);
        } catch (IllegalStateException | InterruptedException e) {
            throw new VormerkException("Vormerken fehlgeschlagen");
        }
    }

    /**
     * Pruefung ob erster Eintrag der Liste dem angegebenen
     * 
     * @param vormerker fuer den der erste Wert der Liste geprueft werden soll.
     * @return boolean: true falls Vormerker equals erster Eintrag der Liste, sonst false.
     */
    
    public boolean equalsErsterVormerker(Kunde vormerker) {
        return this.get_vormerkerListe().peek().equals(vormerker);
    }

    /**
     * 
     * @return mediun zu dem die Vormerkungen gespeichert werden sollen.
     */
    Medium get_medium() {
        return _medium;
    }

    /**
     * 
     * @return
     */
    
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

    /**
     * Gibt und entfernt den Vormerker an Index:1.
     */
    public void verleiheAnVormerker() {
        _vormerkerListe.poll();
    }
}

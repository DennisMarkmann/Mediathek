package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.concurrent.ArrayBlockingQueue;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * Vormerkkarte als Material für Vormerkungen in der Mediathek
 * @Author SE2 4er-Team
 * @Date 25/05/2016
 * 
 */
public class Vormerkkarte
{

    private final Medium _medium;
    private ArrayBlockingQueue<Kunde> _vormerkerListe;

    /**
     * 
     * Konstruktor für die Vormerkkarte
     * Erstellen einer Queue mit der Kapazität von 3.
     * Nur 3 Kunden können gleichzeitig vormerken.
     * @param das zur Vormerkkarte gehörige Medium.
     * 
     */
    public Vormerkkarte(Medium medium)
    {
        _medium = medium;
        _vormerkerListe = new ArrayBlockingQueue<Kunde>(3);
    }

    /**
     * Hinzufügen eines Vormerkers nach dem FIFO-Prinzip.
     * @param 
     */
    public void addVormerker(Kunde vormerker)
    {
        try
        {
            _vormerkerListe.put(vormerker);
        }
        catch (IllegalStateException | InterruptedException e)
        {
            System.out.println("ERROR");
            //TODO Error handling.
        }
    }

    public Medium get_medium()
    {
        return _medium;
    }

    public ArrayBlockingQueue<Kunde> get_vormerkerListe()
    {
        return _vormerkerListe;
    }

    public void set_vormerkerListe(ArrayBlockingQueue<Kunde> _vormerkerListe)
    {
        this._vormerkerListe = _vormerkerListe;
        //WTF?
    }

    /**
     * Gibt und entfernt den Vormerker an Index:1.
     */
    public void verleiheAnVormerker()
    {
        _vormerkerListe.poll();
    }

}

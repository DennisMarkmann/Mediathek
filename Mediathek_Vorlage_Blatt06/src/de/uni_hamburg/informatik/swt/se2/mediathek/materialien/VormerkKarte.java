package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

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
public class VormerkKarte
{

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
     * Konstruktor für die Vormerkkarte Erstellen einer Queue mit der Kapazität
     * von 3. Nur 3 Kunden können gleichzeitig vormerken.
     *
     * @param kunde
     *            für den die erste Vormerkung durchgeführt werden soll.
     * @param medium
     *            zu dem die Vormerkungen gespeichert werden sollen.
     * @throws VormerkException
     *             Exception die geworfen wird, sollte beim Hinzufuegen eines
     *             Vormerkers etwas schief gehen.
     *
     * @ensure kunde != null
     * @ensure medium != null
     */
    public VormerkKarte(Medium medium, Kunde kunde) throws VormerkException
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        assert medium != null : "Vorbedingung verletzt: medium != null";

        _medium = medium;
        _vormerkerListe = new ArrayBlockingQueue<Kunde>(3);
        this.addVormerker(kunde);
    }

    /**
     * Hinzufügen eines Vormerkers nach dem FIFO-Prinzip.
     *
     * @param kunde
     *            der in die Liste der vormerker hinzugefuegt werden soll.
     * @throws VormerkException
     *             Exception die geworfen wird, sollte beim Hinzufuegen eines
     *             Vormerkers etwas schief gehen.
     * @ensure !istVorgemerktVonKunde(vormerker)
     * @ensure _vormerkerListe.size() <= 2
     * @ensure kunde != null
     *
     */
    public void addVormerker(Kunde kunde) throws VormerkException
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        assert!istVorgemerktVonKunde(
                kunde) : "Vorbedingung verletzt: !istVorgemerktVonKunde(vormerker)";
        assert _vormerkerListe
            .size() <= 2 : "Vorbedingung verletzt: _vormerkerListe.size() <= 2";

        try
        {
            _vormerkerListe.put(kunde);
        }
        catch (IllegalStateException | InterruptedException e)
        {
            throw new VormerkException("Vormerken fehlgeschlagen");
        }
    }

    /**
     * Pruefung ob erster Eintrag der Liste dem angegebenen
     *
     * @param vormerker
     *            fuer den der erste Wert der Liste geprueft werden soll.
     * @return boolean: true falls Vormerker equals erster Eintrag der Liste,
     *         sonst false.
     * @ensure kunde != null
     */

    public boolean equalsErsterVormerker(Kunde kunde)
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";

        return this.getVormerkerListe()
            .peek()
            .equals(kunde);
    }

    /**
     * Gibt für das Protokoll einen String zurück, der aus dem ersten Kunden und dem Medium besteht.
     *
     * @return String, der aus dem ersten Kunden und dem Medium besteht.
     */

    public String getFormatiertenString()
    {
        return "Kunde: \"" + _vormerkerListe.peek() + "\" Medium: \"" + _medium
                + "\"";
    }

    /**
     * Gibt das medium zurück, zu dem diese Vormerkung gehört.
     *
     * @return mediun zu dem die Vormerkungen gespeichert werden sollen.
     */
    Medium getMedium()
    {
        Medium medium = _medium;
        return medium;
    }

    /**
     * Gibt die Vormerkliste zurück.
     *
     * @return vormerkliste in der die Vormerkungen stehen.
     *
     * @ensure _vormerkerListe != null
     **/

    public ArrayBlockingQueue<Kunde> getVormerkerListe()
    {
        assert _vormerkerListe != null : "Vorbedingung verletzt: _vormerkerListe != null";
        ArrayBlockingQueue<Kunde> vormerkerListe = new ArrayBlockingQueue<Kunde>(
                3, true, _vormerkerListe);
        return vormerkerListe;
    }

    /**
     * Gibt den Kunden zurück, der sich an dem angegebenen Index befindet.
     * @param index des Kunden der returned werden soll.
     *
     * @return Kunde an dem angegebenen Index.
     *
     * @ensure index <= 2
     */

    public Kunde gibKundeFuerIndex(int index)
    {
        assert index <= 2 : "Vorbedingung verletzt: index <= 2";
        Iterator<Kunde> iterator = _vormerkerListe.iterator();
        Kunde kunde = null;
        for (int i = 0; i <= index; i++)
        {
            if (iterator.hasNext())
            {
                kunde = iterator.next();
            }
            else
            {
                kunde = null;
            }
        }
        return kunde;
    }

    /**
     * Prüft ob die Liste 3 Einträge hat / voll ist.
     *
     * @return boolean ob die Liste voll ist oder nicht.
     */

    public boolean istKomplettVorgemerkt()
    {
        return 3 <= this.getVormerkerListe()
            .size();
    }

    /**
     * Prüft ob die Liste leer ist.
     *
     * @return boolean ob die Liste leer ist oder nicht.
     */

    public boolean istListeEmpty()
    {
        return 0 == this.getVormerkerListe()
            .size();
    }

    /**
     * Prüft ob der angegebene Kunde sich bereits in der Liste befindet.
     *
     * @param kunde auf den geprüft wird.
     * @return boolean ob der Kunde sich in der Liste befindet oder nicht.
     *
     * @ensure kunde != null
     * @ensure _vormerkerListe != null
     */

    public boolean istVorgemerktVonKunde(Kunde kunde)
    {
        assert kunde != null : "Vorbedingung verletzt: kunde != null";
        assert _vormerkerListe != null : "Vorbedingung verletzt: _vormerkerListe != null";

        Iterator<Kunde> iterator = _vormerkerListe.iterator();
        for (int i = 0; i < 3; i++)
        {
            if (iterator.hasNext() && iterator.next()
                .equals(kunde))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Entfernt den Vormerker an Index:1.
     */
    public void verleiheAnVormerker()
    {
        _vormerkerListe.poll();
    }
}

package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.VormerkKarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken.VormerkException;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger Kommentar.
 *
 * @author SE2-Team
 * @version SoSe 2016
 */
public class VerleihServiceImpl extends AbstractObservableService
        implements VerleihService
{

    /**
     * Diese Map speichert für jedes eingefügte Medium die dazugehörige Verleihkarte. Ein Zugriff auf die Verleihkarte ist
     * dadurch leicht über die Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
     */
    private Map<Medium, Verleihkarte> _verleihkarten;

    /**
     * Der Medienbestand.
     */
    private MedienbestandService _medienbestand;

    /**
     * Der Kundenstamm.
     */
    private KundenstammService _kundenstamm;

    /**
     * Der Protokollierer für die Verleihvorgänge.
     */
    private VerleihProtokollierer _protokollierer;

    /**
     * Diese Map speichert für jedes eingefügte Medium die dazugehörige Vormerkkarte. Ein Zugriff auf die Vormerkkarte ist
     * dadurch leicht über die Angabe des Mediums möglich. Beispiel: _vormerkKarten.get(medium)
     */
    private Map<Medium, VormerkKarte> _vormerkKarten = new HashMap<Medium, VormerkKarte>();

    /**
     * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
     *
     * @param kundenstamm Der KundenstammService.
     * @param medienbestand Der MedienbestandService.
     * @param initialBestand Der initiale Bestand.
     *
     * @require kundenstamm != null
     * @require medienbestand != null
     * @require initialBestand != null
     */
    public VerleihServiceImpl(KundenstammService kundenstamm,
            MedienbestandService medienbestand,
            List<Verleihkarte> initialBestand)
    {
        assert kundenstamm != null : "Vorbedingung verletzt: kundenstamm  != null";
        assert medienbestand != null : "Vorbedingung verletzt: medienbestand  != null";
        assert initialBestand != null : "Vorbedingung verletzt: initialBestand  != null";

        _verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
        _kundenstamm = kundenstamm;
        _medienbestand = medienbestand;
        _protokollierer = new VerleihProtokollierer();
    }

    /**
     *  Entfernt VormerkKarte.
     *
     * @param medium fuer das die Vormerkkarte entfernt werden soll.
     * @param kunde auf den geprueft wird ob er sich an Index 0 der Liste befindet.
     *
     * @throws ProtokollierException falls zum Beispiel die Datei gesperrt ist in die protokolliert werden soll.
     * 
     * @ensure kundeImBestand(kunde)
     * @ensure mediumImBestand(medium)
     */
    void entferneVormerkKarte(Medium medium, Kunde kunde)
            throws ProtokollierException
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: medienImBestand(medien)";

        VormerkKarte vormerkKarte = this.getVormerkKarteFuer(medium);
        if (vormerkKarte != null && vormerkKarte.gibKundeFuerIndex(0)
            .equals(kunde))
        {
            _protokollierer.protokolliere(
                    VerleihProtokollierer.VormerkEreignis.ENTFERNUNG,
                    vormerkKarte);
            vormerkKarte.verleiheAnVormerker();
            if (vormerkKarte.getVormerkerListe()
                .size() == 0)
            {
                _vormerkKarten.remove(vormerkKarte);
            }
        }
    }

    /**
     * Erzeugt eine neue HashMap aus dem Initialbestand.
     *
     * @param initialBestand der fuer die Verleihkarten verwendet werden soll.
     * @return HashMap<Medium, Verleihkarte> verleihkarten fuer die Bestand erzeugt werden soll.
     */
    private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(
            List<Verleihkarte> initialBestand)
    {
        HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
        for (Verleihkarte verleihkarte : initialBestand)
        {
            result.put(verleihkarte.getMedium(), verleihkarte);
        }
        return result;
    }

    /**
     * Liefert alle Medien, die von dem gegebenen Kunden ausgeliehen sind.
     * @param kunde
     * 
     * @ensure kunde != null
     * @ensure kundeImBestand(kunde) = true
     */
    @Override
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        List<Medium> result = new ArrayList<Medium>();
        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher()
                .equals(kunde))
            {
                result.add(verleihkarte.getMedium());
            }
        }
        return result;
    }

    @Override
    public Kunde getEntleiherFuer(Medium medium)
    {
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: medienImBestand(medien)";
        assert istVerliehen(
                medium) : "Vorbedingung verletzt: istVerliehen(medium)";
        Verleihkarte verleihkarte = _verleihkarten.get(medium);
        return verleihkarte.getEntleiher();
    }

    @Override
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: medienImBestand(medien)";
        return _verleihkarten.get(medium);
    }

    @Override
    public List<Verleihkarte> getVerleihkarten()
    {
        return new ArrayList<Verleihkarte>(_verleihkarten.values());
    }

    @Override
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        List<Verleihkarte> result = new ArrayList<Verleihkarte>();
        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher()
                .equals(kunde))
            {
                result.add(verleihkarte);
            }
        }
        return result;
    }

    /**
     * Gibt die VormerkKarte für das Medium.
     *
     * @param medium Medium fuer das eine Vormerkkarte gesucht werden soll.
     * @return VormerkKarte die zu dem Medium gefunden wurde.
     */

    @Override
    public VormerkKarte getVormerkKarteFuer(Medium medium)
    {
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: medienImBestand(medien)";
        return _vormerkKarten.get(medium);
    }

    @Override
    public boolean hatKundeMediumAusgeliehen(Kunde kunde, Medium medium)
    {
        List<Verleihkarte> verleihkarten = this.getVerleihkartenFuer(kunde);
        for (Verleihkarte verleihkarte : verleihkarten)
        {
            if (verleihkarte.getMedium()
                .equals(medium))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(
                medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for (Medium medium : medien)
        {
            VormerkKarte vormerkKarte = getVormerkKarteFuer(medium);
            if (vormerkKarte != null && !vormerkKarte.istListeEmpty()
                    && !vormerkKarte.equalsErsterVormerker(kunde))
            {
                return false;
            }
        }

        return sindAlleNichtVerliehen(medien);
    }

    @Override
    public boolean istVerliehen(Medium medium)
    {
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: mediumExistiert(medium)";
        return _verleihkarten.get(medium) != null;
    }

    @Override
    public boolean istVerliehenAn(Kunde kunde, Medium medium)
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(
                medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

        return istVerliehen(medium) && getEntleiherFuer(medium).equals(kunde);
    }

    @Override
    public boolean kundeImBestand(Kunde kunde)
    {
        return _kundenstamm.enthaeltKunden(kunde);
    }

    @Override
    public boolean medienImBestand(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: medien != null";
        assert !medien.isEmpty() : "Vorbedingung verletzt: !medien.isEmpty()";

        boolean result = true;
        for (Medium medium : medien)
        {
            if (!mediumImBestand(medium))
            {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean mediumImBestand(Medium medium)
    {
        return _medienbestand.enthaeltMedium(medium);
    }

    @Override
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
            throws ProtokollierException
    {
        assert sindAlleVerliehen(
                medien) : "Vorbedingung verletzt: sindAlleVerliehen(medien)";
        assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

        for (Medium medium : medien)
        {
            Verleihkarte verleihkarte = _verleihkarten.get(medium);
            _verleihkarten.remove(medium);
            _protokollierer.protokolliere(
                    VerleihProtokollierer.VerleihEreignis.RUECKGABE,
                    verleihkarte);
        }

        informiereUeberAenderung();
    }

    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {
        assert medienImBestand(
                medien) : "Vorbedingung verletzt: medienImBestand(medien)";
        boolean result = true;
        for (Medium medium : medien)
        {
            if (istVerliehen(medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean sindAlleVerliehen(List<Medium> medien)
    {
        assert medienImBestand(
                medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        boolean result = true;
        for (Medium medium : medien)
        {
            if (!istVerliehen(medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien)
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(
                medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        boolean result = true;
        for (Medium medium : medien)
        {
            if (!istVerliehenAn(kunde, medium))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
            throws ProtokollierException
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert sindAlleNichtVerliehen(
                medien) : "Vorbedingung verletzt: sindAlleNichtVerliehen(medien) ";
        assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
        assert istVerleihenMoeglich(kunde,
                medien) : "Vorbedingung verletzt:  istVerleihenMoeglich(kunde, medien)";

        try
        {
            for (Medium medium : medien)
            {
                Verleihkarte verleihkarte = new Verleihkarte(kunde, medium,
                        ausleihDatum);

                _verleihkarten.put(medium, verleihkarte);
                _protokollierer.protokolliere(
                        VerleihProtokollierer.VerleihEreignis.AUSLEIHE,
                        verleihkarte);
                entferneVormerkKarte(medium, kunde);
            }
        }
        catch (ProtokollierException pe)
        {
            throw pe;
        }
        finally
        {
            informiereUeberAenderung();
        }
    }

    /**
     * Setzt eine Vormerkung von einem Kunden an ein Medium.
     *
     * @param kunde für den die medien vorgemerkt werden sollen.
     * @param medien die für den Kunden vorgemerkt werden sollen.^
     * 
     * @ensure medienImBestand(medien)
     * @ensure kundeImBestand(kunde)
     */

    @Override
    public void vormerkenAn(Kunde kunde, List<Medium> medien)
            throws ProtokollierException, VormerkException
    {
        assert kundeImBestand(
                kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(
                medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for (Medium medium : medien)
        {
            VormerkKarte vormerkKarte = getVormerkKarteFuer(medium);
            try
            {
                if (vormerkKarte == null)
                {
                    vormerkKarte = new VormerkKarte(medium, kunde);
                    _vormerkKarten.put(medium, vormerkKarte);
                }
                else
                {
                    vormerkKarte.addVormerker(kunde);
                }
                _protokollierer.protokolliere(
                        VerleihProtokollierer.VormerkEreignis.VORMERKUNG,
                        vormerkKarte);
            }
            catch (VormerkException e)
            {
                throw e;
            }
            catch (ProtokollierException pe)
            {
                throw pe;
            }
            finally
            {
                informiereUeberAenderung();
            }
        }
    }
}

package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.VormerkKarte;

/**
 * Ein Verleihprotokollierer schreibt alle Verleihvorgänge in eine Datei.
 *
 * @author SE2-Team
 * @version SoSe 2016
 */
class VerleihProtokollierer {

    /************************************************
     * Alternative entsprechend Zusatzaufgabe 5.1.7 *
     ************************************************/

    public enum VerleihEreignis {
        AUSLEIHE,
        RUECKGABE;
    }

    public enum VormerkEreignis {
        VORMERKUNG,
        ENTFERNUNG;
    }

    /**
     * Pfad der Datei, in die das Verleihprotokoll geschrieben wird
     */
    private static final String DATEIPFAD = "./verleihProtokoll.txt";

    /**
     * Schreibt eine übergebene Verleihkarte ins Protokoll.
     *
     * @param ereignis Der Name des Verleihereignis: mögliche Namen sind durch die Konstanten EREIGNIS_AUSLEIHE und
     *            EREIGNIS_RUECKGABE definiert.
     * @param verleihkarte eine Verleihkarte, die das Verleihereignis betrifft.
     *
     * @require EREIGNIS_AUSLEIHE.equals(ereignis) || EREIGNIS_RUECKGABE.equals(ereignis)
     * @require verleihkarte != null
     *
     * @throws ProtokollierException wenn das Protokollieren nicht geklappt hat.
     */
    public void protokolliere(VerleihEreignis ereignis, Verleihkarte verleihkarte) throws ProtokollierException {
        assert VerleihEreignis.AUSLEIHE.equals(ereignis) || VerleihEreignis.RUECKGABE.equals(
                ereignis) : "Precondition failed: VerleihEreignis.AUSLEIHE.equals(ereignis) || VerleihEreignis.RUECKGABE.equals(ereignis)";
        assert verleihkarte != null : "Precondition failed: verleihkarte != null";

        String eintrag = Calendar.getInstance().getTime().toString() + ": " + ereignis + "\n"
                + verleihkarte.getFormatiertenString();

        try (FileWriter writer = new FileWriter(DATEIPFAD, true)) {
            writer.write(eintrag);
        } catch (IOException e) {
            throw new ProtokollierException("Beim Schreiben des Verleihprotokolls ist ein Fehler aufgetreten.");
        }
    }

    /**
     * Schreibt eine übergebene Vormerkkarte ins Protokoll.
     *
     * @param ereignis Der Name des Verleihereignis: mögliche Namen sind durch die Konstanten EREIGNIS_VORMERKUNG und
     *            EREIGNIS_ENTFERNUNG definiert.
     * @param vormerkKarte eine Vormerkkarte, die das Vormerkeignis betrifft.
     *
     * @require EREIGNIS_VORMERKUNG.equals(ereignis) || EREIGNIS_ENTFERNUNG.equals(ereignis)
     * @require vormerkKarte != null
     *
     * @throws ProtokollierException wenn das Protokollieren nicht geklappt hat.
     */
    void protokolliere(VormerkEreignis ereignis, VormerkKarte vormerkKarte) throws ProtokollierException {
        assert VormerkEreignis.VORMERKUNG.equals(ereignis) || VormerkEreignis.ENTFERNUNG.equals(
                ereignis) : "Precondition failed: VormerkEreignis.VORMERKUNG.equals(ereignis) || VormerkEreignis.ENTFERNUNG.equals(ereignis)";
        assert vormerkKarte != null : "Precondition failed: vormerkKarte != null";

        String eintrag = Calendar.getInstance().getTime().toString() + ": " + ereignis + "\n"
                + vormerkKarte.getFormatiertenString();

        try (FileWriter writer = new FileWriter(DATEIPFAD, true)) {
            writer.write(eintrag);
        } catch (IOException e) {
            throw new ProtokollierException("Beim Schreiben des Vormerkprotokolls ist ein Fehler aufgetreten.");
        }
    }
}

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
     * Textrepräsentation für das Ereignis Ausleihe
     */
    public static final String EREIGNIS_AUSLEIHE = "Ausleihe";

    /**
     * Textrepräsentation für das Ereignis Rückgabe
     */
    public static final String EREIGNIS_RUECKGABE = "Rückgabe";

    /**
     * Textrepräsentation für das Ereignis Vormerkung
     */
    public static final String EREIGNIS_VORMERKUNG = "Vormerkung";

    /**
     * Textrepräsentation für das Ereignis Entfernung
     */
    public static final String EREIGNIS_ENTFERNUNG = "Entfernung";

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
    public void protokolliere(String ereignis, Verleihkarte verleihkarte) throws ProtokollierException {
        assert EREIGNIS_AUSLEIHE.equals(ereignis) || EREIGNIS_RUECKGABE.equals(
                ereignis) : "Precondition failed: EREIGNIS_AUSLEIHE.equals(ereignis) || EREIGNIS_RUECKGABE.equals(ereignis)";
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
    void protokolliere(String ereignis, VormerkKarte vormerkKarte) throws ProtokollierException {
        assert EREIGNIS_VORMERKUNG.equals(ereignis) || EREIGNIS_ENTFERNUNG.equals(
                ereignis) : "Precondition failed: EREIGNIS_VORMERKUNG.equals(ereignis) || EREIGNIS_ENTFERNUNG.equals(ereignis)";
        assert vormerkKarte != null : "Precondition failed: vormerkKarte != null";

        String eintrag = Calendar.getInstance().getTime().toString() + ": " + ereignis + "\n"
                + vormerkKarte.getFormatiertenString();

        try (FileWriter writer = new FileWriter(DATEIPFAD, true)) {
            writer.write(eintrag);
        } catch (IOException e) {
            throw new ProtokollierException("Beim Schreiben des Vormerkprotokolls ist ein Fehler aufgetreten.");
        }
    }

    /**
     *
     *
     * Schreibt eine übergebene Verleihkarte ins Protokoll.
     *
     * @param ereignis Das Verleihereignis
     * @param verleihkarte eine Verleihkarte, die das Verleihereignis betrifft.
     *
     * @require ereignis != null
     * @require verleihkarte != null
     *
     * @throws ProtokollierException wenn das Protokollieren nicht geklappt hat.
     */
    public void protokolliere(VerleihEreignis ereignis, Verleihkarte verleihkarte) throws ProtokollierException {
        assert ereignis != null : "Precondition failed: ereignis != null";
        assert verleihkarte != null : "Precondition failed: verleihkarte != null";

        protokolliere(ereignis.name(), verleihkarte);
    }

    /**
     *
     *
     * Schreibt eine übergebene Vormerkkarte ins Protokoll.
     *
     * @param ereignis Das Vormerkereignis
     * @param vormerkKarte eine Vormerkkarte, die das Vormerkereignis betrifft.
     *
     * @require ereignis != null
     * @require vormerkKarte != null
     *
     * @throws ProtokollierException wenn das Protokollieren nicht geklappt hat.
     */
    public void protokolliere(VormerkEreignis ereignis, VormerkKarte vormerkKarte) throws ProtokollierException {
        assert ereignis != null : "Precondition failed: ereignis != null";
        assert vormerkKarte != null : "Precondition failed: vormerkKarte != null";

        protokolliere(ereignis.name(), vormerkKarte);
    }
}

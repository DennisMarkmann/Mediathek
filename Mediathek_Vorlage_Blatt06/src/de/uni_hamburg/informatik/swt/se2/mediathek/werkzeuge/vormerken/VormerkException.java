package de.uni_hamburg.informatik.swt.se2.mediathek.werkzeuge.vormerken;

import java.io.IOException;

/**
 * Eine VormerkException wird geworfen wenn das Vormerken
 * in die vormerkListe fehlgeschlagen ist.
 * @author 5hopfman
 *
 */
public class VormerkException extends IOException
{
    private static final long serialVersionUID = 1L;

    /**
     * Initialisiert eine neue DateiLeseException mit der übergebenen
     * Fehlermeldung.
     * 
     * @param message Eine beschreibung des Fehlers.
     * 
     */
    public VormerkException(String message)
    {
        super(message);
    }
}

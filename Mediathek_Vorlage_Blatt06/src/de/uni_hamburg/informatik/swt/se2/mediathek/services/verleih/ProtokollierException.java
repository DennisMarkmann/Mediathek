package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

/**
 * Eine ProtokollierException signalisiert, dass das Protokollieren eines
 * Verleihvorgangs fehlgeschlagen ist.
 * 
 * @author SE2-Team
 * @version SoSe 2016
 */
public class ProtokollierException extends Exception
{

    private static final long serialVersionUID = 1L;

    /**
     * Initilaisert eine neue Exception mit der übergebenen Fehlermeldung.
     * 
     * @param message Eine Fehlerbeschreibung.
     */
    ProtokollierException(String message)
    {
        super(message);
    }
}

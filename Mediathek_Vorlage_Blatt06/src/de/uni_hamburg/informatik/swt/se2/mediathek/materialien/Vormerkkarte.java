package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.ArrayList;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class Vormerkkarte
{

    private final Medium _medium;
    private ArrayList<Kunde> _vormerkerListe;

    public Vormerkkarte(Medium medium)
    {
        _medium = medium;
        _vormerkerListe = new ArrayList<Kunde>(3);
    }

    public void addVormerker(Kunde vormerker)
    {

    }

    public Medium get_medium()
    {
        return _medium;
    }

    public ArrayList<Kunde> get_vormerkerListe()
    {
        return _vormerkerListe;
    }

    public void set_vormerkerListe(ArrayList<Kunde> _vormerkerListe)
    {
        this._vormerkerListe = _vormerkerListe;
    }

    public void verleiheAnVormerker()
    {

    }

}

package de.uni_hamburg.informatik.swt.se2.kino.ui;

import java.util.HashSet;
import java.util.Set;

/**
 * Basisklasse für Submodule, die ihr Supermodul bei Änderungen
 * benachrichtigen möchten.
 * 
 * Diese Klasse implementiert die Schnittstelle, über die sich Beobachter an dem
 * Submodul registrieren können. In der Regel wird es genau ein beobachtendes
 * Supermodul geben. In Ausnahmen koennen es auch mehrere sein, wenn
 * indirekte Supermodule ebenfalls beobachten. Diese Klasse erlaubt beides.
 * 
 * Erbende Klassen rufen die Methode {@link #informiereUeberAenderung()} auf, um
 * alle Beobachter zu benachrichtigen. Erbende Klassen müssen dokumentieren, in
 * welchen Fällen sie ihre Beobachter informieren.
 * 
 * Diese Klasse entspricht der Klasse "Beobachtbar" im Beobachter-Muster.
 * 
 * @author SE2-Team
 * @version SoSe 2024
 */
public abstract class ObservableSubmodul
{
    private Set<SubmodulObserver> _alleBeobachter;

    /**
     * Initialisiert ein beobachtbares Submodul.
     */
    protected ObservableSubmodul()
    {
        _alleBeobachter = new HashSet<>();
    }

    /**
     * Registriert einen Beobachter an diesem Submodul. Der Beobachter wird
     * informiert, wenn sich bei diesem Modul etwas ändert.
     * 
     * @require beobachter != null
     */
    public void registriereBeobachter(SubmodulObserver beobachter)
    {
        assert beobachter != null : "Vorbedingung verletzt: beobachter != null";
        _alleBeobachter.add(beobachter);
    }

    /**
     * Entfernt einen Beobachter dieses Submoduls. Wenn der Beobachter gar
     * nicht registriert war, passiert nichts.
     */
    public void entferneBeobachter(SubmodulObserver beobachter)
    {
        _alleBeobachter.remove(beobachter);
    }

    /**
     * Informiert alle an diesem Submodul registrierten Beobachter über eine
     * Änderung.
     * 
     * Diese Methode muss von der erbenden Klasse immer dann aufgerufen werden,
     * wenn eine Änderung geschehen ist, die für Beobachter interessant ist.
     */
    protected void informiereUeberAenderung()
    {
        for (SubmodulObserver beobachter : _alleBeobachter)
        {
            beobachter.reagiereAufAenderung();
        }
    }
}

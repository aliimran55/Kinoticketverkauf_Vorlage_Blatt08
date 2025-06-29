package de.uni_hamburg.informatik.swt.se2.kino.ui;

/**
 * Interface für Supermodule, die Submodule beobachten möchten.
 * 
 * UI-Module, die dieses Interface implementieren, können sich an einem
 * Submodul, das von {@link ObservableSubmodul} erbt, als Beobachter
 * registrieren. Sie werden dann vom Submodul bei Änderungen benachrichtigt,
 * zum Beispiel bei der Auswahl einer Entität in einer Liste durch den
 * Benutzer.
 * 
 * Damit ein Supermodul mehrere Submodul beobachten und auf deren
 * Nachrichten unterschiedlich reagieren kann, bietet es sich an, dieses
 * Interface in inneren Klassen (inner classes) zu implementieren.
 * 
 * @author SE2-Team
 * @version SoSe 2024
 */
public interface SubmodulObserver
{
    /**
     * Reagiert auf eine Änderung in dem beobachteten Submodul.
     */
    void reagiereAufAenderung();
}

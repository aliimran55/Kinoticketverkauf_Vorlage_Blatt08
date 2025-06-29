package de.uni_hamburg.informatik.swt.se2.kino.ui.vorstellungsauswaehler;

import de.uni_hamburg.informatik.swt.se2.kino.model.entitaeten.Tagesplan;
import de.uni_hamburg.informatik.swt.se2.kino.model.entitaeten.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.ui.ObservableSubmodul;

import javax.swing.JPanel;
import java.util.List;

/**
 * Mit diesem UI-Modul kann der Benutzer oder die Benutzerin eine Vorstellung
 * aus einem Tagesplan auswählen.
 * 
 * Dieses UI-Modul ist ein eingebettetes Submodul. Es benachrichtigt seine
 * Beobachter, wenn sich die ausgewählte Vorstellung geändert hat.
 */
public class VorstellungsAuswaehlController extends ObservableSubmodul
{
    private VorstellungsAuswaehlView _view;

    /**
     * Initialisiert das UI-Modul.
     */
    public VorstellungsAuswaehlController()
    {
        _view = new VorstellungsAuswaehlView();
        registriereUIAktionen();
    }

    /**
     * Diese Methode wird aufgerufen, wenn eine Vorstellung ausgewaehlt wurde.
     */
    private void vorstellungWurdeAusgewaehlt()
    {
        informiereUeberAenderung();
    }

    /**
     * Gibt das Panel dieses Submoduls zurück. Das Panel sollte von einem
     * Supermoduls eingebettet werden.
     * 
     * @ensure result != null
     */
    public JPanel getUIPanel()
    {
        return _view.getUIPanel();
    }

    /**
     * Gibt die derzeit ausgewählte Vorstellung zurück.
     * 
     * @return die derzeitig ausgewählte Vorstellung, oder null, wenn keine
     *         Vorstellung ausgewählt ist.
     */
    public Vorstellung getAusgewaehlteVorstellung()
    {
        Vorstellung result = null;
        VorstellungsFormatierer adapter = _view.getVorstellungAuswahlList()
                .getSelectedValue();
        if (adapter != null)
        {
            result = adapter.getVorstellung();
        }

        return result;
    }

    /**
     * Setzt den Tagesplan, dessen Vorstellungen zur Auswahl angeboten werden.
     * 
     * @require tagesplan != null
     */
    public void setTagesplan(Tagesplan tagesplan)
    {
        assert tagesplan != null : "Vorbedingung verletzt: tagesplan != null";

        List<Vorstellung> vorstellungen = tagesplan.getVorstellungen();
        aktualisiereAngezeigteVorstellungsliste(vorstellungen);
    }

    /**
     * Aktualisiert die Liste der Vorstellungen.
     */
    private void aktualisiereAngezeigteVorstellungsliste(
            List<Vorstellung> vorstellungen)
    {
        VorstellungsFormatierer[] varray = new VorstellungsFormatierer[vorstellungen
                .size()];
        for (int i = 0; i < vorstellungen.size(); i++)
        {
            varray[i] = new VorstellungsFormatierer(vorstellungen.get(i));
        }
        _view.getVorstellungAuswahlList().setListData(varray);
        _view.getVorstellungAuswahlList().setSelectedIndex(0);
    }

    /**
     * 
     * Verbindet die fachlichen Aktionen mit den Interaktionselementen der
     * graphischen Benutzungsoberfläche.
     */
    private void registriereUIAktionen()
    {
        _view.getVorstellungAuswahlList().addListSelectionListener(
                event -> {
                    if (!event.getValueIsAdjusting())
                    {
                        vorstellungWurdeAusgewaehlt();
                    }
                });
    }
}

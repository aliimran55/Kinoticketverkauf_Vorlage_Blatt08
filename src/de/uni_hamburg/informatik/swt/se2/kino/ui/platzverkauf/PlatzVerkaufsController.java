package de.uni_hamburg.informatik.swt.se2.kino.ui.platzverkauf;

import javax.swing.JPanel;

import de.uni_hamburg.informatik.swt.se2.kino.model.entitaeten.Kinosaal;
import de.uni_hamburg.informatik.swt.se2.kino.model.entitaeten.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.model.wertobjekte.Platz;

import java.util.List;
import java.util.Set;

/**
 * Mit diesem UI-Modul können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Entität. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses UI-Modul ist ein eingebettetes Submodul.
 * 
 * @author SE2-Team
 * @version SoSe 2024
 */
public class PlatzVerkaufsController
{
    // Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
    private Vorstellung _vorstellung;

    private PlatzVerkaufsView _view;

    /**
     * Initialisiert das PlatzVerkaufsModul.
     */
    public PlatzVerkaufsController()
    {
        _view = new PlatzVerkaufsView();
        registriereUIAktionen();
        // Am Anfang wird keine Vorstellung angezeigt:
        setVorstellung(null);
    }

    /**
     * Gibt das Panel dieses Submoduls zurück. Das Panel sollte von einem
     * Supermodul eingebettet werden.
     * 
     * @ensure result != null
     * 
     * @return JPanel des Platzverkaufsmoduls
     */
    public JPanel getUIPanel()
    {
        return _view.getUIPanel();
    }

    /**
     * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
     */
    private void registriereUIAktionen()
    {
        _view.getVerkaufenButton().addActionListener(e -> fuehreBarzahlungDurch());

        _view.getStornierenButton().addActionListener(e -> stornierePlaetze(_vorstellung));

        _view.getPlatzplan().addPlatzSelectionListener(
                event -> reagiereAufNeuePlatzAuswahl(event
                        .getAusgewaehltePlaetze()));
    }

    /**
     * Startet die Barzahlung.
     */
    private void fuehreBarzahlungDurch()
    {
        verkaufePlaetze(_vorstellung);
    }

    /**
     * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert
     * hat.
     * 
     * @param plaetze die jetzt ausgewählten Plätze.
     */
    private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze)
    {
        _view.getVerkaufenButton().setEnabled(istVerkaufenMoeglich(plaetze));
        _view.getStornierenButton().setEnabled(istStornierenMoeglich(plaetze));
        aktualisierePreisanzeige(plaetze);
    }

    /**
     * Aktualisiert den anzuzeigenden Gesamtpreis
     */
    private void aktualisierePreisanzeige(Set<Platz> plaetze)
    {
        if (istVerkaufenMoeglich(plaetze))
        {
            int preis = _vorstellung.getPreisFuerPlaetze(plaetze);
            _view.getPreisLabel().setText(
                    "Gesamtpreis: " + preis + " Eurocent");
        }
        else if (istStornierenMoeglich(plaetze))
        {
            int preis = _vorstellung.getPreisFuerPlaetze(plaetze);
            _view.getPreisLabel().setText(
                    "Gesamtstorno: " + preis + " Eurocent");
        }
        else if (!plaetze.isEmpty())
        {
            _view.getPreisLabel().setText(
                    "Verkauf und Storno nicht gleichzeitig möglich!");
        }
        else
        {
            _view.getPreisLabel().setText(
                    "Gesamtpreis: 0 Eurocent");
        }
    }

    /**
     * Prüft, ob die angegebenen Plätze alle storniert werden können.
     * 
     * @return true, wenn der Platz sowohl leer als auch die Vorstellung stornierbar ist
     */
    private boolean istStornierenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
    }

    /**
     * Prüft, ob die angegebenen Plätze alle verkauft werden können.
     * 
     * @return true, wenn der Platz sowohl leer als auch die Vorstellung zum Verkauf steht
     */
    private boolean istVerkaufenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
    }

    /**
     * Setzt die Vorstellung. Sie ist die Entität dieses UI-Moduls. Wenn die
     * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
     * Vorstellung darf auch null sein.
     */
    public void setVorstellung(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        aktualisierePlatzplan();
    }

    /**
     * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
     */
    private void aktualisierePlatzplan()
    {
        if (_vorstellung != null)
        {
            Kinosaal saal = _vorstellung.getKinosaal();
            initialisierePlatzplan(saal.getAnzahlReihen(),
                    saal.getAnzahlSitzeProReihe());
            markiereNichtVerkaufbarePlaetze(saal.getPlaetze());
        }
        else
        {
            initialisierePlatzplan(0, 0);
        }
    }

    /**
     * Setzt am Platzplan die Anzahl der Reihen und der Sitze.
     * 
     * @param saal Ein Saal mit dem der Platzplan initialisiert wird.
     */
    private void initialisierePlatzplan(int reihen, int sitzeProReihe)
    {
        _view.getPlatzplan().setAnzahlPlaetze(reihen, sitzeProReihe);
    }

    /**
     * Markiert alle nicht verkaufbaren Plätze im Platzplan als verkauft.
     * 
     * @param plaetze Eine Liste mit allen Plaetzen im Saal.
     */
    private void markiereNichtVerkaufbarePlaetze(List<Platz> plaetze)
    {
        for (Platz platz : plaetze)
        {
            if (!_vorstellung.istVerkaufbar(platz))
            {
                _view.getPlatzplan().markierePlatzAlsVerkauft(platz);
            }
        }
    }

    /**
     * Verkauft die ausgewählten Plaetze.
     */
    private void verkaufePlaetze(Vorstellung vorstellung)
    {
        Set<Platz> plaetze = _view.getPlatzplan().getAusgewaehltePlaetze();
        vorstellung.verkaufePlaetze(plaetze);
        aktualisierePlatzplan();
    }

    /**
     * Storniert die ausgewählten Plaetze.
     */
    private void stornierePlaetze(Vorstellung vorstellung)
    {
        Set<Platz> plaetze = _view.getPlatzplan().getAusgewaehltePlaetze();
        vorstellung.stornierePlaetze(plaetze);
        aktualisierePlatzplan();
    }
}

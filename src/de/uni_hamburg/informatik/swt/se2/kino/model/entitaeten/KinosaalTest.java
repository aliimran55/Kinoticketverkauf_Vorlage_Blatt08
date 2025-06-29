package de.uni_hamburg.informatik.swt.se2.kino.model.entitaeten;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.kino.model.wertobjekte.Platz;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class KinosaalTest
{
    @Test
    public void testeKonstruktoren()
    {
        Kinosaal k = new Kinosaal("Name", 90, 16);
        assertEquals("Name", k.getName());
        assertEquals(90, k.getAnzahlReihen());
        assertEquals(16, k.getAnzahlSitzeProReihe());
        assertNotNull(k.toString());
    }

    @Test
    public void testeIstPlatzVorhanden()
    {
        Kinosaal k = new Kinosaal("Name", 90, 16);
        assertTrue(k.hatPlatz(Platz.get(80, 8)));
        assertFalse(k.hatPlatz(Platz.get(100, 4)));
    }

    @Test
    public void testeGibPlaetze()
    {
        Kinosaal k = new Kinosaal("Name", 3, 4);
        List<Platz> plaetze = k.getPlaetze();
        assertEquals(12, plaetze.size());
        assertTrue(plaetze.contains(Platz.get(0, 0)));
        assertTrue(plaetze.contains(Platz.get(0, 3)));
        assertTrue(plaetze.contains(Platz.get(2, 0)));
        assertTrue(plaetze.contains(Platz.get(2, 3)));
    }

    @Test
    public void testeEqualsUndHashCode()
    {
        Kinosaal k1 = new Kinosaal("Name", 90, 16);
        Kinosaal k2 = new Kinosaal("Name", 90, 16);
        assertEquals(k2, k1);
        assertEquals(k1.hashCode(), k2.hashCode());
    }
}

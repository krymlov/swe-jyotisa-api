/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.lagna;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Structural sweep of the 9 special-lagna leaves (fid 0..8). Also pins a real gap: only 4 of
 * the 9 ({@code janma}/{@code bhava}/{@code hora}/{@code ghati}) have a dedicated accessor on
 * {@link ILagnas} - {@code indu}/{@code sree}/{@code vighati}/{@code varnada}/{@code pranapada}
 * are commented out there, reachable only through {@code all()}. See CLAUDE.md.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class LagnaTest {

    static abstract class LagnaStub implements ILagna {
        public ILagna[] all() { return new ILagna[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Janma extends LagnaStub implements ILagnaJanma {}
    static class Bhava extends LagnaStub implements ILagnaBhava {}
    static class Hora extends LagnaStub implements ILagnaHora {}
    static class Ghati extends LagnaStub implements ILagnaGhati {}
    static class Vighati extends LagnaStub implements ILagnaVighati {}
    static class Varnada extends LagnaStub implements ILagnaVarnada {}
    static class Sree extends LagnaStub implements ILagnaSree {}
    static class Pranapada extends LagnaStub implements ILagnaPranapada {}
    static class Indu extends LagnaStub implements ILagnaIndu {}

    static List<ILagna> allNine() {
        return Arrays.asList(new Janma(), new Bhava(), new Hora(), new Ghati(), new Vighati(),
                new Varnada(), new Sree(), new Pranapada(), new Indu());
    }

    @Test
    void nineLeaves_haveUniqueFidsZeroThroughEight() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (ILagna l : allNine()) fids.add(l.fid());
        assertEquals(9, fids.size());
        assertEquals(0, fids.first().intValue());
        assertEquals(8, fids.last().intValue());
    }

    @Test
    void nineLeaves_codeIsLPrefixPlusFid() {
        for (ILagna l : allNine()) assertEquals("L" + l.fid(), l.code());
    }

    @Test
    void nineLeaves_lengthIsZero() {
        for (ILagna l : allNine()) assertEquals(0., l.length(), 1e-9);
    }

    @Test
    void janmaLagnaHasFidZero() {
        // the main ascendant, fid 0 - the reserved NIL_FID value repurposed as a real leaf
        // here (harmless, since ILagna's own segment()/length() default to 0 regardless)
        assertEquals(0, new Janma().fid());
        assertEquals("L0", new Janma().code());
    }
}

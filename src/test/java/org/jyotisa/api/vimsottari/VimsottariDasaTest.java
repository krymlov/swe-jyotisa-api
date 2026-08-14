/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.vimsottari;

import org.jyotisa.api.graha.IGraha;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The 9-leaf {@code IVimsottariDasa} year table - the classical Vimshottari cycle totals
 * exactly 120 years, a hard constraint worth pinning directly rather than trusting the nine
 * individual literals never drift.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class VimsottariDasaTest {

    static abstract class VimsottariStub implements IVimsottariDasa {
        public IVimsottariDasa[] all() { return new IVimsottariDasa[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
    }

    static class Surya extends VimsottariStub implements IVimsottariDasaSurya {}
    static class Chandra extends VimsottariStub implements IVimsottariDasaChandra {}
    static class Mangala extends VimsottariStub implements IVimsottariDasaMangala {}
    static class Rahu extends VimsottariStub implements IVimsottariDasaRahu {}
    static class Guru extends VimsottariStub implements IVimsottariDasaGuru {}
    static class Shani extends VimsottariStub implements IVimsottariDasaShani {}
    static class Budha extends VimsottariStub implements IVimsottariDasaBudha {}
    static class Ketu extends VimsottariStub implements IVimsottariDasaKetu {}
    static class Shukra extends VimsottariStub implements IVimsottariDasaShukra {}

    static List<IVimsottariDasa> allNine() {
        return Arrays.asList(new Surya(), new Chandra(), new Mangala(), new Rahu(), new Guru(),
                new Shani(), new Budha(), new Ketu(), new Shukra());
    }

    @Test
    void yearsPerGraha_matchTheDocumentedClassicalValues() {
        assertEquals(6., new Surya().length(), 1e-9);
        assertEquals(10., new Chandra().length(), 1e-9);
        assertEquals(7., new Mangala().length(), 1e-9);
        assertEquals(18., new Rahu().length(), 1e-9);
        assertEquals(16., new Guru().length(), 1e-9);
        assertEquals(19., new Shani().length(), 1e-9);
        assertEquals(17., new Budha().length(), 1e-9);
        assertEquals(7., new Ketu().length(), 1e-9);
        assertEquals(20., new Shukra().length(), 1e-9);
    }

    @Test
    void totalCycleIsExactlyOneHundredTwentyYears() {
        double total = 0;
        for (IVimsottariDasa d : allNine()) total += d.length();
        assertEquals(120., total, 1e-9);
    }

    @Test
    void nineLeaves_haveUniqueFidsOneThroughNine() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IVimsottariDasa d : allNine()) fids.add(d.fid());
        assertEquals(9, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(9, fids.last().intValue());
    }

    @Test
    void nineLeaves_codeEndsWithVD() {
        for (IVimsottariDasa d : allNine()) {
            org.junit.jupiter.api.Assertions.assertTrue(d.code().endsWith("VD"), d.code());
        }
    }
}

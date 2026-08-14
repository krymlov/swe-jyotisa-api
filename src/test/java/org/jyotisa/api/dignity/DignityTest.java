/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.dignity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The 11-leaf {@code IDignity} strength scale - {@code length()} is an alias for
 * {@code power()} (a 0-100 strength score, not a degree span), and the sequence is
 * monotonically increasing but not evenly spaced.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class DignityTest {

    static abstract class DignityStub implements IDignity {
        public IDignity[] all() { return new IDignity[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Neecha extends DignityStub implements IDignityNeecha {}
    static class Deficient extends DignityStub implements IDignityDeficient {}
    static class Adhisatru extends DignityStub implements IDignityAdhisatru {}
    static class Satru extends DignityStub implements IDignitySatru {}
    static class Sama extends DignityStub implements IDignitySama {}
    static class Mitra extends DignityStub implements IDignityMitra {}
    static class Adhimitra extends DignityStub implements IDignityAdhimitra {}
    static class Swakshetra extends DignityStub implements IDignitySwakshetra {}
    static class Mulatrikona extends DignityStub implements IDignityMulatrikona {}
    static class Excellent extends DignityStub implements IDignityExcellent {}
    static class Uccha extends DignityStub implements IDignityUccha {}

    static List<IDignity> allEleven() {
        return Arrays.asList(new Neecha(), new Deficient(), new Adhisatru(), new Satru(), new Sama(),
                new Mitra(), new Adhimitra(), new Swakshetra(), new Mulatrikona(), new Excellent(), new Uccha());
    }

    @Test
    void powerScale_isExactlyTheDocumentedSequence() {
        int[] expected = {0, 1, 3, 6, 12, 25, 37, 50, 75, 95, 100};
        List<IDignity> all = allEleven();
        for (int i = 0; i < 11; i++) {
            assertEquals(expected[i], all.get(i).power(), all.get(i).getClass().getSimpleName());
        }
    }

    @Test
    void lengthAliasesPower() {
        for (IDignity d : allEleven()) assertEquals((double) d.power(), d.length(), 1e-9);
    }

    @Test
    void powerScale_isStrictlyMonotonicallyIncreasing() {
        List<IDignity> all = allEleven();
        for (int i = 1; i < all.size(); i++) {
            assertTrue(all.get(i).power() > all.get(i - 1).power(),
                    all.get(i).getClass().getSimpleName() + " must be strictly stronger than the previous");
        }
    }

    @Test
    void neechaIsTheWeakestAndUcchaIsTheStrongest() {
        assertEquals(0, new Neecha().power());
        assertEquals(100, new Uccha().power());
    }

    @Test
    void elevenLeaves_haveUniqueFidsOneThroughEleven() {
        java.util.TreeSet<Integer> fids = new java.util.TreeSet<>();
        for (IDignity d : allEleven()) fids.add(d.fid());
        assertEquals(11, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(11, fids.last().intValue());
    }

    @Test
    void elevenLeaves_codeIsDGPrefixPlusFid() {
        for (IDignity d : allEleven()) assertEquals("DG" + d.fid(), d.code());
    }
}

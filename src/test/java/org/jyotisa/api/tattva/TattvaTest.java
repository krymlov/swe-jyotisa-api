/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.tattva;

import org.jyotisa.api.graha.IGraha;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Structural sweep of the 5 classical Panchamahabhuta {@code ITattva} leaves.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class TattvaTest {

    static abstract class TattvaStub implements ITattva {
        public ITattva[] all() { return new ITattva[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
    }

    static class Akasha extends TattvaStub implements ITattvaAkasha {}
    static class Agni extends TattvaStub implements ITattvaAgni {}
    static class Prithvi extends TattvaStub implements ITattvaPrithvi {}
    static class Vayu extends TattvaStub implements ITattvaVayu {}
    static class Jala extends TattvaStub implements ITattvaJala {}

    static List<ITattva> allFive() {
        return Arrays.asList(new Akasha(), new Agni(), new Prithvi(), new Vayu(), new Jala());
    }

    @Test
    void fiveLeaves_haveUniqueFidsOneThroughFive() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (ITattva t : allFive()) fids.add(t.fid());
        assertEquals(5, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(5, fids.last().intValue());
    }

    @Test
    void fiveLeaves_codeIsTTPrefixPlusFid() {
        for (ITattva t : allFive()) assertEquals("TT" + t.fid(), t.code());
    }

    @Test
    void fiveLeaves_lengthIsZero() {
        for (ITattva t : allFive()) assertEquals(0., t.length(), 1e-9);
    }
}

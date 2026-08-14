/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.vaara;

import org.jyotisa.api.graha.IGraha;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Structural sweep of the 7 {@code IVaara} (weekday) leaves. {@code length()} is 24 (hours
 * in a day), not a degree span - the only family besides {@code IEkadasi} where the length
 * unit is not degrees.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class VaaraTest {

    static abstract class VaaraStub implements IVaara {
        public IVaara[] all() { return new IVaara[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
    }

    static class Surya extends VaaraStub implements IVaaraSurya {}
    static class Chandra extends VaaraStub implements IVaaraChandra {}
    static class Mangala extends VaaraStub implements IVaaraMangala {}
    static class Budha extends VaaraStub implements IVaaraBudha {}
    static class Guru extends VaaraStub implements IVaaraGuru {}
    static class Shukra extends VaaraStub implements IVaaraShukra {}
    static class Shani extends VaaraStub implements IVaaraShani {}

    static List<IVaara> allSeven() {
        return Arrays.asList(new Surya(), new Chandra(), new Mangala(), new Budha(), new Guru(),
                new Shukra(), new Shani());
    }

    @Test
    void sevenLeaves_haveUniqueFidsOneThroughSevenInWeekdayOrder() {
        // the traditional weekday order: Sun, Moon, Mars, Mercury, Jupiter, Venus, Saturn -
        // note this differs from the graha family's own fid ordering (where e.g. Guru=3)
        List<IVaara> all = allSeven();
        for (int i = 0; i < 7; i++) {
            assertEquals(i + 1, all.get(i).fid(), all.get(i).getClass().getSimpleName());
        }
    }

    @Test
    void sevenLeaves_haveUniqueVRSuffixedCodes() {
        TreeSet<String> codes = new TreeSet<>();
        for (IVaara v : allSeven()) {
            codes.add(v.code());
            org.junit.jupiter.api.Assertions.assertTrue(v.code().endsWith("VR"), v.code());
        }
        assertEquals(7, codes.size());
    }

    @Test
    void sevenLeaves_lengthIsTwentyFourHours() {
        for (IVaara v : allSeven()) assertEquals(24., v.length(), 1e-9);
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.upagraha;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Structural sweep of the 11 {@code IUpagraha} leaves. {@code length()} is 0 for every
 * upagraha (they are point positions, not degree segments), unlike most other families.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class UpagrahaTest {

    static abstract class UpagrahaStub implements IUpagraha {
        public IUpagraha[] all() { return new IUpagraha[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Dhuma extends UpagrahaStub implements IUpagrahaDhuma {}
    static class Vyatipaata extends UpagrahaStub implements IUpagrahaVyatipaata {}
    static class Parivesha extends UpagrahaStub implements IUpagrahaParivesha {}
    static class Indrachaapa extends UpagrahaStub implements IUpagrahaIndrachaapa {}
    static class Upaketu extends UpagrahaStub implements IUpagrahaUpaketu {}
    static class Kaala extends UpagrahaStub implements IUpagrahaKaala {}
    static class Mrityu extends UpagrahaStub implements IUpagrahaMrityu {}
    static class Arthaprahaara extends UpagrahaStub implements IUpagrahaArthaprahaara {}
    static class Yamaghantaka extends UpagrahaStub implements IUpagrahaYamaghantaka {}
    static class Gulika extends UpagrahaStub implements IUpagrahaGulika {}
    static class Maandi extends UpagrahaStub implements IUpagrahaMaandi {}

    static List<IUpagraha> allEleven() {
        return Arrays.asList(new Dhuma(), new Vyatipaata(), new Parivesha(), new Indrachaapa(), new Upaketu(),
                new Kaala(), new Mrityu(), new Arthaprahaara(), new Yamaghantaka(), new Gulika(), new Maandi());
    }

    @Test
    void elevenLeaves_haveUniqueFidsOneThroughEleven() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IUpagraha u : allEleven()) fids.add(u.fid());
        assertEquals(11, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(11, fids.last().intValue());
    }

    @Test
    void elevenLeaves_codeIsUGPrefixPlusFid() {
        for (IUpagraha u : allEleven()) assertEquals("UG" + u.fid(), u.code());
    }

    @Test
    void elevenLeaves_lengthIsZero() {
        for (IUpagraha u : allEleven()) assertEquals(0., u.length(), 1e-9);
    }

    @Test
    void gulikaAndMaandi_areTheTenthAndEleventhUpagraha() {
        // the two upagrahas most commonly used in modern practice (birth-time-dependent
        // shadow points), worth pinning their fid explicitly since downstream code is most
        // likely to reference them by name
        assertEquals(10, new Gulika().fid());
        assertEquals(11, new Maandi().fid());
    }
}

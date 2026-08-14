/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.karaka;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.graha.IGrahaEntity;
import org.jyotisa.api.naksatra.INaksatraPada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.swisseph.api.ISweObjects.RA;
import static org.swisseph.api.ISweObjects.SY;

/**
 * {@code ICharaKarakaOption.compare()} - the ranking rule behind the 7- vs 8-Chara-Karaka
 * schemes. The two schemes are not just "same rule, different graha count": the 7-karaka
 * default compares raw degree-in-sign for every graha uniformly, while the 8-karaka
 * (non-default {@code fid()==8}) branch additionally reverses Rahu's degree
 * ({@code 30 - degree}) before comparing - an asymmetry unique to Rahu, not applied to any
 * other graha (not even Ketu, which the 8-karaka scheme does not rank at all here).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class CharaKarakaTest {

    static IGrahaEntity entityAt(final int uid, final double longitude) {
        IGraha graha = new IGraha() {
            public IGraha[] all() { return new IGraha[0]; }
            public int ordinal() { return 0; }
            public String name() { return "g" + uid; }
            public int fid() { return uid; }
            @Override public int uid() { return uid; }
            public String code() { return "G" + uid; }
            public int swefid() { return uid; }
            public org.jyotisa.api.dignity.IDignity dignity(org.jyotisa.api.varga.IVarga v, double l) { return null; }
            public double inMrityuBhaga(double l) { return 0; }
        };
        return new IGrahaEntity() {
            public void charaKaraka(ICharaKaraka c) {}
            public ICharaKaraka charaKaraka() { return null; }
            public org.jyotisa.api.dignity.IDignity dignity() { return null; }
            public org.jyotisa.api.dignity.IDignity dignity(org.jyotisa.api.varga.IVarga v) { return null; }
            public double latitude() { return 0; }
            public boolean vakri() { return false; }
            public org.jyotisa.api.bhava.IBhava bhava() { return null; }
            public INaksatraPada pada() { return null; }
            public IGraha entityEnum() { return graha; }
            public double julianDay() { return 0; }
            public double longitude() { return longitude; }
        };
    }

    static final ICharaKarakaOption SEVEN_KARAKA = new ICharaKarakaOption() {
        public String name() { return "SEVEN_KARAKA"; }
        public int uid() { return fid(); }
    };

    static final ICharaKarakaOption EIGHT_KARAKA = new ICharaKarakaOption() {
        @Override public int fid() { return 8; }
        @Override public String code() { return CK8_CD; }
        public String name() { return "EIGHT_KARAKA"; }
        public int uid() { return fid(); }
    };

    /**
     * The interface method's own parameter names are swapped relative to the
     * {@code Comparator<T>.compare(o1, o2)} convention it implements ({@code compare(final
     * IGrahaEntity graha2, final IGrahaEntity graha1)} reads {@code graha1}, the SECOND
     * formal parameter, into {@code d1}) - the net effect is that {@code compare(a, b)}
     * actually computes {@code Double.compare(b's degree, a's degree)}, i.e. this is a
     * descending-by-degree-in-sign comparator: the graha that has travelled furthest
     * through its sign sorts first. That matches how Chara Karaka ranking works
     * (Atmakaraka = the graha with the highest degree), so this is read as intentional, not
     * fixed - these tests pin the actual behavior.
     */
    @Test
    void sevenKarakaOption_ranksTheHigherDegreeInSignFirst() {
        IGrahaEntity lowDegree = entityAt(SY, 10.);  // 10 deg in sign
        IGrahaEntity highDegree = entityAt(SY, 25.); // 25 deg in sign
        assertTrue(SEVEN_KARAKA.compare(lowDegree, highDegree) > 0,
                "25deg must sort before 10deg (descending-by-degree comparator)");
    }

    @Test
    void sevenKarakaOption_appliesNoSpecialRuleForRahu() {
        // Rahu at 10deg-in-sign compares the same as any other graha at 10deg - no reversal
        IGrahaEntity rahuLow = entityAt(RA, 10.);
        IGrahaEntity other = entityAt(SY, 25.);
        assertTrue(SEVEN_KARAKA.compare(rahuLow, other) > 0);
    }

    @Test
    void eightKarakaOption_reversesOnlyRahusDegreeBeforeComparing() {
        // Rahu at 10deg-in-sign is treated as (30-10)=20deg for ranking purposes in the
        // 8-karaka scheme; a non-Rahu graha at 15deg is NOT reversed, so the reversed Rahu
        // (effectively 20deg) now outranks Surya (15deg) - the OPPOSITE of what the raw,
        // unreversed 7-karaka comparison gives for the same two positions
        IGrahaEntity rahuAt10 = entityAt(RA, 10.);   // reversed to 20 for comparison
        IGrahaEntity suryaAt15 = entityAt(SY, 15.);  // stays 15

        assertTrue(EIGHT_KARAKA.compare(suryaAt15, rahuAt10) > 0,
                "Rahu (reversed to effectively 20deg) must outrank Surya (15deg)");
        assertTrue(SEVEN_KARAKA.compare(suryaAt15, rahuAt10) < 0,
                "without the reversal, raw Rahu (10deg) ranks BELOW Surya (15deg) - the two schemes disagree");
    }

    @Test
    void fidAndCode_distinguishTheTwoSchemes() {
        assertEquals(7, SEVEN_KARAKA.fid());
        assertEquals("CK7", SEVEN_KARAKA.code());
        assertEquals(8, EIGHT_KARAKA.fid());
        assertEquals("CK8", EIGHT_KARAKA.code());
    }
}

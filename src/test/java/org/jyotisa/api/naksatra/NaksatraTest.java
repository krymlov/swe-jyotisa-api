/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.naksatra;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.panchanga.IPanchanga;
import org.jyotisa.api.rasi.IRasi;
import org.junit.jupiter.api.Test;
import org.swisseph.api.ISweSegment;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.naksatra.INaksatra.progress;

/**
 * {@code INaksatra}'s {@code progress()} formula, {@code INaksatraPada}'s composite
 * {@code fid()}/{@code code()}/{@code pada()}/{@code segment()} logic, and structural checks
 * of all 27 naksatra leaves.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class NaksatraTest {

    static final double NAK_LEN = 360. / 27.; // 13.333...

    // ============================================================== INaksatra.progress

    @Test
    void progress_isPercentageThroughTheCurrentNaksatra() {
        assertEquals(0., progress(0.));
        assertEquals(50., progress(NAK_LEN / 2));
        assertEquals(0., progress(NAK_LEN), "wraps into the next naksatra at 0%, not 100%");
    }

    @Test
    void progress_panchangaOverload_readsChandraLongitude() {
        IPanchanga panchanga = stubPanchanga(NAK_LEN / 2, 999.);
        assertEquals(50., progress(panchanga));
    }

    static IPanchanga stubPanchanga(final double chandraLon, final double suryaLon) {
        return new IPanchanga() {
            public double chandraLongitude() { return chandraLon; }
            public double suryaLongitude() { return suryaLon; }
            public INaksatraPada pada() { return null; }
            public org.jyotisa.api.nityayoga.INityaYoga yoga() { return null; }
            public org.jyotisa.api.karana.IKarana karana() { return null; }
            public org.jyotisa.api.vaara.IVaara vaara() { return null; }
            public org.jyotisa.api.tithi.ITithi tithi() { return null; }
        };
    }

    // ============================================================== leaf structure (27)

    static abstract class NaksatraStub implements INaksatra {
        public INaksatra[] all() { return new INaksatra[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
    }

    static class Ashwini extends NaksatraStub implements INaksatraAshwini {}
    static class Bharani extends NaksatraStub implements INaksatraBharani {}
    static class Krittika extends NaksatraStub implements INaksatraKrittika {}
    static class Rohini extends NaksatraStub implements INaksatraRohini {}
    static class Mrigashira extends NaksatraStub implements INaksatraMrigashira {}
    static class Ardra extends NaksatraStub implements INaksatraArdra {}
    static class Punarvasu extends NaksatraStub implements INaksatraPunarvasu {}
    static class Pushya extends NaksatraStub implements INaksatraPushya {}
    static class Ashlesha extends NaksatraStub implements INaksatraAshlesha {}
    static class Magha extends NaksatraStub implements INaksatraMagha {}
    static class PurvaPhalguni extends NaksatraStub implements INaksatraPurvaPhalguni {}
    static class UttaraPhalguni extends NaksatraStub implements INaksatraUttaraPhalguni {}
    static class Hasta extends NaksatraStub implements INaksatraHasta {}
    static class Chitra extends NaksatraStub implements INaksatraChitra {}
    static class Swati extends NaksatraStub implements INaksatraSwati {}
    static class Vishakha extends NaksatraStub implements INaksatraVishakha {}
    static class Anuradha extends NaksatraStub implements INaksatraAnuradha {}
    static class Jyeshtha extends NaksatraStub implements INaksatraJyeshtha {}
    static class Mula extends NaksatraStub implements INaksatraMula {}
    static class PurvaAshadha extends NaksatraStub implements INaksatraPurvaAshadha {}
    static class UttaraAshadha extends NaksatraStub implements INaksatraUttaraAshadha {}
    static class Shravana extends NaksatraStub implements INaksatraShravana {}
    static class Dhanishta extends NaksatraStub implements INaksatraDhanishta {}
    static class Shatabhisha extends NaksatraStub implements INaksatraShatabhisha {}
    static class PurvaBhadrapada extends NaksatraStub implements INaksatraPurvaBhadrapada {}
    static class UttaraBhadrapada extends NaksatraStub implements INaksatraUttaraBhadrapada {}
    static class Revati extends NaksatraStub implements INaksatraRevati {}

    static List<INaksatra> allTwentySeven() {
        return Arrays.asList(new Ashwini(), new Bharani(), new Krittika(), new Rohini(), new Mrigashira(),
                new Ardra(), new Punarvasu(), new Pushya(), new Ashlesha(), new Magha(), new PurvaPhalguni(),
                new UttaraPhalguni(), new Hasta(), new Chitra(), new Swati(), new Vishakha(), new Anuradha(),
                new Jyeshtha(), new Mula(), new PurvaAshadha(), new UttaraAshadha(), new Shravana(),
                new Dhanishta(), new Shatabhisha(), new PurvaBhadrapada(), new UttaraBhadrapada(), new Revati());
    }

    @Test
    void twentySevenLeaves_haveUniqueFidsOneThroughTwentySeven() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (INaksatra n : allTwentySeven()) fids.add(n.fid());
        assertEquals(27, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(27, fids.last().intValue());
    }

    @Test
    void twentySevenLeaves_codeMatchesNPrefixPlusFid() {
        for (INaksatra n : allTwentySeven()) {
            assertEquals("N" + n.fid(), n.code(), "code must be N<fid> for " + n.getClass().getSimpleName());
        }
    }

    @Test
    void twentySevenLeaves_uidDefaultsToFid() {
        for (INaksatra n : allTwentySeven()) assertEquals(n.fid(), n.uid(), n.code());
    }

    @Test
    void naksatraLeaf_lengthIsThreeHundredSixtyOverTwentySeven() {
        assertEquals(NAK_LEN, new Ashwini().length(), 1e-9);
    }

    // ============================================================== INaksatraPada

    static INaksatraPada padOf(final INaksatra naksatra, final int uid) {
        return new INaksatraPada() {
            public IRasi rasi() { throw new UnsupportedOperationException(); }
            public IRasi navamsa() { throw new UnsupportedOperationException(); }
            public INaksatra naksatra() { return naksatra; }
            public INaksatraPada[] all() { return new INaksatraPada[0]; }
            public int ordinal() { return 0; }
            public String name() { return "pada" + uid; }
            @Override public int uid() { return uid; }
        };
    }

    @Test
    void pada_cyclesOneToFourFromUid() {
        INaksatraPada p1 = padOf(new Ashwini(), 1);
        INaksatraPada p4 = padOf(new Ashwini(), 4);
        INaksatraPada p5 = padOf(new Ashwini(), 5); // next naksatra's first pada
        assertEquals(1, p1.pada());
        assertEquals(4, p4.pada());
        assertEquals(1, p5.pada(), "uid 5 wraps back to pada 1 of the next naksatra");
    }

    @Test
    void fid_concatenatesNaksatraFidWithPadaNumber() {
        // naksatra fid=1 (Ashwini), pada=2 -> "1"+"2" = 12
        INaksatraPada p = padOf(new Ashwini(), 2);
        assertEquals(2, p.pada());
        assertEquals(12, p.fid());
    }

    @Test
    void fid_forDoubleDigitNaksatraStillRoundTripsUnambiguously() {
        // naksatra fid=27 (Revati), pada=4 -> "27"+"4" = 274; pada is always a single digit
        // (1-4) so the last digit of the composite fid is always the pada and everything
        // before it is always the naksatra fid, with no collision against any other
        // (naksatra,pada) pair - see the class javadoc discussion this test pins
        INaksatraPada p = padOf(new Revati(), 4);
        assertEquals(4, p.pada());
        assertEquals(274, p.fid());
    }

    @Test
    void fid_isNilWhenNaksatraIsAbsent() {
        INaksatraPada p = padOf(null, 1);
        assertEquals(0, p.fid());
    }

    @Test
    void code_isNaksatraCodePlusPSuffixPadaNumber() {
        INaksatraPada p = padOf(new Magha(), 3);
        assertEquals(3, p.pada());
        assertEquals("N10P3", p.code());
    }

    @Test
    void segment_spansThePadasOwnThirdOfThirdDegreeSlot() {
        // NAKSHATRA_PADA_LENGTH = 360/108 = 3.333...
        double padLen = 360. / 108.;
        INaksatraPada p = padOf(new Ashwini(), 3); // uid=3 (global pada index)
        ISweSegment seg = p.segment();
        assertEquals(2 * padLen, seg.start(), 1e-9);
        assertEquals(3 * padLen, seg.close(), 1e-9);
    }

    @Test
    void progress_forNaksatraPada_isPercentageThroughTheCurrentPada() {
        double padLen = 360. / 108.;
        assertEquals(0., INaksatraPada.progress(0.));
        assertEquals(50., INaksatraPada.progress(padLen / 2));
    }
}

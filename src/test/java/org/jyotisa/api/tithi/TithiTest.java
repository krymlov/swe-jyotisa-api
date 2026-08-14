/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.tithi;

import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.karana.IKarana;
import org.jyotisa.api.panchanga.IPanchanga;
import org.junit.jupiter.api.Test;
import org.swisseph.api.ISweSegment;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.tithi.ITithi.progress;

/**
 * {@code ITithi.progress()}, {@code segment()}, {@code paksa()} and {@link EPaksa}'s static
 * {@code byLongitude}, plus a structural sweep of the 15 tithi "type" leaves shared between
 * both fortnights (see the class javadoc on why there are 15, not 30, leaves here).
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class TithiTest {

    // ============================================================== ITithi.progress

    @Test
    void progress_isPercentageThroughTheCurrentTithi() {
        IPanchanga p = panchanga(6., 0.); // chandra-surya = 6 = half of TITHI_LENGTH(12)
        assertEquals(50., progress(p));
    }

    @Test
    void progress_wrapsAtTithiBoundary() {
        assertEquals(0., progress(panchanga(12., 0.)), "12 degrees is the start of the next tithi");
    }

    static IPanchanga panchanga(final double chandraLon, final double suryaLon) {
        return new IPanchanga() {
            public double chandraLongitude() { return chandraLon; }
            public double suryaLongitude() { return suryaLon; }
            public org.jyotisa.api.naksatra.INaksatraPada pada() { return null; }
            public org.jyotisa.api.nityayoga.INityaYoga yoga() { return null; }
            public IKarana karana() { return null; }
            public org.jyotisa.api.vaara.IVaara vaara() { return null; }
            public ITithi tithi() { return null; }
        };
    }

    // ============================================================== paksa() / segment()

    static abstract class TithiStub implements ITithi {
        int ordinal;
        TithiStub(int ordinal) { this.ordinal = ordinal; }
        public ITithi[] all() { return new ITithi[0]; }
        public int ordinal() { return ordinal; }
        public String name() { return getClass().getSimpleName(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
    }

    static class Pratipada extends TithiStub implements ITithiPratipada {
        Pratipada(int ordinal) { super(ordinal); }
    }

    static class Poornima extends TithiStub implements ITithiPoornima {
        Poornima(int ordinal) { super(ordinal); }
    }

    @Test
    void paksa_isDeterminedByOrdinalParity() {
        // ordinal%2==0 -> KRSNA, else GAURA - as declared on ITithi itself
        assertTrue(new Pratipada(1).paksa().gaura(), "odd ordinal (Shukla side)");
        assertTrue(new Pratipada(2).paksa().krsna(), "even ordinal (Krishna side)");
    }

    @Test
    void segment_usesUidNotFid_forItsDegreeSpan() {
        // fid() is always 15 for Poornima (a fixed "type" id shared by both paksas); the
        // degree span must come from uid(), which a real Krishna-side instance overrides to
        // 30 (see ETithi/TithiPoornima.K15 in swe-jyotisa-lib) - simulated here by an
        // anonymous leaf whose uid() differs from its fid()
        ITithi krishnaAmavasya = new Poornima(30) {
            @Override public int uid() { return 30; }
        };
        assertEquals(15, krishnaAmavasya.fid(), "fid stays the type id");
        assertEquals(30, krishnaAmavasya.uid());

        ISweSegment seg = krishnaAmavasya.segment();
        assertEquals(29 * 12., seg.start(), 1e-9);
        assertEquals(30 * 12., seg.close(), 1e-9);
    }

    @Test
    void segment_forTheShuklaInstanceUsesFidWhenUidIsNotOverridden() {
        ITithi shuklaPoornima = new Poornima(15);
        assertEquals(15, shuklaPoornima.uid(), "uid defaults to fid when not overridden");
        ISweSegment seg = shuklaPoornima.segment();
        assertEquals(14 * 12., seg.start(), 1e-9);
        assertEquals(15 * 12., seg.close(), 1e-9);
    }

    // ============================================================== EPaksa.byLongitude

    @Test
    void byLongitude_gauraForTheFirstFifteenTithis() {
        // tithiIdx = 1 + floor(diff/12); GAURA while tithiIdx <= 15
        assertTrue(EPaksa.byLongitude(0., 0.).gaura(), "diff=0 -> tithiIdx 1");
        assertTrue(EPaksa.byLongitude(0., 179.).gaura(), "diff=179 -> tithiIdx 15 (179/12=14.9->+1=15)");
    }

    @Test
    void byLongitude_krsnaForTheLastFifteenTithis() {
        assertTrue(EPaksa.byLongitude(0., 180.).krsna(), "diff=180 -> tithiIdx 16");
        assertTrue(EPaksa.byLongitude(0., 359.).krsna(), "diff=359 -> tithiIdx 30");
    }

    @Test
    void byLongitude_wrapsWhenChandraIsBehindSurya() {
        // surya=350, chandra=10 -> diff=-340, wraps to +20 -> tithiIdx 1+floor(20/12)=2 (Gaura)
        assertTrue(EPaksa.byLongitude(350., 10.).gaura());
    }

    @Test
    void ePaksa_fidUidCode() {
        assertEquals(0, EPaksa.KRSNA.fid());
        assertEquals(0, EPaksa.KRSNA.uid());
        assertEquals("KRSNA", EPaksa.KRSNA.code());
        assertEquals(1, EPaksa.GAURA.fid());
        assertFalse(EPaksa.KRSNA.gaura());
        assertFalse(EPaksa.GAURA.krsna());
    }

    // ============================================================== 15-leaf structural sweep

    static class Dwitiya extends TithiStub implements ITithiDwitiya { Dwitiya() { super(2); } }
    static class Tritiya extends TithiStub implements ITithiTritiya { Tritiya() { super(3); } }
    static class Chaturthi extends TithiStub implements ITithiChaturthi { Chaturthi() { super(4); } }
    static class Panchami extends TithiStub implements ITithiPanchami { Panchami() { super(5); } }
    static class Shashthi extends TithiStub implements ITithiShashthi { Shashthi() { super(6); } }
    static class Saptami extends TithiStub implements ITithiSaptami { Saptami() { super(7); } }
    static class Ashtami extends TithiStub implements ITithiAshtami { Ashtami() { super(8); } }
    static class Navami extends TithiStub implements ITithiNavami { Navami() { super(9); } }
    static class Dashami extends TithiStub implements ITithiDashami { Dashami() { super(10); } }
    static class Ekadasi extends TithiStub implements ITithiEkadasi { Ekadasi() { super(11); } }
    static class Dwadasi extends TithiStub implements ITithiDwadasi { Dwadasi() { super(12); } }
    static class Trayodasi extends TithiStub implements ITithiTrayodasi { Trayodasi() { super(13); } }
    static class Chaturdasi extends TithiStub implements ITithiChaturdasi { Chaturdasi() { super(14); } }

    static List<ITithi> allFifteen() {
        return Arrays.asList(new Pratipada(1), new Dwitiya(), new Tritiya(), new Chaturthi(), new Panchami(),
                new Shashthi(), new Saptami(), new Ashtami(), new Navami(), new Dashami(), new Ekadasi(),
                new Dwadasi(), new Trayodasi(), new Chaturdasi(), new Poornima(15));
    }

    @Test
    void fifteenLeaves_haveUniqueFidsOneThroughFifteen() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (ITithi t : allFifteen()) fids.add(t.fid());
        assertEquals(15, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(15, fids.last().intValue());
    }

    @Test
    void fifteenLeaves_codeIsAlwaysTheShuklaForm() {
        // every leaf's own default code() returns its "S<n>" constant - never "K<n>"; the
        // Krishna form only appears via a downstream enum instance overriding code(), as
        // segment_usesUidNotFid_forItsDegreeSpan's anonymous class does not (out of scope
        // for the pure-default value tested here)
        for (ITithi t : allFifteen()) {
            assertTrue(t.code().startsWith("S"), t.code());
            assertEquals("S" + t.fid(), t.code());
        }
    }

    @Test
    void fifteenLeaves_lengthIsTwelveDegreesEach() {
        for (ITithi t : allFifteen()) assertEquals(12., t.length(), 1e-9);
    }
}

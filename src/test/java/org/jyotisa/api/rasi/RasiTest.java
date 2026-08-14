/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.rasi;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.graha.IGraha;
import org.jyotisa.api.tattva.ITattva;
import org.jyotisa.api.varga.IVarga;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.swisseph.api.ISweGender;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.jyotisa.api.rasi.IRasi.*;

/**
 * {@code IRasi}'s eight static formulas (pure functions of an ecliptic longitude) plus a
 * structural check of all 12 leaf interfaces.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class RasiTest {

    // ============================================================== static formulas

    @Test
    void rasiFid0_isZeroBasedAndWrapsEveryThirtyDegrees() {
        assertEquals(0, rasiFid0(0.));
        assertEquals(0, rasiFid0(29.999));
        assertEquals(1, rasiFid0(30.));
        assertEquals(11, rasiFid0(359.999));
        assertEquals(0, rasiFid0(360.)); // fix360(360) snaps to 0
    }

    @Test
    void rasiFid_isOneBased() {
        assertEquals(1, rasiFid(0.));
        assertEquals(1, rasiFid(29.999));
        assertEquals(2, rasiFid(30.));
        assertEquals(12, rasiFid(359.999));
    }

    @Test
    void rasiFid0_wrapsNegativeLongitudes() {
        assertEquals(11, rasiFid0(-0.001), "just under 0 wraps to the last sign");
        assertEquals(0, rasiFid0(-360.));
    }

    @Test
    void rasiDegree_isTheDegreeWithinTheCurrentSign() {
        assertEquals(0., rasiDegree(30.), 1e-9);
        assertEquals(15., rasiDegree(45.), 1e-9);
        assertEquals(29.5, rasiDegree(359.5), 1e-9);
    }

    @Test
    void progress_isThePercentageThroughTheCurrentSignRoundedToTwoDecimals() {
        assertEquals(0., progress(0.));
        assertEquals(50., progress(15.));
        // 30 degrees is the start of the NEXT sign, i.e. degree 0 of it - modulo(30, 30)
        // snaps to 0, not to the modulus, so this is 0% again, not 100%
        assertEquals(0., progress(30.));
    }

    @Test
    void inOddRasi_flagsTheOneBasedRasiNumberBeingOdd() {
        // inOddRasi shifts the longitude by one full sign before testing parity, so it
        // reports whether the 1-based rasi number (not rasiFid0) is odd: Mesha=1 (odd,
        // true), Vrishabha=2 (even, false), Mithuna=3 (odd, true)
        assertTrue(inOddRasi(0.), "Mesha is rasi 1 (odd)");
        assertFalse(inOddRasi(30.), "Vrishabha is rasi 2 (even)");
        assertTrue(inOddRasi(60.), "Mithuna is rasi 3 (odd)");
    }

    @Test
    void inMovableFixedDualRasi_cycleEveryThreeSigns() {
        // Mesha(1,0deg) movable, Vrishabha(2,30deg) fixed, Mithuna(3,60deg) dual, repeating;
        // sign N (1-based) starts at (N-1)*30 degrees
        assertTrue(inMovableRasi(0.));
        assertTrue(inFixedRasi(30.));
        assertTrue(inDualRasi(60.));
        assertTrue(inMovableRasi(90.));  // Karkata, sign 4
        assertTrue(inFixedRasi(300.));   // Kumbha, sign 11
        assertTrue(inDualRasi(240.));    // Dhanus, sign 9
    }

    @Test
    void inMovableFixedDualRasi_areMutuallyExclusiveAndExhaustive() {
        for (double lon = 0.; lon < 360.; lon += 7.5) {
            int trueCount = (inMovableRasi(lon) ? 1 : 0) + (inFixedRasi(lon) ? 1 : 0) + (inDualRasi(lon) ? 1 : 0);
            assertEquals(1, trueCount, "exactly one of movable/fixed/dual must hold at " + lon);
        }
    }

    // ============================================================== leaf structure

    static abstract class RasiStub implements IRasi {
        public IRasi[] all() { return new IRasi[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public ISweGender gender() { throw new UnsupportedOperationException(); }
        public ITattva tattva() { throw new UnsupportedOperationException(); }
        public IGraha lord() { throw new UnsupportedOperationException(); }
        public IRasi badhaka() { throw new UnsupportedOperationException(); }
    }

    static class Mesha extends RasiStub implements IRasiMesha {}
    static class Vrishabha extends RasiStub implements IRasiVrishabha {}
    static class Mithuna extends RasiStub implements IRasiMithuna {}
    static class Karkata extends RasiStub implements IRasiKarkata {}
    static class Simha extends RasiStub implements IRasiSimha {}
    static class Kanya extends RasiStub implements IRasiKanya {}
    static class Tula extends RasiStub implements IRasiTula {}
    static class Vrischika extends RasiStub implements IRasiVrischika {}
    static class Dhanus extends RasiStub implements IRasiDhanus {}
    static class Makara extends RasiStub implements IRasiMakara {}
    static class Kumbha extends RasiStub implements IRasiKumbha {}
    static class Meena extends RasiStub implements IRasiMeena {}

    static List<IRasi> allTwelve() {
        return Arrays.asList(new Mesha(), new Vrishabha(), new Mithuna(), new Karkata(), new Simha(),
                new Kanya(), new Tula(), new Vrischika(), new Dhanus(), new Makara(), new Kumbha(), new Meena());
    }

    @Test
    void twelveLeaves_haveUniqueFidsOneThroughTwelve() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IRasi r : allTwelve()) fids.add(r.fid());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), new java.util.ArrayList<>(fids));
    }

    @Test
    void twelveLeaves_uidDefaultsToFid() {
        // IKundaliSequence's default: uid()==fid() unless a leaf overrides it - none of the
        // 12 rasi leaves do (unlike several graha leaves - see GrahaTest)
        for (IRasi r : allTwelve()) assertEquals(r.fid(), r.uid(), r.code());
    }

    @Test
    void twelveLeaves_lengthIsThirtyDegreesEach() {
        for (IRasi r : allTwelve()) assertEquals(30., r.length(), 1e-9, r.code());
    }

    @ParameterizedTest
    @MethodSource("movableFixedDualExpectations")
    void leaf_movableFixedDualFlags_matchTheClassicalCycle(IRasi rasi, boolean movable, boolean fixed, boolean dual) {
        assertEquals(movable, rasi.movable(), rasi.code() + ".movable()");
        assertEquals(fixed, rasi.fixed(), rasi.code() + ".fixed()");
        assertEquals(dual, rasi.dual(), rasi.code() + ".dual()");
    }

    static List<Object[]> movableFixedDualExpectations() {
        List<IRasi> all = allTwelve();
        return Arrays.asList(
                new Object[]{all.get(0), true, false, false},  // Mesha
                new Object[]{all.get(1), false, true, false},  // Vrishabha
                new Object[]{all.get(2), false, false, true},  // Mithuna
                new Object[]{all.get(3), true, false, false},  // Karkata
                new Object[]{all.get(4), false, true, false},  // Simha
                new Object[]{all.get(5), false, false, true},  // Kanya
                new Object[]{all.get(6), true, false, false},  // Tula
                new Object[]{all.get(7), false, true, false},  // Vrischika
                new Object[]{all.get(8), false, false, true},  // Dhanus
                new Object[]{all.get(9), true, false, false},  // Makara
                new Object[]{all.get(10), false, true, false}, // Kumbha
                new Object[]{all.get(11), false, false, true}  // Meena
        );
    }
}

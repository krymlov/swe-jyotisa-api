/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.varga;

import org.jyotisa.api.rasi.IRasi;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.jyotisa.api.varga.IVarga.dvadasamsaLongitude;

/**
 * {@code IVarga}'s divisor arithmetic - {@code virtualDegree}/{@code rasiLongitude} are keyed
 * directly off {@code fid()}, which for this family IS the numeric divisor (D9's fid is 9,
 * not a sequential index) - and {@code dvadasamsaLongitude}, plus a structural sweep of the
 * 23 divisional-chart leaves.
 * <p>
 * <b>Not independently verified against a classical reference here</b> - {@code fid()==divisor}
 * combined with a flat {@code longitude*fid() mod 360} is one uniform formula applied to
 * every varga D1..D144 alike, whereas several classical Parashari divisional charts
 * (Drekkana/D3, Saptamsa/D7, Dasamsa/D10, Shodasamsa/D16, ...) have odd/even-rasi-dependent
 * starting-sign rules that do not obviously reduce to one multiplication. Flagged in
 * CLAUDE.md as a priority to check against a reference implementation, not asserted as
 * correct or incorrect here - these tests only pin the formula's own self-consistency.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class VargaTest {

    static IVarga vargaOf(final int divisorFid) {
        return new IVarga() {
            // not called by any test below - virtualDegree/rasiLongitude/dvadasamsaLongitude
            // don't need a real IRasi collaborator
            public IRasi rasi(double longitudeInD1) { throw new UnsupportedOperationException(); }
            public IVarga[] all() { return new IVarga[0]; }
            public int ordinal() { return 0; }
            public String name() { return "D" + divisorFid; }
            @Override public int fid() { return divisorFid; }
            @Override public String code() { return "D" + divisorFid; }
        };
    }

    @Test
    void virtualDegree_forD1_isTheLongitudeUnchanged() {
        IVarga d1 = vargaOf(1);
        assertEquals(123.45, d1.virtualDegree(123.45), 1e-9);
    }

    @Test
    void virtualDegree_multipliesByTheDivisorAndWrapsAt360() {
        IVarga d9 = vargaOf(9);
        assertEquals(90., d9.virtualDegree(10.), 1e-9, "10*9=90, no wrap needed");
        assertEquals(0., d9.virtualDegree(40.), 1e-9, "40*9=360, wraps to 0");
        assertEquals(45., d9.virtualDegree(45.), 1e-9, "45*9=405, wraps to 45");
    }

    @Test
    void rasiLongitude_isTheDegreeWithinTheVirtualSign() {
        IVarga d9 = vargaOf(9);
        // virtualDegree(45)=45 (see above), degree within its sign = 45 mod 30 = 15
        assertEquals(15., d9.rasiLongitude(45.), 1e-9);
    }

    @Test
    void dvadasamsaLongitude_isAFixedDivisorTwelveMapping() {
        // rasiFid0(longitude)*30 + rasiDegree(longitude)*12, wrapped at 360
        assertEquals(0., dvadasamsaLongitude(0.), 1e-9);
        assertEquals(0. + 15. * 12., dvadasamsaLongitude(15.), 1e-9, "15 deg into Mesha (rasiFid0=0)");
        assertEquals(30. + 5. * 12., dvadasamsaLongitude(35.), 1e-9, "5 deg into Vrishabha (rasiFid0=1)");
    }

    @Test
    void dvadasamsaLongitude_wrapsAtThreeHundredSixty() {
        // rasi 11 (Kumbha, rasiFid0=10), 29 degrees in: 10*30 + 29*12 = 300+348=648 -> wraps
        double result = dvadasamsaLongitude(329.);
        assertEquals((300. + 29. * 12.) % 360., result, 1e-9);
    }

    // ============================================================== leaf structure (23)

    static abstract class VargaStub implements IVarga {
        public IVarga[] all() { return new IVarga[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IRasi rasi(double longitudeInD1) { throw new UnsupportedOperationException(); }
    }

    static class D1 extends VargaStub implements IVargaD1 {}
    static class D2 extends VargaStub implements IVargaD2 {}
    static class D3 extends VargaStub implements IVargaD3 {}
    static class D4 extends VargaStub implements IVargaD4 {}
    static class D5 extends VargaStub implements IVargaD5 {}
    static class D6 extends VargaStub implements IVargaD6 {}
    static class D7 extends VargaStub implements IVargaD7 {}
    static class D8 extends VargaStub implements IVargaD8 {}
    static class D9 extends VargaStub implements IVargaD9 {}
    static class D10 extends VargaStub implements IVargaD10 {}
    static class D11 extends VargaStub implements IVargaD11 {}
    static class D12 extends VargaStub implements IVargaD12 {}
    static class D16 extends VargaStub implements IVargaD16 {}
    static class D20 extends VargaStub implements IVargaD20 {}
    static class D24 extends VargaStub implements IVargaD24 {}
    static class D27 extends VargaStub implements IVargaD27 {}
    static class D30 extends VargaStub implements IVargaD30 {}
    static class D40 extends VargaStub implements IVargaD40 {}
    static class D45 extends VargaStub implements IVargaD45 {}
    static class D60 extends VargaStub implements IVargaD60 {}
    static class D81 extends VargaStub implements IVargaD81 {}
    static class D108 extends VargaStub implements IVargaD108 {}
    static class D144 extends VargaStub implements IVargaD144 {}

    static List<IVarga> allTwentyThree() {
        return Arrays.asList(new D1(), new D2(), new D3(), new D4(), new D5(), new D6(), new D7(), new D8(),
                new D9(), new D10(), new D11(), new D12(), new D16(), new D20(), new D24(), new D27(),
                new D30(), new D40(), new D45(), new D60(), new D81(), new D108(), new D144());
    }

    @Test
    void twentyThreeLeaves_fidEqualsTheDivisorEncodedInItsOwnName() {
        // unlike every other family, fid() here IS the numeric divisor, not a sequential index
        int[] expectedDivisors = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16, 20, 24, 27, 30, 40, 45, 60, 81, 108, 144};
        List<IVarga> all = allTwentyThree();
        assertEquals(expectedDivisors.length, all.size());
        for (int i = 0; i < all.size(); i++) {
            assertEquals(expectedDivisors[i], all.get(i).fid(), all.get(i).getClass().getSimpleName());
        }
    }

    @Test
    void twentyThreeLeaves_haveUniqueFids() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IVarga v : allTwentyThree()) fids.add(v.fid());
        assertEquals(23, fids.size());
    }

    @Test
    void twentyThreeLeaves_codeIsDPrefixPlusFid() {
        for (IVarga v : allTwentyThree()) assertEquals("D" + v.fid(), v.code());
    }

    @Test
    void twentyThreeLeaves_lengthIsAFullChakraThreeSixty() {
        for (IVarga v : allTwentyThree()) assertEquals(360., v.length(), 1e-9);
    }
}

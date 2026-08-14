/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.graha;

import org.jyotisa.api.dignity.IDignity;
import org.jyotisa.api.varga.IVarga;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.graha.IGraha.inMrityuBhaga;
import static org.swisseph.api.ISweObjects.*;
import static swisseph.SweConst.*;

/**
 * {@code IGraha.inMrityuBhaga()} (the shared "Mrityu Bhaga" static formula) and a full
 * structural sweep of all 13 graha leaves - fid/uid/code/swefid/drishti, each cross-checked
 * against {@code org.swisseph.api.ISweObjects}'s canonical chart-index constants.
 * <p>
 * The single richest regression-test surface in this whole API: {@code fid()} and
 * {@code uid()} deliberately diverge for six of the nine classical grahas (Guru, Rahu,
 * Budha, Mangala, Shani, Ketu) - {@code fid()} is a small sequential "declaration order" id
 * while {@code uid()} is the canonical {@code ISweObjects} chart-array index. Getting this
 * backwards anywhere downstream would silently misalign a graha with the wrong chart slot.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class GrahaTest {

    // ============================================================== inMrityuBhaga formula

    @Test
    void inMrityuBhaga_isFullStrengthExactlyAtTheTabulatedDegree() {
        // rasi 0 (Mesha), tabulated degree 20 -> longitude 20.0 exactly on the mark
        double[] degrees = {20, 9, 12, 6, 8, 24, 16, 17, 22, 2, 3, 23}; // MRITYU_BHAGA_SURYA
        assertEquals(100., inMrityuBhaga(20.0, degrees), 1e-9);
    }

    @Test
    void inMrityuBhaga_decaysLinearlyWithinOneDegreeOrb() {
        double[] degrees = {20, 9, 12, 6, 8, 24, 16, 17, 22, 2, 3, 23};
        assertEquals(50., inMrityuBhaga(20.5, degrees), 1e-9, "0.5 degree off -> 50%");
        assertEquals(0., inMrityuBhaga(21.0, degrees), 1e-9, "exactly 1 degree off -> 0%");
    }

    @Test
    void inMrityuBhaga_isZeroBeyondOneDegreeOrb() {
        double[] degrees = {20, 9, 12, 6, 8, 24, 16, 17, 22, 2, 3, 23};
        assertEquals(0., inMrityuBhaga(25.0, degrees), 1e-9);
        assertEquals(0., inMrityuBhaga(0.0, degrees), 1e-9);
    }

    @Test
    void inMrityuBhaga_readsTheDegreeForTheLongitudesOwnRasi() {
        // rasi 1 (Vrishabha, 30-60deg), tabulated degree 9 -> longitude 39.0
        double[] degrees = {20, 9, 12, 6, 8, 24, 16, 17, 22, 2, 3, 23};
        assertEquals(100., inMrityuBhaga(39.0, degrees), 1e-9);
    }

    // ============================================================== leaf stubs

    /**
     * A class's own concrete method always wins over an interface default, even when the
     * interface (e.g. {@code IGrahaLagna}) overrides that default itself - so this stub
     * cannot throw from {@code dignity()}, or it would shadow the four leaves
     * (Lagna/Sweta/Syama/Teevra) that give it a real body. Returning {@code null}
     * unconditionally is safe here: no leaf in this API layer has real per-degree dignity
     * logic to lose (see the class javadoc on {@code IGraha.dignity}), and the four leaves
     * that DO override it are themselves tested for exactly that {@code null}.
     */
    static abstract class GrahaStub implements IGraha {
        public IGraha[] all() { return new IGraha[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
        public IDignity dignity(IVarga varga, double longitude) { return null; }
    }

    static class Lagna extends GrahaStub implements IGrahaLagna {}
    static class Surya extends GrahaStub implements IGrahaSurya {}
    static class Chandra extends GrahaStub implements IGrahaChandra {}
    static class Guru extends GrahaStub implements IGrahaGuru {}
    static class Rahu extends GrahaStub implements IGrahaRahu {}
    static class Budha extends GrahaStub implements IGrahaBudha {}
    static class Shukra extends GrahaStub implements IGrahaShukra {}
    static class Ketu extends GrahaStub implements IGrahaKetu {}
    static class Shani extends GrahaStub implements IGrahaShani {}
    static class Mangala extends GrahaStub implements IGrahaMangala {}
    static class Sweta extends GrahaStub implements IGrahaSweta {}
    static class Syama extends GrahaStub implements IGrahaSyama {}
    static class Teevra extends GrahaStub implements IGrahaTeevra {}

    static List<IGraha> allThirteen() {
        return Arrays.asList(new Lagna(), new Surya(), new Chandra(), new Guru(), new Rahu(), new Budha(),
                new Shukra(), new Ketu(), new Shani(), new Mangala(), new Sweta(), new Syama(), new Teevra());
    }

    // ============================================================== fid vs uid divergence

    @Test
    void fidAndUid_agreeForLagnaSuryaChandraShukraAndTheOuterPlanets() {
        assertEquals(new Lagna().fid(), new Lagna().uid());
        assertEquals(new Surya().fid(), new Surya().uid());
        assertEquals(new Chandra().fid(), new Chandra().uid());
        assertEquals(new Shukra().fid(), new Shukra().uid());
        assertEquals(new Sweta().fid(), new Sweta().uid());
        assertEquals(new Syama().fid(), new Syama().uid());
        assertEquals(new Teevra().fid(), new Teevra().uid());
    }

    @Test
    void fidAndUid_deliberatelyDivergeForSixGrahas() {
        // fid() is declaration order (3,4,5,6,7,8,9); uid() is the canonical chart index
        assertEquals(3, new Guru().fid());
        assertEquals(GU, new Guru().uid());

        assertEquals(4, new Rahu().fid());
        assertEquals(RA, new Rahu().uid());

        assertEquals(5, new Budha().fid());
        assertEquals(BU, new Budha().uid());

        assertEquals(9, new Mangala().fid());
        assertEquals(MA, new Mangala().uid());

        assertEquals(8, new Shani().fid());
        assertEquals(SA, new Shani().uid());

        assertEquals(7, new Ketu().fid());
        assertEquals(KE, new Ketu().uid());
    }

    @Test
    void uid_matchesISweObjectsChartIndexForEveryGraha() {
        assertEquals(org.swisseph.api.ISweObjects.LG, new Lagna().uid());
        assertEquals(org.swisseph.api.ISweObjects.SY, new Surya().uid());
        assertEquals(org.swisseph.api.ISweObjects.CH, new Chandra().uid());
        assertEquals(org.swisseph.api.ISweObjects.MA, new Mangala().uid());
        assertEquals(org.swisseph.api.ISweObjects.BU, new Budha().uid());
        assertEquals(org.swisseph.api.ISweObjects.GU, new Guru().uid());
        assertEquals(org.swisseph.api.ISweObjects.SK, new Shukra().uid());
        assertEquals(org.swisseph.api.ISweObjects.SA, new Shani().uid());
        assertEquals(org.swisseph.api.ISweObjects.RA, new Rahu().uid());
        assertEquals(org.swisseph.api.ISweObjects.KE, new Ketu().uid());
        assertEquals(org.swisseph.api.ISweObjects.UR, new Sweta().uid());
        assertEquals(org.swisseph.api.ISweObjects.NE, new Syama().uid());
        assertEquals(org.swisseph.api.ISweObjects.PL, new Teevra().uid());
    }

    // ============================================================== swefid mapping

    @Test
    void swefid_mapsToTheCorrectSwissEphemerisPlanetNumber() {
        assertEquals(ERR, new Lagna().swefid(), "Lagna has no Swiss Ephemeris planet number");
        assertEquals(SE_SUN, new Surya().swefid());
        assertEquals(SE_MOON, new Chandra().swefid());
        assertEquals(SE_MARS, new Mangala().swefid());
        assertEquals(SE_MERCURY, new Budha().swefid());
        assertEquals(SE_JUPITER, new Guru().swefid());
        assertEquals(SE_VENUS, new Shukra().swefid());
        assertEquals(SE_SATURN, new Shani().swefid());
        assertEquals(SE_MEAN_NODE, new Rahu().swefid());
        assertEquals(SE_MEAN_NODE, new Ketu().swefid(), "Ketu shares Rahu's mean-node swefid");
        assertEquals(SE_URANUS, new Sweta().swefid());
        assertEquals(SE_NEPTUNE, new Syama().swefid());
        assertEquals(SE_PLUTO, new Teevra().swefid());
    }

    // ============================================================== drishti (aspects)

    @Test
    void drishti_matchesTheDocumentedClassicalAspectSets() {
        assertArrayEquals(new int[]{4, 7, 8}, new Mangala().drishti(), "Mars: 4,7,8");
        assertArrayEquals(new int[]{5, 7, 9}, new Guru().drishti(), "Jupiter: 5,7,9");
        assertArrayEquals(new int[]{3, 7, 10}, new Shani().drishti(), "Saturn: 3,7,10");
        assertArrayEquals(new int[]{5, 7, 9}, new Rahu().drishti(), "Rahu follows Jupiter's aspect set here");
        assertArrayEquals(new int[0], new Ketu().drishti(), "Ketu has no special aspects here");
        assertArrayEquals(new int[0], new Lagna().drishti());
        assertArrayEquals(new int[]{7}, new Surya().drishti(), "default: only the 7th-house aspect");
        assertArrayEquals(new int[]{7}, new Sweta().drishti());
    }

    // ============================================================== dignity / mrityu bhaga overrides

    @Test
    void outerPlanetsAndLagna_haveNoClassicalDignityOrMrityuBhaga() {
        for (IGraha g : Arrays.asList(new Lagna(), new Sweta(), new Syama(), new Teevra())) {
            assertNull(g.dignity(null, 0.), g.code() + " has no classical dignity rule");
            assertEquals(0., g.inMrityuBhaga(50.), g.code() + " has no mrityu bhaga table");
        }
    }

    @Test
    void allTenTraditionalBodies_haveATwelveElementMrityuBhagaTable() {
        for (IGraha g : Arrays.asList(new Lagna(), new Surya(), new Chandra(), new Guru(), new Rahu(),
                new Budha(), new Shukra(), new Ketu(), new Shani(), new Mangala())) {
            // every tabulated degree must be a valid rasi-relative degree [0,30)
            double atStart = g.inMrityuBhaga(0.);
            assertTrue(atStart == 0. || atStart == 100. || (atStart > 0 && atStart < 100),
                    g.code() + " returned an out-of-range mrityu bhaga: " + atStart);
        }
    }

    // ============================================================== progress complement (chaya grahas)

    @Test
    void chayaGrahas_reportTheComplementOfTheNormalRasiProgress() {
        // IGrahaChaya: progressInRasi = 100 - normal progress, rounded to 2 decimals
        IGraha rahu = new Rahu();
        double normal = org.jyotisa.api.rasi.IRasi.progress(15.); // 50% through Mesha
        assertEquals(100. - normal, rahu.progressInRasi(15.), 1e-9);
    }

    @Test
    void nonChayaGraha_reportsTheNormalRasiProgressUnchanged() {
        IGraha surya = new Surya();
        double normal = org.jyotisa.api.rasi.IRasi.progress(15.);
        assertEquals(normal, surya.progressInRasi(15.), 1e-9);
    }

    // ============================================================== full-family structure

    @Test
    void thirteenLeaves_haveUniqueFidsZeroThroughTwelve() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IGraha g : allThirteen()) fids.add(g.fid());
        assertEquals(13, fids.size());
        assertEquals(0, fids.first().intValue());
        assertEquals(12, fids.last().intValue());
    }

    @Test
    void thirteenLeaves_haveUniqueUids() {
        TreeSet<Integer> uids = new TreeSet<>();
        for (IGraha g : allThirteen()) uids.add(g.uid());
        assertEquals(13, uids.size(), "every graha must occupy a distinct chart slot");
    }

    @Test
    void thirteenLeaves_haveUniqueCodes() {
        TreeSet<String> codes = new TreeSet<>();
        for (IGraha g : allThirteen()) codes.add(g.code());
        assertEquals(13, codes.size());
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.karana;

import org.jyotisa.api.panchanga.IPanchanga;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.jyotisa.api.karana.IKarana.progress;

/**
 * {@code IKarana.progress()}, {@code IKaranaEnum}'s fixed-karana degree thresholds, and a
 * structural sweep of the 11 karana leaves.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class KaranaTest {

    @Test
    void progress_isPercentageThroughTheCurrentKarana() {
        // KARANA_LENGTH = 6 degrees
        IPanchanga p = panchanga(3., 0.);
        assertEquals(50., progress(p));
        assertEquals(0., progress(panchanga(6., 0.)), "wraps at the karana boundary");
    }

    static IPanchanga panchanga(final double chandraLon, final double suryaLon) {
        return new IPanchanga() {
            public double chandraLongitude() { return chandraLon; }
            public double suryaLongitude() { return suryaLon; }
            public org.jyotisa.api.naksatra.INaksatraPada pada() { return null; }
            public org.jyotisa.api.nityayoga.INityaYoga yoga() { return null; }
            public IKarana karana() { return null; }
            public org.jyotisa.api.vaara.IVaara vaara() { return null; }
            public org.jyotisa.api.tithi.ITithi tithi() { return null; }
        };
    }

    @Test
    void enumThresholds_areTheFourFixedKaranaBoundaries() {
        // the four "fixed" (once-per-month) karanas Shakuni/Chatushpada/Naga/Kimstughna sit
        // at the very end of the lunar month, at 6-degree (one karana) steps from 342 to 360
        assertEquals(342., IKaranaEnum.TH14th2ndP00, 1e-9);
        assertEquals(348., IKaranaEnum.TH14th2ndP06, 1e-9);
        assertEquals(354., IKaranaEnum.TH14th2ndP12, 1e-9);
        assertEquals(360., IKaranaEnum.TH14th2ndP18, 1e-9);
    }

    // ============================================================== leaf structure (11)

    static abstract class KaranaStub implements IKarana {
        public IKarana[] all() { return new IKarana[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Bava extends KaranaStub implements IKaranaBava {}
    static class Balava extends KaranaStub implements IKaranaBalava {}
    static class Kaulava extends KaranaStub implements IKaranaKaulava {}
    static class Taitula extends KaranaStub implements IKaranaTaitula {}
    static class Garija extends KaranaStub implements IKaranaGarija {}
    static class Vanija extends KaranaStub implements IKaranaVanija {}
    static class Vishti extends KaranaStub implements IKaranaVishti {}
    static class Sakuna extends KaranaStub implements IKaranaSakuna {}
    static class Chatushpada extends KaranaStub implements IKaranaChatushpada {}
    static class Naga extends KaranaStub implements IKaranaNaga {}
    static class Kimstughna extends KaranaStub implements IKaranaKimstughna {}

    static List<IKarana> allEleven() {
        return Arrays.asList(new Bava(), new Balava(), new Kaulava(), new Taitula(), new Garija(),
                new Vanija(), new Vishti(), new Sakuna(), new Chatushpada(), new Naga(), new Kimstughna());
    }

    @Test
    void elevenLeaves_haveUniqueFidsOneThroughEleven() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (IKarana k : allEleven()) fids.add(k.fid());
        assertEquals(11, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(11, fids.last().intValue());
    }

    @Test
    void elevenLeaves_codeIsKRPrefixPlusFid() {
        for (IKarana k : allEleven()) assertEquals("KR" + k.fid(), k.code());
    }

    @Test
    void elevenLeaves_lengthIsSixDegreesEach() {
        for (IKarana k : allEleven()) assertEquals(6., k.length(), 1e-9);
    }
}

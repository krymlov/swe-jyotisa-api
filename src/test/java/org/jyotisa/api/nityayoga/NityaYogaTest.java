/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.nityayoga;

import org.jyotisa.api.panchanga.IPanchanga;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.jyotisa.api.nityayoga.INityaYoga.progress;

/**
 * {@code INityaYoga.progress()} - notably keyed on {@code chandraLongitude() +
 * suryaLongitude()} (a sum, unlike Tithi/Karana which use the difference) - plus a
 * structural sweep of the 27 nityayoga leaves.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class NityaYogaTest {

    static final double NY_LEN = 360. / 27.;

    @Test
    void progress_usesTheSumOfChandraAndSuryaLongitudes() {
        IPanchanga p = panchanga(NY_LEN / 4, NY_LEN / 4); // sum = NY_LEN/2
        assertEquals(50., progress(p));
    }

    @Test
    void progress_wrapsAtTheNityaYogaBoundary() {
        assertEquals(0., progress(panchanga(NY_LEN, 0.)), "wraps at the boundary, not 100%");
    }

    static IPanchanga panchanga(final double chandraLon, final double suryaLon) {
        return new IPanchanga() {
            public double chandraLongitude() { return chandraLon; }
            public double suryaLongitude() { return suryaLon; }
            public org.jyotisa.api.naksatra.INaksatraPada pada() { return null; }
            public INityaYoga yoga() { return null; }
            public org.jyotisa.api.karana.IKarana karana() { return null; }
            public org.jyotisa.api.vaara.IVaara vaara() { return null; }
            public org.jyotisa.api.tithi.ITithi tithi() { return null; }
        };
    }

    // ============================================================== leaf structure (27)

    static abstract class NityaYogaStub implements INityaYoga {
        public INityaYoga[] all() { return new INityaYoga[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Vishkambha extends NityaYogaStub implements INityaYogaVishkambha {}
    static class Preeti extends NityaYogaStub implements INityaYogaPreeti {}
    static class Ayushmana extends NityaYogaStub implements INityaYogaAyushmana {}
    static class Saubhagya extends NityaYogaStub implements INityaYogaSaubhagya {}
    static class Sobhana extends NityaYogaStub implements INityaYogaSobhana {}
    static class Atiganda extends NityaYogaStub implements INityaYogaAtiganda {}
    static class Sukarman extends NityaYogaStub implements INityaYogaSukarman {}
    static class Dhriti extends NityaYogaStub implements INityaYogaDhriti {}
    static class Shula extends NityaYogaStub implements INityaYogaShula {}
    static class Ganda extends NityaYogaStub implements INityaYogaGanda {}
    static class Vriddhi extends NityaYogaStub implements INityaYogaVriddhi {}
    static class Dhruva extends NityaYogaStub implements INityaYogaDhruva {}
    static class Vyaghata extends NityaYogaStub implements INityaYogaVyaghata {}
    static class Harshana extends NityaYogaStub implements INityaYogaHarshana {}
    static class Vajra extends NityaYogaStub implements INityaYogaVajra {}
    static class Siddhi extends NityaYogaStub implements INityaYogaSiddhi {}
    static class Vyatipata extends NityaYogaStub implements INityaYogaVyatipata {}
    static class Variyan extends NityaYogaStub implements INityaYogaVariyan {}
    static class Parigha extends NityaYogaStub implements INityaYogaParigha {}
    static class Shiva extends NityaYogaStub implements INityaYogaShiva {}
    static class Siddha extends NityaYogaStub implements INityaYogaSiddha {}
    static class Sadhya extends NityaYogaStub implements INityaYogaSadhya {}
    static class Shubha extends NityaYogaStub implements INityaYogaShubha {}
    static class Shukla extends NityaYogaStub implements INityaYogaShukla {}
    static class Brahma extends NityaYogaStub implements INityaYogaBrahma {}
    static class Indra extends NityaYogaStub implements INityaYogaIndra {}
    static class Vaidhriti extends NityaYogaStub implements INityaYogaVaidhriti {}

    static List<INityaYoga> allTwentySeven() {
        return Arrays.asList(new Vishkambha(), new Preeti(), new Ayushmana(), new Saubhagya(), new Sobhana(),
                new Atiganda(), new Sukarman(), new Dhriti(), new Shula(), new Ganda(), new Vriddhi(),
                new Dhruva(), new Vyaghata(), new Harshana(), new Vajra(), new Siddhi(), new Vyatipata(),
                new Variyan(), new Parigha(), new Shiva(), new Siddha(), new Sadhya(), new Shubha(),
                new Shukla(), new Brahma(), new Indra(), new Vaidhriti());
    }

    @Test
    void twentySevenLeaves_haveUniqueFidsOneThroughTwentySeven() {
        TreeSet<Integer> fids = new TreeSet<>();
        for (INityaYoga y : allTwentySeven()) fids.add(y.fid());
        assertEquals(27, fids.size());
        assertEquals(1, fids.first().intValue());
        assertEquals(27, fids.last().intValue());
    }

    @Test
    void twentySevenLeaves_codeIsNYPrefixPlusFid() {
        for (INityaYoga y : allTwentySeven()) assertEquals("NY" + y.fid(), y.code());
    }

    @Test
    void twentySevenLeaves_lengthMatchesNaksatraLength() {
        for (INityaYoga y : allTwentySeven()) assertEquals(NY_LEN, y.length(), 1e-9);
    }
}

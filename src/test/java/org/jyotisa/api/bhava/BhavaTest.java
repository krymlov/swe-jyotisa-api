/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.bhava;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.jyotisa.api.bhava.IBhava.progress;

/**
 * {@code IBhava.progress()} and the 12-leaf x 10-flag classification matrix (trikona, kendra,
 * apoklima, upachaya, apachaya, dusthana, panapara, chaturasra, maraka, trishadaya) - each
 * flag must be true for exactly its classically-documented house numbers and false
 * everywhere else, cross-checked against the javadoc comment on every leaf file.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class BhavaTest {

    @Test
    void progress_isPercentageThroughTheCurrentBhava() {
        assertEquals(0., progress(0.));
        assertEquals(50., progress(15.));
        assertEquals(0., progress(30.), "wraps into the next bhava at 0%, not 100%");
    }

    static abstract class BhavaStub implements IBhava {
        public IBhava[] all() { return new IBhava[0]; }
        public int ordinal() { return 0; }
        public String name() { return getClass().getSimpleName(); }
    }

    static class Tanu extends BhavaStub implements IBhavaTanu {}
    static class Dhana extends BhavaStub implements IBhavaDhana {}
    static class Bhratri extends BhavaStub implements IBhavaBhratri {}
    static class Matri extends BhavaStub implements IBhavaMatri {}
    static class Putra extends BhavaStub implements IBhavaPutra {}
    static class Ari extends BhavaStub implements IBhavaAri {}
    static class Kama extends BhavaStub implements IBhavaKama {}
    static class Ayur extends BhavaStub implements IBhavaAyur {}
    static class Dharma extends BhavaStub implements IBhavaDharma {}
    static class Karma extends BhavaStub implements IBhavaKarma {}
    static class Labha extends BhavaStub implements IBhavaLabha {}
    static class Vyaya extends BhavaStub implements IBhavaVyaya {}

    static List<IBhava> allTwelve() {
        return Arrays.asList(new Tanu(), new Dhana(), new Bhratri(), new Matri(), new Putra(), new Ari(),
                new Kama(), new Ayur(), new Dharma(), new Karma(), new Labha(), new Vyaya());
    }

    @Test
    void twelveLeaves_haveUniqueFidsOneThroughTwelveMatchingHouseNumber() {
        List<IBhava> all = allTwelve();
        for (int i = 0; i < 12; i++) {
            assertEquals(i + 1, all.get(i).fid(), all.get(i).getClass().getSimpleName());
        }
    }

    @Test
    void twelveLeaves_lengthIsThirtyDegreesEach() {
        for (IBhava b : allTwelve()) assertEquals(30., b.length(), 1e-9);
    }

    // The classical house groupings, as (1-based) house numbers - the source of truth for
    // the parameterized checks below.
    static final Set<Integer> TRIKONA = new HashSet<>(Arrays.asList(1, 5, 9));
    static final Set<Integer> KENDRA = new HashSet<>(Arrays.asList(1, 4, 7, 10));
    static final Set<Integer> APOKLIMA = new HashSet<>(Arrays.asList(3, 6, 9, 12));
    static final Set<Integer> UPACHAYA = new HashSet<>(Arrays.asList(3, 6, 10, 11));
    static final Set<Integer> APACHAYA = new HashSet<>(Arrays.asList(1, 2, 4, 7, 8));
    static final Set<Integer> DUSTHANA = new HashSet<>(Arrays.asList(6, 8, 12));
    static final Set<Integer> PANAPARA = new HashSet<>(Arrays.asList(2, 5, 8, 11));
    static final Set<Integer> CHATURASRA = new HashSet<>(Arrays.asList(4, 8));
    static final Set<Integer> MARAKA = new HashSet<>(Arrays.asList(2, 7));
    static final Set<Integer> TRISHADAYA = new HashSet<>(Arrays.asList(3, 6, 11));

    static List<Object[]> flagCases() {
        List<Object[]> cases = new ArrayList<>();
        cases.add(new Object[]{"trikona", (Predicate<IBhava>) IBhava::trikona, TRIKONA});
        cases.add(new Object[]{"kendra", (Predicate<IBhava>) IBhava::kendra, KENDRA});
        cases.add(new Object[]{"apoklima", (Predicate<IBhava>) IBhava::apoklima, APOKLIMA});
        cases.add(new Object[]{"upachaya", (Predicate<IBhava>) IBhava::upachaya, UPACHAYA});
        cases.add(new Object[]{"apachaya", (Predicate<IBhava>) IBhava::apachaya, APACHAYA});
        cases.add(new Object[]{"dusthana", (Predicate<IBhava>) IBhava::dusthana, DUSTHANA});
        cases.add(new Object[]{"panapara", (Predicate<IBhava>) IBhava::panapara, PANAPARA});
        cases.add(new Object[]{"chaturasra", (Predicate<IBhava>) IBhava::chaturasra, CHATURASRA});
        cases.add(new Object[]{"maraka", (Predicate<IBhava>) IBhava::maraka, MARAKA});
        cases.add(new Object[]{"trishadaya", (Predicate<IBhava>) IBhava::trishadaya, TRISHADAYA});
        return cases;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("flagCases")
    void classificationFlag_isTrueForExactlyItsHousesAndFalseElsewhere(
            String flagName, Predicate<IBhava> flag, Set<Integer> expectedHouses) {
        List<IBhava> all = allTwelve();
        for (IBhava b : all) {
            boolean expected = expectedHouses.contains(b.fid());
            assertEquals(expected, flag.test(b), flagName + " at house " + b.fid());
        }
    }
}

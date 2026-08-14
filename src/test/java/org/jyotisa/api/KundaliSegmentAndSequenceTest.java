/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api;

import org.junit.jupiter.api.Test;
import org.swisseph.api.ISweSegment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.swisseph.api.ISweEnum.NIL_CD;
import static org.swisseph.api.ISweEnum.NIL_FID;

/**
 * The two mechanisms every {@code IKundaliSequence} family (graha, rasi, naksatra, tithi,
 * karana, nityayoga, bhava, ...) is built on: {@link IKundaliSegment#segment()} - the
 * {@code [(fid-1)*length, fid*length)} degree span - and {@code ISweEnumSequence.follow()}
 * (declared in swe-java-lib, exercised here through {@link IKundaliSequence}) - the
 * next/previous wraparound used by every family's iterator. A bug in either is invisible in
 * any single leaf's own tests but corrupts every family at once, so it is pinned here with a
 * small synthetic sequence rather than relying on one real family to exercise every case.
 * <p>
 * {@code ordinal()} is the item's own index within {@code all()} - {@code follow()} indexes
 * {@code all()} directly with it, exactly like {@code java.lang.Enum#ordinal()} does with
 * {@code values()}. It is deliberately kept separate from {@code fid()} below (which starts
 * wherever a test wants) to prove {@code follow()} only ever relies on {@code ordinal()}.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
class KundaliSegmentAndSequenceTest {

    static class Item implements IKundaliSequence<Item> {
        final int fid;
        final int ordinal;
        final String code;
        Item[] all;
        Item first, last;

        Item(int fid, int ordinal, String code) {
            this.fid = fid;
            this.ordinal = ordinal;
            this.code = code;
        }

        @Override public int fid() { return fid; }
        @Override public String code() { return code; }
        @Override public String name() { return code; }
        @Override public double length() { return 10.; }
        @Override public Item[] all() { return all; }
        @Override public int ordinal() { return ordinal; }
        @Override public Item first() { return null != first ? first : all[0]; }
        @Override public Item last() { return null != last ? last : all[all.length - 1]; }
    }

    static Item[] chain(int firstFid, int count) {
        Item[] items = new Item[count];
        for (int i = 0; i < count; i++) items[i] = new Item(firstFid + i, i, "C" + (firstFid + i));
        for (Item item : items) item.all = items;
        return items;
    }

    /**
     * Mirrors {@code ETithi}: a NIL sentinel occupies array index/ordinal 0, but
     * {@code first()}/{@code last()} are overridden to skip it, exactly like
     * {@code ETithi.first()==SHUKLA_PRATIPADA} (ordinal 1) despite {@code NIL} sitting at
     * ordinal 0 in {@code values()}.
     */
    static Item[] chainWithSkippedNilSentinel(int count) {
        Item[] items = new Item[count + 1];
        items[0] = new Item(NIL_FID, 0, NIL_CD);
        for (int i = 1; i <= count; i++) items[i] = new Item(i, i, "C" + i);
        for (Item item : items) {
            item.all = items;
            item.first = items[1];
            item.last = items[count];
        }
        return items;
    }

    // ============================================================== segment()

    @Test
    void segment_spansFidMinusOneToFidTimesLength() {
        Item item = chain(1, 3)[1]; // fid=2, length=10
        ISweSegment seg = item.segment();
        assertEquals(10., seg.start(), 1e-9);
        assertEquals(20., seg.close(), 1e-9);
    }

    @Test
    void segment_firstFidStartsAtZero() {
        Item item = chain(1, 3)[0]; // fid=1
        ISweSegment seg = item.segment();
        assertEquals(0., seg.start(), 1e-9);
        assertEquals(10., seg.close(), 1e-9);
    }

    @Test
    void segment_theReservedNilCodeIsAlwaysTheZeroSegment() {
        // IKundaliSegment special-cases fid==0 && code.equals(NIL_CD) to the zero segment,
        // regardless of length() - this is how every family represents "no value yet"
        Item nil = new Item(NIL_FID, 0, NIL_CD);
        nil.all = new Item[]{nil};
        ISweSegment seg = nil.segment();
        assertEquals(0., seg.start(), 1e-9);
        assertEquals(0., seg.close(), 1e-9);
    }

    // ================================================================ follow()/wraparound

    @Test
    void following_stepsForwardByOne() {
        Item[] items = chain(1, 5);
        assertSame(items[1], items[0].following());
        assertSame(items[4], items[3].following());
    }

    @Test
    void previous_stepsBackwardByOne() {
        Item[] items = chain(1, 5);
        assertSame(items[3], items[4].previous());
        assertSame(items[0], items[1].previous());
    }

    @Test
    void following_wrapsFromLastToFirst_whenFirstOrdinalIsZero() {
        // no NIL sentinel: first().ordinal()==0, matching e.g. IRasi/INaksatra style families
        Item[] items = chain(0, 4);
        assertSame(items[0], items[3].following(), "last + 1 must wrap to first");
    }

    @Test
    void previous_wrapsFromFirstToLast_whenFirstOrdinalIsZero() {
        Item[] items = chain(0, 4);
        assertSame(items[3], items[0].previous(), "first - 1 must wrap to last");
    }

    @Test
    void following_wrapsFromLastToFirst_whenFirstOrdinalIsOne() {
        // NIL sentinel at ordinal 0, real values start at ordinal 1 (first()/last()
        // overridden to skip the sentinel) - matches ETithi exactly
        Item[] items = chainWithSkippedNilSentinel(4); // NIL(0), C1(1)..C4(4)
        assertSame(items[1], items[4].following(), "last + 1 must wrap to first, not to NIL");
        assertSame(items[4], items[1].previous(), "first - 1 must wrap to last, not to NIL");
    }

    @Test
    void follow_byMultipleStepsWrapsCorrectly_bothSentinelStyles() {
        Item[] zeroBased = chain(0, 5); // 0..4
        assertSame(zeroBased[1], zeroBased[4].follow(2), "4 + 2 = 6, wraps to 1 (mod 5)");
        assertSame(zeroBased[3], zeroBased[0].follow(-2), "0 - 2 wraps to 3 (mod 5)");

        Item[] nilSkipped = chainWithSkippedNilSentinel(5); // NIL(0), C1(1)..C5(5)
        assertSame(nilSkipped[2], nilSkipped[5].follow(2), "5 + 2 = 7, wraps to 2, not to NIL");
        assertSame(nilSkipped[4], nilSkipped[1].follow(-2), "1 - 2 wraps to 4, not to NIL");
    }

    @Test
    void follow_byExactlyTheChainLengthReturnsToTheSameElement() {
        Item[] items = chain(1, 7);
        for (Item item : items) {
            assertSame(item, item.follow(items.length), "a full lap must return to the start");
        }

        Item[] nilSkipped = chainWithSkippedNilSentinel(7);
        for (int i = 1; i <= 7; i++) {
            assertSame(nilSkipped[i], nilSkipped[i].follow(7), "a full lap must return to the start");
        }
    }
}

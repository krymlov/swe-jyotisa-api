/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.bhava;

import org.jyotisa.api.graha.IGraha;
import org.swisseph.api.ISweSegment;

import java.io.Serializable;

/**
 * The <b>Bhava Chalit</b> chart - where a graha falls once the bhavas are the real, unequal arcs
 * of the sky rather than whole signs.
 *
 * <h2>The scheme</h2>
 * <b>Porphyry cusps read as Sripati bhavas.</b> Each quadrant of the sky - ascendant to nadir,
 * nadir to descendant, and so on - is trisected, and the resulting twelve points are the
 * <b>middles</b> of the bhavas, not their beginnings. The ascendant is therefore the
 * {@link #madhya(IBhava) madhya} of the first bhava, sitting halfway along it, and a bhava runs
 * from the midpoint it shares with the previous one to the midpoint it shares with the next.
 * <p>
 * This is the construction Jagannatha Hora prints under "Бхава чаліт", and
 * {@code JhoraChalitTest} holds this library against it.
 *
 * <h2>Why it can disagree with the {@code Bhava} column of the report</h2>
 * That column is the <b>whole sign</b> bhava - the sign counted from the ascendant's own sign -
 * which is what Jyotisha usually means by a bhava and what the rest of this library uses. Chalit
 * is the other reading, and the two genuinely differ: a graha late in the sign before the
 * ascendant's is in whole-sign bhava 12 but, being within half a bhava of the ascendant, in
 * chalit bhava 1. Neither is a correction of the other.
 *
 * <h2>It needs the ascendant and the midheaven</h2>
 * Both come from {@code ascmc}, so a chart built without an ascendant has no chalit -
 * {@link #isCalculated()} says so, and every accessor then answers NIL or an empty result rather
 * than a number counted from a point that does not exist.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IBhavaChalit extends Serializable {

    /**
     * Whether the chart supplied an ascendant and a midheaven to build this from.
     *
     * @return false for a chart built with {@code buildAscendant = false}
     */
    boolean isCalculated();

    /**
     * The bhava madhya - the trisection point itself, and the <b>middle</b> of the bhava rather
     * than its start. For the first bhava this is the ascendant.
     *
     * @return absolute longitude in the chart's own zodiac, or NaN when not calculated
     */
    double madhya(IBhava bhava);

    /** where the bhava begins: halfway between this madhya and the previous one */
    double start(IBhava bhava);

    /** where the bhava ends: halfway between this madhya and the next one */
    double close(IBhava bhava);

    /**
     * The bhava as an arc. It may wrap past 360&deg; - {@code close()} is measured forward from
     * {@code start()}, so the twelfth bhava's segment closes past the end of the zodiac.
     */
    ISweSegment segment(IBhava bhava);

    /** the arc's width in degrees; the twelve are unequal and sum to 360 */
    double length(IBhava bhava);

    /**
     * The bhava a longitude falls in.
     *
     * @return the bhava, or {@code EBhava.NIL}'s bhava when there is no ascendant to count from
     */
    IBhava bhava(double longitude);

    /** the bhava a graha of this chart falls in, or NIL if the graha was never calculated */
    IBhava bhava(IGraha graha);

    /** the grahas that fall in a bhava, in the chart's own object order */
    IGraha[] grahas(IBhava bhava);
}

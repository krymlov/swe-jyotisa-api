/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.arudha;

import org.jyotisa.api.IKundaliSequence;

/**
 * One <b>Arudha Pada</b> - the "perceived" image of a bhava, {@code A1} through {@code A12}.
 *
 * <h2>What a member of this family is</h2>
 * It is a <b>label</b>, not a position: {@code A7} means "the arudha of the seventh bhava", and
 * which rasi that lands in is a property of a chart rather than of the label. The chart's answer
 * lives in {@link IArudhaPadas}, exactly as a rasi's own answer lives in a built {@code Kundali}
 * rather than in {@code IRasi}. That is why {@link #length()} is 0 and the segment is empty -
 * there is no fixed arc of the zodiac to point at.
 *
 * <h2>The two with names of their own</h2>
 * {@code A1} is the <b>Arudha Lagna</b> and {@code A12} the <b>Upapada Lagna</b>; both carry that
 * name as a second enum constant, so {@link org.swisseph.api.ISweEnum#label()} answers
 * {@code "AL"} and {@code "UL"} while {@link #code()} stays {@code "A1"} and {@code "A12"}. The
 * other ten have only their code.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see IArudhaPadas
 */
public interface IArudhaPada extends IKundaliSequence<IArudhaPada> {

    String A01_CD = "A1";
    String A02_CD = "A2";
    String A03_CD = "A3";
    String A04_CD = "A4";
    String A05_CD = "A5";
    String A06_CD = "A6";
    String A07_CD = "A7";
    String A08_CD = "A8";
    String A09_CD = "A9";
    String A10_CD = "A10";
    String A11_CD = "A11";
    String A12_CD = "A12";

    /** the Arudha Lagna, the arudha of the first bhava */
    String AL_CD = "AL";

    /** the Upapada Lagna, the arudha of the twelfth bhava */
    String UL_CD = "UL";

    /**
     * An arudha pada names a bhava, it does not occupy an arc - so it has no length, the same
     * choice {@code IGraha} and {@code IUpagraha} make for a point.
     */
    @Override
    default double length() {
        return 0.;
    }
}

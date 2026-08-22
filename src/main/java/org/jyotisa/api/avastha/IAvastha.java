/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

import org.jyotisa.api.IKundaliSequence;

/**
 * One <b>Baaladi Avastha</b> - how far through its own life a graha stands in the sign it
 * occupies, from infant to dead.
 *
 * <h2>The rule</h2>
 * The sign is cut into five parts of 6&deg;. In an <b>odd</b> sign they run forward - infant,
 * youth, adult, elder, dead - and in an <b>even</b> sign backward, so a graha at the very start of
 * an even sign is already dead and one at its end is newly born. It is the same odd/even
 * reversal the hora division uses.
 * <p>
 * The strength runs up and down again: {@code Yuva}, the middle part, is the strongest and both
 * ends are weak.
 *
 * <h2>This is the age avastha, not the only one</h2>
 * Jagannatha Hora prints four families side by side - this one under "age", the
 * <i>jagradadi</i> three under "wakefulness", the <i>deeptadi</i> and <i>lajjitadi</i> moods, and
 * the twelve-fold <i>shayanadi</i> activity. Only the first is a single value that follows from
 * the longitude alone; the moods are a set per graha and the other two need the dignity and a
 * further computation. This family is the one implemented.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvastha extends IKundaliSequence<IAvastha> {

    String AV1_CD = "AV1";
    String AV2_CD = "AV2";
    String AV3_CD = "AV3";
    String AV4_CD = "AV4";
    String AV5_CD = "AV5";

    /** each avastha is a fifth of a sign */
    @Override
    default double length() {
        return 6.;
    }
}

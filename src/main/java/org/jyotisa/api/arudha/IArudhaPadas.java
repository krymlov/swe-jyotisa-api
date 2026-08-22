/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.arudha;

import org.jyotisa.api.bhava.IBhava;
import org.jyotisa.api.rasi.IRasi;

import java.io.Serializable;

/**
 * The twelve <b>Arudha Padas</b> of one chart - which rasi each bhava's "perceived image" falls
 * in.
 *
 * <h2>The rule</h2>
 * For a bhava, count how far its lord has gone from it, then count that same distance again from
 * the lord. Both counts are inclusive, so a lord sitting in its own bhava counts as one:
 *
 * <pre>
 * n = the distance from the bhava's rasi to its lord's rasi, counting both
 * A = the rasi n signs from the lord's, counting the lord's as the first
 * </pre>
 *
 * <h2>And its one exception, which really does have two halves</h2>
 * If {@code A} lands on the bhava's own rasi, or on the seventh from it, the arudha is taken as
 * the <b>tenth</b> from {@code A} instead. An arudha is meant to be the image a bhava casts, and
 * a bhava cannot cast an image onto itself or onto the point directly opposite.
 * <p>
 * The two halves are not the same case. {@code A} falls on the bhava itself when the lord is in
 * the first or the seventh from it, and on the seventh from the bhava when the lord is in the
 * fourth or the tenth - because {@code A} advances by twice whatever the lord did. Implementations
 * that only guard the first half get the fourth and tenth wrong, which is checkable: in the 1970
 * reference chart it is {@code A5} that turns on it.
 *
 * <h2>Two of them have names of their own</h2>
 * {@code A1} is the <b>Arudha Lagna</b> and {@code A12} the <b>Upapada Lagna</b>;
 * {@link #arudhaLagna()} and {@link #upapadaLagna()} are there so a caller need not remember
 * which index that is.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see IArudhaPada
 */
public interface IArudhaPadas extends Serializable {

    /**
     * Whether the chart supplied an ascendant to count the bhavas from.
     *
     * @return false for a chart built with {@code buildAscendant = false}
     */
    boolean isCalculated();

    /**
     * The rasi an arudha pada falls in.
     *
     * @return the rasi, or the NIL rasi when the pada is NIL or the chart has no ascendant
     */
    IRasi rasi(IArudhaPada pada);

    /** the same, addressed by the bhava whose image it is */
    IRasi rasi(IBhava bhava);

    /** the bhava - counted from the ascendant, whole sign - that an arudha pada falls in */
    IBhava bhava(IArudhaPada pada);

    /** {@code A1} - the Arudha Lagna, the perceived self */
    IRasi arudhaLagna();

    /** {@code A12} - the Upapada Lagna, the perceived marriage */
    IRasi upapadaLagna();

    /** all twelve, indexed 1..12 by {@link IArudhaPada#fid()}; index 0 is the NIL rasi */
    IRasi[] all();
}

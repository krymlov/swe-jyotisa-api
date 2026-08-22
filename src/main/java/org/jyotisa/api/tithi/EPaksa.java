/*
* Copyright (C) By the Author
* Author    Yura Krymlov
* Created   2019-09
*/

package org.jyotisa.api.tithi;


import org.jyotisa.api.tithi.IPaksa;

import static org.swisseph.api.ISweConstants.*;

/**
 * <pre>
 * Paksha (or pakṣa: Sanskrit: पक्ष) refers to a fortnight or a lunar phase in a month.<br>
 * The first fortnight between New Moon Day and Full Moon Day is called "Gaura Paksha" or Shukla Paksha, the
 * period of the brightening moon (waxing moon), and the second fortnight of the month is called Krishna Paksha
 * 
 * Range 0..1, or {k, K, krsna, Gaura, g, G, gaura}
 * <pre>
 * 
 * @author Yura Krymlov
 * @version 1.1, 2019-10
 */
public enum EPaksa implements IPaksa {
    KRSNA, // 0 for Krsna Paksa
    GAURA; // 1 for Gaura Paksa

    @Override
    public int fid() {
        return ordinal();
    }
    
    @Override
    public int uid() {
        return ordinal();
    }
    
    @Override
    public String code() {
        return name();
    }

    @Override
    public boolean krsna() {
        return this == KRSNA;
    }

    @Override
    public boolean gaura() {
        return this == GAURA;
    }

    /**
     * The fortnight the Sun-Moon elongation falls in.
     * <p>
     * <b>An undetermined longitude is refused, not answered.</b> Without the guard
     * {@code (int) NaN} is <b>0</b> in Java, so a NaN elongation resolved to tithi 1 and came
     * back as {@link #GAURA} - a real fortnight, indistinguishable downstream from a computed
     * one. The six {@code byLongitude} lookups in {@code swe-jyotisa-lib} answer their family's
     * reserved NIL in that situation; this family declares none - {@code KRSNA} and {@code GAURA}
     * are the only two fortnights there are, and neither means "unknown" - so it fails loudly
     * instead, exactly as {@code ISweEnum.nilOrFail} does for {@code EGraha} and {@code ELagna}.
     *
     * @throws IllegalArgumentException if either longitude is not a number
     */
    public static IPaksa byLongitude(final double suryaLongitude, final double chandraLongitude) {
        if (Double.isNaN(suryaLongitude) || Double.isNaN(chandraLongitude)) {
            throw new IllegalArgumentException("An undetermined longitude names no paksa"
                    + " (surya=" + suryaLongitude + ", chandra=" + chandraLongitude + "),"
                    + " and this family declares no NIL member to answer with");
        }

        double diff = chandraLongitude - suryaLongitude;
        if ( d0 > diff ) diff += d360;

        int tithiIdx = 1 + (int)(diff / d12);
        if ( tithiIdx > 15 ) return KRSNA;
        else return GAURA;
    }
}

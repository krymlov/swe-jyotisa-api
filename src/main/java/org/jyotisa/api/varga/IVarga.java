/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

import org.jyotisa.api.IKundaliSequence;
import org.jyotisa.api.rasi.IRasi;

import static org.jyotisa.api.rasi.IRasi.rasiDegree;
import static org.jyotisa.api.rasi.IRasi.rasiFid0;
import static org.swisseph.api.ISweConstants.CHAKRA_LENGTH;
import static org.swisseph.api.ISweConstants.RASI_LENGTH;
import static org.swisseph.utils.IModuloUtils.fix360;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IVarga extends IKundaliSequence<IVarga> {
    IRasi rasi(double longitudeInD1);

    @Override
    default double length() {
        return CHAKRA_LENGTH;
    }

    /**
     * For finding longitude of an object in any divisional chart (varga chakra)
     * multiply the degrees, minutes, seconds by the number of the Varga
     * (2 for hora, 3 for drekkana and so on) now leave the completed signs
     * and retain the degrees, minutes, seconds as the longitudeInD1
     * of the object for that divisional chart.
     *
     * @return longitude in a sign of the varga
     */
    default double rasiLongitude(double longitudeInD1) {
        return rasiDegree(virtualDegree(longitudeInD1));
    }

    /**
     * For finding a degree (virtual longitude) of an object in any divisional chart (varga chakra)
     * multiply the degrees, minutes, seconds by the number of the Varga (2 for hora and so on).
     *
     * @return degree in a whole varga chakra. It is a virtual longitude needed to calculate {@link IRasi}
     * and you should not use it as a real longitude
     */
    default double virtualDegree(final double longitudeInD1) {
        // the D1 special case this used to carry was a shortcut, not a rule: multiplying by
        // fid() == 1 is the identity, so the general form already answers it - and, unlike the
        // shortcut, it normalises
        return fix360(longitudeInD1 * fid());
    }

    /**
     * @return longitude in a whole varga chakra, always in [0, 360)
     *
     * <p>The {@code fid() == 1} shortcut is kept because for D1 the general formula reduces to
     * {@code fix360(longitudeInD1)} exactly, and taking it directly avoids the intermediate
     * {@code (rasi - 1) * 30 + degree} addition, which can differ in the last ULP.
     *
     * <p><b>It used to return the input verbatim</b>, with no {@code fix360} - making D1 the only
     * varga of the 23 that could hand back a value outside [0, 360). That is not merely
     * theoretical: {@code swe_calc} can return a longitude a few ULPs <i>below</i> zero (the
     * reason {@code IModuloUtils.modulo} carries a tolerance snap at all), and such a value fell
     * through every band of {@code GrahaXxx.dignity(D1, ...)} and answered {@code null}, while
     * D2..D144 answered correctly from the same input.
     */
    default double chakraLongitude(final double longitudeInD1) {
        if (1 == fid()) return fix360(longitudeInD1);
        return fix360(((rasi(longitudeInD1).fid() - 1) * RASI_LENGTH)
                + rasiLongitude(longitudeInD1));
    }

    String D01_CD = "D1";
    String D02_CD = "D2";
    String D03_CD = "D3";
    String D04_CD = "D4";
    String D05_CD = "D5";
    String D06_CD = "D6";
    String D07_CD = "D7";
    String D08_CD = "D8";
    String D09_CD = "D9";
    String D10_CD = "D10";
    String D11_CD = "D11";
    String D12_CD = "D12";
    String D16_CD = "D16";
    String D20_CD = "D20";
    String D24_CD = "D24";
    String D27_CD = "D27";
    String D30_CD = "D30";
    String D40_CD = "D40";
    String D45_CD = "D45";
    String D60_CD = "D60";
    String D81_CD = "D81";
    String D108_CD = "D108";
    String D144_CD = "D144";

    static double dvadasamsaLongitude(final double longitude) {
        return fix360(rasiFid0(longitude) * 30 + rasiDegree(longitude) * 12);
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.varga;

/**
 * Hora  	D-2  	Wealth, Resources and Money
 * <p>
 * Each rasi is divided into two equal parts of fifteen degrees.
 * <p>
 * <b>Which two signs those halves belong to depends on the scheme, and this library follows
 * Jagannatha Hora rather than Brihat Parashara Hora Shastra.</b> Under the classical rule the
 * first half of an odd rasi is the Sun’s hora and the second the Moon’s (and the reverse for
 * an even rasi), so every object lands in Leo or Cancer and nowhere else. JHora’s "D-2 (US)"
 * instead runs the hora forward through an odd sign and backward through an even one and lays the
 * doubled position on the whole zodiac. The implementation and the evidence for it are in
 * {@code org.jyotisa.varga.VargaD2}.
 *
 * @author Yura Krymlov
 * @version 2.0, 2026-08
 */
public interface IVargaD2 extends IVarga {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return D02_CD;
    }
}

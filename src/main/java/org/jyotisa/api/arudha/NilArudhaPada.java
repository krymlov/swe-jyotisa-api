/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * The "not an arudha pada" member - a Null Object, so that a lookup which cannot name one has
 * something to return instead of {@code null} and without throwing.
 * <p>
 * See {@link org.jyotisa.api.rasi.NilRasi} for the fuller account of why a Null Object rather
 * than {@code null} or an exception.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum NilArudhaPada implements IArudhaPada {
    NIL;

    @Override
    public int fid() {
        return NIL_FID;
    }

    @Override
    public String code() {
        return NIL_CD;
    }

    @Override
    public IArudhaPada[] all() {
        return values();
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * The "not an avastha" member - a Null Object, so that a lookup which cannot name one has
 * something to return instead of {@code null} and without throwing.
 * <p>
 * See {@link org.jyotisa.api.rasi.NilRasi} for the fuller account.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum NilAvastha implements IAvastha {
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
    public IAvastha[] all() {
        return values();
    }
}

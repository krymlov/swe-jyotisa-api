/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.vaara;

import org.jyotisa.api.graha.IGraha;

/**
 * The "not a vaara" member - a Null Object, so that a lookup which cannot name one has
 * something to return instead of {@code null} and without throwing.
 * <p>
 * It lives beside the interface it implements, because "a failed lookup answers NIL" is part of
 * the <b>contract</b> of IVaara rather than of any one implementation: a caller holding only the
 * interface can test the result with {@link org.swisseph.api.ISweEnum#isNil()}.
 * <p>
 * Before this existed, {@code EVaara.NIL.vaara()} answered {@code null} - so every {@code by*}
 * lookup either threw or, once they were made total, would have handed back a {@code null} that
 * an unsuspecting caller dereferences. See {@link org.jyotisa.api.rasi.NilRasi} for the fuller
 * account of why a Null Object rather than {@code null} or an exception.
 * <p>
 * Its accessors stay {@code null}: a non-vaara has no properties, and inventing some would trade
 * a visible failure for an invisible wrong answer - which is what this class exists to stop.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum NilVaara implements IVaara {
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
    public IVaara[] all() {
        return values();
    }

    @Override
    public IGraha lord() {
        return null;
    }
}

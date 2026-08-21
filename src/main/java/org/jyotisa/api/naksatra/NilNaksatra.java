/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.api.naksatra;

import org.jyotisa.api.graha.IGraha;

/**
 * The "not a naksatra" member - a Null Object, so that a lookup which cannot name one has
 * something to return instead of {@code null}.
 * <p>
 * It lives here, beside the interface it implements, because "a failed lookup answers NIL" is
 * part of the <b>contract</b> of INaksatra rather than of any one implementation. A caller
 * holding only the interface can test it with {@link org.swisseph.api.ISweEnum#isNil()}.
 * <p>
 * <b>Why it exists.</b> Three different answers to "I do not know" used to coexist, and the
 * middle one was the dangerous one:
 * <pre>
 * byIndex(0)        -&gt; null                 an NPE at the call site
 * byLongitude(NaN)  -&gt; the first member     a plausible, wrong answer that travels on
 * </pre>
 * {@code (int) NaN} is {@code 0} in Java, so an explicitly-undetermined longitude resolved to
 * the first real member and was then rendered as ordinary chart data.
 * <p>
 * Its accessors stay {@code null}: a non-naksatra has no lord and no element, and inventing one
 * would trade a visible failure for an invisible wrong answer - which is what this class exists
 * to stop.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public enum NilNaksatra implements INaksatra {
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
    public INaksatra[] all() {
        return values();
    }

    @Override
    public IGraha lord() {
        return null;
    }
}

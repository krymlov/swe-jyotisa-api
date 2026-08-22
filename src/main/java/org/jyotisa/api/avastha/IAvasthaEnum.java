/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

import org.jyotisa.api.IKundaliSequence;

/**
 * The registry contract of the five avasthas - the same shape {@code IRasiEnum} has.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaEnum extends IKundaliSequence<IAvasthaEnum> {

    IAvastha avastha();

    @Override
    default int fid() {
        return avastha().fid();
    }

    @Override
    default String code() {
        return avastha().code();
    }

    @Override
    default double length() {
        return avastha().length();
    }
}

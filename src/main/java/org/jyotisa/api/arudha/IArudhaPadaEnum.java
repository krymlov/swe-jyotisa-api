/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

import org.jyotisa.api.IKundaliSequence;

/**
 * The registry contract of the twelve arudha padas - the same shape {@code IRasiEnum} has, with
 * {@link #fid()} and {@link #code()} delegated to the member itself.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaEnum extends IKundaliSequence<IArudhaPadaEnum> {

    IArudhaPada arudhaPada();

    @Override
    default int fid() {
        return arudhaPada().fid();
    }

    @Override
    default String code() {
        return arudhaPada().code();
    }

    @Override
    default double length() {
        return arudhaPada().length();
    }
}

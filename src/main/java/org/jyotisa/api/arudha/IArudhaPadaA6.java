/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 6.  the perceived enemies and debts
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA6 extends IArudhaPada {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return A06_CD;
    }
}

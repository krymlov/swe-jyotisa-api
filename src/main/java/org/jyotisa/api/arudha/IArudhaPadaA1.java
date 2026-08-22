/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 1.  Arudha Lagna - the perceived self
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA1 extends IArudhaPada {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return A01_CD;
    }
}

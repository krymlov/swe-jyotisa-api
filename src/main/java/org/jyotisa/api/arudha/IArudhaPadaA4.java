/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 4.  the perceived home and mother
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA4 extends IArudhaPada {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return A04_CD;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 12.  Upapada Lagna - the perceived marriage and expenditure
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA12 extends IArudhaPada {

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return A12_CD;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 10.  the perceived work and standing
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA10 extends IArudhaPada {

    @Override
    default int fid() {
        return 10;
    }

    @Override
    default String code() {
        return A10_CD;
    }
}

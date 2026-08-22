/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 7.  the perceived spouse and partnership
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA7 extends IArudhaPada {

    @Override
    default int fid() {
        return 7;
    }

    @Override
    default String code() {
        return A07_CD;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 11.  the perceived gains and network
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA11 extends IArudhaPada {

    @Override
    default int fid() {
        return 11;
    }

    @Override
    default String code() {
        return A11_CD;
    }
}

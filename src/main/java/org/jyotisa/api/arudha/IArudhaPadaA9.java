/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 9.  the perceived fortune and father
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA9 extends IArudhaPada {

    @Override
    default int fid() {
        return 9;
    }

    @Override
    default String code() {
        return A09_CD;
    }
}

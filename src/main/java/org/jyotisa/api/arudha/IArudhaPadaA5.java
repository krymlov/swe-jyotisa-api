/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 5.  the perceived children and learning
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA5 extends IArudhaPada {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return A05_CD;
    }
}

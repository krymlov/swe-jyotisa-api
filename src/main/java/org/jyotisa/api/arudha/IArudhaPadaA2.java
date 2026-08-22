/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.arudha;

/**
 * 2.  the perceived wealth and family
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IArudhaPadaA2 extends IArudhaPada {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return A02_CD;
    }
}

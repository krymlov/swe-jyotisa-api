/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * 1.  Bala - the infant - newly entered, with little of its power yet awake
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaBala extends IAvastha {

    @Override
    default int fid() {
        return 1;
    }

    @Override
    default String code() {
        return AV1_CD;
    }
}

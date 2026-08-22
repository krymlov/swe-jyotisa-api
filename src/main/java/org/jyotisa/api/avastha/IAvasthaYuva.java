/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * 3.  Yuva - the adult - at full power, the strongest of the five
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaYuva extends IAvastha {

    @Override
    default int fid() {
        return 3;
    }

    @Override
    default String code() {
        return AV3_CD;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * 2.  Kumara - the youth - gathering strength
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaKumara extends IAvastha {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return AV2_CD;
    }
}

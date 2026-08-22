/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * 5.  Mrita - the dead - spent, with least effect
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaMrita extends IAvastha {

    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return AV5_CD;
    }
}

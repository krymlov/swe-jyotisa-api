/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 6. Shashthi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiShashthi extends ITithi {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return S06_CD;
    }

}

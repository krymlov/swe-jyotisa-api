/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 2. Dwitiya
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiDwitiya extends ITithi {

    @Override
    default int fid() {
        return 2;
    }

    @Override
    default String code() {
        return S02_CD;
    }

}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.tithi;

/**
 * 12. Dwadashi
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface ITithiDwadasi extends ITithi {

    @Override
    default int fid() {
        return 12;
    }

    @Override
    default String code() {
        return S12_CD;
    }

}

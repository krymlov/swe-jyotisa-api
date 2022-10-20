/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2022
 */

package org.jyotisa.api.ekadasi;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-01
 */
public interface ISattilaEkadasi extends IEkadasi {
    @Override
    default int fid() {
        return 5;
    }

    @Override
    default String code() {
        return EK05_CD;
    }
}

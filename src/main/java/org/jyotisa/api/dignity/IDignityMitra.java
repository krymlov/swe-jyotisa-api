/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */
package org.jyotisa.api.dignity;

/**
 * 6 Mitra
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface IDignityMitra extends IDignity {

    @Override
    default int fid() {
        return 6;
    }

    @Override
    default String code() {
        return MIR_CD;
    }

    @Override
    default int power() {
        return 25;
    }
}

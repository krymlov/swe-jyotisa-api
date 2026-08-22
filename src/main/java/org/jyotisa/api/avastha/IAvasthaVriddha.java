/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */

package org.jyotisa.api.avastha;

/**
 * 4.  Vriddha - the elder - waning
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public interface IAvasthaVriddha extends IAvastha {

    @Override
    default int fid() {
        return 4;
    }

    @Override
    default String code() {
        return AV4_CD;
    }
}

/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.naksatra;

/**
 * 25. Poorvabhadrapada
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INaksatraPurvaBhadrapada extends INaksatra {


    @Override
    default int fid() {
        return 25;
    }

    @Override
    default String code() {
        return N25_CD;
    }

}

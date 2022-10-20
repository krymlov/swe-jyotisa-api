/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.api.nityayoga;

/**
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public interface INityaYogaParigha extends INityaYoga {

    @Override
    default int fid() {
        return 19;
    }

    @Override
    default String code() {
        return NY19_CD;
    }

}

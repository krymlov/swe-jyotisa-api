/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2020-12
 */

package org.jyotisa.api.naksatra;

import org.jyotisa.api.IKundaliSequenceEntity;
import org.jyotisa.api.graha.IGraha;

/**
 * @author Yura Krymlov
 * @version 1.0, 2023-10
 */
public interface INaksatraGrahaEntity extends INaksatraEntity {
    IGraha graha();
}

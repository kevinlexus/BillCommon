package com.ric.cmn;

import java.math.BigDecimal;

/**
 * Вид распределяемой коллекции, с изменяемыми элементами
 */
public interface DistributableBigDecimal {

    BigDecimal getBdForDist();
    void setBdForDist(BigDecimal bd);

}

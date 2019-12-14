package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 elements's count
 */
public class ElemCountFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        return BigDecimal.valueOf(vectorDrawing.getTotalElementCounts());
    }
}

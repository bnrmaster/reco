package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 elements's count
 */
public class ElemCountPercentFeature extends VtFeature {

    public ElemCountPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "元素数比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        if (orginVtDraw.getTotalElementCounts() == 0) {
            return BigDecimal.valueOf(0);
        } else {
            return BigDecimal.valueOf(vectorDrawing.getTotalElementCounts()).divide(BigDecimal.valueOf(orginVtDraw.getTotalElementCounts()), 5);
        }
    }
}

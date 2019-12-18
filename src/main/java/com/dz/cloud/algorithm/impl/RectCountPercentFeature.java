package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 rectangle's count
 */
public class RectCountPercentFeature extends VtFeature {

    public RectCountPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "矩形个数比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        if (orginVtDraw.getRects().size() == 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(vectorDrawing.getRects().size()).divide(BigDecimal.valueOf(orginVtDraw.getRects().size()), 5);
    }
}

package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 circle's count in vectordrawing
 */
public class CircleCountPercentFeature extends VtFeature {

    public CircleCountPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "圆比圆数";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        if (orginVtDraw.getCircles().isEmpty()) {
            return BigDecimal.valueOf(0);
        } else {
            return BigDecimal.valueOf(vectorDrawing.getCircles().size()).divide(BigDecimal.valueOf(orginVtDraw.getCircles().size()), 5);
        }
    }
}

package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 ellipse's count
 */
public class EllipseCountPercentFeature extends VtFeature {

    public EllipseCountPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "椭圆个数比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        if (orginVtDraw.getEllipses().size() == 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(vectorDrawing.getEllipses().size()).divide(BigDecimal.valueOf(orginVtDraw.getEllipses().size()), 5);
    }
}

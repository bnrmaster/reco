package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 line's count
 */
public class LineCountPercentFeature extends VtFeature {

    public LineCountPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "线条个数比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        if (orginVtDraw.getLines().size() == 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(vectorDrawing.getLines().size()).divide(BigDecimal.valueOf(orginVtDraw.getLines().size()), 5);
    }
}

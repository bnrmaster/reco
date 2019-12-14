package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
    Circle's total area percent in the whole vector drawing
 */
public class EllipseAreaPercentFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        BigDecimal totalEllipseArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getEllipses().size(); i++) {
            totalEllipseArea = totalEllipseArea.add(BigDecimal.valueOf(vectorDrawing.getEllipses().get(i).getGeometry().getArea()));
        }
        return totalEllipseArea.divide(vectorDrawing.getArea());
    }
}

package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
    Circle's total area percent in the whole vector drawing
 */
public class CircleAreaPercentFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        BigDecimal totalCircleArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getCircles().size(); i++) {
            totalCircleArea = totalCircleArea.add(BigDecimal.valueOf(vectorDrawing.getCircles().get(i).getGeometry().getArea()));
        }
        return totalCircleArea.divide(vectorDrawing.getArea());
    }
}

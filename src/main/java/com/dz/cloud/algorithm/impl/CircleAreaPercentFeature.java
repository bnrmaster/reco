package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
    Circle's total area percent in the whole vector drawing
 */
public class CircleAreaPercentFeature extends VtFeature {

    public CircleAreaPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "圆面积占比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        BigDecimal totalCircleArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getCircles().size(); i++) {
            totalCircleArea = totalCircleArea.add(BigDecimal.valueOf(vectorDrawing.getCircles().get(i).getGeometry().getArea()));
        }

        BigDecimal totalArea = BigDecimal.valueOf(orginVtDraw.getArea());
        if (totalArea.compareTo(BigDecimal.valueOf(0)) == 0){
            return BigDecimal.valueOf(0);
        }
        return totalCircleArea.divide(totalArea);
    }
}

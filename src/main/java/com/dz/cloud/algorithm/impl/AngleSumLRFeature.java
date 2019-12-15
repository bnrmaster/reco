package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
    Sum of the angle with line and rectangle
 */
public class AngleSumLRFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        BigDecimal totalCircleArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getCircles().size(); i++) {
            totalCircleArea = totalCircleArea.add(BigDecimal.valueOf(vectorDrawing.getCircles().get(i).getGeometry().getArea()));
        }
        // 直线与直线
        // 矩形与直线
        // 矩形与矩形
        return totalCircleArea.divide(vectorDrawing.getArea());
    }
}

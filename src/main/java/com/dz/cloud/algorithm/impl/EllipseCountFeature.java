package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
 ellipse's count
 */
public class EllipseCountFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        return BigDecimal.valueOf(vectorDrawing.getCircles().size());
    }
}

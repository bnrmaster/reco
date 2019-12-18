package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Circle;
import com.dz.cloud.geolib.Ellipse;
import com.dz.cloud.geolib.Rect;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;

/**
    Circle's total area percent in the whole vector drawing
 */
public class EllipseAreaPercentFeature extends VtFeature {

    public EllipseAreaPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "椭圆面积/所有图形形面积和";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        BigDecimal currentEllipseArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getEllipses().size(); i++) {
            Ellipse ellipse = vectorDrawing.getEllipses().get(i);
            if (!ellipse.getVisible()) {
                continue;
            }
            currentEllipseArea = currentEllipseArea.add(BigDecimal.valueOf(ellipse.getGeometry().getArea()));
        }
        BigDecimal totalRectArea = BigDecimal.valueOf(0);
        for (int i = 0; i < orginVtDraw.getRects().size(); i++) {
            Rect rect =  orginVtDraw.getRects().get(i);
            if (!rect.getVisible()) {
                continue;
            }
            totalRectArea = totalRectArea.add(BigDecimal.valueOf(rect.getGeometry().getArea()));
        }
        BigDecimal totalCircleArea = BigDecimal.valueOf(0);
        for (int i = 0; i < orginVtDraw.getCircles().size(); i++) {
            Circle circle = orginVtDraw.getCircles().get(i);
            if (!circle.getVisible()) {
                continue;
            }
            totalCircleArea = totalCircleArea.add(BigDecimal.valueOf(circle.getGeometry().getArea()));
        }
        BigDecimal totalEllipseArea = BigDecimal.valueOf(0);
        for (int i = 0; i < orginVtDraw.getEllipses().size(); i++) {
            Ellipse ellipse = orginVtDraw.getEllipses().get(i);
            if (!ellipse.getVisible()) {
                continue;
            }
            totalEllipseArea = totalEllipseArea.add(BigDecimal.valueOf(ellipse.getGeometry().getArea()));
        }

        BigDecimal totalArea = totalEllipseArea.add(totalRectArea.add(totalCircleArea));
        if (totalArea.equals(BigDecimal.valueOf(0))) {
            return BigDecimal.valueOf(0);
        }
        return currentEllipseArea.divide(totalArea, 5);
    }
}

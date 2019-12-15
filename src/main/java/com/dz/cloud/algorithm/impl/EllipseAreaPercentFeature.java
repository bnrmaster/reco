package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.geolib.Circle;
import com.dz.cloud.geolib.Ellipse;
import com.dz.cloud.geolib.Rect;
import com.dz.cloud.model.VectorDrawing;
import org.locationtech.jts.geom.Geometry;

import java.math.BigDecimal;

/**
    Circle's total area percent in the whole vector drawing
 */
public class EllipseAreaPercentFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
        BigDecimal totalEllipseArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getEllipses().size(); i++) {
            Ellipse ellipse = vectorDrawing.getEllipses().get(i);
            if (!ellipse.getVisible()) {
                continue;
            }
            totalEllipseArea = totalEllipseArea.add(BigDecimal.valueOf(ellipse.getGeometry().getArea()));
        }
        BigDecimal totalRectArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getRects().size(); i++) {
            Rect rect =  vectorDrawing.getRects().get(i);
            if (!rect.getVisible()) {
                continue;
            }
            totalRectArea = totalRectArea.add(BigDecimal.valueOf(rect.getGeometry().getArea()));
        }
        BigDecimal totalCircleArea = BigDecimal.valueOf(0);
        for (int i = 0; i < vectorDrawing.getCircles().size(); i++) {
            Circle circle = vectorDrawing.getCircles().get(i);
            if (!circle.getVisible()) {
                continue;
            }
            totalCircleArea = totalCircleArea.add(BigDecimal.valueOf(circle.getGeometry().getArea()));
        }

        BigDecimal totalArea = totalEllipseArea.add(totalRectArea.add(totalCircleArea));
        if (totalArea.equals(BigDecimal.valueOf(0))) {
            return BigDecimal.valueOf(0);
        }
        return totalEllipseArea.divide(totalArea, 6);
    }
}

package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Line;
import com.dz.cloud.model.VectorDrawing;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.Vector2D;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
    Sum of the angle with line and line
 */
public class AngleSumLLPercentFeature extends VtFeature {

    public AngleSumLLPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "线交角比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        BigDecimal totalAngleSum = angleSumLLSum(orginVtDraw);
        BigDecimal curAngleSum = angleSumLLSum(vectorDrawing);
        if (totalAngleSum.compareTo(BigDecimal.valueOf(0)) == 0) {
            return BigDecimal.valueOf(0);
        } else {
            return curAngleSum.divide(totalAngleSum, 5);
        }
    }

    public BigDecimal angleSumLLSum(VectorDrawing vectorDrawing) {
        BigDecimal totalAngle = BigDecimal.valueOf(0);
        Set<String> preRevertCompare = new HashSet<>();
        for (int i = 0; i < vectorDrawing.getLines().size(); i++) {
            for (int j = 0; j < vectorDrawing.getLines().size(); j++) {
                // prevent one element take in place twice
                if (preRevertCompare.contains(j + "|" + i)) {
                    continue;
                }
                preRevertCompare.add(i + "|" + j);
                Line l1 = vectorDrawing.getLines().get(i);
                Line l2 = vectorDrawing.getLines().get(j);
                if (!l1.getVisible() || !l2.getVisible()) {
                    continue;
                }
                Vector2D directOne = Vector2D.create(
                        new Coordinate(l1.getX1(), l1.getY1()),
                        new Coordinate(l1.getX2(), l1.getY2())
                );
                Vector2D directTwo = Vector2D.create(
                        new Coordinate(l2.getX1(), l2.getY1()),
                        new Coordinate(l2.getX2(), l2.getY2())
                );

                double angle = directOne.angleTo(directTwo);
                angle = Math.abs(angle);
                // 只取锐角
                if (angle > Math.PI/2){
                    angle = Math.PI - angle;
                };
                totalAngle = totalAngle.add(BigDecimal.valueOf(angle));
            }
        }
        return totalAngle;
    }
}

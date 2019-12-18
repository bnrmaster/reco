package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Shape;
import com.dz.cloud.math.GeoOpt;
import com.dz.cloud.model.VectorDrawing;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 intersected points's count
 */
public class InterPointPercentFeature extends VtFeature {

    public InterPointPercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "交点个数比";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        int totalIntPtCount = vtDrawInterPt(orginVtDraw);
        int curIntPtCount = vtDrawInterPt(vectorDrawing);
        if (totalIntPtCount == 0) {
            return BigDecimal.valueOf(0);
        } else {
            return BigDecimal.valueOf(curIntPtCount).divide(BigDecimal.valueOf(totalIntPtCount), 5);
        }
    }

    private int vtDrawInterPt(VectorDrawing vectorDrawing) {
        Set<String> preRevertCompare = new HashSet<>();
        BigDecimal result = new BigDecimal(0.0);
        for (Integer i = 0; i < vectorDrawing.getTotalElementCounts(); i++) {
            for (Integer j = 0; j < vectorDrawing.getTotalElementCounts(); j++) {
                Shape shapeA = vectorDrawing.getShape(i);
                Shape shapeB = vectorDrawing.getShape(j);
                // prevent one element take in place twice
                if (preRevertCompare.contains(j + "|" + i)) {
                    continue;
                }
                preRevertCompare.add(i + "|" + j);
                // only care about the visible element
                if (shapeA.getVisible() && shapeB.getVisible()) {
                    if(!shapeA.equals(shapeB)){
                        GeoOpt geoOpt = new GeoOpt(shapeA.getGeometry(),shapeB.getGeometry());
                        result = result.add(BigDecimal.valueOf(
                                geoOpt.intersectPointCount()
                        ));
                    }
                }
            }
        }
        return result.intValue();
    }
}

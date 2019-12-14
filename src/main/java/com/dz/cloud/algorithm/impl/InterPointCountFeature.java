package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.geolib.Shape;
import com.dz.cloud.math.GeoOpt;
import com.dz.cloud.model.VectorDrawing;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 intersected points's count
 */
public class InterPointCountFeature implements IVtFeature {
    @Override
    public BigDecimal calc(VectorDrawing vectorDrawing) {
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

        return result;
    }
}

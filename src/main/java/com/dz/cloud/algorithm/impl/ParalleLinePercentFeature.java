package com.dz.cloud.algorithm.impl;

import com.dz.cloud.algorithm.intf.VtFeature;
import com.dz.cloud.geolib.Line;
import com.dz.cloud.geolib.Shape;
import com.dz.cloud.math.GeoOpt;
import com.dz.cloud.math.MathUtil;
import com.dz.cloud.model.VectorDrawing;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.Vector2D;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 rectangle's count
 */
public class ParalleLinePercentFeature extends VtFeature {

    public ParalleLinePercentFeature(double w) {
        super(w);
    }

    @Override
    public String getName() {
        return "线条平行比例";
    }

    @Override
    public BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing) {
        // 两两比较, 有一对平行则记录平行度+1
        long paralleNum = getParalleLineNum(vectorDrawing);
        // 两两比较的组合数量为C(m,n)
        long combineNum = MathUtil.combineNum(orginVtDraw.getLines().size(), 2);
        if (BigDecimal.valueOf(combineNum).compareTo(BigDecimal.valueOf(0)) == 0) {
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(paralleNum).divide(BigDecimal.valueOf(combineNum), 5);
    }

    private long getParalleLineNum(VectorDrawing vtDraw) {
        long result = 0;
        Set<String> preRevertCompare = new HashSet<>();
        for (Integer i = 0; i < vtDraw.getLines().size(); i++) {
            for (Integer j = 0; j < vtDraw.getLines().size(); j++) {
                Line line1 = vtDraw.getLines().get(i);
                Line line2 = vtDraw.getLines().get(j);
                // prevent one element take in place twice
                if (preRevertCompare.contains(j + "|" + i)) {
                    continue;
                }
                preRevertCompare.add(i + "|" + j);
                Vector2D vector1 = Vector2D.create(new Coordinate(line1.getX1(), line1.getY1()), new Coordinate(line1.getX2(), line1.getY2()));
                Vector2D vector2 = Vector2D.create(new Coordinate(line2.getX1(), line2.getY1()), new Coordinate(line2.getX2(), line2.getY2()));
                if (vector1.isParallel(vector2)) {
                    result++;
                }
            }
        }
        return result;
    }
}

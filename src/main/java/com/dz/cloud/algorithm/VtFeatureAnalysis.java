package com.dz.cloud.algorithm;

import com.dz.cloud.algorithm.impl.*;
import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VtFeatureAnalysis {
    private static List<IVtFeature> IVtFeatures = new ArrayList<>();
    static {
        IVtFeatures.add(new InterPointCountFeature());
        IVtFeatures.add(new CircleAreaPercentFeature());
        IVtFeatures.add(new EllipseAreaPercentFeature());
        IVtFeatures.add(new CircleCountFeature());
        IVtFeatures.add(new ElemCountFeature());
        IVtFeatures.add(new EllipseCountFeature());
        IVtFeatures.add(new LineCountFeature());
        IVtFeatures.add(new RectCountFeature());
        IVtFeatures.add(new RightAngleNumFeature());
        IVtFeatures.add(new AngleSumLLFeature());
    }
    public static List<BigDecimal> analyse(VectorDrawing vectorDrawing) {
        List<BigDecimal> result = new ArrayList<>();
        IVtFeatures.stream().forEach(
                feture-> result.add(feture.calc(vectorDrawing))
        );
        return result;
    }
}

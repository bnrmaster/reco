package com.dz.cloud.algorithm;

import com.dz.cloud.algorithm.impl.CircleAreaPercent;
import com.dz.cloud.algorithm.impl.InterPointCountFeature;
import com.dz.cloud.algorithm.intf.IVtFeature;
import com.dz.cloud.model.VectorDrawing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VtFeatureAnalysis {
    private static List<IVtFeature> IVtFeatures = new ArrayList<>();
    static {
        IVtFeatures.add(new InterPointCountFeature());
        IVtFeatures.add(new CircleAreaPercent());
    }
    public static List<BigDecimal> analyse(VectorDrawing vectorDrawing) {
        List<BigDecimal> result = new ArrayList<>();
        IVtFeatures.stream().forEach(
                feture-> result.add(feture.calc(vectorDrawing))
        );
        return result;
    }
}

package com.dz.cloud.algorithm.intf;

import com.dz.cloud.model.VectorDrawing;
import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class VtFeature {
    private double weight;
    public abstract  BigDecimal calc(VectorDrawing orginVtDraw, VectorDrawing vectorDrawing);
    public VtFeature(double w) {
        this.weight = w;
    }
    public abstract String getName();
}

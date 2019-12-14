package com.dz.cloud.model;


import com.dz.cloud.algorithm.VtFeatureAnalysis;
import com.dz.cloud.comon.Common;
import com.dz.cloud.geolib.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class VectorDrawing {
    private List<List<Shape>> shapes = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private List<Rect> rects = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();
    private List<Ellipse> ellipses = new ArrayList<>();
    private Integer totalElementCounts;
    private Double width = 500d;
    private Double height = 500d;



    public VectorDrawing(List<List<Shape>> packer) {
        this.shapes = packer;
        for (Integer i = 0; i < packer.size(); i++) {
            List shapeList = packer.get(i);
            if (shapeList.size() == 0) {
                continue;
            }
            Class clazz = Common.getInnerClass(shapeList);
            if (clazz.equals(Line.class)) {
                this.lines = shapeList;
            }
            if (clazz.equals(Rect.class)) {
                this.rects = shapeList;
            }
            if (clazz.equals(Circle.class)) {
                this.circles = shapeList;
            }
            if (clazz.equals(Ellipse.class)) {
                this.ellipses = shapeList;
            }
        }
    }

    /**
     获取特征向量
     @return
     */
    public List<BigDecimal> makeFeature() {
        return VtFeatureAnalysis.analyse(this);
    }

    /**
     获取所有图形个数
     @return
     */
    public Integer getTotalElementCounts() {
        if (totalElementCounts == null ){
            totalElementCounts = 0;
            for (Integer i = 0; i < this.shapes.size(); i++) {
                totalElementCounts += this.shapes.get(i).size();
            }
        }
        return totalElementCounts;
    }

    public void consume(Consumer<Shape> consumer) {
        this.shapes.stream().forEach(
                shapeList->{
                    shapeList.stream().forEach(
                            shape->{
                                consumer.accept(shape);
                            }
                    );
                }
        );
    }

    public Shape getShape(Integer index) {
        Integer currentCount = 0;
        for (int i = 0; i < this.shapes.size(); i++) {
            currentCount += this.shapes.get(i).size();
            if (index < currentCount) {
                return this.shapes.get(i).get(index - (currentCount - this.shapes.get(i).size()) );
            }
        }
        return null;
    }

    public BigDecimal getArea() {
        return BigDecimal.valueOf(width * height);
    }
}


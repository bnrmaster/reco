package com.dz.cloud.model;


import com.dz.cloud.algorithm.VtFeatureAnalysis;
import com.dz.cloud.comon.Common;
import com.dz.cloud.geolib.*;
import com.dz.cloud.math.ShapeUtils;
import lombok.Data;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.math.Vector2D;

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
    private MultiPolygon multiPolygon = null;
    private Coordinate centroid = null;
    private Double raduis;
    private List<BigDecimal> feature;
    private String filePath;
    private Double totalArea;


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
        if (this.feature == null) {
            this.feature = VtFeatureAnalysis.getFeature(this);
        }
        return this.feature;
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

    public Double getArea() {
        if (this.totalArea == null) {
            Double result = 0d;
            for (int i = 0; i < this.getTotalElementCounts(); i++) {
                result += this.getShape(i).getGeometry().getArea();
            }
            this.totalArea = result;
        }

        return this.totalArea;
    }

    public Coordinate getCentroid() {
        if (this.centroid == null) {
            MultiPolygon multiPolygon = this.getMultiPolygon();
            this.centroid = multiPolygon.getCentroid().getCoordinate();
        }

        return this.centroid;
    }

    public MultiPolygon getMultiPolygon() {
        if (this.multiPolygon == null) {
            Polygon[] polygons = new Polygon[this.getTotalElementCounts()];
            GeometryFactory geoFactory =  new GeometryFactory();
            for (int i = 0; i < this.getTotalElementCounts(); i++) {
                Coordinate[] coords = null;
                if (this.getShape(i).getClass().equals(Line.class)) {
                    Geometry geo = ShapeUtils.lineToPolygonGeo((Line)this.getShape(i));
                    coords = geo.getCoordinates();
                } else {
                    coords = this.getShape(i).getGeometry().getCoordinates();
                }

                polygons[i] =  new Polygon(geoFactory.createLinearRing(coords), null, geoFactory);
            }
            MultiPolygon multiPolygon = new MultiPolygon(polygons, geoFactory);
            this.multiPolygon = multiPolygon;
        }
        return this.multiPolygon;
    }

    public double getRaduis() {
        if (this.raduis == null) {
            MultiPolygon multiPolygon = this.getMultiPolygon();
            Coordinate centroid = this.getCentroid();
            Coordinate[] coordinates = multiPolygon.getCoordinates();
            this.raduis = 0d;
            for (Coordinate coordinate : coordinates) {
                double distance = Vector2D.create(centroid).distance(Vector2D.create(coordinate));
                if (this.raduis < distance) {
                    this.raduis = distance;
                }
            }
        }
        return this.raduis;
    }

    public VectorDrawing intersect(Geometry geometry) {
        List<List<Shape>> newShapes = new ArrayList();
        for (List<Shape> list : this.shapes) {
            List<Shape> newList = new ArrayList<>();
            for (Shape shape : list) {
                if (shape.getGeometry().intersects(geometry)) {
                    newList.add(shape);
                }
            }
            newShapes.add(newList);
        }
        return new VectorDrawing(newShapes);
    }

    public BigDecimal distance(VectorDrawing template) {
        List<BigDecimal> thisFeature = this.makeFeature();
        List<BigDecimal> thatFeature = template.makeFeature();
        BigDecimal dist = BigDecimal.valueOf(0);

        Double biggest = Double.MIN_VALUE;
        for (int i = 0; i < thisFeature.size(); i++) {
            BigDecimal delta = thisFeature.get(i).subtract(thatFeature.get(i));
            if (biggest.compareTo(Math.abs(delta.doubleValue())) < 0) {
                biggest = Math.abs(delta.doubleValue());
            }
        }
        //if (biggest.compareTo(0d) == 0 || biggest.compareTo(1d) < 0) {
            biggest = 1d;
        //}
        for (int i = 0; i < thisFeature.size(); i++) {
            BigDecimal num = thisFeature.get(i).subtract(thatFeature.get(i)).divide(BigDecimal.valueOf(biggest), 5);
            dist = dist.add(num.multiply(num));
        }
        return BigDecimal.valueOf(Math.sqrt(dist.doubleValue()));
    }
}


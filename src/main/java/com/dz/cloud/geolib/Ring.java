package com.dz.cloud.geolib;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;

public class Ring extends Shape {
    private double cx;
    private double cy;
    private double nr;
    private double or;
    /**
     构造圆环
     @param cx 中心x坐标
     @param cy 中心y坐标
     @param nr 内环半径
     @param or 外环半径
     */
    public Ring (double cx, double cy, double nr, double or) {
        this.cx = cx;
        this.cy = cy;
        this.nr = nr;
        this.or = or;
    }
    @Override
    public Geometry getGeometry() {
        if (nr == 0) {
            // 内环为0则退化为圆形
            return new Circle(cx, cy, or).getGeometry();
        }
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setCentre(new Coordinate(cx, cy));
        shapeFactory.setSize(nr*2);
        Polygon polygonInternal =shapeFactory.createCircle();
        shapeFactory.setSize(or*2);
        Polygon polygonOuterline =shapeFactory.createCircle();

        GeometryFactory geoFactory = new GeometryFactory();
        LinearRing lrInteral = geoFactory.createLinearRing(polygonInternal.getCoordinates());
        LinearRing orInteral = geoFactory.createLinearRing(polygonOuterline.getCoordinates());
        LinearRing[] holes = {lrInteral};
        Polygon ringPolygon = new Polygon(orInteral, holes, geoFactory);
        return ringPolygon;
    }
}

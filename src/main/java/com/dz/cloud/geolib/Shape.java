package com.dz.cloud.geolib;


import lombok.Data;
import org.locationtech.jts.geom.Polygon;

@Data
public abstract class Shape{
    public Shape() {
        this.visible = true;
    }
    abstract public Polygon getPolygon();

    private Boolean visible;
}



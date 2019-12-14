package com.dz.cloud.geolib;


import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

@Data
public abstract class Shape{
    public Shape() {
        this.visible = true;
    }
    abstract public Geometry getGeometry();

    private Boolean visible;
}



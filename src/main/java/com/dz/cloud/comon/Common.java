package com.dz.cloud.comon;

import java.util.List;

public class Common {
    public static Class getInnerClass(List shapeList) {
        if (shapeList == null || shapeList.isEmpty()) {
            return Void.class;
        } else {
            return shapeList.get(0).getClass();
        }
    }
}

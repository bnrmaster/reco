import com.dz.cloud.model.VectorDrawing;
import com.dz.cloud.utlis.VtDrawUtils;
import org.junit.Test;

public class ResarchTest {
    /**
     src/test/materials/research/why_13_22_14.jpeg
     */
    @Test
    public void research1() {
        VectorDrawing vt36 = VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image36.svg");
        VectorDrawing vt21 = VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image21.svg");
        VectorDrawing vt37 = VtDrawUtils.getVectorDrawing("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image37.svg");
        System.out.println(vt36.makeFeature());
        System.out.println(vt21.makeFeature());
        System.out.println(vt37.makeFeature());
        //1调权重
        //2做调权重页面
    }
}

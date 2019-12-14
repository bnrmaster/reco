import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.VectorDrawing;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class VectorDrawingTest {
    @Test
    public void SVGTest() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("/Users/wangdz/Downloads/method-draw-image4.svg");
            VectorDrawing vectorDrawing = SVGParser.parse(d);
            vectorDrawing.setWidth(500d);
            vectorDrawing.setHeight(400d);
            System.out.println("Circle:" + vectorDrawing.getCircles().size());
            System.out.println("Ecllipse:" + vectorDrawing.getEllipses().size());
            System.out.println("Line:"+ vectorDrawing.getLines().size());
            System.out.println("Rect:"+ vectorDrawing.getRects().size());
            List<BigDecimal> feature = vectorDrawing.makeFeature();
            System.out.println("Feature Vector:"+feature.stream().map(e->e.doubleValue()).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

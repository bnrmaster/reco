import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.model.SVG;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SVGTest {
    @Test
    public void SVGTest() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("/Users/wangdz/Downloads/method-draw-image1.svg");
            SVG svg = SVGParser.parse(d);
            System.out.println("Circle:" + svg.getCircles().size());
            System.out.println("Ecllipse:" + svg.getEllipses().size());
            System.out.println("Line:"+svg.getLines().size());
            System.out.println("Rect"+svg.getRects().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

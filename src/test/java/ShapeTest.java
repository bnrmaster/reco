import com.dz.cloud.geolib.Line;
import com.dz.cloud.geolib.Ring;
import com.dz.cloud.geolib.parser.SVGParser;
import com.dz.cloud.math.ShapeUtils;
import com.dz.cloud.model.VectorDrawing;
import org.junit.Test;
import org.locationtech.jts.geom.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ShapeTest {
    @Test
    public void ringTest() {
        Ring ring = new Ring(100, 100, 50, 100);
        Geometry geo = ring.getGeometry();
        Geometry b = geo.getEnvelope();
        int a = 0;
    }

    @Test
    public void centeroidTest() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document d = builder.parse("/Users/wangdz/github/SpringCloudAlibaba/svgindexengine/src/test/materials/material1/method-draw-image38.svg");
        VectorDrawing vectorDrawing = SVGParser.parse(d);
        System.out.println(vectorDrawing.getMultiPolygon());
        System.out.println(vectorDrawing.getCentroid());
        System.out.println(vectorDrawing.getRaduis());
    }
}

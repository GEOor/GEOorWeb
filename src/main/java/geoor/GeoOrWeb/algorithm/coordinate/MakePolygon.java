package geoor.GeoOrWeb.algorithm.coordinate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.opengis.referencing.operation.TransformException;

import java.util.ArrayList;

public class MakePolygon {

    public Polygon test() throws TransformException {
        ConvertSRID convertSRID = new ConvertSRID();
        ArrayList<TestPoint> testPointArray = new ArrayList<>();
        // dem 격자 한 개의 좌표 정보가 리스트로 들어온다 가정
        testPointArray.add(new TestPoint(29.66791433418666, 137.49944614184602));
        testPointArray.add(new TestPoint(29.66833963776334, 137.49949690722175));
        testPointArray.add(new TestPoint(29.668374716257276, 137.49927328443786));
        testPointArray.add(new TestPoint(29.66789759825671, 137.49919256408702));
        // 받은 리스트의 좌표들을 SHP 파일에 맞는 좌표계로 변환
        Coordinate[] coords = new Coordinate[5];
        for (int i = 0; i < 4; i++) {
            coords[i] = convertSRID.convertCoordinate(
                    testPointArray.get(i).getLongitude(),
                    testPointArray.get(i).getLatitude());
        }
        // polygon의 마지막 점은 시작점으로 세팅
        coords[4] = coords[0];
        // 좌표들을 polygon으로 변환
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        LinearRing ring = geometryFactory.createLinearRing(coords);
        LinearRing holes[] = null; // polygon 내부에 빈칸은 없다..
        Polygon polygon = geometryFactory.createPolygon(ring, holes);
        return polygon;
    }
}

@Getter
@AllArgsConstructor
class TestPoint {
    private double longitude;
    private double latitude;
}
package geoor.GeoOrWeb.repository;

import geoor.GeoOrWeb.algorithm.coordinate.MakePolygon;
import geoor.GeoOrWeb.model.shp.Shp;
import org.geotools.feature.FeatureIterator;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKBWriter;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.operation.TransformException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static geoor.GeoOrWeb.config.ApplicationProperties.getProperty;

public class ShpRepository {
    private final WKBWriter writer = new WKBWriter();

    public ShpRepository() {
    }

    public void save(Connection conn, ArrayList<Shp> shps, ArrayList<String> columns) throws SQLException {
        String insertQuery = getInsertQuery(columns);
        int featureCount = 0;
        try (PreparedStatement pStmt = conn.prepareStatement(insertQuery)) {
            for (Shp shp : shps) {
                System.out.print(shp.getFile().getName() + " save... ");
                featureCount = saveShp(pStmt, shp, columns);
                System.out.println(featureCount);
            }
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }

    private String getInsertQuery(ArrayList<String> columns) {
        StringBuilder query = new StringBuilder("insert into " + getProperty("shp.table") + " values (");
        // hillshade 값 하나는 제외시킴
        for (int i = 0; i < columns.size() - 2; i++) {
            query.append("?, ");
        }
        query.append("?);");
        //System.out.println(query);
        return query.toString();
    }

    private int saveShp(PreparedStatement pStmt, Shp shp, ArrayList<String> columns) throws SQLException {
        int featureCount = 0;
        FeatureIterator<SimpleFeature> features = shp.getFeature();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            setShpObject(pStmt, feature, columns);
            featureCount++;
        }
        features.close();
        pStmt.executeBatch();
        pStmt.clearBatch();
        return featureCount;
    }

    private void setShpObject(PreparedStatement pStmt, SimpleFeature feature, ArrayList<String> columns) throws SQLException {
        pStmt.setObject(1, writer.write((Geometry) feature.getDefaultGeometryProperty().getValue()));
        // 하나 빼는 이유는 hillshade 기본값 0으로 둘려고
        for (int i = 1; i < columns.size() - 1; i++) {
            pStmt.setObject(i+1, feature.getAttribute(columns.get(i)));
        }
        pStmt.addBatch();
    }

    public void findOverlapPolygon(Connection conn) throws SQLException {
        String query = getUpdateQuery();
        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            MakePolygon makePolygon = new MakePolygon();
            Polygon polygon = makePolygon.test();
            pStmt.setObject(1, 1234);
            pStmt.setObject(2, polygon.toString());
            int updateCount = pStmt.executeUpdate();
            System.out.println("변경된 row : " + updateCount);
        } catch (TransformException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }

    public String getUpdateQuery() {
        StringBuilder query = new StringBuilder("update ");
        query.append(getProperty("shp.table"));
        query.append(" set hillshade = ?");
        query.append(" where ST_Overlaps(ST_GeometryFromText(?, 5179), SHP.the_geom)");
        System.out.println(query);
        return query.toString();
    }
}

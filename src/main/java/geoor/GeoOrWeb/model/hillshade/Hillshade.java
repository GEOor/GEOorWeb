package geoor.GeoOrWeb.model.hillshade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Hillshade {

    //x,y 좌표 값
    private Double x;
    private Double y;

    //위도, 경도
    private Double latitude, longitude;

    //hillshade 값
    private Double hillshade;

}

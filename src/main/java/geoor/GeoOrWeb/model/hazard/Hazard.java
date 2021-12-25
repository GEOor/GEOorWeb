package geoor.GeoOrWeb.model.hazard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Hazard {

    //위도, 경도
    private Double latitude, longitude;
}

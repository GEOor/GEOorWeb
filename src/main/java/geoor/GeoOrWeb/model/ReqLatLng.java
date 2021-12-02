package geoor.GeoOrWeb.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReqLatLng {
	
	private double sLat;
	private double sLng;
	private double eLat;
	private double eLng;
	
	@Override
	public String toString() {
		return "ReqLatLng{" +
				"sLat=" + sLat +
				", sLng=" + sLng +
				", eLat=" + eLat +
				", eLng=" + eLng +
				'}';
	}
}

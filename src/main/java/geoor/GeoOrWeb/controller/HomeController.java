package geoor.GeoOrWeb.controller;

import geoor.GeoOrWeb.algorithm.coordinate.TransformCoordinate;
import geoor.GeoOrWeb.algorithm.hillshade.HillshadeAlgorithm;
import geoor.GeoOrWeb.config.ApplicationProperties;
import geoor.GeoOrWeb.model.dem.Dem;
import geoor.GeoOrWeb.model.dem.DemInfo;
import geoor.GeoOrWeb.model.hillshade.Hillshade;
import geoor.GeoOrWeb.service.DemService;
import geoor.GeoOrWeb.service.SunService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String main(){

		return "WFS";
	}
}

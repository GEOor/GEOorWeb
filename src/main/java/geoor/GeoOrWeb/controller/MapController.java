package geoor.GeoOrWeb.controller;

import geoor.GeoOrWeb.model.ReqLatLng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class MapController {
	
	@GetMapping("/getLatLng")
	private String test1(@ModelAttribute ReqLatLng reqLatLng) {
		log.info(reqLatLng.toString());
		return "test";
	}
	
}

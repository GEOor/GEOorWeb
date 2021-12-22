package geoor.GeoOrWeb.service;

import geoor.GeoOrWeb.model.dem.Dem;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

@Getter @Setter
public class DemService {
	
	private String rootPath = "src/main/java/geoor/GeoOrWeb/files";
	public ArrayList<Dem> Dems = new ArrayList<Dem>();
	
	public ArrayList<Dem> prepareDem(){
		try (
				DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(rootPath))) {
			for (Path file: stream) {
				// System.out.println(file.getFileName());
				Dem dem = new Dem(rootPath + "/" + file.getFileName().toString());
				Dems.add(dem);
			}
		} catch (IOException | DirectoryIteratorException ex) {
			System.err.println(ex);
		}
		
		return Dems;
	}
}

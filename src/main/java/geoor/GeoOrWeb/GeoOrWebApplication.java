package geoor.GeoOrWeb;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class GeoOrWebApplication {

	public static void main(String[] args) throws IOException, ParseException {
		SpringApplication.run(GeoOrWebApplication.class, args);
	}
}

package geoor.GeoOrWeb;

import geoor.GeoOrWeb.algorithm.coordinate.MakePolygon;
import geoor.GeoOrWeb.algorithm.coordinate.TransformCoordinate;
import geoor.GeoOrWeb.algorithm.hillshade.HillshadeAlgorithm;
import geoor.GeoOrWeb.config.ApplicationProperties;
import geoor.GeoOrWeb.model.dem.Dem;
import geoor.GeoOrWeb.model.hillshade.Hillshade;
import geoor.GeoOrWeb.service.DemService;
import geoor.GeoOrWeb.service.ShpService;
import geoor.GeoOrWeb.service.SunService;
import org.opengis.referencing.operation.TransformException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication
public class GeoOrWebApplication {

	// 멤버변수가 직접 쓰이지는 않지만 프로퍼티 값을 쓰기 위해 한번 호출은 시켜야 한다.
	private static final ApplicationProperties applicationProperties = new ApplicationProperties();

	private static DemService demService = new DemService();
	private static SunService sun = new SunService();
	private static ShpService shpService = new ShpService();
	private static TransformCoordinate tf;

	public static void main(String[] args) {

/*		try {
			shpService.demMapping();
//			shpService.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/

		/**
		 dem api 호출 부분
		 반환 값 : 253 x 314 모양의 2차원 ArrayList
		 */
		ArrayList<Dem> demArr = demService.prepareDem();
		/**
		 2021-11-22
		 작성자 : 천수환
		 내용 : dem 좌표를 이용해 태양고도각 추출
		 사용 방법 : 이미 구한 demArr, 몇등분 할 지 정하는 변수 k, 시각 t 를 넣고 SunService의 run 함수 사용
		 작동 원리
		 1) 2차원 dem ArrayList를 k * k등분, k^2개의 사각형으로 나눈다.
		 2) 각각의 사각형의 중심 dem 값을 TransformCoordinate를 이용해 위,경도로 변환
		 3) 변환된 위,경도를 크롤러를 이용해 고도각으로 변환
		 4) 변환된 고도각을 똑같이 2차원 SunInfo ArrayList의 사각형에 위치한 곳으로 모두 채운다.
		 */
		ArrayList<Hillshade> hillshadeArr = new ArrayList<Hillshade>();
		for(int i=0; i<demArr.size(); i++) {
			int k = 2; //k등분으로 태양고도각 크롤링 (크롤링 횟수 조절을 위함)
			int t = 9; //태양고도각 시각 (0 ~ 23 사이 정수만 가능함)
			sun.run(demArr.get(i).getArr(), k, t);

			//System.out.println(sun.get().toString()); // 테스트 코드

			/**
			 2021-11-22
			 작성자 : 천수환
			 내용 : HillshadeAlgorithm 사용 부분
			 사용 방법
			 1) HillshadeAlgorithm 클래스 선언 (hs)
			 2) hsConverter 함수에 태양 고도각, dem값을 넣는다. (둘다 2차원 ArrayList)
			 3) 리턴된 값은 해당 dem 격자의 음영기복도(hillshade)값이 된다. * 테두리 제외
			 */
			HillshadeAlgorithm hs = new HillshadeAlgorithm();
			ArrayList<ArrayList<Hillshade>> hs2DArr = hs.hsConverter(sun.get(), demArr.get(i).getArr());

			for (ArrayList<Hillshade> row : hs2DArr) {
				hillshadeArr.addAll(row);
			}
		}

		System.out.println(hillshadeArr.toString()); // 테스트 코드

		SpringApplication.run(GeoOrWebApplication.class, args);
	}

}

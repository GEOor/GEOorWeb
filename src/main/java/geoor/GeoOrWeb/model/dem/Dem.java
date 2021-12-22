package geoor.GeoOrWeb.model.dem;

import geoor.GeoOrWeb.algorithm.coordinate.TransformCoordinate;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Getter @Setter
public class Dem {
	
	private File file;
	private ArrayList<Double> arrX = new ArrayList<Double>();
	private ArrayList<Double> arrY = new ArrayList<Double>();
	private ArrayList<Double> arrZ = new ArrayList<Double>();
	private ArrayList<ArrayList<DemInfo>> arr = new ArrayList<ArrayList<DemInfo>>();
	private int row, col;
	private TransformCoordinate tf = new TransformCoordinate();
	
	public Dem(String path) {
		// 파일 객체 생성
		this.setFile(new File(path));
		setArr();
		toDemInfo();
	}
	
	private void setArr(){
		row = 0;
		col = 0;
		try{
			//입력 스트림 생성
			FileReader file_reader = new FileReader(file);
			String temp = "";
			int cur = 0;
			int flag = 0;
			double prev_x = -1;
			boolean colChk = true;
			ArrayList<Double> arrX = new ArrayList<Double>();
			ArrayList<Double> arrY = new ArrayList<Double>();
			ArrayList<Double> arrZ = new ArrayList<Double>();
			while((cur = file_reader.read()) != -1){
				char c = (char)cur;
				if(('0' <= c && c <= '9') || c == '.') {
					temp += c;
				}
				else{
					if(temp.equals("")) {
						continue;
					}
					if(flag == 0) {
						double cmp = Double.parseDouble(temp);
						arrX.add(cmp);
						if(prev_x == -1) prev_x = cmp;
						if(prev_x != cmp){
							prev_x = cmp;
							row++;
							colChk = false;
						}
					}
					else if(flag == 1){
						arrY.add(Double.parseDouble(temp));
						if(colChk) col++;
					}
					else {
						arrZ.add(Double.parseDouble(temp));
					}
					flag++;
					flag %= 3;
					temp = "";
				}
			}
			col++;
			setArrX(arrX);
			setArrY(arrY);
			setArrZ(arrZ);
			file_reader.close();
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch(IOException e){
			e.getStackTrace();
		}
	}
	
	/**
	 x축 = 동, 서 방향을 결정 => 열
	 y축 = 북, 남 방향을 결정 => 행
	 
	 따라서
	 열이 증가함 -> 2차원 평면 상 지도가 오른쪽으로 감 (x 좌표 증가)
	 행이 증가함 -> 2차원 평면 상 지도가 아래로 내려감 (y 좌표 감소)
	 **/
	private void toDemInfo(){
		setRow(row);
		setCol(col);
		ArrayList<Double> arrX = getArrX();
		ArrayList<Double> arrY = getArrY();
		ArrayList<Double> arrZ = getArrZ();
		ArrayList<ArrayList<DemInfo>> tempArr = new ArrayList<ArrayList<DemInfo>>();
		for(int i=0; i<row; i++){
			ArrayList<DemInfo> temp = new ArrayList<>();
			for(int j=0; j<col; j++){
				double x = arrX.get(col*i + j);
				double y = arrY.get(col*i + j);
				double z = arrZ.get(col*i + j);
				tf.setX(y); tf.setY(x); tf.transform();
				//System.out.println(x + " " + y + " " + tf.getLatitude() + " " + tf.getLongitude());
				temp.add(new DemInfo(x, y, z, tf.getLatitude(), tf.getLongitude()));
			}
			tempArr.add(temp);
		}
		setArr(tempArr);
	}
}

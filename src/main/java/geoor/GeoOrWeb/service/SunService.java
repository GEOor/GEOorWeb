package geoor.GeoOrWeb.service;

import geoor.GeoOrWeb.controller.SunController;
import geoor.GeoOrWeb.model.dem.DemInfo;
import geoor.GeoOrWeb.model.sun.SunInfo;

import java.util.ArrayList;

public class SunService {

    private SunController sc = new SunController();

    public ArrayList<ArrayList<SunInfo>> get(){
        return sc.getSunInfo();
    }

    public void run(ArrayList<ArrayList<DemInfo>> dem, int num, int time){
        sc.cutSquare(dem, num, time);
    }
}

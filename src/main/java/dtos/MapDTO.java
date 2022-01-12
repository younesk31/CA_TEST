package dtos;

import entities.MapInfo;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class MapDTO {

    private String currentMap;
    private String nextMap;
    private int durationMins;
    private String currentMapImage;


    public static List<MapDTO> getDtos(List<MapInfo> m){
        List<MapDTO> mapinfoDTOSdtos = new ArrayList();
        m.forEach(mi -> mapinfoDTOSdtos.add(new MapDTO(mi)));
        return mapinfoDTOSdtos;
    }

    public MapDTO(MapInfo mapInfo) {
        this.currentMap = mapInfo.getCurrentMap();
        this.nextMap = mapInfo.getNextMap();
        this.durationMins = mapInfo.getDurationMins();
        this.currentMapImage = mapInfo.getCurrentMapImage();
    }


    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public String getNextMap() {
        return nextMap;
    }

    public void setNextMap(String nextMap) {
        this.nextMap = nextMap;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(int durationMins) {
        this.durationMins = durationMins;
    }

    public String getCurrentMapImage() {
        return currentMapImage;
    }

    public void setCurrentMapImage(String currentMapImage) {
        this.currentMapImage = currentMapImage;
    }
}

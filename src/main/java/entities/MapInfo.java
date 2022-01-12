package entities;

import dtos.MapDTO;

import javax.persistence.*;

@Table(name = "map_info")
@Entity
public class MapInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "current_map")
    private String currentMap;

    @Column(name = "next_map")
    private String nextMap;

    @Column(name = "duration_mins")
    private int durationMins;

    @Lob
    @Column(name = "current_map_image")
    private String currentMapImage;

    public MapInfo() {
    }

    public MapInfo(MapDTO mapDTO) {
        this.currentMap = mapDTO.getCurrentMap();
        this.nextMap = mapDTO.getNextMap();
        this.durationMins = mapDTO.getDurationMins();
        this.currentMapImage = getCurrentMapImage();
    }

    public String getCurrentMapImage() {
        String lastParam = currentMapImage.substring(currentMapImage.lastIndexOf('/') + 1);
        String[] maparray = lastParam.split("_",5);
        switch (maparray[0]) {
            case "Worlds":
                setCurrentMapImage("https:\\/\\/apexlegendsstatus.com\\/assets\\/maps\\/Worlds_Edge.png");
                break;
            case "Storm":
                setCurrentMapImage("https:\\/\\/apexlegendsstatus.com\\/assets\\/maps\\/Strom_Point.png");
                break;
            case "Olympus":
                setCurrentMapImage("https://apexlegendsstatus.com//assets//maps//Olympus.png");
                break;
            default:
                setCurrentMapImage("undefined");
                break;
        }
        return currentMapImage;
    }

    public void setCurrentMapImage(String currentMapImage) {
        this.currentMapImage = currentMapImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurationMins() {
        return durationMins;
    }

    public void setDurationMins(int durationMins) {
        this.durationMins = durationMins;
    }

    public String getNextMap() {
        return nextMap;
    }

    public void setNextMap(String nextMap) {
        this.nextMap = nextMap;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }
}
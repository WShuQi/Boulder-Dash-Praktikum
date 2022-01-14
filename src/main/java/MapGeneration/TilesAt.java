package MapGeneration;

public class TilesAt {

    private Coordinate coordinate;
    private String kindName;
    private int defaultValue;  //"Standard-Kachelvarianten" bei der Kartengeneration wenn Anzahl Versuche > maxNumberOfTrials  -> TileVersion möglicherweise

    public TilesAt(Coordinate coordinate, String kindName, int defaultValue) {
        this.coordinate = coordinate;
        this.kindName = kindName;
        this.defaultValue = defaultValue;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getKindName() {
        return kindName;
    }

    public int getDefaultValue() {
        return defaultValue;
    }
}



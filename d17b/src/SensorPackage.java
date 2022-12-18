public class SensorPackage {
    public GridCell sensor;
    public GridCell beacon;
    public int dist;

    public SensorPackage(GridCell sensor, GridCell beacon, int dist) {
        this.sensor = sensor;
        this.beacon = beacon;
        this.dist = dist;
    }

    public String toString() {
        return String.format("(%d,%d) to (%d, %d) - %d",
                this.sensor.X, this.sensor.Y,
                this.beacon.X, this.beacon.Y,
                this.dist);
    }
}

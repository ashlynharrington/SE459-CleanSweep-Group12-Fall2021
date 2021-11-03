package SE459.CleanSweep.tiles;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public double distanceToPoint(Point other){
        double distance = Math.sqrt(Math.pow((this.getX()-other.getX()), 2) + Math.pow((this.getY()- other.getY()), 2));
        return distance;
    }

    //overriding equals and hashCode allows us to search the FloorTile map with (int x, int y)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point key)) return false;
        return x == key.x && y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

}

package pl.kskowronski.freemapsmonitoringboats.models;

public class Point {

    private double x;
    private double y;

    private String name;
    private String destination;

    private double destinationX;
    private double destinationY;

    public Point(double x, double y, String name, String destination, double destinationX, double destinationY) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.destination = destination;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(double destinationX) {
        this.destinationX = destinationX;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(double destinationY) {
        this.destinationY = destinationY;
    }
}

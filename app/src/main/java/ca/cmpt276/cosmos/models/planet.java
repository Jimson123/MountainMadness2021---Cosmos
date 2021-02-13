package ca.cmpt276.cosmos.models;

public class planet {
    private double x;
    private double y;
    private double gravity;
    private String type;

    public planet(double x, double y, double gravity, String type) {
        this.x = x;
        this.y = y;
        this.gravity = gravity;
        this.type = type;
    }
}

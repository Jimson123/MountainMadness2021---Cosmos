package ca.cmpt276.cosmos.models;

public class Spaceship {
    private double x;
    private double y;
    private double angle;
    private double velocity;

    public Spaceship(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.velocity = 10;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}

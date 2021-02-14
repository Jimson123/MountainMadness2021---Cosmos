package ca.cmpt276.cosmos.models;

public class Spaceship {
    private double x;
    private double y;
    private double rotation;
    private double radius;
    private double speed;
    private double velX;
    private double velY;

    public Spaceship(double x, double y, double rotation, double radius, double speed, double velX, double velY) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.radius = radius;
        this.speed = speed;
        this.velX = velX;
        this.velY = velY;
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

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) { this.speed = speed; }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void incrementRotation(double increment) {
        this.rotation += increment;
        if (rotation >= 360.0) {
            this.rotation = rotation % 360;
        }
    }

    public void incrementSpeed(double increment) {
        this.speed = speed + increment;
    }

    public void addGravityAttraction(double angle, double gravity) {
        double yGravity = gravity * Math.sin(Math.toRadians(angle));
        double xGravity = gravity * Math.cos(Math.toRadians(angle));
        this.velX += xGravity;
        this.velY += yGravity;
    }

}

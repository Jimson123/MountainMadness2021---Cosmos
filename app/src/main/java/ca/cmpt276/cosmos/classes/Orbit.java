package ca.cmpt276.cosmos.classes;

// notes
/*
    positive x is right, positive y is down
    x and y do not correspond to screen pixels
    asteroids orbit clockwise, starting from the top
    all orbits are circular
 */

public class Orbit {
    public double x;
    public double y;
    public double radius;
    public double period;
    public double epoch;

    public Orbit(double x, double y, double radius, double period, double epoch) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.period = period;
        this.epoch = epoch;
    }

    public double getX() { return x; }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public double getEpoch() {
        return epoch;
    }

    public void setEpoch(double epoch) {
        this.epoch = epoch;
    }

    public double getOrbitX(double currentTime) {
        double phase = (currentTime + epoch) / (period / 6.283185307);
        return x + (radius * Math.sin(phase));
    }

    public double getOrbitY(double currentTime) {
        double phase = (currentTime + epoch) / (period / 6.283185307);
        return y + (radius * -1 * Math.cos(phase));
    }
}

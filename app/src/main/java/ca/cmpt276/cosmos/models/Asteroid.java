package ca.cmpt276.cosmos.models;

import ca.cmpt276.cosmos.classes.Orbit;

public class Asteroid {
    private String name;
    private Orbit orbit;
    private double x;
    private double y;
    private double radius;

    public Asteroid(String name, Orbit orbit, double x, double y, double radius) {
        this.name = name;
        this.orbit = orbit;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; };

    public Orbit getOrbit() { return orbit; }

    public void setOrbit(Orbit orbit) { this.orbit = orbit; };

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

    public double getNewX(double currentTime) {
        this.x = orbit.getOrbitX(currentTime);
        return x;
    }

    public double getNewY(double currentTime) {
        this.y = orbit.getOrbitY(currentTime);
        return y;
    }
}

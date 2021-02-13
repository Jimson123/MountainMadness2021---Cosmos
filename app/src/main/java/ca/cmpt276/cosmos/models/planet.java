package ca.cmpt276.cosmos.models;

public class planet {
    private double x;
    private double y;
    private double mass;
    private double radius;

    public planet(double x, double y, double mass, double radius) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
    }

    public double getGravity(){
        double force = (9.81*mass*10)/(radius * radius);
        return force;
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

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

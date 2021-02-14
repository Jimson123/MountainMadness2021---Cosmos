package ca.cmpt276.cosmos.models;

import ca.cmpt276.cosmos.MainActivity;

import static java.lang.Math.asin;
import static java.lang.Math.pow;

public class Planet {
    private double x;
    private double y;
    private double mass;
    private double radius;

    public Planet(double x, double y, double mass, double radius) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
    }

    public double getGravity(double sX, double sY){
        double xdiff = x - sX;
        double ydiff = y - sY;
        double rad = Math.sqrt(Math.pow(xdiff,2) + Math.pow(ydiff,2));
        double force = (9.81*mass*10)/pow(rad, 2);
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

    public double getAngle(double sX, double sY){
        double diff = (x-sX)/(y-sY);
//        if (Math.abs(diff) > 1){
//            return 90;
//        }
        double angle = Math.atan(diff);
        return angle;
    }
}

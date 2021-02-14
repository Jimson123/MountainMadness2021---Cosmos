package ca.cmpt276.cosmos.models;

import ca.cmpt276.cosmos.MainActivity;

import static java.lang.Math.asin;
import static java.lang.Math.pow;

public class Planet {
    private String name;
    private double x;
    private double y;
    private double radius;
    private double mass;
    private double gravityRadius;

    public Planet(String name, double x, double y, double radius, double mass, double gravityRadius) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.gravityRadius = gravityRadius;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; };

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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getGravityRadius() {
        return gravityRadius;
    }

    public void setGravityRadius(double gravityRadius) {
        this.gravityRadius = gravityRadius;
    }

    public int getIndex() { // I got lazy
        if (name.equals("moon")) {
            return 0;
        } else if (name.equals("earth")) {
            return 1;
        } else if (name.equals("venus")) {
            return 2;
        } else if (name.equals("mars")) {
            return 3;
        } else if (name.equals("neptune")) {
            return 4;
        } else if (name.equals("red_sun")) {
            return 5;
        } else if (name.equals("sun")) {
            return 6;
        } else if (name.equals("white_sun")) {
            return 7;
        } else {
            return 8;
        }
    }

    public double getGravity(double sX, double sY) {
        double xdiff = x - sX;
        double ydiff = y - sY;
        double rad = Math.sqrt(Math.pow(xdiff,2) + Math.pow(ydiff,2));
        double force = (9.81*mass*10)/pow(rad, 2);
        return force;
    }

    public double getAngle(double sX, double sY) {
//      double diff = (x-sX)/(y-sY);
//      if (Math.abs(diff) > 1){
//          return 90;
//      }
//      double angle = Math.atan(diff);
        // Math.atan2 returns the correct angle for all angles from 0 to 360.
        double xdiff = x - sX;
        double ydiff = y = sY;
        double angle = Math.atan2(ydiff, xdiff);
        return angle;
    }
}

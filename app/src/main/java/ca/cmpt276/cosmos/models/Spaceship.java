package ca.cmpt276.cosmos.models;

public class Spaceship {
    private double x;
    private double y;
    private double angle;
    private double spaceshipForwardVel;
    private double spaceshipVelX;
    private double spaceshipVelY;

    public Spaceship(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.spaceshipForwardVel = 0;
        this.spaceshipVelX = 0;
        this.spaceshipVelY = 0;
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

    public double getSpaceshipForwardVel() {
        return spaceshipForwardVel;
    }

    public void setSpaceshipForwardVel(double spaceshipForwardVel) {
        this.spaceshipForwardVel = spaceshipForwardVel;
    }

    public double getSpaceshipVelX() {
        return spaceshipVelX;
    }

    public void setSpaceshipVelX(double spaceshipVelX) {
        this.spaceshipVelX = spaceshipVelX;
    }

    public double getSpaceshipVelY() {
        return spaceshipVelY;
    }

    public void setSpaceshipVelY(double spaceshipVelY) {
        this.spaceshipVelY = spaceshipVelY;
    }

    public void updateAngle(int r) {
        this.angle += r;
        if (angle > 360){
            this.angle = 0;
        }
    }

    public void incrementForwardVelocity(double increment) {
        this.spaceshipForwardVel = spaceshipForwardVel + increment;
    }

    public void addGravityAttraction(double angle, double gravity){
        double yGravity = gravity * Math.sin(Math.toRadians(angle));
        double xGravity = gravity * Math.cos(Math.toRadians(angle));
        this.spaceshipVelX += xGravity;
        this.spaceshipVelY += yGravity;
    }

}

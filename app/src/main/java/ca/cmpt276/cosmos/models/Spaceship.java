package ca.cmpt276.cosmos.models;

public class Spaceship {
    private int x;
    private int y;
    private int angle;
    private int velocity;

    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
        this.velocity = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}

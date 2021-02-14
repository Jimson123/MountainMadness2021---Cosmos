package ca.cmpt276.cosmos.classes;

public class Camera {
    public double x;
    public double y;
    public double zoom; // stored coordinate * zoom = screen coordinate
    public double w; // what stored x difference is out of bounds
    public double h; // what stored y difference is out of bounds

    public Camera() { // the camera must be adjusted for each level
        this.x = 0.0;
        this.y = 0.0;
        this.zoom = 1.0;
    }

    public void setCamera(double topBound, double rightBound, double bottomBound, double leftBound, int displayX, int displayY) {
        this.x = (leftBound + rightBound) / 2.0;
        this.y = (topBound + bottomBound) / 2.0;
        // assumed that pixels are square
        this.zoom = Math.min(displayX / (rightBound + 20 - leftBound), displayY / (bottomBound + 20 - topBound)); // leave a 10 wide border on all sides
        this.w = displayX / zoom;
        this.h = displayY / zoom;
    }

    public int dimensionToPixels(double size) {
        return (int) (size * zoom);
    }

    public int[] positionToPixels(double pX, double pY, double sX, double sY) {
        int[] result;
        result = new int[2];
        result[0] = (int) (((w / 2) + (pX - x) - (sX / 2)) * zoom);
        result[1] = (int) (((h / 2) + (pY - y) - (sY / 2)) * zoom);
        return result;
    }
}

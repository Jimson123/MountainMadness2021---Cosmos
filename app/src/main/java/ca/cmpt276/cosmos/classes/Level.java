package ca.cmpt276.cosmos.classes;

import java.util.List;

import ca.cmpt276.cosmos.models.Spaceship;
import ca.cmpt276.cosmos.models.Asteroid;
import ca.cmpt276.cosmos.models.Planet;

public class Level {
    public Spaceship spaceship;
    public List<Asteroid> asteroids;
    public List<Planet> planets;

    public Level(Spaceship spaceship, List<Asteroid> asteroids, List<Planet> planets) {
        this.spaceship = spaceship;
        this.asteroids = asteroids;
        this.planets = planets;
    }

    public Spaceship getSpaceship() { return spaceship; }

    public void setSpaceship(Spaceship spaceship) { this.spaceship = spaceship; }

    public List<Asteroid> getAsteroids() { return asteroids; }

    public void setAsteroids(List<Asteroid> asteroids) { this.asteroids = asteroids; }

    public List<Planet> getPlanets() { return planets; }

    public void setPlanets(List<Planet> planets) { this.planets = planets; }

    public double combine(double dimension, double radius) {
        if (dimension < 0) {
            return dimension - radius;
        } else {
            return dimension + radius;
        }
    }

    public double[] getBounds() {
        double topBound = -1.0;
        double rightBound = 1.0;
        double bottomBound = 1.0;
        double leftBound = -1.0;
        // handle spaceship
        if (combine(spaceship.getY(), spaceship.getRadius()) < topBound) {
            topBound = combine(spaceship.getY(), spaceship.getRadius());
        }
        if (combine(spaceship.getX(), spaceship.getRadius()) > rightBound) {
            rightBound = combine(spaceship.getX(), spaceship.getRadius());
        }
        if (combine(spaceship.getY(), spaceship.getRadius()) > bottomBound) {
            bottomBound = combine(spaceship.getY(), spaceship.getRadius());
        }
        if (combine(spaceship.getX(), spaceship.getRadius()) < leftBound) {
            leftBound = combine(spaceship.getX(), spaceship.getRadius());
        }
        // handle asteroids
        for (Asteroid a: asteroids) {
            Orbit o = a.getOrbit();
            if (combine(o.getY() - o.getRadius(), a.getRadius()) < topBound) {
                topBound = combine(o.getY() - o.getRadius(), a.getRadius());
            }
            if (combine(o.getX() + o.getRadius(), a.getRadius()) > rightBound) {
                rightBound = combine(o.getX() + o.getRadius(), a.getRadius());
            }
            if (combine(o.getY() + o.getRadius(), a.getRadius()) > bottomBound) {
                bottomBound = combine(o.getY() + o.getRadius(), a.getRadius());
            }
            if (combine(o.getX() - o.getRadius(), a.getRadius()) < leftBound) {
                leftBound = combine(o.getX() - o.getRadius(), a.getRadius());
            }
        }
        // handle planets
        for (Planet p: planets) {
            double greaterRadius = Math.max(p.getRadius(), p.getGravityRadius());
            if (combine(p.getY(), greaterRadius) < topBound) {
                topBound = combine(p.getY(), greaterRadius);
            }
            if (combine(p.getX(), greaterRadius) > rightBound) {
                rightBound = combine(p.getX(), greaterRadius);
            }
            if (combine(p.getY(), greaterRadius) > bottomBound) {
                bottomBound = combine(p.getY(), greaterRadius);
            }
            if (combine(p.getX(), greaterRadius) < leftBound) {
                leftBound = combine(p.getX(), greaterRadius);
            }
        }
        double[] result;
        result = new double[4];
        result[0] = topBound;
        result[1] = rightBound;
        result[2] = bottomBound;
        result[3] = leftBound;
        return result; // there may or may not be a memory leak
    }
}

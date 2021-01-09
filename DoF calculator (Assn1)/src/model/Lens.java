package model;

/**
 * Lens class models the information about a lens.
 * Data includes name, maximum aperture, focal length.
 * It supports retrieving its attributes.
 */

public class Lens {
    private String lensName;
    private double maximumAperture;
    private int focalLength;        // Measured in mm

    public Lens(String lensName, double maximumAperture, int focalLength) {
        this.lensName = lensName;
        this.maximumAperture = maximumAperture;
        this.focalLength = focalLength;
    }

    //Getters for attributes
    public String getLensName() {
        return lensName;
    }

    public double getMaximumAperture() {
        return maximumAperture;
    }

    public int getFocalLength() {
        return focalLength;
    }

}

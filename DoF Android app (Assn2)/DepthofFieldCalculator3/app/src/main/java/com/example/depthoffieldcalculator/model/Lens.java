package com.example.depthoffieldcalculator.model;

/**
 * Lens class models the information about a lens.
 * Data includes name, maximum aperture, focal length, iconID.
 * It supports retrieving its attributes.
 */

public class Lens {
    private String lensName;
    private double maximumAperture;
    private int focalLength;        // Measured in mm

    private int iconID;             // Lens image id

    // Constructor
    public Lens(String lensName, double maximumAperture, int focalLength, int iconID) {
        this.lensName = lensName;
        this.maximumAperture = maximumAperture;
        this.focalLength = focalLength;
        this.iconID = iconID;
    }

    // Getter to get icon id
    public int getIconID() {
        return iconID;
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

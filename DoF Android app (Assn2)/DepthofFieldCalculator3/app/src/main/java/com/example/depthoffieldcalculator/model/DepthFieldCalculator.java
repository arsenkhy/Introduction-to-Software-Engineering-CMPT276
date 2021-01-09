package com.example.depthoffieldcalculator.model;

/**
 * The DepthFieldCalculator class models the information
 * about the Lens, user input aperture and distance. All
 * calculations made in this class are made in mm. It supports
 * calculations of Depth of Field, Hyperfocal distance, near and
 * far focal points.
 */

public class DepthFieldCalculator {
    public static final double M_TO_MM_CONVERSION = 1000;
    private Lens lens;
    private double aperture;
    private double subjectDistance;          //Measured in m
    private double circleOfConfusion;        //Measured in mm

    // Constructor
    public DepthFieldCalculator(Lens lens, double aperture, double subjectDistance, double circleOfConfusion) {
        this.lens = lens;
        this.aperture = aperture;
        this.subjectDistance = subjectDistance * M_TO_MM_CONVERSION;
        this.circleOfConfusion = circleOfConfusion;
    }

    public double getHyperFocalDistance() {
        return (lens.getFocalLength() * lens.getFocalLength())
                / (aperture * circleOfConfusion);
    }

    //Private function to reduce the code repentance in calculations
    private double getNumerator() {
        return getHyperFocalDistance() * subjectDistance;
    }

    private double getDenominator() {
        return subjectDistance - lens.getFocalLength();
    }

    public double getNearFocalPoint() {
        return getNumerator() /
                (getHyperFocalDistance() + getDenominator());
    }

    private boolean isBeyondHyperfocalDistance(){
        //The subject distance cannot be bigger than hyperfocal distance in order to find far focal point
        return subjectDistance > getHyperFocalDistance();
    }

    public double getFarFocalPoint() {
        if (isBeyondHyperfocalDistance()) {
            return Double.POSITIVE_INFINITY;    // +infinity, because distance is bigger that hyper focal distance
        }
        return getNumerator() /
                (getHyperFocalDistance() - getDenominator());
    }

    public double getDepthOfField() {
        if (isBeyondHyperfocalDistance()) {
            return Double.POSITIVE_INFINITY;
        }
        return getFarFocalPoint() - getNearFocalPoint();
    }
}

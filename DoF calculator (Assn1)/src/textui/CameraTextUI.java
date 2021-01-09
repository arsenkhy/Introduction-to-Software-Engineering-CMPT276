package textui;

import model.DepthFieldCalculator;
import model.Lens;
import model.LensManager;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * CameraTextUI class models user interface in the terminal
 * that taking inputs from the user in order to compute
 * consequent calculations. Data includes LensManager
 * It supports the information user inputs from terminal.
 * Invariants: The user choice shall not exceed required values
 *             Aperture cannot exceed its maximum value
 */
public class CameraTextUI {
    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    public static final int MM_TO_M_CONVERTER = 1000;   //Conversion from mm to m
    private LensManager manager;
    private Scanner in = new Scanner(System.in);// Read from keyboard

    public CameraTextUI(LensManager manager) {
        // Accept and store a reference to the lens manager (the model)
        // (this design is called "dependency injection")
        this.manager = manager;

        // Populate lenses (Make, max aperture (smallest supported F number), focal length [mm]):
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }

    public void show() {
        boolean isDone = false;
        while(!isDone) {
            System.out.println("Lenses to pick from: ");

            //Printing all current lenses that stored from the LensManager
            int showNumber = 0;
            for (Lens lens : manager) {
                System.out.println("\t" + showNumber + ". " +
                        lens.getLensName() + " " +
                        lens.getFocalLength() + "mm F" +
                        lens.getMaximumAperture());
                showNumber++;
            }
            System.out.println("\t(-1 to exit)");

            System.out.print(": ");
            int choice = in.nextInt();
            boolean exit = choice == -1;
            boolean wrongIndex = choice < 0 || choice > manager.getCounter() -1;    //Correct user input checking
            if (exit) {
                break;
            } else if (wrongIndex) {
                System.out.println("Error: Invalid lens index.\n");
            } else {
                System.out.print("Aperture [the F number]: ");
                double aperture = in.nextDouble();

                //Checking if the user's given aperture is in compliance with class invariant
                boolean wrongAperture = aperture <
                                        manager.retrieveLens(choice).getMaximumAperture();
                if (wrongAperture) {
                    System.out.println("ERROR: This aperture is not possible with this lens\n");
                }
                else {
                    System.out.print("Distance to subject [m]: ");
                    double subjectDistance = in.nextDouble();

                    DepthFieldCalculator output = new DepthFieldCalculator(
                            manager.retrieveLens(choice),
                            aperture, subjectDistance,
                            COC);

                    //Printing the calculations
                    System.out.println("\tIn focus: " + formatM(output.getNearFocalPoint()) +
                            "m to " + formatM(output.getFarFocalPoint()) +
                            "m [DoF = " + formatM(output.getDepthOfField()) +
                            "m]");
                    System.out.println("\tHyperfocal point: " + formatM(output.getHyperFocalDistance())
                            + "m\n");
                }
            }
        }
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM / MM_TO_M_CONVERTER);  //Calculations made in mm. Converted into m.
    }
}

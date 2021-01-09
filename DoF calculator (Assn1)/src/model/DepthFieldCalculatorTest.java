package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The DepthFieldCalculatorTest class models test asserts.
 * It creates assertEquals tests of all methods of DepthFieldCalculator
 * class.
 */
class DepthFieldCalculatorTest {
    double COC = 0.029;

    //Initializing the test values for the class for further checking
    Lens firstLens = new Lens("Canon", 1.8, 50);
    DepthFieldCalculator output1 = new DepthFieldCalculator(firstLens, 1.8, 1.1, COC);

    Lens secondLens = new Lens("Tamron", 2.8, 90);
    DepthFieldCalculator output2 = new DepthFieldCalculator(secondLens, 3.4, 2.3, COC);

    Lens thirdLens = new Lens("Nikon", 4, 200);
    DepthFieldCalculator output3 = new DepthFieldCalculator(thirdLens, 5.6, 2.8, COC);


    @Test
    void getDepthOfField() {
        assertEquals(48.25, output1.getDepthOfField(), 0.01);   //First combination of lens
        assertEquals(123.84, output2.getDepthOfField(), 0.01);   //Second combination of lens
        assertEquals(59.12, output3.getDepthOfField(), 0.01);   //Third combination of lens
    }

    @Test
    void getHyperFocalDistance() {
        assertEquals(47893, output1.getHyperFocalDistance(), 1);   //First combination of lens
        assertEquals(82150, output2.getHyperFocalDistance(), 1);   //Second combination of lens
        assertEquals(246305, output3.getHyperFocalDistance(), 1);   //Third combination of lens
    }

    @Test
    void getNearFocalPoint() {
        assertEquals(1076, output1.getNearFocalPoint(), 1);   //First combination of lens
        assertEquals(2240, output2.getNearFocalPoint(), 1);   //Second combination of lens
        assertEquals(2771, output3.getNearFocalPoint(), 1);   //Third combination of lens
    }

    @Test
    void getFarFocalPoint() {
        assertEquals(1125, output1.getFarFocalPoint(), 1);   //First combination of lens
        assertEquals(2364, output2.getFarFocalPoint(), 1);   //Second combination of lens
        assertEquals(2830, output3.getFarFocalPoint(), 1);   //Third combination of lens
    }
}
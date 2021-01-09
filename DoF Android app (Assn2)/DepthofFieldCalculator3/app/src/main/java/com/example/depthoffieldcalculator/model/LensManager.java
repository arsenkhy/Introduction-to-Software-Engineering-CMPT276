package com.example.depthoffieldcalculator.model;

import com.example.depthoffieldcalculator.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * LensManager singleton class models the list of lenses and their
 * information. Data includes list, counter. The class
 * is iterable and supports adding, retrieving and
 * iterating through lenses.
 */
public class LensManager implements Iterable<Lens>{
    private final List<Lens> lenses = new ArrayList<>();
    private static int counter = 0;


    // *Used course tutorial on: https://www.youtube.com/watch?v=evkPjPIV6cw&feature=youtu.be
    private static LensManager instance;

    private LensManager() {
        // To prevent anyone else of instantiation
    }

    // Singleton
    public static LensManager getInstance() {
        // If there is no LensManager, create one
        if (instance == null) {
            instance = new LensManager();
            addLenses();               // Populate with existing lenses
        }
        return instance;
    }

    public List<Lens> getLenses() {
        return lenses;
    }

    public static int getCounter() {
        return counter;
    }

    public void add(Lens lens) {
        lenses.add(lens);
        counter++;      //Each additions increments counter to track list size.
    }

    public Lens retrieveLens(int index) {
        return lenses.get(index);
    }

    public static void addLenses() {
        instance.add(new Lens("Canon", 1.8, 50, R.drawable.canon));
        instance.add(new Lens("Tamron", 2.8, 90, R.drawable.tamron));
        instance.add(new Lens("Sigma", 2.8, 200, R.drawable.cannon));
        instance.add(new Lens("Nikon", 4, 200, R.drawable.nikon));
    }

    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }
}

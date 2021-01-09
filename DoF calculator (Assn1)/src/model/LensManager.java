package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * LensManager class models the list of lenses and their
 * information. Data includes list, counter. The class
 * is iterable and supports adding, retrieving and
 * iterating through lenses.
 */
public class LensManager implements Iterable<Lens>{
    private final List<Lens> lenses = new ArrayList<>();
    private static int counter = 0;

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

    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }
}

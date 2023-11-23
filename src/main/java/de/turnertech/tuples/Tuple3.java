package de.turnertech.tuples;

import java.util.Objects;

/**
 * A Triplet
 */
public class Tuple3<A,B,C> extends Tuple {

    private final A element0;

    private final B element1;

    private final C element2;

    /**
     * Constructor
     * @param element0 element 0
     * @param element1 element 1
     * @param element2 element 2
     */
    public Tuple3(A element0, B element1, C element2) {
        super(new Object[]{Objects.requireNonNull(element0), Objects.requireNonNull(element1), Objects.requireNonNull(element2)});
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
    }

    /**
     * Gets element at the 0 index
     * @return element at the 0 index
     */
    public A getElement0() {
        return element0;
    }

    /**
     * Gets element at the 1 index
     * @return element at the 1 index
     */
    public B getElement1() {
        return element1;
    }

    /**
     * Gets element at the 2 index
     * @return element at the 2 index
     */
    public C getElement2() {
        return element2;
    }
    
}

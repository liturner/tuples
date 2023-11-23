package de.turnertech.tuples;

import java.util.Objects;

/**
 * A Single
 */
public class Tuple1<A> extends Tuple {

    private final A element0;

    /**
     * Constructor
     * @param element0 element 0
     */
    public Tuple1(A element0) {
        super(new Object[]{Objects.requireNonNull(element0)});
        this.element0 = element0;
    }

    /**
     * Gets element at the 0 index
     * @return element at the 0 index
     */
    public A getElement0() {
        return element0;
    }
    
}

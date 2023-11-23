package de.turnertech.tuples;

import java.util.Objects;

/**
 * A Double
 */
public class Tuple2<A,B> extends Tuple {

    private final A element0;

    private final B element1;

    /**
     * Constructor
     * @param element0 element 0
     * @param element1 element 1
     */
    public Tuple2(A element0, B element1) {
        super(new Object[]{Objects.requireNonNull(element0), Objects.requireNonNull(element1)});
        this.element0 = element0;
        this.element1 = element1;
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
    
}

package de.turnertech.tuples;

import java.util.Objects;

public class Tuple2<A,B> extends Tuple {

    private final A element0;

    private final B element1;

    public Tuple2(A element0, B element1) {
        super(new Object[]{Objects.requireNonNull(element0), Objects.requireNonNull(element1)});
        this.element0 = element0;
        this.element1 = element1;
    }

    public A getElement0() {
        return element0;
    }

    public B getElement1() {
        return element1;
    }
    
}

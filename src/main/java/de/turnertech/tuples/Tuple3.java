package de.turnertech.tuples;

import java.util.Objects;

public class Tuple3<A,B,C> extends Tuple {

    private final A element0;

    private final B element1;

    private final C element2;

    public Tuple3(A element0, B element1, C element2) {
        super(new Object[]{Objects.requireNonNull(element0), Objects.requireNonNull(element1), Objects.requireNonNull(element2)});
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
    }

    public A getElement0() {
        return element0;
    }

    public B getElement1() {
        return element1;
    }

    public C getElement2() {
        return element2;
    }
    
}

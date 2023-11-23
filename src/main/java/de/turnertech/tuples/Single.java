package de.turnertech.tuples;

import java.util.Objects;

public class Single<A> extends Tuple {

    private final A element0;

    public Single(A element0) {
        super(new Object[]{Objects.requireNonNull(element0)});
        this.element0 = element0;
    }

    public A getElement0() {
        return element0;
    }
    
}

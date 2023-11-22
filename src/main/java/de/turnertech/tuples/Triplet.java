package de.turnertech.tuples;

public class Triplet<A,B,C> extends Tuple {

    public Triplet(A element0, B element1, C element2) {
        super(new Object[]{element0, element1, element2});
    }

    public A getElement0() {
        return (A)elements[0];
    }

    public B getElement1() {
        return (B)elements[1];
    }

    public C getElement2() {
        return (C)elements[2];
    }
    
}

package de.turnertech.tuples;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * n-Tuple
 */
public class Tuple {
    
    protected final Object[] elements;

    public static <A,B,C> Triplet<A,B,C> from(A element0, B element1, C element2) {
        return new Triplet<>(element0, element1, element2);
    }

    public Tuple(Object... elements) {
        this.elements = elements == null ? new Object[0] : elements;
    }

    public Object getElement(int index) {
        return elements[index];
    }

    public int getSize() {
        return elements.length;
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    private void flatten(List<Object> out) {
        for(Object element : elements) {
            if(element instanceof Tuple) {
                ((Tuple)element).flatten(out);
            } else {
                out.add(element);
            }
        }
    }

    public Tuple flatten() {
        LinkedList<Object> returnCollection = new LinkedList<>();
        this.flatten(returnCollection);
        return new Tuple(returnCollection.toArray());
    }

    @Override
    public String toString() {
        final StringWriter stringWriter = new StringWriter();
        stringWriter.write("(");
        if(elements.length > 0) {
            stringWriter.write(Objects.toString(elements[0], ""));
        }
        for(int i = 1; i < elements.length; ++i) {
            stringWriter.write(", ");
            stringWriter.write(Objects.toString(elements[i], ""));
        }
        stringWriter.write(")");
        return stringWriter.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(elements);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tuple other = (Tuple) obj;
        return Arrays.deepEquals(elements, other.elements);
    }
    
}

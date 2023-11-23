package de.turnertech.tuples;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * n-Tuple, the core of this package. This class models the concept of an untyped
 * n-Tuple of any length. It provides basic functionality for creation and reading
 * of immutable Tuples.
 */
public class Tuple implements Collection<Object> {
    
    private class TupleIterator implements Iterator<Object> {

        private int currentIndex;

        private final Object[] elements;

        public TupleIterator(Tuple tuple) {
            this.elements = tuple.flatten().toArray();
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < elements.length;
        }

        @Override
        public Object next() {
            if(currentIndex >= elements.length) {
                throw new NoSuchElementException("Attempted to iterate beyond the end of a tuple.");
            }
            return elements[currentIndex++];
        }
        
    }

    protected final Object[] elements;

    public static EmptyTuple from() {
        return new EmptyTuple();
    }

    public static <A> Single<A> from(A element0) {
        return new Single<>(element0);
    }

    public static <A,B> Double<A,B> from(A element0, B element1) {
        return new Double<>(element0, element1);
    }

    public static <A,B,C> Triple<A,B,C> from(A element0, B element1, C element2) {
        return new Triple<>(element0, element1, element2);
    }

    public Tuple(Object... elements) {
        if(elements == null) {
            this.elements = new Object[0];
        } else {
            for(Object element : elements) {
                Objects.requireNonNull(element);
            }
            this.elements = elements;
        }
    }

    public Object get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(index);
        }
        final TupleIterator iterator = new TupleIterator(this);
        for(int i = 0; i < index; ++i) {
            iterator.next();
        }
        return iterator.next();
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
            stringWriter.write(Objects.toString(elements[0], "()"));
        }
        for(int i = 1; i < elements.length; ++i) {
            stringWriter.write(", ");
            stringWriter.write(Objects.toString(elements[i], "()"));
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

    /**
     * <p>Note that the size of a Tuple is not the same as for other collections! For a Tuple,
     * the size of the collection is the cont of elements.</p>
     * {@inheritDoc}
     */
    @Override
    public int size() {
        int returnSize = 0;
        for(Object element : elements) {
            returnSize += element instanceof Tuple ? ((Tuple)element).size() : 1;
        }
        return returnSize;
    }

    @Override
    public boolean contains(Object o) {
        for(Object element : elements) {
            if(element instanceof Tuple) {
                if(((Tuple)element).contains(o)) {
                    return true;
                }
            } else {
                if(Objects.equals(element, o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return Arrays.asList(this.flatten().toArray()).containsAll(Objects.requireNonNull(c));
    }

    @Override
    public Iterator<Object> iterator() {
        return new TupleIterator(this);
    }

    @Override
    public Object[] toArray() {
        return elements;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean add(Object e) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'add'");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'remove'");
    }

    @Override
    public boolean addAll(Collection<? extends Object> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'addAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'retainAll'");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'clear'");
    }
    
}

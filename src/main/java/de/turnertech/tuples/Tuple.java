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
 * <p>n-Tuple, the core of this package. This class models the concept of an untyped
 * n-Tuple of any length. It provides basic functionality for creation and reading
 * of immutable Tuples. It implements the Collection interface in a read only 
 * manner. Any calls to functions which would modify the collection will throw
 * exceptions.</p>
 * 
 * <p>This implementation does not support null elements!</p>
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

    private final Object[] elements;

    /**
     * Create an empty tuple
     * @return an empty tuple
     */
    public static Tuple0 from() {
        return new Tuple0();
    }

    /**
     * Create a single tuple
     * @param <A> the type of element0
     * @param element0 the element to store
     * @return the tuple
     */
    public static <A> Tuple1<A> from(A element0) {
        return new Tuple1<>(element0);
    }

    /**
     * Create a double tuple
     * @param <A> the type of element0
     * @param <B> the type of element1
     * @param element0 the element to store
     * @param element1 the element to store
     * @return the tuple
     */
    public static <A,B> Tuple2<A,B> from(A element0, B element1) {
        return new Tuple2<>(element0, element1);
    }

    /**
     * Create a triple tuple
     * @param <A> the type of element0
     * @param <B> the type of element1
     * @param <C> the type of element2
     * @param element0 the element to store
     * @param element1 the element to store
     * @param element2 the element to store
     * @return the tuple
     */
    public static <A,B,C> Tuple3<A,B,C> from(A element0, B element1, C element2) {
        return new Tuple3<>(element0, element1, element2);
    }

    /**
     * Constructs a fixed length, non typed tuple.
     * 
     * @param elements the elements
     */
    public Tuple(Object... elements) {
        if(elements == null || elements.length == 0) {
            this.elements = new Object[0];
        } else {
            for(Object element : elements) {
                Objects.requireNonNull(element);
            }
            this.elements = elements;
        }
    }

    /**
     * Can be considered as tuple.flatten().get(...)
     * <pre>
     *(4, (), 8, (((), 6))).get(1) == 8
     * </pre>
     * @param index of the next non null element in the tuple.
     * @return the non null element in the tuple.
     */
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

    /**
     * Gets the element at the index in this tuple, such that:
     * <pre>
     *(4, (), 8, (((), 6))).getElement(1) == ()
     * </pre>
     * @param index the next element in the Tuple
     * @return the element in the Tuple
     */
    public Object getElement(int index) {
        if(index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException(index);
        }
        return elements[index];
    }

    /**
     * Equivelant to tuple.flatten().size() == 0
     */
    public boolean isEmpty() {
        return flatten().elements.length == 0;
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

    /**
     * <p>Removes all Tuples contained in this Tuple, and return a single "flat" Tuple. For example:</p>
     * <pre>
     *myTuple.toString() = (4, (), 8, (((), 6)))
     *myTuple.flatten().toString() = (4, 8, 6)
     * </pre>
     * 
     * @return A tuple containing no sub tuples
     */
    public Tuple flatten() {
        LinkedList<Object> returnCollection = new LinkedList<>();
        this.flatten(returnCollection);
        return new Tuple(returnCollection.toArray());
    }

    /**
     * Uses Objects.toString() to represent this tuple in the for ((), 2, SomeString, etc)
     */
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

    /**
     * Contrary to the rest of the functions, this implementation checks equality
     * of the underlying array, and not equality in terms of "Tuple Equality".
     * e.g. ((), 5) != (5, ()).
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(elements);
        return result;
    }

    /**
     * Contrary to the rest of the functions, this implementation checks equality
     * of the underlying array, and not equality in terms of "Tuple Equality".
     * e.g. ((), 5) != (5, ()).
     */
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
     * the size of the collection is the count of elements contained in this and all sub tuples.
     * For example:</p>
     * <pre>
     *((), 8, ((5)), ()).size() == 2
     * </pre>
     * @see #length()
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

    /**
     * <p>Gets the length of the Tuple</p>
     * <pre>
     *((), 8, ((5)), ()).length() == 4
     * </pre>
     * @see #size()
     * @return the length of the tuple
     */
    public int length() {
        return elements.length;
    }

    /**
     * Equivelant to tuple.flatten().contains(...)
     */
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

    /**
     * Equivelant to tuple.flatten().containsAll(...)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return Arrays.asList(this.flatten().toArray()).containsAll(Objects.requireNonNull(c));
    }

    /**
     * <p>Returns a "Tuple Aware" iterator which ignores empty tuples, and iterates through child Tuples. For
     * example given the Tuple ((9, ((), 1)), 1), this Iterator will respond with:</p>
     * <pre>
     *iter.next() == 9
     *iter.next() == 1
     *iter.next() == 1
     *iter.next() throws NoSuchElementException
     * </pre>
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleIterator(this);
    }

    /**
     * Returns the raw contents of the Tuple, such that they could be used to create another equal Tuple.
     * For example, given the Tuple ((), 1), this function will return an Object[]{Tuple0, 1}.
     */
    @Override
    public Object[] toArray() {
        return elements;
    }

    /**
     * Tuples are multi-typed and do not support 'toArray'
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Tuples are multi-typed and do not support 'toArray'");
    }

    /**
     * Tuples are immutable and do not support 'add'
     */
    @Override
    public boolean add(Object e) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'add'");
    }

    /**
     * Tuples are immutable and do not support 'remove'
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'remove'");
    }

    /**
     * Tuples are immutable and do not support 'addAll'
     */
    @Override
    public boolean addAll(Collection<? extends Object> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'addAll'");
    }

    /**
     * Tuples are immutable and do not support 'removeAll'
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'removeAll'");
    }

    /**
     * Tuples are immutable and do not support 'retainAll'
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'retainAll'");
    }

    /**
     * Tuples are immutable and do not support 'clear'
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Tuples are immutable and do not support 'clear'");
    }
    
}

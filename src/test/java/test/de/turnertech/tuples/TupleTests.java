package test.de.turnertech.tuples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import de.turnertech.tuples.Tuple;
import de.turnertech.tuples.Tuple0;
import de.turnertech.tuples.Tuple1;
import de.turnertech.tuples.Tuple2;

class TupleTests {

    private final Tuple testTuple1 = new Tuple(5, new Tuple(new Tuple2<>(new Tuple0(), 1337), "Billy"));

    @Test
    void valueEquality() {
        Tuple a = new Tuple(5, 10);
        Tuple b = new Tuple(5, 10);
        assertEquals(b, a);

        b = new Tuple(6, 10);
        assertNotEquals(b, a);
    }

    @Test
    void lengthTest() {
        assertEquals(3, testTuple1.size());
        assertEquals(2, testTuple1.length());
    }

    @Test
    void getTest() {
        assertEquals("(5, (((), 1337), Billy))", testTuple1.toString());
        assertEquals(5, testTuple1.get(0));
        assertEquals(1337, testTuple1.get(1));
        assertEquals("Billy", testTuple1.get(2));
        assertThrows(IndexOutOfBoundsException.class,() -> testTuple1.get(3));
        assertThrows(IndexOutOfBoundsException.class,() -> testTuple1.get(-1));
    }

    @Test
    void toStringTest() {
        assertEquals("(5, 10)", new Tuple(5, 10).toString());
        assertEquals("(5, (5, 10))", new Tuple(5, new Tuple(5, 10)).toString());
        assertEquals("(5, ((), 10))", new Tuple(5, new Tuple(new Tuple(), 10)).toString());
        assertEquals("(5, ((), Billy))", new Tuple(5, new Tuple(new Tuple(), "Billy")).toString());
        assertEquals("(5, ((()), Billy))", new Tuple(5, new Tuple(new Tuple1<>(new Tuple0()), "Billy")).toString());
        assertEquals("(5, (((7, 3)), Billy))", new Tuple(5, new Tuple(new Tuple1<>(new Tuple2<>(7, 3)), "Billy")).toString());
    }

    @Test
    void sizeTest() {
        assertEquals(2, new Tuple(5, 10).size());
        assertEquals(3, new Tuple(5, new Tuple(5, 10)).size());
        assertEquals(2, new Tuple(5, new Tuple(new Tuple(), 10)).size());
        assertEquals(2, new Tuple(5, new Tuple(new Tuple(), "Billy")).size());
        assertEquals(2, new Tuple(5, new Tuple(new Tuple1<>(new Tuple0()), "Billy")).size());
        assertEquals(0, Tuple.from(Tuple.from(new Tuple0()), new Tuple0(), new Tuple0()).size());
    }

    @Test
    void flattenTest() {
        assertEquals("(5, 10)", new Tuple(5, 10).flatten().toString());
        assertEquals("(5, 5, 10)", new Tuple(5, new Tuple(5, 10)).flatten().toString());
        assertEquals("(5, 10)", new Tuple(5, new Tuple(new Tuple(), 10)).flatten().toString());
        assertEquals("(5, Billy)", new Tuple(5, new Tuple(new Tuple(), "Billy")).flatten().toString());
    }

    @Test
    void isEmptyTest() {
        assertFalse(testTuple1.isEmpty());
        assertTrue(Tuple.from(Tuple.from(new Tuple0()), new Tuple0(), new Tuple0()).isEmpty());
    }

    @Test
    void tuple1Test() {
        assertEquals(9, new Tuple1<>(9).getElement0());
    }

    @Test
    void containsAllTest() {
        List<Integer> col = Arrays.asList(3, 6);
        assertTrue(new Tuple(3, 6, 8).containsAll(col));
        assertFalse(new Tuple(3, 7, 8).containsAll(col));
    }

    @Test
    void containsTest() {
        assertTrue(testTuple1.contains(1337));
        assertTrue(testTuple1.contains("Billy"));
    }

    @Test
    void unsupportedTest() {
        List<Integer> col = Arrays.asList(4, 5);

        assertThrows(UnsupportedOperationException.class,() -> testTuple1.clear());
        assertThrows(UnsupportedOperationException.class,() -> testTuple1.add(7));
        assertThrows(UnsupportedOperationException.class,() -> testTuple1.addAll(col));
        assertThrows(UnsupportedOperationException.class,() -> testTuple1.remove(2));
        assertThrows(UnsupportedOperationException.class,() -> testTuple1.retainAll(col));
        assertThrows(UnsupportedOperationException.class,() -> testTuple1.removeAll(col));
    }

    @Test
    void fromTest() {
        assertNotNull(Tuple.from());
        assertNotNull(new Tuple((Object[])null));
    }
    
    @Test
    void iteratorTest() {
        Iterator<Object> iter = testTuple1.iterator();
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(1337, iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Billy", iter.next());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, ()->iter.next());
    }

    @Test
    void getElementTest() {
        assertThrows(IndexOutOfBoundsException.class, ()->testTuple1.getElement(-1));
        assertEquals(5, testTuple1.getElement(0));
        assertInstanceOf(Tuple.class, testTuple1.getElement(1));
        assertThrows(IndexOutOfBoundsException.class, ()->testTuple1.getElement(2));
    }

}

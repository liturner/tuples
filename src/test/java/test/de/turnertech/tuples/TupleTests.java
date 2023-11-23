package test.de.turnertech.tuples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.turnertech.tuples.Double;
import de.turnertech.tuples.EmptyTuple;
import de.turnertech.tuples.Single;
import de.turnertech.tuples.Tuple;

class TupleTests {

    private final Tuple testTuple1 = new Tuple(5, new Tuple(new Double<>(new EmptyTuple(), 1337), "Billy"));

    @Test
    void valueEquality() {
        Tuple a = new Tuple(5, 10);
        Tuple b = new Tuple(5, 10);
        assertEquals(b, a);

        b = new Tuple(6, 10);
        assertNotEquals(b, a);
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
        assertEquals("(5, ((()), Billy))", new Tuple(5, new Tuple(new Single<>(new EmptyTuple()), "Billy")).toString());
    }

    @Test
    void sizeTest() {
        assertEquals(2, new Tuple(5, 10).size());
        assertEquals(3, new Tuple(5, new Tuple(5, 10)).size());
        assertEquals(2, new Tuple(5, new Tuple(new Tuple(), 10)).size());
        assertEquals(2, new Tuple(5, new Tuple(new Tuple(), "Billy")).size());
        assertEquals(2, new Tuple(5, new Tuple(new Single<>(new EmptyTuple()), "Billy")).size());
    }

    @Test
    void flattenTest() {
        assertEquals("(5, 10)", new Tuple(5, 10).flatten().toString());
        assertEquals("(5, 5, 10)", new Tuple(5, new Tuple(5, 10)).flatten().toString());
        assertEquals("(5, 10)", new Tuple(5, new Tuple(new Tuple(), 10)).flatten().toString());
        assertEquals("(5, Billy)", new Tuple(5, new Tuple(new Tuple(), "Billy")).flatten().toString());
    }
    
}

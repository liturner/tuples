package test.de.turnertech.tuples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import de.turnertech.tuples.Tuple;

class TupleTests {

    @Test
    void valueEquality() {
        Tuple a = new Tuple(5, 10);
        Tuple b = new Tuple(5, 10);
        assertEquals(b, a);

        b = new Tuple(6, 10);
        assertNotEquals(b, a);
    }

    @Test
    void toStringTest() {
        assertEquals("(5, 10)", new Tuple(5, 10).toString());
        assertEquals("(5, (5, 10))", new Tuple(5, new Tuple(5, 10)).toString());
        assertEquals("(5, ((), 10))", new Tuple(5, new Tuple(new Tuple(), 10)).toString());
        assertEquals("(5, ((), Billy))", new Tuple(5, new Tuple(new Tuple(), "Billy")).toString());
        assertEquals("(5, (, Billy))", new Tuple(5, new Tuple(null, "Billy")).toString());
    }

    @Test
    void flattenTest() {
        assertEquals("(5, 10)", new Tuple(5, 10).flatten().toString());
        assertEquals("(5, 5, 10)", new Tuple(5, new Tuple(5, 10)).flatten().toString());
        assertEquals("(5, 10)", new Tuple(5, new Tuple(new Tuple(), 10)).flatten().toString());
        assertEquals("(5, Billy)", new Tuple(5, new Tuple(new Tuple(), "Billy")).flatten().toString());
    }
    
}

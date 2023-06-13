package moteur.couchbase.src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAdd() {
        Calcul calculator = new Calcul();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    void testSubtract() {
        Calcul calculator = new Calcul();
        int result = calculator.subtract(5, 3);
        assertEquals(2, result);
    }
}

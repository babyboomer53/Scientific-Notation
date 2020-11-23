package ScientificNotation;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExponentialNotationTest {

    @Test
    public void positiveExponent() {
        assertEquals("123,456.7800", new ExponentialNotation("1234.5678E+2").convertNumber());
    }

    @Test
    public void negativeExponent() {
        assertEquals("12.3457", new ExponentialNotation("1234.5678E-2").convertNumber());
    }

    @Test
    public void anotherNegativeExponent() {
        assertEquals("0.1235", new ExponentialNotation("1234.56E-4").convertNumber());
    }

    @Test
    public void noDecimalPoint() {
        assertEquals("-123,456.7800", new ExponentialNotation("-12345678E-2").convertNumber());
    }

    @Test
    public void leadingDecimalPoint() {
        ExponentialNotation exponentialNotation = new ExponentialNotation(".12345678e5");
        assertEquals("12,345.6780", exponentialNotation.convertNumber());
    }

    @Test
    public void trailingDecimalPoint() {
        ExponentialNotation exponentialNotation = new ExponentialNotation("12345678.e-8");
        assertEquals("0.1235", exponentialNotation.convertNumber());
    }

    @Test
    public void instantiationTest() {
        ExponentialNotation exponentialNotation = new ExponentialNotation("12345.7e5");
        assertEquals("1,234,570,000.0000", exponentialNotation.convertNumber());
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormatException() {
        ExponentialNotation exponentialNotation = new ExponentialNotation("12345.7z5");
    }
/*
    @org.junit.jupiter.api.Test
    void convertNumber() {
    }
 */
}
package FunWithScientificNotation;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScientificNotationTest {

    @Test
    public void positiveExponent() {
        assertEquals("123,456.7800", new ScientificNotation("1234.5678E+2").convertNumber());
    }

    @Test
    public void negativeExponent() {
        assertEquals("12.3457", new ScientificNotation("1234.5678E-2").convertNumber());
    }

    @Test
    public void anotherNegativeExponent() {
        assertEquals("0.1235", new ScientificNotation("1234.56E-4").convertNumber());
    }

    @Test
    public void noDecimalPoint() {
        assertEquals("-123,456.7800", new ScientificNotation("-12345678E-2").convertNumber());
    }

    @Test
    public void leadingDecimalPoint() {
        ScientificNotation scientificNotation = new ScientificNotation(".12345678e5");
        assertEquals("12,345.6780", scientificNotation.convertNumber());
    }

    @Test
    public void trailingDecimalPoint() {
        ScientificNotation scientificNotation = new ScientificNotation("12345678.e-8");
        assertEquals("0.1235", scientificNotation.convertNumber());
    }

    @Test
    public void instantiationTest() {
        ScientificNotation scientificNotation = new ScientificNotation("12345.7e5");
        assertEquals("1,234,570,000.0000", scientificNotation.convertNumber());
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormatException() {
        ScientificNotation scientificNotation = new ScientificNotation("12345.7z5");
    }

    @Test(expected = InvalidPrecisionNumber.class)
    public void setPrecisionNumber() throws InvalidPrecisionNumber {
        ScientificNotation scientificNotation = new ScientificNotation("12345.089E4");
        try {
            scientificNotation.setPrecision(-2);
        } catch (InvalidPrecisionNumber invalidPrecisionNumber) {
            throw new InvalidPrecisionNumber("Oops!");
        }
    }

}
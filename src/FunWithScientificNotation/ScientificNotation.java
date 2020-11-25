package FunWithScientificNotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScientificNotation {

    private final String regex = "^(-?\\d*)(\\.?)(\\d*)([eE])([-\\+]?)(\\d+)$";

    private String number = "";

    private int precision = 4;
    private final int exponent;
    private final String mantissa;

    public ScientificNotation(String number) {
        this.number = number;
        this.exponent = Integer.parseInt(number.replaceAll(regex, "$6"));
        this.mantissa = number.replaceAll(regex, "$1$2$3");
    }

    public void setPrecision(int precision) throws InvalidPrecisionNumber {
        if (precision >= 0) {
            this.precision = precision;
        } else {
            throw new InvalidPrecisionNumber(String.format("The argument must be greater then 0 (zero): %d", precision));
        }
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String convertNumber() throws NumberFormatException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        StringBuilder stringBuilder;
        int indexOfDecimalPoint = mantissa.indexOf('.') >= 0 ? mantissa.indexOf('.') : mantissa.length();
        String direction = number.replaceAll(regex, "$5");
        String result = "";
        if (matcher.find()) {
            String zeroFill = "";
            switch (direction) {
                case "-":
                    zeroFill = exponent >= indexOfDecimalPoint ? "0".repeat(exponent - indexOfDecimalPoint) : "";
                    stringBuilder = new StringBuilder(zeroFill.concat(mantissa.replaceAll("\\.", "")));
                    stringBuilder.insert(Math.abs(exponent - (indexOfDecimalPoint + zeroFill.length())), '.');
                    result = stringBuilder.toString();
                    break;
                case "+":
                case "":
                    int distanceFromEnd = mantissa.length() - indexOfDecimalPoint - 1;
                    zeroFill = exponent > distanceFromEnd ? "0".repeat(exponent - distanceFromEnd + 1) : "";
                    stringBuilder = new StringBuilder(mantissa.replaceAll("\\.", "").concat(zeroFill));
                    stringBuilder.insert((indexOfDecimalPoint + exponent), '.');
                    result = stringBuilder.toString();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }
        } else {
            throw new NumberFormatException();
        }
        return String.format("%,." + precision + "f", Double.parseDouble(result));
    }


    public static void main(String[] args) {
        ScientificNotation scientificNotation = new ScientificNotation("12545.678e3");
        try {
            scientificNotation.setPrecision(-3);
            System.out.printf("The conversion of %s is %s%n",
                    scientificNotation.getNumber(),
                    scientificNotation.convertNumber());
        } catch (InvalidPrecisionNumber invalidPrecisionNumber) {
            invalidPrecisionNumber.printStackTrace();
            System.exit(-1);
        }
    }
}

class InvalidPrecisionNumber extends Exception {
    private static final long serialVersionUID = 1L;
    InvalidPrecisionNumber(String message) {
        super(message);
    }
}

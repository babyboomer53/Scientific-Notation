package ScientificNotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExponentialNotation {

    private final String regex = "^(-?\\d*)(\\.?)(\\d*)([eE])([-\\+]?)(\\d+)$";

    private String number = "";

    private int precision = 4;
    private int exponent;
    private String base;
    private String padding = "";
    private String result = "";

    public ExponentialNotation(String number) {
        this.number = number;
        this.exponent = Integer.parseInt(number.replaceAll(regex, "$6"));
        this.base = number.replaceAll(regex, "$1$2$3");
    }

    public void setPrecision(int precision) {
        this.precision = precision;
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
        int indexOfDecimalPoint = base.indexOf('.') >= 0 ? base.indexOf('.') : base.length();
        String direction = number.replaceAll(regex, "$5");
        if (matcher.find()) {
            switch (direction) {
                case "-" -> {
                    //int distanceFromBeginning = indexOfDecimalPoint;
                    padding = exponent >= indexOfDecimalPoint ? "0".repeat(exponent - indexOfDecimalPoint) : "";
                    stringBuilder = new StringBuilder(padding.concat(base.replaceAll("\\.", "")));
                    stringBuilder.insert(Math.abs(exponent - (indexOfDecimalPoint + padding.length())), '.');
                    result = stringBuilder.toString();
                }
                case "+", "" -> {
                    int distanceFromEnd = base.length() - indexOfDecimalPoint - 1;
                    padding = exponent > distanceFromEnd ? "0".repeat(exponent - distanceFromEnd + 1) : "";
                    stringBuilder = new StringBuilder(base.replaceAll("\\.", "").concat(padding));
                    stringBuilder.insert((indexOfDecimalPoint + exponent), '.');
                    result = stringBuilder.toString();
                }
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
        } else {
            throw new NumberFormatException();
        }
        return String.format("%,." + precision + "f", Double.parseDouble(result));
    }

    public static void main(String[] args) {
        ExponentialNotation exponentialNotation = new ExponentialNotation("12345.7e-4");
        exponentialNotation.setPrecision(2);
        System.out.printf("The conversion of %s is %s%n",
                exponentialNotation.getNumber(),
                exponentialNotation.convertNumber());
    }
}


package ScientificNotation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExponentialNotation {

    private final String regex = "^(-?\\d*)(\\.?)(\\d*)([eE])([-\\+]?)(\\d+)$";

    private String number = "";

    private int precision = 4;
    private final int exponent;
    private final String base;

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
        String result = "";
        if (matcher.find()) {
            String zeroFill = "";
            switch (direction) {
                case "-":
                    zeroFill = exponent >= indexOfDecimalPoint ? "0".repeat(exponent - indexOfDecimalPoint) : "";
                    stringBuilder = new StringBuilder(zeroFill.concat(base.replaceAll("\\.", "")));
                    stringBuilder.insert(Math.abs(exponent - (indexOfDecimalPoint + zeroFill.length())), '.');
                    result = stringBuilder.toString();
                    break;
                case "+":
                case "":
                    int distanceFromEnd = base.length() - indexOfDecimalPoint - 1;
                    zeroFill = exponent > distanceFromEnd ? "0".repeat(exponent - distanceFromEnd + 1) : "";
                    stringBuilder = new StringBuilder(base.replaceAll("\\.", "").concat(zeroFill));
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
        ExponentialNotation exponentialNotation = new ExponentialNotation("12545.678e-3");
        exponentialNotation.setPrecision(3);
        System.out.printf("The conversion of %s is %s%n",
                exponentialNotation.getNumber(),
                exponentialNotation.convertNumber());
    }
}

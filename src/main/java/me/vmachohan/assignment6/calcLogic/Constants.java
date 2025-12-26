package me.vmachohan.assignment6.calcLogic;

/**
 * Class that contains all constants.
 * <p>
 * Constants are Strings for easier processing.
 */
public class Constants {
    /**
     * private constructor to avoid creating instance of this class
     */
    private Constants() {
    }

    /*
     * Constants related to the math (Operators, braces, etc.) */
    /// plus sign represented as string
    public static final String PLUS = "+";
    /// minus sign represented as string
    public static final String MINUS = "-";
    /// multiplication sign represented as string
    public static final String MULTIPLICATION = "*";
    /// division sign represented as string
    public static final String DIVISION = "/";
    /// exponent sign represented as string
    public static final String EXPONENT = "^";

    /// open brackets sign represented as string
    public static final String OPEN_BRACKET = "(";
    /// close brackets sign represented as string
    public static final String CLOSE_BRACKET = ")";

    /// floating point sign
    public static final String FLOATING_POINT = ".";

    /// equals sign represented as string
    public static final String EQUAL = "=";

    /// zero represented as a string
    public static final String ZERO = "0";

    /*
        math functions names
     */

    public static final String TAN = "tan";
    public static final String ATAN = "atan";
    public static final String SIN = "sin";
    public static final String COS = "cos";
    public static final String LOG10 = "log10";
    public static final String LOG2 = "log2";
    public static final String SQRT = "sqrt";


    /*
     * Regex constants
     * */

    /// regex that contains all allowed signs
    public static final String SIGN_REGEX = "[+\\-*/^]";
    /// number format (Allowed only numbers without floating point, or with 1 floating point.)
    public static final String NUMBER_FORMAT = "\\d+(\\.\\d+)?";
    /// format for the variables (Allowed only letters and numbers)
    public static final String VARIABLE_FORMAT = "[a-zA-Z]+(\\d)*?";


    public static boolean isOperator(String token) {
        return token.matches(SIGN_REGEX);
    }
}

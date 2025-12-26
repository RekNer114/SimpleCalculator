package me.vmachohan.assignment6.calcLogic.functions;

import java.util.HashMap;

import static me.vmachohan.assignment6.calcLogic.Constants.*;

/**
 * Class that contains all functions collected in hashmap where key=functionName
 * and value=IAction implementation.
 */
public class FunctionMapper {
    /*HashMap that contains String value of the function name
     and implementation of the functional interface IAction*/
    private static final HashMap<String, IFunction> functions = new HashMap<>();


    /*
        Static block called before calling anything from this class, so here put functions and their names to
        the map.
        Use method reference operator to avoid creation of class for each function, and improve readability
     */
    static {
        functions.put(SIN, Math::sin);
        functions.put(COS, Math::cos);
        functions.put(TAN, Math::tan);
        functions.put(ATAN, Math::atan);
        functions.put(LOG10, Math::log10);
        functions.put(LOG2, x -> Math.log(x) / Math.log(2));
        functions.put(SQRT, Math::sqrt);
    }

    /**
     * Method to that calculate provided function for provided value.
     *
     * @param function function name (sin, cos, tan, atan, etc.)
     * @param n        number for which this function will be applied
     * @return result of the function with provided number
     */
    public static double calculate(String function, double n) {
        IFunction func = functions.get(function);

        return func.calculate(n);
    }


    /**
     * Check if program support function provided by user
     *
     * @param functionName string that represents name of some function
     * @return true if functionName is present in HashMap
     */
    public static boolean isFunction(String functionName) {
        return functions.containsKey(functionName);
    }


}

package mike.spring.webstore.bootstrap.utilities;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class PreConditions {

    private PreConditions() {}

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 
     * @param condition a boolean expression that will be negate
     * @param message   exception message used if the test fails; will be converted to a string
     *                  using <i>String.format</i>.
     * @param args      optional exception message arguments
     * @throws IllegalArgumentException if conditions is false.
     */
    public static void test(boolean condition, String message, Object... args) {

        if (!condition) {
            throw new IllegalArgumentException(String.format(message, args));
        }
    }

    /**
     * The string value must match the given pattern.
     * 
     * @param str     value to assert
     * @param pattern regex pattern used for evaluation
     * @param message exception message used if the test fails; will be converted to a string
     * @param args    optional exception message arguments
     * @return the original string if conditions have been met.
     * @see {@link PreConditions#test(boolean, String, Object...)}
     */
    public static String match(String str, Pattern pattern, String message, Object... args) {
        PreConditions.notNull(str, message, args);
        PreConditions.test(pattern.asMatchPredicate().test(str), message, args);
        return str;
    }

    /**
     * The object must not be null.
     * 
     * @param obj object to assert
     * @return the original object if object is not null.
     * @See {@link PreConditions#notNull(Object, String, Object...)}
     */
    public static <T> T notNull(T obj) {
        return notNull(obj, "object required (null)");
    }

    /**
     * The object must not be null.
     * 
     * @param obj     object to assert
     * @param message exception message used if the test fails
     * @param args    optional exception message arguments
     * @return the original object if conditions have been met.
     * @See {@link PreConditions#test(boolean, String, Object...)}
     */
    public static <T> T notNull(T obj, String message, Object... args) {
        PreConditions.test(obj != null, message, args);
        return obj;
    }

    /**
     * The string value must not be null and must contain at least one non-whitespace character.
     * 
     * @param str value to assert
     * @return the original string if conditions have been met.
     * @See {@link PreConditions#notBlank(String, String, Object...)}
     */
    public static String notBlank(String str) {
        return notBlank(str, "string value not set (null, blank or empty)");
    }

    /**
     * The string value must not be null and must contain at least one non-whitespace character.
     * 
     * @param str     value to assert
     * @param message exception message used if the test fails
     * @param args    optional exception message arguments
     * @return the original value if conditions have been met.
     * @See {@link PreConditions#test(boolean, String, Object...)}
     */
    public static String notBlank(String str, String message, Object... args) {
        PreConditions.test(str != null && !str.isBlank(), message, args);
        return str;
    }

    /**
     * The object (String, Map or Collection) must not be null nor empty.
     * 
     * @param obj object to assert
     * @return the original object if conditions have been met.
     * @See {@link PreConditions#notEmpty(boolean, String, Object...)}
     */
    public static <T> T notEmpty(T obj) {
        return notEmpty(obj, "object is null or empty");
    }

    /**
     * The object (String, Map or Collection) must not be null nor empty.
     * 
     * @param obj     object to assert
     * @param message exception message used if the test fails
     * @param args    optional exception message arguments
     * @return the original object if conditions have been met.
     * @throws UnsupportedOperationException if object type is not supported.
     */
    public static <T> T notEmpty(T obj, String message, Object... args) {

        PreConditions.notNull(obj, message, args);

        switch (obj) {
          case String string -> PreConditions.test(!string.isEmpty(), message, args);
          case Collection<?> collection -> PreConditions.test(!collection.isEmpty(), message, args);
          case Map<?, ?> map -> PreConditions.test(!map.isEmpty(), message, args);
          default -> throw new UnsupportedOperationException(
                      "object type not supported: type=%s".formatted(obj.getClass().getName()));
        }

        return obj;
    }
}

package me.jameschan.hole.common;

/**
 * Represents the status codes for operations. This enum defines various status codes used to
 * indicate the outcome of an operation, where each code is associated with a specific meaning.
 */
public enum StatusCode {
    /**
     * Indicates null status.
     */
    NULL(-1),

    /**
     * Indicates that the operation was successful.
     */
    SUCCESS(0),

    /**
     * Indicates that the operation failed due to an invalid command.
     */
    INVALID_COMMAND(1);

    /**
     * Code associated with the status code.
     */
    private final int code;

    /**
     * Constructs a StatusCode with the specified code.
     * @param code The integer code of the status code.
     */
    StatusCode(final int code) {
        this.code = code;
    }

    /**
     * Returns the code associated with this status code.
     * @return The integer code of this status code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Converts a code into a status code.
     * @param code The code to convert.
     * @return the status code; or NULL status code if the code does not exist.
     */
    public static StatusCode fromCode(final int code) {
        for (final StatusCode statusCode : StatusCode.values()) {
            if (statusCode.getCode() == code) {
                return statusCode;
            }
        }

        return NULL;
    }
}

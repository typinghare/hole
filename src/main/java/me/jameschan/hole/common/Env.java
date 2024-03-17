package me.jameschan.hole.common;

/**
 * The essential environment properties of the local machine.
 * @param dir The user's present working directory.
 */
public record Env(
    String dir
) {
}

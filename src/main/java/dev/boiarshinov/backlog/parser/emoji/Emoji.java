package dev.boiarshinov.backlog.parser.emoji;

public record Emoji(
    String emoji,
    String name,
    String shortname,
    String unicode,
    String html,
    String category,
    String order) implements JsonPojo {
}

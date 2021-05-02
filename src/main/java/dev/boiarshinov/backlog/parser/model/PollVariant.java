package dev.boiarshinov.backlog.parser.model;

public record PollVariant(String emoji, Variant variant) {

    private static final String TEMPLATE = "- %s `%s` %s | %s";

    public String toPrettyString() {
        return String.format(TEMPLATE, emoji, variant.type().name().toLowerCase(), variant.topic(), variant.host());
    }
}

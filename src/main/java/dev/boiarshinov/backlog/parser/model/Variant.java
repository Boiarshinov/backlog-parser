package dev.boiarshinov.backlog.parser.model;

import java.util.EnumSet;

public record Variant(String topic, String host, Type type) {

    public enum Type {
        HOLYWAR(":fire:"),
        SHARING(":two_hearts:"),
        BRAINSTORM(":cyclone:"),
        LECTURE(":mortar_board:"),
        CODE_REVIEW(":eyes:"),
        UNKNOWN("");

        private final String emoji;

        private static final EnumSet<Type> LEGAL_TYPES = EnumSet.of(HOLYWAR, SHARING, BRAINSTORM, LECTURE, CODE_REVIEW);

        Type(String emoji) {
            this.emoji = emoji;
        }

        public static Type fromEmoji(String emoji) {
            return LEGAL_TYPES.stream()
                .filter(it -> it.getEmoji().equals(emoji))
                .findFirst()
                .orElse(UNKNOWN);
        }

        public String getEmoji() {
            return emoji;
        }
    }
}

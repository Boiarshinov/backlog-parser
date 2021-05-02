package dev.boiarshinov.backlog.parser.emoji;

import java.util.List;

public record EmojiList(List<Emoji> emojis) implements JsonPojo {
}

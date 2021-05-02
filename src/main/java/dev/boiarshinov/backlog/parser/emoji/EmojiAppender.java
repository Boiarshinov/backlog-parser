package dev.boiarshinov.backlog.parser.emoji;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.boiarshinov.backlog.parser.emoji.Emoji;
import dev.boiarshinov.backlog.parser.emoji.EmojiList;
import dev.boiarshinov.backlog.parser.model.PollVariant;
import dev.boiarshinov.backlog.parser.model.Variant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class EmojiAppender {

    private static final Random RANDOM = new Random();

    /** Mutable list */
    private final List<String> emojiList;

    public EmojiAppender() {
        //todo сделать так, чтобы из множества удалялись эмодзи, которые уже побеждали. Для этого нужно сканировать все страницы /overlook/id.
        final ObjectMapper objectMapper = new ObjectMapper();

        final String jsonString = getAllEmojiAsJsonString();
        final EmojiList emojis;
        try {
            emojis = objectMapper.readValue(jsonString, EmojiList.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to map emoji json to POJO", e);
        }

        final List<String> emojiShortcodes = emojis.emojis().stream()
            .filter(emoji -> emoji.category().contains("Animals & Nature"))
            .map(Emoji::shortname)
            .toList();

        //Пересоздаю список, т.к. List.of() иммутабельный
        emojiList = new ArrayList<>(emojiShortcodes);
    }

    public List<PollVariant> appendToAll(List<Variant> variants) {
        return variants.stream()
            .map(variant -> new PollVariant(getUniqueRandomEmoji(), variant))
            .toList();
    }

    private String getUniqueRandomEmoji() {
        if (emojiList.isEmpty()) {
            throw new RuntimeException("All unique emojis are gone!");
        }

        final int randomIndex = RANDOM.nextInt(emojiList.size());
        final String randomEmoji = emojiList.get(randomIndex);
        emojiList.remove(randomIndex);
        return randomEmoji;
    }

    private String getAllEmojiAsJsonString() {
        final Path path = Paths.get("src/main/resources/emoji.json");
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read a file " + path, e);
        }
    }
}

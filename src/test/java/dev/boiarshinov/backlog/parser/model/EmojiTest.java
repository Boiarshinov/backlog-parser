package dev.boiarshinov.backlog.parser.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.boiarshinov.backlog.parser.emoji.Emoji;
import org.junit.jupiter.api.Test;

class EmojiTest {

    @Test
    void fromJson() throws JsonProcessingException {
        final String inputString = """
            {
                  "emoji": "👩‍👩‍👧‍👧",
                  "name": "family: woman, woman, girl, girl",
                  "shortname": ":woman_woman_girl_girl:",
                  "unicode": "1F469 200D 1F469 200D 1F467 200D 1F467",
                  "html": "&#128105;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;",
                  "category": "People & Body (family)",
                  "order": ""
            }
            """;

        final ObjectMapper objectMapper = new ObjectMapper();
        final Emoji emoji = objectMapper.readValue(inputString, Emoji.class);
        System.out.println(emoji);
    }

}
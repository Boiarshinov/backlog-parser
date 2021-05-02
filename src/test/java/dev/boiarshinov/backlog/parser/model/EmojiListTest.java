package dev.boiarshinov.backlog.parser.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.boiarshinov.backlog.parser.emoji.EmojiList;
import org.junit.jupiter.api.Test;

class EmojiListTest {

    @Test
    void fromJson() throws JsonProcessingException {
        final String inputString = """
            {
              "emojis": [
                {
                  "emoji": "👩‍👩‍👧‍👧",
                  "name": "family: woman, woman, girl, girl",
                  "shortname": ":woman_woman_girl_girl:",
                  "unicode": "1F469 200D 1F469 200D 1F467 200D 1F467",
                  "html": "&#128105;&zwj;&#128105;&zwj;&#128103;&zwj;&#128103;",
                  "category": "People & Body (family)",
                  "order": ""
                },
                {
                  "emoji": "👩‍👩‍👧‍👦",
                  "name": "family: woman, woman, girl, boy",
                  "shortname": ":woman_woman_girl_boy:",
                  "unicode": "1F469 200D 1F469 200D 1F467 200D 1F466",
                  "html": "&#128105;&zwj;&#128105;&zwj;&#128103;&zwj;&#128102;",
                  "category": "People & Body (family)",
                  "order": ""
                }
              ]
            }
            """;

        final ObjectMapper objectMapper = new ObjectMapper();
        final EmojiList emojiList = objectMapper.readValue(inputString, EmojiList.class);
        System.out.println(emojiList);
    }
}
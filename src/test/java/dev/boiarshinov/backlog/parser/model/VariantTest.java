package dev.boiarshinov.backlog.parser.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VariantTest {

    @ParameterizedTest
    @ValueSource(strings = {":fire:", ":two_hearts:", ":cyclone:", ":mortar_board:", ":eyes:"})
    void typeFromEmojiAtNormalCase(String inputEmoji) {
        final Variant.Type actualType = Variant.Type.fromEmoji(inputEmoji);

        assertNotEquals(Variant.Type.UNKNOWN, actualType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"not_emoji", ":gloves:", ""})
    @NullSource
    void typeFromEmojiAtUnknown(String input) {
        final Variant.Type actualType = Variant.Type.fromEmoji(input);

        assertEquals(Variant.Type.UNKNOWN, actualType);
    }
}
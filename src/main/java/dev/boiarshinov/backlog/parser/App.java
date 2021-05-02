package dev.boiarshinov.backlog.parser;

import dev.boiarshinov.backlog.parser.emoji.EmojiAppender;
import dev.boiarshinov.backlog.parser.model.PollVariant;
import dev.boiarshinov.backlog.parser.model.Variant;

import java.util.List;

public class App {

    private static final String BACKLOG_URL = "https://raw.githubusercontent.com/Boiarshinov/holywar4j/master/content/backlog.md";

    public static void main(String[] args) {
        final BacklogPoller backlogPoller = new BacklogPoller(BACKLOG_URL);
        final BacklogParser backlogParser = new BacklogParser();
        final EmojiAppender emojiAppender = new EmojiAppender();

        final String mdBacklog = backlogPoller.poll();
        final List<Variant> variants = backlogParser.parseAndGetVotingVariants(mdBacklog);
        final List<PollVariant> pollVariants = emojiAppender.appendToAll(variants);

        //todo записывать в md файл
        pollVariants.stream()
            .map(PollVariant::toPrettyString)
            .forEach(System.out::println);
    }
}

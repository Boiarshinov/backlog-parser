package dev.boiarshinov.backlog.parser;

import dev.boiarshinov.backlog.parser.model.Table;
import dev.boiarshinov.backlog.parser.model.Variant;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public final class BacklogParser {

    private static final int BACKLOG_TABLE_INDEX = 0;
    private static final int EMOJI_COLUMN_INDEX = 0;
    private static final int TOPIC_COLUMN_INDEX = 1;
    private static final int HOST_COLUMN_INDEX = 2;

    private final Parser markdownParser;

    public BacklogParser() {
        this.markdownParser = Parser.builder()
            .extensions(List.of(
                TablesExtension.create(),
                YamlFrontMatterExtension.create()
            ))
            .build();
    }

    public List<Variant> parseAndGetVotingVariants(String input) {
        final Node rootNode = markdownParser.parse(input);

        final TableVisitor tableVisitor = new TableVisitor();
        rootNode.accept(tableVisitor);

        final List<Table> tables = tableVisitor.getTables();
        if (tables.isEmpty()) {
            throw new RuntimeException("There is no tables in backlog");
        }
        final Table table = tables.get(BACKLOG_TABLE_INDEX);

        final List<Table.Row> rows = table.content().rows();

        return rows.stream()
            .map(this::convertRowToVariant)
            .toList();
    }

    private Variant convertRowToVariant(Table.Row row) {
        final List<String> cells = row.cells();
        final String emoji = cells.get(EMOJI_COLUMN_INDEX);
        final String topic = cells.get(TOPIC_COLUMN_INDEX);
        final String host = cells.get(HOST_COLUMN_INDEX);

        return new Variant(topic, host, Variant.Type.fromEmoji(emoji));
    }


    private static class TableVisitor extends AbstractVisitor {

        private final List<Table> tables = new ArrayList<>();

        @Override
        public void visit(CustomBlock customBlock) {
            if (customBlock instanceof TableBlock) {
                final Table table = Table.from((TableBlock) customBlock);
                tables.add(table);
            } else {
                super.visit(customBlock);
            }
        }

        public List<Table> getTables() {
            return tables;
        }
    }
}

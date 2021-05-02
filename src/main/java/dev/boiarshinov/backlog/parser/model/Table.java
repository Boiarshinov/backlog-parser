package dev.boiarshinov.backlog.parser.model;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableBody;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.ext.gfm.tables.TableRow;
import org.commonmark.node.Node;
import org.commonmark.node.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Table(Header header, Content content) {

    public static Table from(TableBlock tableBlock) {
        final TableHead tableHead = (TableHead) tableBlock.getFirstChild();
        final TableBody tableBody = (TableBody) tableBlock.getLastChild();

        return new Table(Header.from(tableHead), Content.from(tableBody));
    }

    public record Header(Row row) {

        public static Header from(TableHead tableHead) {
            final TableRow headerRow = (TableRow) tableHead.getFirstChild();
            return new Header(Row.from(headerRow));
        }
    }

    public record Content(List<Row> rows) {

        public static Content from(TableBody tableBody) {
            final TableRow firstRow = (TableRow) tableBody.getFirstChild();

            if (firstRow == null) {
                return new Content(Collections.emptyList());
            }

            final ArrayList<TableRow> tableRows = new ArrayList<>();
            tableRows.add(firstRow);

            TableRow tempRow = firstRow;
            while ((tempRow = (TableRow) tempRow.getNext()) != null) {
                tableRows.add(tempRow);
            }

            final List<Row> rows = tableRows.stream()
                .map(Row::from)
                .toList();

            return new Content(rows);
        }
    }

    public record Row(List<String> cells) {

        public static Row from(TableRow row) {
            final TableCell firstCell = (TableCell) row.getFirstChild();

            if (firstCell == null) {
                return new Row(Collections.emptyList());
            }

            final ArrayList<String> cells = new ArrayList<>();

            final String firstCellLiteral = ((Text) firstCell.getFirstChild()).getLiteral();
            cells.add(firstCellLiteral);

            TableCell tempCell = firstCell;
            while ((tempCell = (TableCell) tempCell.getNext()) != null) {
                final Node textNode = tempCell.getFirstChild();
                if (textNode != null) {
                    final String literal = ((Text) textNode).getLiteral();
                    cells.add(literal);
                } else {
                    cells.add("");
                }
            }

            return new Row(cells);
        }
    }
}


import com.fasterxml.jackson.databind.JsonNode;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TablePanel extends JPanel {
    private static final String FIELD_DATA = "/securities/data";
    private static final String FIELD_COLUMNS = "/securities/columns";
    private String[] columns;
    private Object[][] data;

    private String[] getColumnsNames(JsonNode jsonNode)
    {
        JsonNode jNodeColumns = jsonNode.at(FIELD_COLUMNS);
        Iterator<JsonNode> elements = jNodeColumns.elements();
        List<String> columns = new ArrayList<String>();

        while(elements.hasNext())
            columns.add(elements.next().textValue());

        return columns.toArray(new String[columns.size()]);
    }

    private Object[][] getTableData(JsonNode jsonNode)
    {
        JsonNode jNodeData = jsonNode.at(FIELD_DATA);
        Iterator<JsonNode> rows = jNodeData.elements();
        List<Object[]> obj = new ArrayList<Object[]>();

        while(rows.hasNext())
        {
            List<Object> o = new ArrayList<Object>();
            Iterator<JsonNode> cells = rows.next().elements();

            while(cells.hasNext())
                o.add(cells.next().toString());

            Object[] oo = o.toArray();
            obj.add(oo);
        }

        return obj.toArray(new Object[obj.size()][]);
    }

    public TablePanel(JsonNode jsonNode)
    {
        super (new GridLayout(1,0));
        columns = getColumnsNames(jsonNode);
        data = getTableData(jsonNode);
        JsonNode jNodeData = jsonNode.at(FIELD_DATA);

        JTable table = new JTable(data, columns);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

    }
}
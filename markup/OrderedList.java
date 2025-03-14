package markup;

import java.util.List;

public class OrderedList implements DocBookElement {
    private final List<ListItem> items;

    public OrderedList(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public void toDocBook(StringBuilder sb) {
        sb.append("<orderedlist>");
        for (ListItem item : items) {
            item.toDocBook(sb);
        }
        sb.append("</orderedlist>");
    }
}

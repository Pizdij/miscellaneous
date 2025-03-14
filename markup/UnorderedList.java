package markup;

import java.util.List;

public class UnorderedList implements DocBookElement {
    private final List<ListItem> items;

    public UnorderedList(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public void toDocBook(StringBuilder sb) {
        //copypaste
        sb.append("<itemizedlist>");
        for (ListItem item : items) {
            item.toDocBook(sb);
        }
        sb.append("</itemizedlist>");
    }
}

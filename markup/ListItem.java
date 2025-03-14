package markup;

import java.util.List;

public class ListItem {
    private final List<DocBookElement> elements;

    public ListItem(List<DocBookElement> elements) {
        this.elements = elements;
    }


    public void toDocBook(StringBuilder sb) {
        sb.append("<listitem>");
        for (DocBookElement element : elements) {
            element.toDocBook(sb);
        }
        sb.append("</listitem>");
    }
}

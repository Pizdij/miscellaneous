package markup;

import java.util.List;

public class Paragraph extends AbstractMarkDown implements DocBookElement {

    public Paragraph(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    public String getBeginEnd() {
        return "";
    }

    @Override
    public String getDocStart() {
        return "<para>";
    }

    @Override
    public String getDocEnd() {
        return "</para>";
    }
}
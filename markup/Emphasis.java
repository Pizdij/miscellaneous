package markup;

import java.util.List;

public class Emphasis extends AbstractMarkDown implements MarkdownElement {

    public Emphasis(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    public String getBeginEnd() {
        return "*";
    }

    @Override
    public String getDocStart() {
        return "<emphasis>";
    }

    @Override
    public String getDocEnd() {
        return "</emphasis>";
    }

}
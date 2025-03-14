package markup;

import java.util.List;

public class Strong extends AbstractMarkDown implements MarkdownElement {

    public Strong(List<MarkdownElement> elements) {
        super(elements);
    }

    @Override
    public String getBeginEnd() {
        return "__";
    }

    @Override
    public String getDocStart() {
        return "<emphasis role='bold'>";
    }

    @Override
    public String getDocEnd() {
        return "</emphasis>";
    }
}
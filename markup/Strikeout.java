package markup;

import java.util.List;

public class Strikeout extends AbstractMarkDown implements MarkdownElement {

    public Strikeout(List<MarkdownElement> elements){
            super(elements);
    }

    @Override
    public String getBeginEnd() {
        return "~";
    }

    @Override
    public String getDocStart() {
        return "<emphasis role='strikeout'>";
    }

    @Override
    public String getDocEnd() {
        return "</emphasis>";
    }
}
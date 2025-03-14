package markup;

import java.util.List;

abstract class AbstractMarkDown {
    protected List<MarkdownElement> elements;

    protected AbstractMarkDown(List<MarkdownElement> elements) {
        this.elements = elements;
    }

    public abstract String getBeginEnd();
    public abstract String getDocStart();
    public abstract String getDocEnd();
    //TODO
    // getDoc()
    // getmark()
    public void toDocBook(StringBuilder sb) {
        sb.append(getDocStart());
        for (MarkdownElement element : elements) {
            element.toDocBook(sb);
        }
        sb.append(getDocEnd());
    }


    public void toMarkdown(StringBuilder sb) {
        sb.append(getBeginEnd());
        for (MarkdownElement element : elements) {
            element.toMarkdown(sb);
        }
        sb.append(getBeginEnd());
    }
}

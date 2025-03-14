package markup;

public interface MarkdownElement {
    void toMarkdown(StringBuilder sb);
    void toDocBook(StringBuilder sb);
}
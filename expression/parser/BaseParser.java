package expression.parser;

public class BaseParser {
    private static final char END = '\0';
    private final CharSource source;
    protected char ch = 0xffff;

    public BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    public char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }


    public boolean test(String s) {
        int c = 0;
        while (c < s.length() && take(s.charAt(c))) {
            c++;
        }
        source.prev();
        for (int i = 0; i < c; i++) {
            source.prev();
        }
        take();
        return c == s.length();
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean eof() {
        return take(END);
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected void SkipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}

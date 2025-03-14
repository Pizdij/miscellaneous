public class Sum {
    public static void main(String[] args) {
        int sm = 0;
        for (String arg : args) {
            for (int i = 0; i < arg.length(); i++) {
                char iter = arg.charAt(i);
                if (!(Character.isWhitespace(iter)) && !(Character.getType(iter) == Character.START_PUNCTUATION) && !(Character.getType(iter) == Character.END_PUNCTUATION)) {
                    int j = i;
                    while (j < arg.length() && !(Character.isWhitespace(arg.charAt(j))) && !(Character.getType(arg.charAt(j)) == Character.START_PUNCTUATION) && !(Character.getType(arg.charAt(j)) == Character.END_PUNCTUATION)) {
                        j++;
                    }
                    sm += Integer.parseInt(arg.substring(i, j));
                    i = j;
                }
            }
        }
        System.out.println(sm);
    }
}

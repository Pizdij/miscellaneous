package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Md2Html {

    // private parse?
    public static String[] f(StringBuilder s, int ind, String el) {
        StringBuilder myString = new StringBuilder();
        // parsing
        // iteration over string -> StringSource

        while (ind < s.length()) {
            String[] functionRes;
            String element = String.valueOf(s.charAt(ind));
            if (element.equals("\\")){
                myString.append(s.charAt(ind+1));
                ind+=2;
                continue;
            }
            String elementLen2 = "";
            String searchEl = "";
            if (ind < s.length() - 1) {
                elementLen2 = element + s.charAt(ind + 1);
            }
            if (elementLen2.equals("**")) {
                ind++;
                searchEl = "**";
            } else if (elementLen2.equals("__")) {
                ind++;
                searchEl = "__";
            } else if (elementLen2.equals("--")) {
                ind++;
                searchEl = "--";
            } else if (element.equals("*")) {
                searchEl = "*";
            } else if (element.equals("_")) {
                searchEl = "_";
            } else if (element.equals("`")) {
                searchEl = "`";
            } else if (element.equals("!") && ind + 1 < s.length() && s.charAt(ind + 1) == '[') {
                int closeAlt = s.indexOf("]", ind + 2);

                if (closeAlt != -1 && s.charAt(closeAlt - 1) != '\\' && closeAlt + 1 < s.length() && s.charAt(closeAlt + 1) == '(') {
                    int closeSrc = s.indexOf(")", closeAlt + 2);
                    if (closeSrc != -1 && s.charAt(closeSrc - 1) != '\\') {
                        String alt = s.substring(ind + 2, closeAlt);
                        String src = s.substring(closeAlt + 2, closeSrc);
                        myString.append("<img alt='").append(alt).append("' src='").append(src).append("'>");
                        ind = closeSrc + 1;
                        continue;
                    }
                }
            }
            if (!searchEl.isEmpty() && !searchEl.equals(el)) {
                functionRes = f(s, ind + 1, searchEl);
                ind = Integer.parseInt(functionRes[2]);
                if (Objects.equals(functionRes[0], "0")) {
                    myString.append(searchEl);
                    myString.append(functionRes[1]);
                } else {
                    if (searchEl.equals("**") || searchEl.equals("__")) {
                        myString.append("<strong>");
                        myString.append(functionRes[1]);
                        myString.append("</strong>");
                    } else if (searchEl.equals("--")) {
                        myString.append("<s>");
                        myString.append(functionRes[1]);
                        myString.append("</s>");
                    } else if (searchEl.equals("`")) {
                        myString.append("<code>");
                        myString.append(functionRes[1]);
                        myString.append("</code>");
                    } else {
                        myString.append("<em>");
                        myString.append(functionRes[1]);
                        myString.append("</em>");
                    }
                }
            } else if (searchEl.equals(el) && !el.isEmpty()) {
                if (el.length() == 1) {
                    return new String[]{"1", myString.toString(), Integer.toString(ind + el.length())};
                }
                return new String[]{"1", myString.toString(), Integer.toString(ind + el.length() - 1)};
            } else {
                if (element.equals("<")) {
                    myString.append("&lt;");
                } else if (element.equals(">")) {
                    myString.append("&gt;");
                } else if (element.equals("&")) {
                    myString.append("&amp;");
                } else {
                    if (element.equals("\\")) {
                        String type = "";
                        String type2 = "";
                        if (ind + 1 < s.length()) {
                            type += s.charAt(ind + 1);
                        }
                        if (ind + 2 < s.length()) {
                            type2 = type + s.charAt(ind + 2);
                        }
                        if (type2.equals("**")) {
                            myString.append("**");
                            ind += 2;
                        } else if (type2.equals("__")) {
                            myString.append("__");
                            ind += 2;
                        } else if (type2.equals("--")) {
                            myString.append("--");
                            ind += 2;
                        } else if (type.equals("*")) {
                            myString.append("*");
                            ind += 1;
                        } else if (type.equals("_")) {
                            myString.append("_");
                            ind += 1;
                        } else if (type.equals("`")) {
                            myString.append("`");
                            ind += 1;
                        } else {
                            myString.append(element);
                        }
                    } else {
                        myString.append(element);
                    }
                }
                ind++;
            }
        }
        return new String[]{"0", myString.toString(), Integer.toString(ind)};
    }

    public static int isParagraph(StringBuilder line) {
        int ind = 0;
        while (ind < line.length() && line.charAt(ind) == '#') {
            ind++;
        }
        if (ind > 0 && ind < line.length() && line.charAt(ind) == ' ') {
            return ind;
        }
        return -1;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java md2html.Md2Html <input file> <output file>");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            List<StringBuilder> listBlock = new ArrayList<>();
            StringBuilder block = new StringBuilder();

            while (line != null) {
                if (!line.isEmpty()) {
                    block.append(line).append("\n");
                } else {
                    if (!block.isEmpty()) {
                        listBlock.add(block);
                        block = new StringBuilder();
                    }
                }
                line = reader.readLine();
            }
            if (!block.isEmpty()) {
                listBlock.add(block);
            }

            StringBuilder ans = new StringBuilder();
            for (StringBuilder blk : listBlock) {
                int headingLevel = isParagraph(blk);
                if (headingLevel == -1) {
                    ans.append("<p>");
                    ans.append(f(blk, 0, "")[1]);
                    ans.deleteCharAt(ans.length() - 1);
                    ans.append("</p>\n"); // System.lineseparator?
                } else {
                    ans.append("<h").append(headingLevel).append(">");
                    ans.append(f(blk, headingLevel + 1, "")[1]);
                    ans.deleteCharAt(ans.length() - 1);
                    ans.append("</h").append(headingLevel).append(">\n");
                }
            }

            writer.write(ans.toString());

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
}
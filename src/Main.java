import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String question = scanner.next();
        question = "(" + question + ")";
        System.out.println(answerGenerator(question));

        System.out.println(answerGenerator("(3&2&33@66)"));

    }

    private static Integer maxOrMinCalculator(String string) throws Exception {
        //string = parenthesesAdder(string);

        String operation = "";
        int a = 0;
        int b = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '@' || string.charAt(i) == '&') {
                operation = String.valueOf(string.charAt(i));
                a = Integer.parseInt(string.substring(1, i));
                b = Integer.parseInt(string.substring(i+1, string.length()-1));
            }
        }
        if (operation.equals("@")) {
            return Integer.parseInt(String.valueOf(Math.min(a, b)));
        } else if (operation.equals("&")) {
            return Integer.parseInt(String.valueOf(Math.max(a, b)));
        } else if (operation.equals("") && a == 0 && b == 0) {
            return Integer.parseInt(string.substring(1,string.length()-1));
        } else {
            throw (new Exception("Error")); // Change this to an exception
        }
    }
    private static String answerGenerator(String input) throws Exception {

        int openBracketIndex = 0;
        int closedBracketIndex = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' ) {
                openBracketIndex = i;
            } else if (input.charAt(i) == ')' ) {
                closedBracketIndex = i;
                break;
            }
        }

        if (!input.contains("(")) {
            return input;
        }

        String withinBrackets = input.substring(openBracketIndex, closedBracketIndex+1);

        int counter = 0;
        for (int i = 0; i < withinBrackets.length(); i++) {
            if (withinBrackets.charAt(i) == '@' || withinBrackets.charAt(i) == '&') {
                counter ++;
            }
        }
        if (counter > 1) {
            return answerGenerator(parenthesesAdder(withinBrackets));
        }

        input = input.replace(withinBrackets, String.valueOf(maxOrMinCalculator(withinBrackets)));
        return answerGenerator(input);
    }
    private static String parenthesesAdder(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '&' || str.charAt(i) == '@' ) {
                count ++;
            }
        }
        for (int i = 0; i < count; i++) {
            str = "(" + str;
        }

        str = str.replaceAll("&", ")&");
        str = str.replaceAll("@", ")@");
        return str;
    }
}

/*
Test Cases
test1 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25)))
test2 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25)))@(3&2&33@66)
 */
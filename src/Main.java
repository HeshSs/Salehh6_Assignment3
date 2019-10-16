import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String question = scanner.nextLine();
        question = "(" + question + ")";

        try {
            System.out.println(answerGenerator(question));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static Integer maxOrMinCalculator(String string){

        String operation = "";
        int a = 0;
        int b = 0;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '@' || string.charAt(i) == '&') {

                operation = String.valueOf(string.charAt(i));
                a = Integer.parseInt(string.substring(1, i));
                b = Integer.parseInt(string.substring(i + 1, string.length() - 1));

            }
        }
            if (operation.equals("@")) {
                return Integer.parseInt(String.valueOf(Math.min(a, b)));
            } else if (operation.equals("&")) {
                return Integer.parseInt(String.valueOf(Math.max(a, b)));
            } else if (operation.equals("") && a == 0 && b == 0) {
                return Integer.parseInt(string.substring(1, string.length() - 1));
            } else {
                return null;
            }
    }

    private static String answerGenerator(String input) throws Exception {

        Exception invalidC = new Exception("INVALID CHARACTERS");
        Exception invalidE = new Exception("INVALID EXPRESSION");
        String invalidCharacters = " ~!#$%^*_+qwertyuiop[]asdfghjkl;'zxcvbnm,./";
        Stack<Character> stack = new Stack<>();
        int openBracketIndex = 0;
        int closedBracketIndex = 0;

        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < invalidCharacters.length(); j++) {
                if (input.charAt(i) == invalidCharacters.charAt(j)) {
                    throw invalidC;
                }
            }
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' )  {
                stack.push(input.charAt(i));
            } else if (input.charAt(i) == ')' ) {
                try {
                    stack.pop();
                } catch (Exception e) {
                    throw invalidE;
                }
            }
        }
        if (!stack.empty()) {
            throw invalidE;
        }

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
        System.out.println(withinBrackets);

        if (counter > 1) {

            input = input.replace(withinBrackets, parenthesesAdder(withinBrackets));
            try {
                return answerGenerator(input);
            } catch (Exception e) {
                throw invalidC;
            }

        }

        input = input.replace(withinBrackets, String.valueOf(maxOrMinCalculator(withinBrackets)));
        try {
            return answerGenerator(input);
        } catch (Exception e) {
            throw invalidC;
        }

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
Part1
------------
test1 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25))) == 44
test2 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25)))@(3&2&33@66) == 33
test3 = 22@((12@45)&14) == 14

Part2
------------
test1 = (((22@(55&55))&(33&44))&(((40@23)&(50@25))) == "INVALID EXPRESSION"
test2 = (22@((12@45)&14)) ( == "INVALID CHARACTERS"
test3 = (22@((12@45)&14))( == "INVALID EXPRESSION"
test4 = (22+((12@45)14z)) == "INVALID CHARACTERS"
 */
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String question = scanner.nextLine();
        question = "(" + question + ")";

        try {
            System.out.println(answerGenerator(question));
        } catch (NumberFormatException e) {
            System.out.println("INVALID EXPRESSION");
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
        String invalidCharacters = " -~!#$%^*_+qwertyuiop[]asdfghjkl;'zxcvbnm,./";
        String validCharacters = ")0123456789";
        Stack<Character> stack = new Stack<>();
        int openBracketIndex = 0;
        int closedBracketIndex = 0;

        // If there's a character other than "()0123456789&@"
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < invalidCharacters.length(); j++) {
                if (input.charAt(i) == invalidCharacters.charAt(j)) {
                    throw invalidC; // "INVALID CHARACTERS"
                }
            }
        }

        // Mismatching Parentheses
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' )  {
                stack.push(input.charAt(i));
            } else if (input.charAt(i) == ')' ) {
                try {
                    stack.pop();
                } catch (Exception e) {
                    throw invalidE; // "INVALID EXPRESSION"
                }
            }
        }

        if (!stack.empty()) {
            throw invalidE; // "INVALID EXPRESSION"
        }

        // (45(34)) or 43(44) or )(
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                for (int j = 0; j < validCharacters.length(); j++) {
                    if (input.charAt(i-1) == validCharacters.charAt(j)) {
                        throw invalidE; // "INVALID EXPRESSION"
                    }
                }
            }
        }

        // Cases 25&30& or (&25&30)
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '&' || input.charAt(i) == '@') {
                if (input.charAt(i-1) == '(' || input.charAt(i+1) == ')' ) {
                    throw invalidE;
                }
            }
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
------------
Part1
------------
test1 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25))) == 44
test2 = (((22@(55&(55)))&(33&44))&((40@23)&(50@25)))@(3&2&33@66) == 33
test3 = 22@((12@45)&14) == 14
test4 = (23&24@23&24&25&(23@24))&(23&24&22&21) == 25
test5 = (23333) == 23333
------------
Part2
------------
test1 = (((22@(55&55))&(33&44))&(((40@23)&(50@25))) == "INVALID EXPRESSION"
test2 = (22@((12@45)&14)) ( == "INVALID CHARACTERS"
test3 = (22@((12@45)&14))( == "INVALID EXPRESSION"
test4 = (22+((12@45)14z)) == "INVALID CHARACTERS"
test5 = (86&45@23&45&84)@(45((23))) ==  "INVALID EXPRESSION"
test6 = (88&(23@12))(76) == "INVALID EXPRESSION"
test7 = (((((98#))))))) == "INVALID CHARACTERS"
test8 = (23&(-25)) == "INVALID CHARACTERS"
test9 = 25&30& == "INVALID EXPRESSION"
test10 = &27@(19) == "INVALID EXPRESSION"
test11 = (2#5) == "INVALID CHARACTERS"

------------
 */
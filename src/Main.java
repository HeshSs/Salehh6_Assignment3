import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        // Asks for user input and adds brackets to it
        Scanner scanner = new Scanner(System.in);
        String question = scanner.nextLine();
        question = "(" + question + ")";

        // Using Try-Catch block to output the final answer
        try {
            System.out.println(answerGenerator(question)); // Try to output the answer
        } catch (NumberFormatException e) {
            System.out.println("INVALID EXPRESSION"); // If NumberFormat Exception was thrown printout "INVALID EXPRESSION"
        } catch (Exception e) {
            System.out.println(e.getMessage()); /* Any other Exception Just printout the message of the Exception
                                                   e.g. if invalidC Exception was thrown, print "INVALID CHARACTERS"  */
        }

    }

    /***
     *
     * @param string Given expression (e.g. (a&b) or (a@b) )
     * @return Max of a or b if (&) is the operator or Min, if (@) is the operator
     */
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

    /***
     *
     * @param input Can be any expression valid {e.g. 22@((12@45)&14)} or invalid {e.g. (23&(-25))}
     * @return An Integer or an Exception
     * @throws Exception throws invalidC or invalidE Exceptions
     */
    private static String answerGenerator(String input) throws Exception {

        Exception invalidC = new Exception("INVALID CHARACTERS");       // invalidC Exception for cases where the input has invalid characters
        Exception invalidE = new Exception("INVALID EXPRESSION");       // invalidE Exception for cases where the input is a invalid expression
        String invalidCharacters = " -~!#$%^*_+qwertyuiop[]asdfghjkl;'zxcvbnm,./";  // list of invalid characters
        String validCharacters = ")0123456789";                         // most of the valid characters for expression checking
        Stack<Character> stack = new Stack<>();
        int openBracketIndex = 0;
        int closedBracketIndex = 0;

        // If there's a character other than "()0123456789&@"
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < invalidCharacters.length(); j++) {
                if (input.charAt(i) == invalidCharacters.charAt(j)) {
                    throw invalidC;         // "INVALID CHARACTERS"
                }
            }
        }

        // When there are mismatching parentheses throw invalidE Exception
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

        // For cases like (45(34)) or 43(44) or )( throw invalidE Exception
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                for (int j = 0; j < validCharacters.length(); j++) {
                    if (input.charAt(i-1) == validCharacters.charAt(j)) {
                        throw invalidE; //
                    }
                }
            }
        }

        // For cases like 25&30& or (&25&30) throw invalidE Exception
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '&' || input.charAt(i) == '@') {
                if (input.charAt(i-1) == '(' || input.charAt(i+1) == ')' ) {
                    throw invalidE;
                }
            }
        }

        // Find indices of substring of inside an open and closed bracket
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(' ) {
                openBracketIndex = i;
            } else if (input.charAt(i) == ')' ) {
                closedBracketIndex = i;
                break;
            }
        }

        // Base case of the Recursion. If there is no more brackets '(' in the string return the answer
        if (!input.contains("(")) {
            return input;
        }

        // Create a within brackets substring that starts with '(' and ends with ')'
        String withinBrackets = input.substring(openBracketIndex, closedBracketIndex+1);

        int counter = 0;

        // If substring has more than one @ or &
        for (int i = 0; i < withinBrackets.length(); i++) {
            if (withinBrackets.charAt(i) == '@' || withinBrackets.charAt(i) == '&') {
                counter ++;
            }
        }

        if (counter > 1) {
            input = input.replace(withinBrackets, parenthesesAdder(withinBrackets)); // Add brackets to it
            try {
                return answerGenerator(input); // And then return the answer
            } catch (Exception e) {
                throw invalidC; // Else throw invalidC Exception
            }

        }

        // If every other cases was satisfied, then replace the within brackets with its max or min answer
        input = input.replace(withinBrackets, String.valueOf(maxOrMinCalculator(withinBrackets)));

        try {
            return answerGenerator(input); // And then return the answer
        } catch (Exception e) {
            throw invalidC; // Else throw invalidC Exception
        }
    }

    /***
     *
     * @param str When str has more than one @ or &
     * @return Adds brackets to the str from left to right
     */
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
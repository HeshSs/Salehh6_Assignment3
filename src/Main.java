import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String question = scanner.next();
        question = "(" + question + ")";
        answerGenerator(question);
        System.out.println(answerGenerator(question));

    }

    static Integer maxOrMinCalculator(String string) throws Exception {

        String operation = "";
        Integer a = 0;
        Integer b = 0;

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
        } else if (operation == "" && a == 0 && b == 0) {
            return Integer.parseInt(string.substring(1,string.length()-1));
        } else {
            throw (new Exception("Error")); // Change this to an exception
        }
    }
    static String answerGenerator(String input) throws Exception {
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

        if (input.contains("(") == false) {
            return input;
        }
        String withinBrackets = input.substring(openBracketIndex, closedBracketIndex+1);
        System.out.println(withinBrackets);

        input = input.replace(withinBrackets, String.valueOf(maxOrMinCalculator(withinBrackets)));

        return answerGenerator(input);
        //answerGenerator(input.replace());

    }
}

/*
        String st = "(12@45)";
        String ab = question.replace(st, String.valueOf(maxOrMinCalculator(st)));
        System.out.println(ab);
        System.out.println(maxOrMinCalculator(st));
        System.out.println(maxOrMinCalculator("(02)"));

 */
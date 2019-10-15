import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {

        String question = "(12@45)&14";
        String st = "(12@45)";
        String ab = question.replace(st, String.valueOf(maxOrMinCalculator(st)));
        System.out.println(ab);
        System.out.println(maxOrMinCalculator(st));
        System.out.println(maxOrMinCalculator("(02)"));

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
            return Math.min(a, b);
        } else if (operation.equals("&")) {
            return Math.max(a, b);
        } else if (operation == "" && a == 0 && b == 0) {
            return Integer.parseInt(string.substring(1,string.length()-1));
        } else {
            throw (new Exception("Error")); // Change this to an exception
        }
    }
    static void answerGenerator(String input) {
        int openBracketIndex = 0;
        int closedBracketIndex = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                openBracketIndex = i;
            } else if (input.charAt(i) == ')' ) {
                closedBracketIndex = i;
            }
        }
    }
}
/*
    static int recursiveSolution(String input) {
        Integer a = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '@' || input.charAt(i) == '&') {
                try {
                    a = Integer.parseInt(input.substring(i-1,i));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return (Integer) a;
    }
 */
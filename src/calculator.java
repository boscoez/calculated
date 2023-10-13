import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class calculator {
    /** Create a Hash Map that stores a string as the key and operator as its value */
    final static Map<String, Operator> opList = new HashMap<>();
    /* Fill OPS with keys and values. */
    static {
        for (Operator operator : Operator.values()) {
            opList.put(operator.symbol, operator);
        }
    }
    /** ShuntingYard algorithm takes a list of infix tokens and rearranges them to RPN.*/
    public static List<String> shuntingYard(List<String> tokens) {
        try {
            /* Create a list to store the output buffer. */
            List<String> output = new LinkedList<>();
            /* Create a stack to hold operators. */
            Stack<String> stack = new Stack<>();
            /* Loop through the expressions one at a time token -> number -> operator. */
            for (String token : tokens) {
                /* If list contains token, enter loop. */
                if (opList.containsKey(token)) {
                    /* While the stack is != empty, and the list map contains a key, continue loop */
                    while (!stack.isEmpty() && opList.containsKey(stack.peek())) {
                        /* Create an instance of Operator */
                        Operator currOp = opList.get(token); /* Gets the current operator */
                        Operator topOp = opList.get(stack.peek()); /* Gets the top operator in the opsList */
                        /* If the current link is left and the current operator's precedence is <= to the
                         top operator in the list or the current operator's link is right and the
                         current operator's precedence is < the top operator in the list.*/
                        if ((currOp.link == Link.L && currOp.comparePrecedence(topOp) <= 0) ||
                                (currOp.link == Link.R && currOp.comparePrecedence(topOp) < 0)) {
                            /* Pop the top of the stack and add it to the output */
                            output.add(stack.pop());
                            continue;
                        }
                        /* Exit loop. */
                        break;
                    }
                    /* Push token back onto the stack */
                    stack.push(token);
                }
                /* Check left parenthesis. */
                else if ("(".equals(token)) {
                    /* Add the left parenthesis onto the stack. */
                    stack.push(token);
                }
                /* Check right parenthesis. */
                else if (")".equals(token)) {
                    /*Loop while the operator stack isn't empty and the top element in the operator stack is != to
                     the left parenthesis.*/
                    while (!stack.isEmpty() && !stack.peek().equals("(")) {
                        /* Pop off the top of the stack and add it into the output. */
                        output.add(stack.pop());
                    }   /* Pop the top of the stack. */
                    stack.pop();
                }
                /* Check for left braces. */
                else if ("{".equals(token)) {
                    stack.push(token);
                }
                /* Check for right braces. */
                else if ("}".equals(token)) {
                    while (!stack.isEmpty() && !stack.peek().equals("{")) {
                        output.add(stack.pop());
                    }
                    stack.pop();
                }
                /* Check for left brackets. */
                else if ("[".equals(token)) {
                    stack.push(token);
                }
                /* Check for right brackets. */
                else if ("]".equals(token)) {
                    while (!stack.isEmpty() && !stack.peek().equals("[")) {
                        output.add(stack.pop());
                    }
                    stack.pop();
                }
                /* If the token is != to an operator, it is a number. */
                else {
                    /* Add this number into the output. */
                    output.add(token);
                }
            }
            /* Loop while the stack != empty. */
            while (!stack.isEmpty()) {
                /* Pop the stack and add it into output. */
                output.add(stack.pop());
            }
            /* Return RPN expression. */
            return output;
        }
        /* Catch NullPointerExceptions and return an error. */
        catch (NullPointerException e) {
            System.out.println("ERROR NULL POINTER VALID VALUE EXPECTED.");

        }
        return null;
    }
    /** confirmExpression analyzes the expression given. */
    public static void confirmExpression(List<String> exp) {
        /* Bracket variables for precedence tests. */
        List<String> ops = Arrays.asList("(", ")", "[", "]", "{", "}");
        try {
            /* Checking for parenthesis, braces, or brackets. */
            for (String s : exp) {
                if (ops.contains(s)) {
                    System.out.println("ERROR IN EXPRESSION: ADD OR REMOVE  ) - ] - } .");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR NULL POINTER VALID VALUE EXPECTED. ");
        }
    }
    /** Main function. */
    public static void main(String[] args) throws FileNotFoundException {
        /* User Input. */
        Scanner scan = new Scanner(System.in);
        boolean continuation = false;
        /* MyCalculator Menu. */
        do {
            System.out.println("\n\t*********>* My CALCULATOR *<*********\n");
            System.out.println("\t-- SELECT AN OPTION FROM THE MENU --\n");
            System.out.println("\t\tPress 1 to Calculate ");
            System.out.println("\t\tPress 2 for Help ");
            System.out.println("\t\tPress 3 to Exit\n ");
            System.out.print("Selection: ");
            /* UI select option. */
            String selection = scan.nextLine();
            /* Remove whitespace/non-characters and move all to lowercase. */
            selection = selection.toLowerCase().replaceAll("\\s", "");
            /* Calculate. */
            switch (selection) {
                case "1", "" -> {
                    boolean repeat;
                    do {
                        System.out.println("\n\nCOMPUTATION");
                        System.out.print("Please enter your expression here: ");
                        String expression = scan.nextLine();
                        expression = expression.toLowerCase().replaceAll("\\s+", "");
                        /* Convert. */
                        List<String> listExpression = createExpression(expression);
                        List<String> rpnExpression = shuntingYard(listExpression);
                        confirmExpression(rpnExpression);
                        /* Calculate. */
                        assert rpnExpression != null;
                        double result = computeExpression(rpnExpression);
                        /* Return result. */
                        System.out.println("Result: " + result);
                        /* Another expression? */
                        System.out.println("Another expression? ( yes / no )");
                        String choice = scan.nextLine();
                        choice = choice.toLowerCase().replaceAll("\\s+", "");
                        /* Check no or yes. */
                        repeat = choice.equals("yes");
                    } while (repeat);
                }
                /* Case for the help document. */
                case "2", "help" -> {
                    /* Case 2 reads from the help.txt file to assist the user in operating the software. */
                    Scanner read = new Scanner(new BufferedReader(new FileReader("src/help.txt")));
                    while (read.hasNextLine()) {
                        System.out.println(read.nextLine());
                    }
                    read.close();
                }
                /* Case to exit the program. */
                case "3", "exit" -> {
                    System.out.println("CLOSING PROGRAM. THANK YOU FOR USING My CALCULATOR!");
                    continuation = true;
                }
                /* Default for no selection made or selection is made incorrectly. */
                default -> System.out.println("Sorry, please reenter your selection.");
            }
        } while (!continuation);
        scan.close();
    }
    /** The computeExpression function computes the rpn expression and calculates it. */
    public static double computeExpression(List<String> expression) throws IllegalArgumentException {
        List<String> basicOps = Arrays.asList("+", "-", "*", "/", "^");
        List<String> trigOps = Arrays.asList("sin", "cos", "tan", "cot", "arcsin", "arccos", "arctan", "arcctg");
        List<String> logOps = Arrays.asList("ln", "log", "sqrt");
        /* Create a copy of the original expression. */
        List<String> copyExp = new LinkedList<>(expression);
        /* Initialize the index variable. */
        int index = 0;
        try {
            while (copyExp.size() > 1) {
                double temp;
                /* Check the next element for a basic operator. */
                if (basicOps.contains(copyExp.get(index))) {
                    if (index >= 2) {
                        /* Check if proper operands exist for the computation. */
                        temp = basicComputation(
                                Double.parseDouble(copyExp.get(index - 2)),
                                Double.parseDouble(copyExp.get(index - 1)),
                                copyExp.get(index)
                        );
                        /* Update result and remove used operands. */
                        copyExp.set(index - 2, Double.toString(temp));
                        copyExp.remove(index - 1);
                        copyExp.remove(index - 1);
                        index = 0;
                    } else if (index == 1) {
                        /* Handle unary operators. */
                        if (copyExp.get(index).equals("-")) {
                            double number = Double.parseDouble(copyExp.get(0));
                            copyExp.set(0, Double.toString(-number));
                            copyExp.remove(index);
                            index = 0;
                        } else if (copyExp.get(index).equals("+")) {
                            copyExp.remove(index);
                            index = 0;
                        } else {
                            System.out.println("ERROR: Invalid expression.");
                            System.exit(0);
                        }
                    }
                } else if (trigOps.contains(copyExp.get(index))) {
                    temp = trigComputation(
                            Double.parseDouble(copyExp.get(index - 1)),
                            copyExp.get(index)
                    );
                    copyExp.set(index - 1, Double.toString(temp));
                    copyExp.remove(index);
                    index = 0;
                } else if (logOps.contains(copyExp.get(index))) {
                    temp = logComputations(
                            Double.parseDouble(copyExp.get(index - 1)),
                            copyExp.get(index)
                    );
                    copyExp.set(index - 1, Double.toString(temp));
                    copyExp.remove(index);
                    index = 0;
                }
                index++;
            }
        } catch (NullPointerException n) {
            System.out.println("ERROR: null expression.");
            System.exit(1);
        } catch (NumberFormatException n) {
            System.out.println("ERROR Located formatting is invalid: " + n);
            System.exit(1);
        }
        double result = 0;
        try {
            result = Double.parseDouble(copyExp.get(0));
        } catch (NumberFormatException n) {
            System.out.println("ERROR Located formatting is invalid: " + n);
            System.exit(1);
        }
        if (result > Double.MAX_VALUE) {
            System.out.println("Error max computational boundary exceeded. " + Double.MAX_VALUE);
            result = Double.MAX_VALUE;
        }
        return result;
    }
    /** basicComputation function. */
    public static double basicComputation(double x, double y, String op) {
        /* store the final computation. */
        double result = 0;
        /* POW */
        if ("^".equals(op)) {
            result = Math.pow(x, y);
        } else {
            /* ADD */
            switch (op) {
                case "+" -> result = x + y;
                /* SUB */
                case "-" -> result = x - y;
                /* DIV */
                case "/" -> {
                    /* Check if the denominator is zero. */
                    if (y == 0) {
                        System.out.println("ERROR! CANNOT DIVIDE BY ZERO!");
                        System.exit(0);
                    } else {
                        result = x / y;
                    } }
                /* MUL */
                case "*" -> {
                    result = x * y;
                    return result;
                }
            }
        }
        return result;
    }
    /** trigComputation function. */
    public static double trigComputation(double x, String op) {
        /* The Result will store the final trig computation when called. */
        return switch (op) {
            case "sin" -> Math.sin(x);
            /* Cosine */
            case "cos" -> Math.cos(x);
            /* Tangent */
            case "tan" -> Math.tan(x);
            /* Cotangent */
            case "cot" -> 1 / Math.tan(x);
            /* Arcsine */
            case "arcsin" -> Math.asin(x);
            /* Arccosine */
            case "arccos" -> Math.acos(x);
            /* Arctangent */
            case "arctan" -> Math.atan(x);
            /* Arccotangent */
            case "arcctg" -> Math.PI / 2 - Math.atan(x);
            default -> 0;
            /* Trig functions return in radian. */
        };
    }
    /*** logComputations functions.*/
    public static double logComputations(double x, String op) {
        /* Result stores the final computation. */
        return switch (op) {
            case "ln" -> Math.log(x);
            /* Log10 */
            case "log" -> Math.log10(x);
            /* Square root */
            case "sqrt" -> Math.sqrt(x);
            default -> 0;
            // Natural Log
        };
    }
    /** createExpression function takes in a string array from user input and creates
    aN array list for the Shunting Yard algorithm.*/
    public static List<String> createExpression(String origin) {
        char[] tmp = origin.toCharArray();
        List<Character> operators = Arrays.asList('+', '-', '/', '*', '(', ')', '{', '}', '[', ']', '^');
        ArrayList<String> result = new ArrayList<>();
        /* Temp variable to store numbers. */
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < tmp.length; i++) {
            try {
                /* Checking for digits. */
                if (Character.isDigit(tmp[i]) || tmp[i] == '.') {
                    number.append(tmp[i]);
                }
                /* Add digits into expression. */
                else if (!number.isEmpty()) {
                    result.add(number.toString());
                    number = new StringBuilder();
                    i--;
                }
                /* Check if a token is in the operator list. */
                else if (operators.contains(tmp[i])) {
                    /* Check if i-1 is not included in the array. */
                    if (i - 1 >= 0) {
                        /* Check for duplicates. */
                        if (tmp[i] == tmp[i - 1]) {
                            /* Check if SUB. */
                            if (tmp[i] == '-') {
                                result.remove(result.size() - 1);
                                result.add("+");
                            } else {
                                result.add(Character.toString(tmp[i]));
                            }
                        } else if (tmp[i] == '-' && operators.contains(tmp[i - 1]) && Character.isDigit(tmp[i + 1])) {
                            number.append("-");
                        }
                        /* Check the rest. */
                        else {
                            result.add(Character.toString(tmp[i]));
                        }
                    } /* Else the operator is first. */
                    else {
                        /* Check for unary SUB symbol. */
                        if (tmp[i] == '-' && Character.isDigit(tmp[i + 1])) {
                            number.append("-");
                        }
                        /* Check for double negative to positive. */
                        else if (tmp[i] == tmp[i + 1]) {
                            result.add("+");
                        }
                        /* Check if proceeded by a parentheses. */
                        else if (tmp[i] == '-' && tmp[i + 1] == '(') {
                            result.add("-");
                        }
                        /* Check for invalid operator. */
                        else {
                            System.out.println("ERROR, '+' MUST BE FOLLOWED BY '-' OR AN INTEGER");
                            System.exit(0);
                        }
                    }
                }
                /* Checking for operators longer than one char and check for SQRT. */
                else if (tmp[i] == 's' && tmp[i + 1] == 'q'
                        && tmp[i + 2] == 'r' && tmp[i + 3] == 't') {
                    result.add("sqrt");
                    i += 3;
                }
                /* Checking for sine. */
                else if (tmp[i] == 's' && tmp[i + 1] == 'i' && tmp[i + 2] == 'n') {
                    result.add("sin");
                    i += 2;
                }
                /* Checking for cosine. */
                else if (tmp[i] == 'c' && tmp[i + 1] == 'o' && tmp[i + 2] == 's') {
                    result.add("cos");
                    i += 2;
                }
                /* Checking for tangent. */
                else if (tmp[i] == 't' && tmp[i + 1] == 'a' && tmp[i + 2] == 'n') {
                    result.add("tan");
                    i += 2;
                }
                /* Checking for cotangent. */
                else if (tmp[i] == 'c' && tmp[i + 1] == 'o' && tmp[i + 2] == 't') {
                    result.add("cot");
                    i += 2;
                }
                /* Checking for arcsine. */
                else if (tmp[i] == 'a' && tmp[i + 1] == 'r' && tmp[i + 2] == 'c'
                        && tmp[i + 3] == 's' && tmp[i + 4] == 'i' && tmp[i + 5] == 'n') {
                    result.add("arcsin");
                    i += 5;
                }
                /* Checking for arccosine. */
                else if (tmp[i] == 'a' && tmp[i + 1] == 'r' && tmp[i + 2] == 'c'
                        && tmp[i + 3] == 'c' && tmp[i + 4] == 'o' && tmp[i + 5] == 's') {
                    result.add("arccos");
                    i += 5;
                }
                /* Checking for arctangent. */
                else if (tmp[i] == 'a' && tmp[i + 1] == 'r' && tmp[i + 2] == 'c'
                        && tmp[i + 3] == 't' && tmp[i + 4] == 'a' && tmp[i + 5] == 'n') {
                    result.add("arctan");
                    i += 5;
                }
                /* Checking for arccotangent. */
                else if (tmp[i] == 'a' && tmp[i + 1] == 'r' && tmp[i + 2] == 'c'
                        && tmp[i + 3] == 'c' && tmp[i + 4] == 't' && tmp[i + 5] == 'g') {
                    result.add("arcctg");
                    i += 5;
                }
                /* Checking for natural log ln. */
                else if (tmp[i] == 'l' && tmp[i + 1] == 'n') {
                    result.add("ln");
                    i++;
                }
                /* Checking for log10. */
                else if (tmp[i] == 'l' && tmp[i + 1] == 'o' && tmp[i + 2] == 'g') {
                    result.add("log");
                    i += 2;
                }
                /* If char does not apply to the expression rules, error. */
                else {
                    System.out.println("ERROR PROCESSING EQUATION: " + tmp[i] + " is not a valid expression.");
                    return null;
                }
            }
            /* Catch errors in expression. */
            catch (Exception e) {
                System.out.println("ERROR IN EXPRESSION: " + tmp[i]);
            }
        }
        if (!number.isEmpty()) {
            result.add(number.toString());
        }
        return result;
    }
    /** Enum helper for the Shunting Yard algorithm */
    public enum Link {L, R}
    /** List the operators with their symbol, link, and their precedence level */
    public enum Operator implements Comparable<Operator> {

        ADD("+", Link.L, 0),
        SUB("-", Link.L, 0),
        DIV("/", Link.L, 5),
        MUL("*", Link.L, 5),
        POW("^", Link.R, 10),
        SQRT("sqrt", Link.R, 10),
        SINE("sin", Link.R, 10),
        COSINE("cos", Link.R, 10),
        TANGENT("tan", Link.R, 10),
        COTANGENT("cot", Link.R, 10),
        ARCSINE("arcsin", Link.R, 10),
        ARCCOSINE("arccos", Link.R, 10),
        ARCTANGENT("arctan", Link.R, 10),
        ARCCOTANGENT("arcctg", Link.R, 10),
        LOGn("ln", Link.R, 10),
        LOG10("log", Link.R, 10);
        /** Create an instance for each attribute of the operator*/
        final Link link;
        final int precedence;
        final String symbol;
        /** Create an operator constructor to fill in the operator's attributes */
        Operator(String symbol, Link link, int precedence) {
            this.symbol = symbol;
            this.link = link;
            this.precedence = precedence;
        }
        /** Function to compare the precedence level of two difference operators */
        public int comparePrecedence(Operator operator) {
            return this.precedence - operator.precedence;
        }
    }
}
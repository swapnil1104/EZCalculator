package swapnil.calcii.legacyioscalculator.logic;

import java.util.Stack;

/**
 * Created by Swapnil on 24-10-2015.
 */
public class infixToPostfixSolve {

    public String infixToPostfix(String infix){

        char[] expression = infix.toCharArray();
        Stack<Character> stack = new Stack<Character>();
        StringBuilder out = new StringBuilder(infix.length());

        for (char c : expression ) {
            if(isOperator(c)){
                while (!stack.empty() && stack.peek()!= '(') {

                }

            }
        }

        return ";";
    }

    private boolean isOperator(char c) {
        if(c=='+'||c=='-'||c=='*'||c=='/') return true;
        else return false;
    }


}

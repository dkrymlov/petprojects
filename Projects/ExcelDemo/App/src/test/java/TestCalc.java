import com.krymlov.excel.calculator.Calculator;
import com.krymlov.excel.calculator.LexemeBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.krymlov.excel.calculator.Calculator.expr;
import static com.krymlov.excel.calculator.Calculator.lexAnalyze;

public class TestCalc {

    @Test
    public void testEval(){
        String expression = "1+10*(2+1+(3*2))";
        List<Calculator.Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        final double expectedResult = 91.0;
        double result = expr(lexemeBuffer);
        System.out.println("Expression: " + expression);
        System.out.println("Expected " + expectedResult + " Actual " + result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testEvalInc(){
        String expression = "10*inc(20)";
        List<Calculator.Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        final double expectedResult = 210.0;
        double result = expr(lexemeBuffer);
        System.out.println("Expression: " + expression);
        System.out.println("Expected " + expectedResult + " Actual " + result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testEvalDec(){
        String expression = "10*dec(20)";
        List<Calculator.Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        final double expectedResult = 190.0;
        double result = expr(lexemeBuffer);
        System.out.println("Expression: " + expression);
        System.out.println("Expected " + expectedResult + " Actual " + result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testEvalSqrt(){
        String expression = "sqrt(49)*7";
        List<Calculator.Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        final double expectedResult = 49.0;
        double result = expr(lexemeBuffer);
        System.out.println("Expression: " + expression);
        System.out.println("Expected " + expectedResult + " Actual " + result);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void testEvalPow(){
        String expression = "pow(7, 3)";
        List<Calculator.Lexeme> lexemes = lexAnalyze(expression);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        final double expectedResult = 343.0;
        double result = expr(lexemeBuffer);
        System.out.println("Expression: " + expression);
        System.out.println("Expected " + expectedResult + " Actual " + result);
        Assertions.assertEquals(expectedResult, result);
    }
}


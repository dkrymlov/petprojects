package com.krymlov.excel.calculator;

import com.krymlov.excel.calculator.Calculator;

import java.util.List;

//buffer of lexemes
public class LexemeBuffer {
    private int pos;

    public List<Calculator.Lexeme> lexemes;

    public LexemeBuffer(List<Calculator.Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Calculator.Lexeme next() {
        return lexemes.get(pos++);
    }

    public void back() {
        pos--;
    }

    public int getPos() {
        return pos;
    }
}

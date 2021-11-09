package com.krymlov.excel.calculator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//String arithmetical expression value parser
public class Calculator {

    private static HashMap<String, IFunction> functionMap;
    private static JTable table;

    public static void setTable(JTable table1){
        table = table1;
    }

    //function map(lambda)
    private static HashMap<String, IFunction> getFunctionMap() {
        HashMap<String, IFunction> functionTable = new HashMap<>();
        functionTable.put("sqrt", args -> {
            return (int) Math.sqrt(args.get(0));
        });
        functionTable.put("pow", args -> {
            if (args.size() != 2) {
                throw new RuntimeException("Wrong argument count for function pow: " + args.size());
            }
            return (int) Math.pow(args.get(0), args.get(1));
        });
        functionTable.put("inc", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function inc: " + args.size());

            }
            return args.get(0)+1;
        });
        functionTable.put("dec", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function dec: " + args.size());
            }
            return args.get(0)-1;
        });
        functionTable.put("mod", args -> {
            if (args.size() != 2) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            return args.get(0)%args.get(1);
        });
        functionTable.put("A", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            int row = args.get(0)-1;
            int column = 0;
            return Integer.parseInt(String.valueOf(table.getValueAt(row, column)));
        });
        functionTable.put("B", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            int row = args.get(0)-1;
            int column = 1;
            return Integer.parseInt(String.valueOf(table.getValueAt(row, column)));
        });
        functionTable.put("C", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            int row = args.get(0)-1;
            int column = 2;
            return Integer.parseInt(String.valueOf(table.getValueAt(row, column)));
        });
        functionTable.put("D", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            int row = args.get(0)-1;
            int column = 3;
            return Integer.parseInt(String.valueOf(table.getValueAt(row, column)));
        });
        functionTable.put("E", args -> {
            if (args.size() != 1) {
                throw new RuntimeException("Wrong argument count for function mod: " + args.size());
            }
            int row = args.get(0)-1;
            int column = 4;
            return Integer.parseInt(String.valueOf(table.getValueAt(row, column)));
        });

        return functionTable;
    }

    //class lexeme
    public static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        functionMap = getFunctionMap();
        int pos = 0;
        while (pos< expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '^':
                    lexemes.add(new Lexeme(LexemeType.OP_POW, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                case ',':
                    lexemes.add(new Lexeme(LexemeType.COMMA, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            if (c >= 'a' && c <= 'z'
                                    || c >= 'A' && c <= 'Z') {
                                StringBuilder sb = new StringBuilder();
                                do {
                                    sb.append(c);
                                    pos++;
                                    if (pos >= expText.length()) {
                                        break;
                                    }
                                    c = expText.charAt(pos);
                                } while (c >= 'a' && c <= 'z'
                                        || c >= 'A' && c <= 'Z');

                                if (functionMap.containsKey(sb.toString())) {
                                    lexemes.add(new Lexeme(LexemeType.NAME, sb.toString()));
                                } else {
                                    throw new RuntimeException("Unexpected character: " + c);
                                }
                            }
                        } else {
                            pos++;
                        }
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static int expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    private static int plusminus(LexemeBuffer lexemes) {
        int value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multdiv(lexemes);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    private static int multdiv(LexemeBuffer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                case OP_POW:
                    value = (int) Math.pow(value, factor(lexemes));
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case COMMA:
                case OP_PLUS:
                case OP_MINUS:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
            }
        }
    }

    private static int factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NAME:
                lexemes.back();
                return func(lexemes);
            case OP_MINUS:
                int value = factor(lexemes);
                return -value;
            case NUMBER:
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET:
                value = plusminus(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value
                            + " at position: " + lexemes.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemes.getPos());
        }
    }

    private static int func(LexemeBuffer lexemeBuffer) {
        String name = lexemeBuffer.next().value;
        Lexeme lexeme = lexemeBuffer.next();

        if (lexeme.type != LexemeType.LEFT_BRACKET) {
            throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
        }

        ArrayList<Integer> args = new ArrayList<>();

        lexeme = lexemeBuffer.next();
        if (lexeme.type != LexemeType.RIGHT_BRACKET) {
            lexemeBuffer.back();
            do {
                args.add(expr(lexemeBuffer));
                lexeme = lexemeBuffer.next();

                if (lexeme.type != LexemeType.COMMA && lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Wrong function call syntax at " + lexeme.value);
                }

            } while (lexeme.type == LexemeType.COMMA);
        }
        return functionMap.get(name).apply(args);
    }

}
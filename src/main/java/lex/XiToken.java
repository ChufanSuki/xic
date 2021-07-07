package lex;

enum TokenType {
    ID,
    UNDERSCORE,
    USE,
    WHILE,
    IF,
    ELSE,
    RETURN,
    LENGTH,
    INT,
    INT_LIT,
    TRUE,
    FALSE,
    BOOL,
    BOOL_LIT,
    STRING_LIT,
    CHAR_LIT,
    ADD,
    AND,
    OR,
    SUB,
    DIV,
    MUL,
    LT,
    GT,
    EQ,
    LTE,
    GTE,
    MINUS,
    NOT,
    NOTEQ,
    BITAND,
    BITOR,
    COLON,
    SEMICOLON,
    COMMA,
    LPAREN,
    RPAREN,
    LBRAC,
    RBRAC,
    LCURL,
    RCURL,
    ERROR,
}

public class XiToken {
    private TokenType tokenType;
    private int line;
    private int column;
    private Object lexeme;

    public XiToken(TokenType type, int line, int column, Object lexeme) {
        this.tokenType = type;
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
    }

    private String format() {
        String s = lexeme.toString();
        switch (tokenType) {
            case STRING_LIT:
            case CHAR_LIT:
                return s.replace("\\", "\\\\")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t")
                        .replace("\"", "\\\"")
                        .replace("\'", "\\'");
            default: return s;
        }
    }

    public String toString() {
        String type_rep = "";
        switch (tokenType) {
            case INT_LIT:       type_rep = "integer "; break;
            case STRING_LIT:    type_rep = "string "; break;
            case CHAR_LIT:      type_rep = "character "; break;
            case ID:            type_rep = "id "; break;
            case ERROR:         type_rep = "error:"; break;
            default:            break;
        }
        // make line and col 1-indexed
        return (line+1) + ":" + (column+1) + " " + type_rep + format();
    }

    public Boolean isError() {
        return tokenType == TokenType.ERROR;
    }
}
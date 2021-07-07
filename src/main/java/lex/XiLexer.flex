package lex;

%%

%public
%class XiLexer
%type XiToken
%function nextToken

%{
    StringBuffer string = new StringBuffer();
    private int stringLiteralStartCol = 0;
    private int charLiteralStartCol = 0;
%}

%unicode
%line
%column

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Whitespace = {LineTerminator} | [ \t\f]
Letter = [a-zA-Z]
Digit = [0-9]
Identifier = {Letter}({Digit}|{Letter}|_|\')*
Integer = 0|[1-9]{Digit}*
Float = {Digit}+ "." {Digit}+
Hex = [a-fA-F0-9]{1,4}
/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

%xstate CHAR, STRING

%%
<YYINITIAL> {
    /* keywords */
    "_" { return new XiToken(TokenType.UNDERSCORE, yyline, yycolumn, yytext()); }
    "use" { return new XiToken(TokenType.USE, yyline, yycolumn, yytext()); }
    "while" { return new XiToken(TokenType.WHILE, yyline, yycolumn, yytext()); }
    "if" { return new XiToken(TokenType.IF, yyline, yycolumn, yytext()); }
    "else" { return new XiToken(TokenType.ELSE, yyline, yycolumn, yytext()); }
    "return" { return new XiToken(TokenType.RETURN, yyline, yycolumn, yytext()); }
    "int" { return new XiToken(TokenType.INT, yyline, yycolumn, yytext()); }
    "bool" { return new XiToken(TokenType.BOOL, yyline, yycolumn, yytext()); }
    "true" { return new XiToken(TokenType.TRUE, yyline, yycolumn, yytext()); }
    "false" { return new XiToken(TokenType.FALSE, yyline, yycolumn, yytext()); }
    "length" { return new XiToken(TokenType.LENGTH, yyline, yycolumn, yytext()); }
    /* identifiers */
    {Identifier} { return new XiToken(TokenType.ID, yyline, yycolumn, yytext()); }
    /* literals */
    {Integer} { return new XiToken(TokenType.INT_LIT, yyline, yycolumn, yytext()); }
    \" { string.setLength(0); stringLiteralStartCol = yycolumn; yybegin(STRING); }
    \' { string.setLength(0); charLiteralStartCol = yycolumn; yybegin(CHAR); }
    /* operators */
    "+" { return new XiToken(TokenType.ADD, yyline, yycolumn, yytext()); }
    "-" { return new XiToken(TokenType.MINUS, yyline, yycolumn, yytext()); }
    "*" { return new XiToken(TokenType.MUL, yyline, yycolumn, yytext()); }
    "/" { return new XiToken(TokenType.DIV, yyline, yycolumn, yytext()); }
    "!" { return new XiToken(TokenType.NOT, yyline, yycolumn, yytext()); }
    "!=" { return new XiToken(TokenType.NOTEQ, yyline, yycolumn, yytext()); }
    "&" { return new XiToken(TokenType.BITAND, yyline, yycolumn, yytext()); }
    "|" { return new XiToken(TokenType.BITOR, yyline, yycolumn, yytext()); }
    "&&" { return new XiToken(TokenType.AND, yyline, yycolumn, yytext()); }
    "||" { return new XiToken(TokenType.OR, yyline, yycolumn, yytext()); }
    ">" { return new XiToken(TokenType.GT, yyline, yycolumn, yytext()); }
    "<" { return new XiToken(TokenType.LT, yyline, yycolumn, yytext()); }
    "=" { return new XiToken(TokenType.EQ, yyline, yycolumn, yytext()); }
    ">=" { return new XiToken(TokenType.GTE, yyline, yycolumn, yytext()); }
    "<=" { return new XiToken(TokenType.LTE, yyline, yycolumn, yytext()); }
    /* separators */
    ":" { return new XiToken(TokenType.COLON, yyline, yycolumn, yytext()); }
    ";" { return new XiToken(TokenType.SEMICOLON, yyline, yycolumn, yytext()); }
    "," { return new XiToken(TokenType.COMMA, yyline, yycolumn, yytext()); }
    "(" { return new XiToken(TokenType.LPAREN, yyline, yycolumn, yytext()); }
    ")" { return new XiToken(TokenType.RPAREN, yyline, yycolumn, yytext()); }
    "[" { return new XiToken(TokenType.LBRAC, yyline, yycolumn, yytext()); }
    "]" { return new XiToken(TokenType.RBRAC, yyline, yycolumn, yytext()); }
    "{" { return new XiToken(TokenType.LCURL, yyline, yycolumn, yytext()); }
    "}" { return new XiToken(TokenType.RCURL, yyline, yycolumn, yytext()); }
     /* whitespace */
     {Whitespace} { /* ignore */ }
     /* comments */
     {Comment} { /* ignore */ }
}

<STRING> {
    \" { yybegin(YYINITIAL); return new XiToken(TokenType.STRING_LIT, yyline, stringLiteralStartCol, string.toString()); }
    {LineTerminator} { return new XiToken(TokenType.ERROR, yyline, stringLiteralStartCol, "missing end double quotes"); }
    \\x{Hex} { string.append((char)Integer.parseInt(yytext().substring(2, yylength()), 16)); }
    [^\n\r\"\\]+ { string.append( yytext() ); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\ { string.append("\\"); }
}

<CHAR> {
    \' { yybegin(YYINITIAL); if (string.length() != 1) { return new XiToken(TokenType.ERROR, yyline, charLiteralStartCol, "Invalid character constant");  }  else return new XiToken(TokenType.CHAR_LIT, yyline, charLiteralStartCol, string.charAt(0)); }
    [^\n\r\'\\]+ { string.append( yytext() ); }
     \\t                           { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}

/* error fallback */
[^] { return new XiToken(TokenType.ERROR, yyline, yycolumn, "illegal symbol <" + yytext() + ">"); }

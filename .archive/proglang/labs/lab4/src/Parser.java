import sun.tools.java.SyntaxError;
import sun.tools.tree.WhileStatement;

import java.util.*;

public class Parser {
  // Recursive descent parser that inputs a C++Lite program and
  // generates its abstract syntax.  Each method corresponds to
  // a concrete syntax grammar rule, which appears as a comment
  // at the beginning of the method.

  Token token;          // current token from the input stream
  Lexer lexer;

  public Parser(Lexer ts) { // Open the C++Lite source program
    lexer = ts;                          // as a token stream, and
    token = lexer.next();            // retrieve its first Token
  }

  private String match (TokenType t) {
    String value = token.value();
    if (token.type().equals(t))
      token = lexer.next();
    else
      error(t);
    return value;
  }

  private void error(TokenType tok) {
    System.err.println("Syntax error: expecting: " + tok
        + "; saw: " + token);
    System.exit(1);
  }

  private void error(String tok) {
    System.err.println("Syntax error: expecting: " + tok
        + "; saw: " + token);
    System.exit(1);
  }

  public Program program() {
    // Program --> void main ( ) '{' Declarations Statements '}'
    TokenType[ ] header = {TokenType.Int, TokenType.Main,
        TokenType.LeftParen, TokenType.RightParen};
    for (int i=0; i<header.length; i++)   // bypass "int main ( )"
      match(header[i]);
    match(TokenType.LeftBrace);
    // student exercise
    Program p = new Program(declarations(), statements());
    match(TokenType.RightBrace);
    return p;  // student exercise
  }

  private Declarations declarations () {
    // Declarations --> { Declaration }
    // student exercise
    Declarations declarations = new Declarations();
    while (isType()) {
      declaration(declarations);
    }
    return declarations;
  }

  private void declaration (Declarations ds) {
    // Declaration  --> Type Identifier { , Identifier } ;
    // student exercise
    Type type;
    String id;

    type = type();

    while (!token.type().equals(TokenType.Semicolon)) {
      id = match(TokenType.Identifier);
      ds.add(new Declaration(new Variable(id), type));
      if (token.type().equals(TokenType.Comma)) {
        match(TokenType.Comma);
      }
    }
    match(TokenType.Semicolon);
  }

  private Type type () {
    // Type  -->  int | bool | float | char
    //    Type t = null;
    Type t;

    // student exercise
    if (token.type().equals(TokenType.Int)) {
      t = Type.INT;
    } else if (token.type().equals(TokenType.Bool)) {
      t = Type.BOOL;
    } else if (token.type().equals(TokenType.Float)) {
      t = Type.FLOAT;
    } else if (token.type().equals(TokenType.Char)){
      t = Type.CHAR;
    } else {
      t = null;
    }
    token = lexer.next();
    return t;
  }

  private Statement statement() {
    // Statement --> ; | Block | Assignment | IfStatement | WhileStatement
    Statement s = new Skip();
    // student exercise
    if (token.type().equals(TokenType.LeftBrace)) {
      token = lexer.next();
      s = statements();
      match(TokenType.RightBrace);
    } else if (token.type().equals(TokenType.Identifier)) {
      s = assignment();
    } else if (token.type().equals(TokenType.If)) {
      s = ifStatement();
    } else if (token.type().equals(TokenType.While)) {
      s = whileStatement();
    }
    return s;
  }

  private Block statements () {
    // Block --> '{' Statements '}'
    Block b = new Block();
    // student exercise
    while (!token.type().equals(TokenType.RightBrace)) {
      b.members.add(statement());
    }
    return b;
  }

  private Assignment assignment () {
    // Assignment --> Identifier = Expression ;
    // student exercise
    Variable t;
    Expression s;

    t = new Variable(match(TokenType.Identifier));
    match(TokenType.Assign);
    s = expression();
    match(TokenType.Semicolon);

    return new Assignment(t, s);
  }

  private Conditional ifStatement () {
    // IfStatement --> if ( Expression ) Statement [ else Statement ]
    // student exercise
    Expression testExpression;
    Statement mainStatement, elseStatement;

    match(TokenType.If);
    testExpression = expression();
    mainStatement = statement();
    elseStatement = new Skip();

    if (token.type().equals(TokenType.Else)) {
      token = lexer.next();
      elseStatement = statement();
    }

    return new Conditional(testExpression, mainStatement, elseStatement);
  }

  private Loop whileStatement () {
    // WhileStatement --> while ( Expression ) Statement
    // student exercise
    Expression testExpression;
    Statement statement;

    match(TokenType.While);
    match(TokenType.LeftParen);
    testExpression = expression();
    match(TokenType.RightParen);
    statement = statement();

    return new Loop(testExpression, statement);
  }

  private Expression expression () {
    // Expression --> Conjunction { || Conjunction }
    // student exercise
    Expression expression1, expression2;
    Operator operator;

    expression1 = conjunction();

    // Expression with OR operator
    while (token.type().equals(TokenType.Or)) {
      operator = new Operator(token.value());
      token = lexer.next();
      expression2 = conjunction();

      return new Binary(operator, expression1, expression2);
    }

    return expression1;
  }

  private Expression conjunction () {
    // Conjunction --> Equality { && Equality }
    // student exercise
    Expression expression1, expression2;
    Operator operator;

    expression1 = equality();

    // Expression with AND operator
    while (token.type().equals(TokenType.And)) {
      operator = new Operator(token.value());
      token = lexer.next();
      expression2 = equality();

      return new Binary(operator, expression1, expression2);
    }


    return expression1;
  }

  private Expression equality () {
    // Equality --> Relation [ EquOp Relation ]
    // student exercise
    // student exercise
    Expression expression1, expression2;
    Operator operator;

    expression1 = relation();

    // Expression with Equal or NotEqual operator
    while (isEqualityOp()) {
      operator = new Operator(token.value());
      token = lexer.next();
      expression2 = relation();

      return new Binary(operator, expression1, expression2);
    }

    return expression1;
  }

  private Expression relation (){
    // Relation --> Addition [RelOp Addition]
    // student exercise
    Expression expression1, expression2;
    Operator operator;

    expression1 = addition();

    // Expression with AND operator
    while (isRelationalOp()) {
      operator = new Operator(token.value());
      token = lexer.next();
      expression2 = addition();

      return new Binary(operator, expression1, expression2);
    }

    return expression1;
  }

  private Expression addition () {
    // Addition --> Term { AddOp Term }
    Expression e = term();
    while (isAddOp()) {
      Operator op = new Operator(match(token.type()));
      Expression term2 = term();
      e = new Binary(op, e, term2);
    }
    return e;
  }

  private Expression term () {
    // Term --> Factor { MultiplyOp Factor }
    Expression e = factor();
    while (isMultiplyOp()) {
      Operator op = new Operator(match(token.type()));
      Expression term2 = factor();
      e = new Binary(op, e, term2);
    }
    return e;
  }

  private Expression factor() {
    // Factor --> [ UnaryOp ] Primary
    if (isUnaryOp()) {
      Operator op = new Operator(match(token.type()));
      Expression term = primary();
      return new Unary(op, term);
    }
    else return primary();
  }

  private Expression primary () {
    // Primary --> Identifier | Literal | ( Expression )
    //             | Type ( Expression )
    Expression e = null;
    if (token.type().equals(TokenType.Identifier)) {
      e = new Variable(match(TokenType.Identifier));
    } else if (isLiteral()) {
      e = literal();
    } else if (token.type().equals(TokenType.LeftParen)) {
      token = lexer.next();
      e = expression();
      match(TokenType.RightParen);
    } else if (isType( )) {
      Operator op = new Operator(match(token.type()));
      match(TokenType.LeftParen);
      Expression term = expression();
      match(TokenType.RightParen);
      e = new Unary(op, term);
    } else error("Identifier | Literal | ( | Type");
    return e;
  }

  private Value literal( ) {
    // student exercise
    String literalValue;

    if (token.type().equals(TokenType.IntLiteral)) {
      literalValue = match(TokenType.IntLiteral);
      return new IntValue(Integer.parseInt(literalValue));
    } else if (token.type().equals(TokenType.FloatLiteral)) {
      literalValue = match(TokenType.FloatLiteral);
      return new FloatValue(Float.parseFloat(literalValue));
    } else if (token.type().equals(TokenType.CharLiteral)) {
      literalValue = match(TokenType.CharLiteral);
      return new CharValue(literalValue.charAt(0));
    } else if (token.type().equals(TokenType.True)) {
      literalValue = match(TokenType.True);
      return new BoolValue(true);
    } else {
      literalValue = match(TokenType.False);
      return new BoolValue(false);
    }

  }

  private boolean isAddOp( ) {
    return token.type().equals(TokenType.Plus) ||
        token.type().equals(TokenType.Minus);
  }

  private boolean isMultiplyOp( ) {
    return token.type().equals(TokenType.Multiply) ||
        token.type().equals(TokenType.Divide);
  }

  private boolean isUnaryOp( ) {
    return token.type().equals(TokenType.Not) ||
        token.type().equals(TokenType.Minus);
  }

  private boolean isEqualityOp( ) {
    return token.type().equals(TokenType.Equals) ||
        token.type().equals(TokenType.NotEqual);
  }

  private boolean isRelationalOp( ) {
    return token.type().equals(TokenType.Less) ||
        token.type().equals(TokenType.LessEqual) ||
        token.type().equals(TokenType.Greater) ||
        token.type().equals(TokenType.GreaterEqual);
  }

  private boolean isType( ) {
    return token.type().equals(TokenType.Int)
        || token.type().equals(TokenType.Bool)
        || token.type().equals(TokenType.Float)
        || token.type().equals(TokenType.Char);
  }

  private boolean isLiteral( ) {
    return token.type().equals(TokenType.IntLiteral) ||
        isBooleanLiteral() ||
        token.type().equals(TokenType.FloatLiteral) ||
        token.type().equals(TokenType.CharLiteral);
  }

  private boolean isBooleanLiteral( ) {
    return token.type().equals(TokenType.True) ||
        token.type().equals(TokenType.False);
  }

  public static void main(String args[]) {
    Parser parser  = new Parser(new Lexer(args[0]));
    Program prog = parser.program();
//    prog.display();           // display abstract syntax tree
  } //main

} // Parser

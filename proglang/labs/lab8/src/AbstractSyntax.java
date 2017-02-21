// Abstract syntax for the language C++Lite,
// exactly as it appears in Appendix B.

import java.util.*;

class Program {
    // Program = Declarations decpart ; Block body
    Declarations decpart;
    Block body;

    // TAB Constant
    private static final String TAB = "  ";

    Program (Declarations d, Block b) {
        decpart = d;
        body = b;
    }

    void display() {
        String prefix = TAB + "Declarations:\n";
        String declarations;

        if (decpart.size() > 0) {
            declarations = TAB + TAB + "{";
            for (Declaration d : decpart) {
              declarations += d.display() + ", ";
            }
          declarations = declarations.substring(0, declarations.length() - 2) + "}\n";
        } else {
          declarations = TAB + TAB + "Empty\n";
        }
      System.out.print(prefix + declarations + display_body(1,body));
    }

    String tab(int n) {
      return new String(new char[n]).replace("\0", TAB);
    }

    String display_body(int tabsize, Object node) {
//      if (node instanceof Assignment) {
//
//        Assignment ass_node = (Assignment) node;
//        String prefix = tab(tabsize) + "Assignment" + ":\n";
//        String branch1 = display_body(tabsize + 1, ass_node.target);
//        String branch2 = display_body(tabsize + 1, ass_node.source);
//        return prefix + branch1 + branch2;

//      } else if (node instanceof Block) {
//
//        Block block_node = (Block) node;
//        String prefix = tab(tabsize) + "Block:\n";
//        String output = "";
//        for (Statement m : block_node.members) {
//          output += display_body(tabsize + 1, m);
//        }
//        return prefix + output;

//      } else if (node instanceof Expression) {
      if (node instanceof Expression) {

        Expression exp_node = (Expression) node;
        String prefix = tab(tabsize) + "Expression:\n";
        String output = "";

        if (exp_node instanceof Value) {
          Value val_node = (Value) node;
          output += tab(tabsize + 1) + "Value:\n";
          output += tab(tabsize + 2) + val_node.toString() + "\n";
        } else if (exp_node instanceof Variable) {
          Variable var_node = (Variable) node;
          output += tab(tabsize + 1) + "Variable:\n";
          output += tab(tabsize + 2) + var_node.toString() + "\n";
        } else if (exp_node instanceof Binary) {
          Binary bin_node = (Binary) node;
          output += tab(tabsize + 1) + "Binary:\n";
          String branch1 = display_body(tabsize + 2, bin_node.op);
          String branch2 = display_body(tabsize + 2, bin_node.term1);
          String branch3 = display_body(tabsize + 2, bin_node.term2);
          output += branch1 + branch2 + branch3;
        } else if (exp_node instanceof Conjunction) {
          Conjunction con_node = (Conjunction) node;
          output += tab(tabsize + 1) + "Conjunction:\n";
          String branch1 = display_body(tabsize + 2, con_node.op);
          String branch2 = display_body(tabsize + 2, con_node.term1);
          String branch3 = display_body(tabsize + 2, con_node.term2);
          output += branch1 + branch2 + branch3;
        } else if (exp_node instanceof Equality) {
          Equality eq_node = (Equality) node;
          output += tab(tabsize + 1) + "Equality:\n";
          String branch1 = display_body(tabsize + 2, eq_node.op);
          String branch2 = display_body(tabsize + 2, eq_node.term1);
          String branch3 = display_body(tabsize + 2, eq_node.term2);
          output += branch1 + branch2 + branch3;
        } else if (exp_node instanceof Relation) {
          Relation rel_node = (Relation) node;
          output += tab(tabsize + 1) + "Relation:\n";
          String branch1 = display_body(tabsize + 2, rel_node.op);
          String branch2 = display_body(tabsize + 2, rel_node.term1);
          String branch3 = display_body(tabsize + 2, rel_node.term2);
          output += branch1 + branch2 + branch3;
        } else if (exp_node instanceof Unary) {
          Unary u_node = (Unary) node;
          output += tab(tabsize + 1) + "Unary:\n";
          String branch1 = display_body(tabsize + 2, u_node.op);
          String branch2 = display_body(tabsize + 2, u_node.term);
          output += branch1 + branch2;
        } else {
          output += "";
        }

        return prefix + output;

      } else if (node instanceof Statement) {
        // Statement = Skip | Block | Assignment | Conditional | Loop
        Statement state_node = (Statement) node;
        String prefix = tab(tabsize) + "Statement:\n";
        String output = "";

        if (state_node instanceof Skip) {
          // do nothing
        } else if (state_node instanceof Block) {
          Block block_node = (Block) node;
          output += tab(tabsize + 1) + "Block:\n";
          for (Statement m : block_node.members) {
            output += display_body(tabsize + 2, m);
          }

        } else if (state_node instanceof Assignment) {
          Assignment ass_node = (Assignment) node;
          output += tab(tabsize + 1) + "Assignment" + ":\n";
          String branch1 = display_body(tabsize + 2, ass_node.target);
          String branch2 = display_body(tabsize + 2, ass_node.source);
          output += branch1 + branch2;

        } else if (state_node instanceof Conditional) {
          Conditional con_node = (Conditional) node;
          output += tab(tabsize + 1) + "Conditional:\n";
          output += tab(tabsize + 2) + "Test:\n";
          String branch1 = display_body(tabsize + 3, con_node.test);
          output += branch1;
          output += tab(tabsize + 2) + "Then:\n";
          String branch2 = display_body(tabsize + 3, con_node.thenbranch);
          output += branch2;
          output += tab(tabsize + 2) + "Else:\n";
          String branch3 = display_body(tabsize + 3, con_node.elsebranch);
          output += branch3;
        } else if (state_node instanceof Loop) {
          Loop loop_node = (Loop) node;
          output += tab(tabsize + 1) + "Loop" + ":\n";
          output += tab(tabsize + 2) + "Test" + ":\n";
          String branch1 = display_body(tabsize + 3, loop_node.test);
          output += branch1;
          output += tab(tabsize + 2) + "Body" + ":\n";
          String branch2 = display_body(tabsize + 3, loop_node.body);
          output += branch2;
        } else {
          output += "";
        }



        return prefix + output;

      } else {

        String prefix = tab(tabsize) + (node.getClass() + "").substring(6) + ": ";
        String value = node + "\n";
        return prefix + value;

      }

    }

}

class Declarations extends ArrayList<Declaration> {
    // Declarations = Declaration*
    // (a list of declarations d1, d2, ..., dn)

}

class Declaration {
// Declaration = Variable v; Type t
    Variable v;
    Type t;

    Declaration (Variable var, Type type) {
        v = var; t = type;
    } // declaration */

    String display() {
        return "<" + v + ", " + t + ">";
    }

}

class Type {
    // Type = int | bool | char | float 
    final static Type INT = new Type("int");
    final static Type BOOL = new Type("bool");
    final static Type CHAR = new Type("char");
    final static Type FLOAT = new Type("float");
    // final static Type UNDEFINED = new Type("undef");
    
    private String id;

    private Type (String t) { id = t; }

    public String toString ( ) { return id; }
}

abstract class Statement {
    // Statement = Skip | Block | Assignment | Conditional | Loop

}

class Skip extends Statement {
}

class Block extends Statement {
    // Block = Statement*
    //         (a Vector of members)
    public ArrayList<Statement> members = new ArrayList<Statement>();

}

class Assignment extends Statement {
    // Assignment = Variable target; Expression source
    Variable target;
    Expression source;

    Assignment (Variable t, Expression e) {
        target = t;
        source = e;
    }

}

class Conditional extends Statement {
// Conditional = Expression test; Statement thenbranch, elsebranch
    Expression test;
    Statement thenbranch, elsebranch;
    // elsebranch == null means "if... then"
    
    Conditional (Expression t, Statement tp) {
        test = t; thenbranch = tp; elsebranch = new Skip( );
    }
    
    Conditional (Expression t, Statement tp, Statement ep) {
        test = t; thenbranch = tp; elsebranch = ep;
    }
    
}

class Loop extends Statement {
// Loop = Expression test; Statement body
    Expression test;
    Statement body;

    Loop (Expression t, Statement b) {
        test = t; body = b;
    }
    
}

class Expressions extends ArrayList<Expression> {
  // Expressions = {Expression, Expression, ... }
}

abstract class Expression {
    // Expression = Variable | Value | Binary | Unary

}

class Variable extends Expression {
    // Variable = String id
    private String id;

    Variable (String s) { id = s; }

    public String toString( ) { return id; }
    
    public boolean equals (Object obj) {
        String s = ((Variable) obj).id;
        return id.equals(s); // case-sensitive identifiers
    }
    
    public int hashCode ( ) { return id.hashCode( ); }

}

abstract class Value extends Expression {
    // Value = IntValue | BoolValue |
    //         CharValue | FloatValue
    protected Type type;
    protected boolean undef = true;

    int intValue ( ) {
        assert false : "should never reach here";
        return 0;
    }
    
    boolean boolValue ( ) {
        assert false : "should never reach here";
        return false;
    }
    
    char charValue ( ) {
        assert false : "should never reach here";
        return ' ';
    }
    
    float floatValue ( ) {
        assert false : "should never reach here";
        return 0.0f;
    }

    boolean isUndef( ) { return undef; }

    Type type ( ) { return type; }

    static Value mkValue (Type type) {
        if (type == Type.INT) return new IntValue( );
        if (type == Type.BOOL) return new BoolValue( );
        if (type == Type.CHAR) return new CharValue( );
        if (type == Type.FLOAT) return new FloatValue( );
        throw new IllegalArgumentException("Illegal type in mkValue");
    }
}

class IntValue extends Value {
    private int value = 0;

    IntValue ( ) { type = Type.INT; }

    IntValue (int v) { this( ); value = v; undef = false; }

    int intValue ( ) {
        assert !undef : "reference to undefined int value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

}

class BoolValue extends Value {
    private boolean value = false;

    BoolValue ( ) { type = Type.BOOL; }

    BoolValue (boolean v) { this( ); value = v; undef = false; }

    boolean boolValue ( ) {
        assert !undef : "reference to undefined bool value";
        return value;
    }

    int intValue ( ) {
        assert !undef : "reference to undefined bool value";
        return value ? 1 : 0;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

}

class CharValue extends Value {
    private char value = ' ';

    CharValue ( ) { type = Type.CHAR; }

    CharValue (char v) { this( ); value = v; undef = false; }

    char charValue ( ) {
        assert !undef : "reference to undefined char value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

}

class FloatValue extends Value {
    private float value = 0;

    FloatValue ( ) { type = Type.FLOAT; }

    FloatValue (float v) { this( ); value = v; undef = false; }

    float floatValue ( ) {
        assert !undef : "reference to undefined float value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

}

class Binary extends Expression {
// Binary = Operator op; Expression term1, term2
    Operator op;
    Expression term1, term2;

    Binary (Operator o, Expression l, Expression r) {
        op = o; term1 = l; term2 = r;
    } // binary

}

class Unary extends Expression {
    // Unary = Operator op; Expression term
    Operator op;
    Expression term;

    Unary (Operator o, Expression e) {
        op = o; term = e;
    } // unary
}

class Conjunction extends Expression {
  Operator op;
  Expression term1, term2;

  Conjunction(Operator o, Expression l, Expression r) {
    op = o; term1 = l; term2 = r;
  }

}

class Equality extends Expression {
  Operator op;
  Expression term1, term2;

  Equality(Operator o, Expression l, Expression r) {
    op = o; term1 = l; term2 = r;
  }

}

class Relation extends Expression {
  Operator op;
  Expression term1, term2;

  Relation(Operator o, Expression l, Expression r) {
    op = o; term1 = l; term2 = r;
  }

}

class Operator {
    // Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp
    // BooleanOp = && | ||
    final static String AND = "&&";
    final static String OR = "||";
    // RelationalOp = < | <= | == | != | >= | >
    final static String LT = "<";
    final static String LE = "<=";
    final static String EQ = "==";
    final static String NE = "!=";
    final static String GT = ">";
    final static String GE = ">=";
    // ArithmeticOp = + | - | * | /
    final static String PLUS = "+";
    final static String MINUS = "-";
    final static String TIMES = "*";
    final static String DIV = "/";
    // UnaryOp = !    
    final static String NOT = "!";
    final static String NEG = "-";
    // CastOp = int | float | char
    final static String INT = "int";
    final static String FLOAT = "float";
    final static String CHAR = "char";
    // Typed Operators
    // RelationalOp = < | <= | == | != | >= | >
    final static String INT_LT = "INT<";
    final static String INT_LE = "INT<=";
    final static String INT_EQ = "INT==";
    final static String INT_NE = "INT!=";
    final static String INT_GT = "INT>";
    final static String INT_GE = "INT>=";
    // ArithmeticOp = + | - | * | /
    final static String INT_PLUS = "INT+";
    final static String INT_MINUS = "INT-";
    final static String INT_TIMES = "INT*";
    final static String INT_DIV = "INT/";
    // UnaryOp = !    
    final static String INT_NEG = "-";
    // RelationalOp = < | <= | == | != | >= | >
    final static String FLOAT_LT = "FLOAT<";
    final static String FLOAT_LE = "FLOAT<=";
    final static String FLOAT_EQ = "FLOAT==";
    final static String FLOAT_NE = "FLOAT!=";
    final static String FLOAT_GT = "FLOAT>";
    final static String FLOAT_GE = "FLOAT>=";
    // ArithmeticOp = + | - | * | /
    final static String FLOAT_PLUS = "FLOAT+";
    final static String FLOAT_MINUS = "FLOAT-";
    final static String FLOAT_TIMES = "FLOAT*";
    final static String FLOAT_DIV = "FLOAT/";
    // UnaryOp = !    
    final static String FLOAT_NEG = "-";
    // RelationalOp = < | <= | == | != | >= | >
    final static String CHAR_LT = "CHAR<";
    final static String CHAR_LE = "CHAR<=";
    final static String CHAR_EQ = "CHAR==";
    final static String CHAR_NE = "CHAR!=";
    final static String CHAR_GT = "CHAR>";
    final static String CHAR_GE = "CHAR>=";
    // RelationalOp = < | <= | == | != | >= | >
    final static String BOOL_LT = "BOOL<";
    final static String BOOL_LE = "BOOL<=";
    final static String BOOL_EQ = "BOOL==";
    final static String BOOL_NE = "BOOL!=";
    final static String BOOL_GT = "BOOL>";
    final static String BOOL_GE = "BOOL>=";
    // Type specific cast
    final static String I2F = "I2F";
    final static String F2I = "F2I";
    final static String C2I = "C2I";
    final static String I2C = "I2C";
    
    String val;
    
    Operator (String s) { val = s; }

    public String toString( ) { return val; }
    public boolean equals(Object obj) { return val.equals(obj); }
    
    boolean BooleanOp ( ) { return val.equals(AND) || val.equals(OR); }
    boolean RelationalOp ( ) {
        return val.equals(LT) || val.equals(LE) || val.equals(EQ)
            || val.equals(NE) || val.equals(GT) || val.equals(GE);
    }
    boolean ArithmeticOp ( ) {
        return val.equals(PLUS) || val.equals(MINUS)
            || val.equals(TIMES) || val.equals(DIV);
    }
    boolean NotOp ( ) { return val.equals(NOT) ; }
    boolean NegateOp ( ) { return val.equals(NEG) ; }
    boolean intOp ( ) { return val.equals(INT); }
    boolean floatOp ( ) { return val.equals(FLOAT); }
    boolean charOp ( ) { return val.equals(CHAR); }

    final static String intMap[ ] [ ] = {
        {PLUS, INT_PLUS}, {MINUS, INT_MINUS},
        {TIMES, INT_TIMES}, {DIV, INT_DIV},
        {EQ, INT_EQ}, {NE, INT_NE}, {LT, INT_LT},
        {LE, INT_LE}, {GT, INT_GT}, {GE, INT_GE},
        {NEG, INT_NEG}, {FLOAT, I2F}, {CHAR, I2C}
    };

    final static String floatMap[ ] [ ] = {
        {PLUS, FLOAT_PLUS}, {MINUS, FLOAT_MINUS},
        {TIMES, FLOAT_TIMES}, {DIV, FLOAT_DIV},
        {EQ, FLOAT_EQ}, {NE, FLOAT_NE}, {LT, FLOAT_LT},
        {LE, FLOAT_LE}, {GT, FLOAT_GT}, {GE, FLOAT_GE},
        {NEG, FLOAT_NEG}, {INT, F2I}
    };

    final static String charMap[ ] [ ] = {
        {EQ, CHAR_EQ}, {NE, CHAR_NE}, {LT, CHAR_LT},
        {LE, CHAR_LE}, {GT, CHAR_GT}, {GE, CHAR_GE},
        {INT, C2I}
    };

    final static String boolMap[ ] [ ] = {
        {EQ, BOOL_EQ}, {NE, BOOL_NE}, {LT, BOOL_LT},
        {LE, BOOL_LE}, {GT, BOOL_GT}, {GE, BOOL_GE},
    };

    final static private Operator map (String[][] tmap, String op) {
        for (int i = 0; i < tmap.length; i++)
            if (tmap[i][0].equals(op))
                return new Operator(tmap[i][1]);
        assert false : "should never reach here";
        return null;
    }

    final static public Operator intMap (String op) {
        return map (intMap, op);
    }

    final static public Operator floatMap (String op) {
        return map (floatMap, op);
    }

    final static public Operator charMap (String op) {
        return map (charMap, op);
    }

    final static public Operator boolMap (String op) {
        return map (boolMap, op);
    }

}

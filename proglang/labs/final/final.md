# PLD Final
Chris Fenton (fenwil28)

## Initialization Declarations

### Example Program

```
int main ( ) {
    int n = 3;
    int i = 1;
    int f = 1;
    while (i < n) {
        i = i + 1;
        f = f * i;
    }
}
```

### Lexer Changes

No new tokens needed

### BNF Changes

Declaration -> BaseDeclaration | AssignmentDeclaration
BaseDeclaration -> Type Identifier;
AssignmentDeclaration -> Type Identifier Assignment;

### AST Changes

Declaration = BaseDeclaration | AssignmentDeclaration
BaseDeclaration = Variable v; Type t
AssignmentDeclaration = Variable v; Type t; Assignment a

### Implementation

Parser.java

```
private void declaration (Declarations ds) {
  // Declaration  --> Type Identifier { , Identifier } ;
  // student exercise
  Type type;
  String id;

  type = type();

  while (!token.type().equals(TokenType.Semicolon)) {
    id = match(TokenType.Identifier);

    if (token.type().equals(TokenType.Assign)) {
      ds.add(new AssignmentDeclaration(new Variable(id), type, assignment()));
    } else {
      ds.add(new BaseDeclaration(new Variable(id), type));
    }

  }
  match(TokenType.Semicolon);
}
```

AbstractSyntax.java

```
abstract class Declaration {
    // Declaration = BaseDeclaration | AssignmentDeclaration
}

class BaseDeclaration extends Declaration{
// Declaration = Variable v; Type t
    Variable v;
    Type t;

    BaseDeclaration (Variable var, Type type) {
        v = var; t = type;
    } // declaration */

}

class AssignmentDeclaration extends Declaration {
  Variable v;
  Type t;
  Assignment a;

  AssignmentDeclaration (Variable var, Type type, Assignment ass) {
    v = var; t = type; a = ass;
  }
}
```

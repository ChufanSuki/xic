package parser;
import java.util.*;

enum ExprType{
    BinopExpr,
    BoolLiteralExpr,
    CharLiteralExpr,
    FunctionCallExpr,
    IdExpr,
    IndexExpr,
    IntLiteralExpr,
    LengthExpr,
    ListLiteralExpr,
    StringLiteralExpr,
    UnopExpr,
    UnderscoreExpr
}

enum StmtType{
    ProcedureReturnStmt,
    FunctionReturnStmt,
    AssignStmt,
    MultiAssignStmt,
    DeclStmt,
    MultiDeclStmt,
    DeclAssignStmt,
    MultiDeclAssignStmt,
    ProcedureCallStmt,
    IfStmt,
    IfElseStmt,
    WhileStmt,
    BlockStmt
}

enum TypeType{
    ListType,
    TupleType,
    Tvar
}

enum Unop{
    NOT,
    UMINUS
}

enum Binop{
    EQ, //=
    MINUS,
    NOT,
    MULT,
    HI_MULT,// *>>
    DIV,
    MOD,
    EQEQ,// ==
    NEQ,
    GT,
    LT,
    GTEQ,
    LTEQ,
    AND,
    OR
}

class Pair<A, B> {
    private A first;
    private B second;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A fst() {
        return first;
    }

    public B snd() {
        return second;
    }

    public String toString() {
        return "(" + first.toString() + "," + second.toString() + ")";
    }
}

abstract class Type {
    public TypeType t_type;

    public TypeType getT_type() {
        return t_type;
    }
}

class Tvar extends Type {
    private String name;
    Tvar(String name) {
        this.name = name;
        this.t_type = TypeType.Tvar;
    }
    public String toString() {
        return name;
    }
    public String getName() {
        return name;
    }
}

class ListType extends Type {
    private Type contentsType;
    private Expr length;

    ListType(Type type) {
        this.contentsType = type;
        this.t_type = TypeType.ListType;
    }

    ListType(Type type, Expr length) {
        this.contentsType = type;
        this.length = length;
        this.t_type = TypeType.ListType;
    }

    public String toString() {
        return contentsType.toString() + " list";
    }

    public Type getContentsType() {
        return contentsType;
    }

    public Expr getLength() {
        return length;
    }
}

class TupleType extends Type {
    private List<Type> contentsTypes;

    TupleType(List<Type> types) {
        this.contentsTypes = types;
        this.t_type = TypeType.TupleType;
    }

    public String toString() {
        List<String> typeNames = new ArrayList<>();
        Arrays.asList(contentsTypes).forEach(elt -> typeNames.add(elt.toString()));
        return String.join(" * ", typeNames);
    }

    public List<Type> getContentsTypes() {
        return contentsTypes;
    }
}

abstract class Expr {
    public ExprType e_type;

    public ExprType getE_type() {
        return e_type;
    }
}

class BinopExpr extends Expr {
    private String op;
    private Expr left;
    private Expr right;

    BinopExpr(String op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
        this.e_type = ExprType.BinopExpr;
    }

    public String getOp() {
        return op;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }
}

class UnopExpr extends Expr {
    private String op;
    private Expr expr;

    UnopExpr(String op, Expr expr) {
        this.op = op;
        this.expr = expr;
        this.e_type = ExprType.UnopExpr;
    }

    public String getOp() {
        return op;
    }

    public Expr getExpr() {
        return expr;
    }
}

class IdExpr extends Expr {
    private String name;
    IdExpr(String name) {
        this.name = name;
        this.e_type = ExprType.IdExpr;
    }

    public String getName() {
        return name;
    }
}

class  IntLiteralExpr extends Expr {
    private Integer value;
    IntLiteralExpr(int val) {
        this.value = value;
        this.e_type = ExprType.IntLiteralExpr;
    }

    public Integer getValue() {
        return value;
    }
}

class BoolLiteralExpr extends Expr {
    private Boolean value;
    BoolLiteralExpr(boolean value) {
        this.value = value;
        this.e_type = ExprType.BoolLiteralExpr;
    }

    public Boolean getValue() {
        return value;
    }
}

class FunctionCallExpr extends Expr {
    private String name;
    private Expr[] args;
    FunctionCallExpr(String name, Expr[] args) {
        this.name = name;
        this.args = args;
        this.e_type = ExprType.FunctionCallExpr;
    }

    public String getName() {
        return name;
    }

    public Expr[] getArgs() {
        return args;
    }
}

abstract class Stmt {
    public StmtType s_type;

    public StmtType getS_type() {
        return s_type;
    }
}

class FunctionReturnStmt extends Stmt {
    private Expr returnExpr;
    FunctionReturnStmt(Expr returnExpr) {
        this.returnExpr = returnExpr;
        this.s_type = StmtType.FunctionReturnStmt;

    }

    public Expr getReturnExpr() {
        return returnExpr;
    }
}

class IfStmt extends Stmt {
    private Expr guard;
    private Stmt thenStmt;

    IfStmt(Expr guard, Stmt thenStmt) {
        this.thenStmt = thenStmt;
        this.guard = guard;
        this.s_type = StmtType.IfStmt;
    }
}

class IfElseStmt extends Stmt {
    private Expr guard;
    private Stmt thenStmt;
    private Stmt elseStmt;

    IfElseStmt(Expr guard, Stmt thenStmt, Stmt elseStmt) {
        this.elseStmt = elseStmt;
        this.thenStmt = thenStmt;
        this.guard = guard;
        this.s_type = StmtType.IfElseStmt;
    }
}

class WhileStmt extends Stmt {
    private Expr guard;
    private Stmt doStmt;

    WhileStmt(Expr guard, Stmt doStmt) {
        this.guard = guard;
        this.doStmt = doStmt;
        this.s_type = StmtType.WhileStmt;
    }

    public Expr getGuard() {
        return guard;
    }

    public Stmt getDoStmt() {
        return doStmt;
    }
}

class ProcedureCallStmt extends Stmt {
    private String name;
    private Expr[] args;

    public ProcedureCallStmt(String name, Expr[] args) {
        this.name = name;
        this.args = args;
        this.s_type = StmtType.ProcedureCallStmt;
    }

    public String getName() {
        return name;
    }

    public Expr[] getArgs() {
        return args;
    }
}

class AssignStmt extends Stmt {
    private Expr left;
    private Expr right;

    public AssignStmt(Expr left, Expr right) {
        this.left = left;
        this.right = right;
        this.s_type = StmtType.AssignStmt;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }
}

class MultiAssignStmt extends Stmt {
    private Expr[] left;
    private Expr[] right;

    public MultiAssignStmt(Expr[] left, Expr[] right) {
        this.left = left;
        this.right = right;
        this.s_type = StmtType.MultiAssignStmt;
    }

    public Expr[] getLeft() {
        return left;
    }

    public Expr[] getRight() {
        return right;
    }
}


class DeclStmt extends Stmt {
    private Pair<String, Type> decl;

    public DeclStmt(Pair<String, Type> decl) {
        this.decl = decl;
        this.s_type = StmtType.DeclStmt;
    }

    public Pair<String, Type> getDecl() {
        return decl;
    }
}

class MultiDeclStmt extends Stmt {
    private Pair<String, Type>[] decls;

    public MultiDeclStmt(Pair<String, Type>[] decls) {
        this.decls = decls;
        this.s_type = StmtType.MultiDeclStmt;
    }

    public Pair<String, Type>[] getDecls() {
        return decls;
    }
}

class BlockStmt extends Stmt {
    private Stmt[] statements;

    public BlockStmt(Stmt[] statements) {
        this.statements = statements;
        this.s_type = StmtType.BlockStmt;
    }

    public BlockStmt() {    //empty block
    }

    public Stmt[] getStatments() {
        return statements;
    }

    public boolean isEmpty(){
        return statements.length == 0;
    }

    public Stmt getLastStatement(){
        return statements[statements.length-1];
    }
}

//definitions are for program files
abstract class Defn {
    public boolean isProcedure;
}

class FunctionDefn extends Defn {
    private String name;
    private Pair<String, Type>[] params;
    private Type output;
    private Stmt body;

    public FunctionDefn(String name, Pair<String, Type>[] params, Type output, Stmt body) {
        this.name = name;
        this.params = params;
        this.output = output;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Pair<String, Type>[] getParams() {
        return params;
    }

    public Type getOutput() {
        return output;
    }

    public Stmt getBody() {
        return body;
    }
}

class ProcedureDefn extends Defn {
    private String name;
    private Pair<String, Type>[] params;
    private Stmt body;

    public ProcedureDefn(String name, Pair<String, Type>[] params, Stmt body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Pair<String, Type>[] getParams() {
        return params;
    }

    public Stmt getBody() {
        return body;
    }

}

class UseInterface {
    private String name;

    public UseInterface(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

//declarations are for interfaces
abstract class Decl {
}

class FunctionDecl extends Decl {
    private String name;
    private Pair<String, Type>[] params;
    private Type output;

    public FunctionDecl(String name, Pair<String, Type>[] params, Type output) {
        this.name = name;
        this.params = params;
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public Pair<String, Type>[] getParams() {
        return params;
    }

    public Type getOutput() {
        return output;
    }

    public boolean isProcedure() {
        return false;
    }
}

class ProcedureDecl extends Decl {
    private String name;
    private Pair<String, Type>[] params;

    public ProcedureDecl(String name, Pair<String, Type>[] params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public Pair<String, Type>[] getParams() {
        return params;
    }

    public boolean isProcedure() {
        return true;
    }
}

//top level "nodes"
abstract class SourceFile {
    abstract boolean isInterface();
}


class InterfaceFile extends SourceFile {
    private ArrayList<Decl> declarations;

    public InterfaceFile(List<Decl> declarations) {
        this.declarations = new ArrayList<>(declarations);
    }

    public List<Decl> getDeclarations() {
        return declarations;
    }

    public void addDecl(Decl decl) {
        declarations.add(decl);
    }

    public boolean isInterface() {
        return true;
    }
}

class ProgramFile extends SourceFile {
    private ArrayList<UseInterface> imports;
    private ArrayList<Defn> definitions;

    public ProgramFile(List<UseInterface> imports, List<Defn> definitions) {
        this.imports = new ArrayList<>(imports);
        this.definitions = new ArrayList<>(definitions);
    }

    public List<UseInterface> getImports() {
        return imports;
    }

    public List<Defn> getDefinitions() {
        return definitions;
    }

    public void addDefn(Defn defn) {
        definitions.add(defn);
    }

    public void addImport(UseInterface use) {
        imports.add(use);
    }

    public boolean isInterface() {
        return false;
    }
}

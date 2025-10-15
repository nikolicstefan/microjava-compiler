// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:6


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(FormPars FormPars) { }
    public void visit(Factor Factor) { }
    public void visit(Statement Statement) { }
    public void visit(PrintWidth PrintWidth) { }
    public void visit(Relop Relop) { }
    public void visit(DeclList DeclList) { }
    public void visit(CondFactList CondFactList) { }
    public void visit(ConstInitList ConstInitList) { }
    public void visit(ElseBlock ElseBlock) { }
    public void visit(FactorList FactorList) { }
    public void visit(Expr Expr) { }
    public void visit(FormPar FormPar) { }
    public void visit(ClassMethodDeclList ClassMethodDeclList) { }
    public void visit(FormParList FormParList) { }
    public void visit(ArrDeclBrackets ArrDeclBrackets) { }
    public void visit(ConstInit ConstInit) { }
    public void visit(Condition Condition) { }
    public void visit(Mulop Mulop) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(AssignValue AssignValue) { }
    public void visit(SignedFactor SignedFactor) { }
    public void visit(Addop Addop) { }
    public void visit(StatementList StatementList) { }
    public void visit(ActParList ActParList) { }
    public void visit(CondTermList CondTermList) { }
    public void visit(ClassMethodBlock ClassMethodBlock) { }
    public void visit(Literal Literal) { }
    public void visit(Setop Setop) { }
    public void visit(RetVal RetVal) { }
    public void visit(VarList VarList) { }
    public void visit(MethodDeclList MethodDeclList) { }
    public void visit(TermList TermList) { }
    public void visit(DoWhileCond DoWhileCond) { }
    public void visit(ActPars ActPars) { }
    public void visit(Designator Designator) { }
    public void visit(VarDeclList VarDeclList) { }
    public void visit(CondFact CondFact) { }
    public void visit(SelectorList SelectorList) { }
    public void visit(RetType RetType) { }
    public void visit(InterfaceMethodDeclList InterfaceMethodDeclList) { }
    public void visit(ExtendsClause ExtendsClause) { }
    public void visit(Var Var) { }
    public void visit(Decl Decl) { }
    public void visit(PostIterStmt PostIterStmt) { }
    public void visit(PrintWidthEpsilon PrintWidthEpsilon) { visit(); }
    public void visit(PrintWidthExists PrintWidthExists) { visit(); }
    public void visit(RetValEpsilon RetValEpsilon) { visit(); }
    public void visit(RetValExists RetValExists) { visit(); }
    public void visit(PostIterStmtEpsilon PostIterStmtEpsilon) { visit(); }
    public void visit(PostIterStmtExists PostIterStmtExists) { visit(); }
    public void visit(DoWhileCondBegin DoWhileCondBegin) { visit(); }
    public void visit(DoWhileCondEpsilon DoWhileCondEpsilon) { visit(); }
    public void visit(DoWhileCondExists DoWhileCondExists) { visit(); }
    public void visit(DoWhileBegin DoWhileBegin) { visit(); }
    public void visit(ElseBegin ElseBegin) { visit(); }
    public void visit(ElseBlockEpsilon ElseBlockEpsilon) { visit(); }
    public void visit(ElseBlockExists ElseBlockExists) { visit(); }
    public void visit(RelopLe RelopLe) { visit(); }
    public void visit(RelopLt RelopLt) { visit(); }
    public void visit(RelopGe RelopGe) { visit(); }
    public void visit(RelopGt RelopGt) { visit(); }
    public void visit(RelopNe RelopNe) { visit(); }
    public void visit(RelopEq RelopEq) { visit(); }
    public void visit(CondFactRelop CondFactRelop) { visit(); }
    public void visit(CondFactSimple CondFactSimple) { visit(); }
    public void visit(CondFactListSimple CondFactListSimple) { visit(); }
    public void visit(CondFactListAnd CondFactListAnd) { visit(); }
    public void visit(CondTerm CondTerm) { visit(); }
    public void visit(CondTermListSimple CondTermListSimple) { visit(); }
    public void visit(CondTermListOr CondTermListOr) { visit(); }
    public void visit(ConditionError ConditionError) { visit(); }
    public void visit(ConditionValid ConditionValid) { visit(); }
    public void visit(ExprMapArrName ExprMapArrName) { visit(); }
    public void visit(ExprMapMethodName ExprMapMethodName) { visit(); }
    public void visit(SetopUnion SetopUnion) { visit(); }
    public void visit(AddopMinus AddopMinus) { visit(); }
    public void visit(AddopPlus AddopPlus) { visit(); }
    public void visit(MulopMod MulopMod) { visit(); }
    public void visit(MulopDiv MulopDiv) { visit(); }
    public void visit(MulopMul MulopMul) { visit(); }
    public void visit(ConstructorCallType ConstructorCallType) { visit(); }
    public void visit(ConstructorCall ConstructorCall) { visit(); }
    public void visit(ActPar ActPar) { visit(); }
    public void visit(ActParListSimple ActParListSimple) { visit(); }
    public void visit(ActParListComma ActParListComma) { visit(); }
    public void visit(ActParsEpsilon ActParsEpsilon) { visit(); }
    public void visit(ActParsExist ActParsExist) { visit(); }
    public void visit(FuncCallInstance FuncCallInstance) { visit(); }
    public void visit(FuncCallMethod FuncCallMethod) { visit(); }
    public void visit(FactorGrouped FactorGrouped) { visit(); }
    public void visit(FactorInstanceAlloc FactorInstanceAlloc) { visit(); }
    public void visit(FactorArrAlloc FactorArrAlloc) { visit(); }
    public void visit(FactorBool FactorBool) { visit(); }
    public void visit(FactorChar FactorChar) { visit(); }
    public void visit(FactorNumber FactorNumber) { visit(); }
    public void visit(FactorFuncCall FactorFuncCall) { visit(); }
    public void visit(FactorDesignator FactorDesignator) { visit(); }
    public void visit(SignedFactorNeg SignedFactorNeg) { visit(); }
    public void visit(SignedFactorPos SignedFactorPos) { visit(); }
    public void visit(FactorListSimple FactorListSimple) { visit(); }
    public void visit(FactorListMulop FactorListMulop) { visit(); }
    public void visit(Term Term) { visit(); }
    public void visit(TermListSimple TermListSimple) { visit(); }
    public void visit(TermListAddop TermListAddop) { visit(); }
    public void visit(ExprMap ExprMap) { visit(); }
    public void visit(ExprArithm ExprArithm) { visit(); }
    public void visit(AssignValueSetSrc AssignValueSetSrc) { visit(); }
    public void visit(AssignValueError AssignValueError) { visit(); }
    public void visit(AssignValueSet AssignValueSet) { visit(); }
    public void visit(AssignValueExpr AssignValueExpr) { visit(); }
    public void visit(Assignop Assignop) { visit(); }
    public void visit(InstanceMemberArrName InstanceMemberArrName) { visit(); }
    public void visit(MemberArrName MemberArrName) { visit(); }
    public void visit(SelectorListMemberArrSimple SelectorListMemberArrSimple) { visit(); }
    public void visit(SelectorListMemberSimple SelectorListMemberSimple) { visit(); }
    public void visit(SelectorListMemberArr SelectorListMemberArr) { visit(); }
    public void visit(SelectorListMember SelectorListMember) { visit(); }
    public void visit(DesignatorBaseInstanceArr DesignatorBaseInstanceArr) { visit(); }
    public void visit(DesignatorBaseInstance DesignatorBaseInstance) { visit(); }
    public void visit(DesignatorArrName DesignatorArrName) { visit(); }
    public void visit(DesignatorMemberSelectorArr DesignatorMemberSelectorArr) { visit(); }
    public void visit(DesignatorMemberSelector DesignatorMemberSelector) { visit(); }
    public void visit(DesignatorArrSelector DesignatorArrSelector) { visit(); }
    public void visit(DesignatorSimple DesignatorSimple) { visit(); }
    public void visit(AssignDesignator AssignDesignator) { visit(); }
    public void visit(DesignatorStatementDec DesignatorStatementDec) { visit(); }
    public void visit(DesignatorStatementInc DesignatorStatementInc) { visit(); }
    public void visit(DesignatorStatementFuncCall DesignatorStatementFuncCall) { visit(); }
    public void visit(DesignatorStatementAssign DesignatorStatementAssign) { visit(); }
    public void visit(StatementBlock StatementBlock) { visit(); }
    public void visit(StatementPrint StatementPrint) { visit(); }
    public void visit(StatementRead StatementRead) { visit(); }
    public void visit(StatementReturn StatementReturn) { visit(); }
    public void visit(StatementContinue StatementContinue) { visit(); }
    public void visit(StatementBreak StatementBreak) { visit(); }
    public void visit(StatementDoWhile StatementDoWhile) { visit(); }
    public void visit(StatementIfElse StatementIfElse) { visit(); }
    public void visit(StatementDesignator StatementDesignator) { visit(); }
    public void visit(StatementListEpsilon StatementListEpsilon) { visit(); }
    public void visit(StatementListExists StatementListExists) { visit(); }
    public void visit(FormParListEpsilon FormParListEpsilon) { visit(); }
    public void visit(FormParListExists FormParListExists) { visit(); }
    public void visit(FormParError FormParError) { visit(); }
    public void visit(FormParValid FormParValid) { visit(); }
    public void visit(FormParsEpsilon FormParsEpsilon) { visit(); }
    public void visit(FormParsExist FormParsExist) { visit(); }
    public void visit(RetTypeVoid RetTypeVoid) { visit(); }
    public void visit(RetTypeNonVoid RetTypeNonVoid) { visit(); }
    public void visit(MethodRetTypeName MethodRetTypeName) { visit(); }
    public void visit(MethodSignature MethodSignature) { visit(); }
    public void visit(MethodDecl MethodDecl) { visit(); }
    public void visit(MethodDeclListEpsilon MethodDeclListEpsilon) { visit(); }
    public void visit(MethodDeclListExists MethodDeclListExists) { visit(); }
    public void visit(InterfaceMethodDeclSigEnd InterfaceMethodDeclSigEnd) { visit(); }
    public void visit(InterfaceMethodDeclListEpsilon InterfaceMethodDeclListEpsilon) { visit(); }
    public void visit(InterfaceMethodDeclListSig InterfaceMethodDeclListSig) { visit(); }
    public void visit(InterfaceMethodDeclListDecl InterfaceMethodDeclListDecl) { visit(); }
    public void visit(InterfaceDeclName InterfaceDeclName) { visit(); }
    public void visit(InterfaceDecl InterfaceDecl) { visit(); }
    public void visit(ConstructorDeclName ConstructorDeclName) { visit(); }
    public void visit(ConstructorSignature ConstructorSignature) { visit(); }
    public void visit(ConstructorDecl ConstructorDecl) { visit(); }
    public void visit(ClassMethodDeclListEpsilon ClassMethodDeclListEpsilon) { visit(); }
    public void visit(ClassMethodDeclListConstructor ClassMethodDeclListConstructor) { visit(); }
    public void visit(ClassMethodDeclListMethod ClassMethodDeclListMethod) { visit(); }
    public void visit(ClassMethodBlockEpsilon ClassMethodBlockEpsilon) { visit(); }
    public void visit(ClassMethodBlockExists ClassMethodBlockExists) { visit(); }
    public void visit(VarDeclListEpsilon VarDeclListEpsilon) { visit(); }
    public void visit(VarDeclListExists VarDeclListExists) { visit(); }
    public void visit(ExtendsClauseEpsilon ExtendsClauseEpsilon) { visit(); }
    public void visit(ExtendsClauseExists ExtendsClauseExists) { visit(); }
    public void visit(ClassDeclName ClassDeclName) { visit(); }
    public void visit(ClassDecl ClassDecl) { visit(); }
    public void visit(VarListEpsilon VarListEpsilon) { visit(); }
    public void visit(VarListExists VarListExists) { visit(); }
    public void visit(ArrDeclBracketsEpsilon ArrDeclBracketsEpsilon) { visit(); }
    public void visit(ArrDeclBracketsExist ArrDeclBracketsExist) { visit(); }
    public void visit(VarError VarError) { visit(); }
    public void visit(VarValid VarValid) { visit(); }
    public void visit(VarDecl VarDecl) { visit(); }
    public void visit(ConstInitListEpsilon ConstInitListEpsilon) { visit(); }
    public void visit(ConstInitListExists ConstInitListExists) { visit(); }
    public void visit(LiteralBool LiteralBool) { visit(); }
    public void visit(LiteralChar LiteralChar) { visit(); }
    public void visit(LiteralNumberNeg LiteralNumberNeg) { visit(); }
    public void visit(LiteralNumberPos LiteralNumberPos) { visit(); }
    public void visit(Type Type) { visit(); }
    public void visit(ConstInitError ConstInitError) { visit(); }
    public void visit(ConstInitValid ConstInitValid) { visit(); }
    public void visit(ConstDecl ConstDecl) { visit(); }
    public void visit(DeclError DeclError) { visit(); }
    public void visit(DeclIface DeclIface) { visit(); }
    public void visit(DeclClass DeclClass) { visit(); }
    public void visit(DeclVar DeclVar) { visit(); }
    public void visit(DeclConst DeclConst) { visit(); }
    public void visit(DeclListEpsilon DeclListEpsilon) { visit(); }
    public void visit(DeclListExists DeclListExists) { visit(); }
    public void visit(ProgramName ProgramName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}

package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class MJSemanticAnalyzer extends VisitorAdaptor {

	/* Utilities */

	private boolean errorDetected = false;
	private Logger log = Logger.getLogger(getClass());

	public boolean isErrorDetected() {
		return errorDetected;
	}

	private void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" at line ").append(line);
		log.info(msg.toString());
	}

	private void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("Semantic error");
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" at line ").append(line);
		msg.append(": ").append(message);
		log.error(msg.toString());
	}

	private void report_fatal_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("Fatal error");
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" at line ").append(line);
		msg.append(": ").append(message);
		log.error(msg.toString());
	}

	private String objToString(Obj obj) {
		int objKind = obj.getKind();
		String message = "[";

		// kind
		switch (objKind) {
		case Obj.Con:
			message += "Con";
			break;
		case Obj.Var:
			message += "Var";
			break;
		case Obj.Type:
			message += "Type";
			break;
		case Obj.Meth:
			message += "Meth";
			break;
		case Obj.Fld:
			message += "Fld";
			break;
		case Obj.Elem:
			message += "Elem";
			break;
		case Obj.Prog:
			message += "Prog";
			break;
		}
		message += " ";

		// name
		message += obj.getName() + ": ";

		// type
		Struct objType = obj.getType();
		if (objType.getKind() == SpecStruct.Array) {
			message += "Arr of ";
			objType = objType.getElemType();
		}
		if (objType == Tab.noType) {
			message += "void";
		} else if (objType == Tab.intType) {
			message += "int";
		} else if (objType == Tab.charType) {
			message += "char";
		} else if (objType == Tab.nullType) {
			message += "null";
		} else if (objType == boolType) {
			message += "bool";
		} else if (objType == setType) {
			message += "set";
		}
		message += ", ";

		// adr
		message += obj.getAdr() + ", ";

		// level
		message += obj.getLevel() + ", ";

		// fpPos
		message += obj.getFpPos();

		// locals
		if (objKind == Obj.Meth) {
			message += ", locals: ";
			boolean first = true;
			for (Obj local : obj.getLocalSymbols()) {
				if (!first) {
					message += ", ";
				}
				message += objToString(local);
				first = false;
			}
		}

		message += "]";

		return message;
	}

	private void printObj(Obj obj, SyntaxNode syntaxNode) {
		int objKind = obj.getKind();
		String message = "Usage of ";

		switch (objKind) {
		case Obj.Con:
			message += "a symbolic constant";
			break;
		case Obj.Var:
			if (obj.getLevel() == 0) {
				message += "a global variable";
			} else if (obj.getLevel() == 1 && obj.getFpPos() == 0) {
				message += "a local variable";
			} else if (obj.getLevel() == 1 && obj.getFpPos() == 1) {
				message += "a formal parameter of a function";
			}
			break;
		case Obj.Meth:
			message += "a global function call";
			break;
		case Obj.Elem:
			message += "an array element access";
			break;
		}

		message += ": " + objToString(obj);

		report_info(message, syntaxNode);
	}

	/* Symbol Table Initialization */

	private static final Struct boolType = new SpecStruct(SpecStruct.Bool); // "Tab.boolType"
	private static final Struct setType = new SpecStruct(SpecStruct.Set); // "Tab.setType"

	static {
		Tab.init();

		Tab.insert(Obj.Type, "bool", boolType);
		Tab.insert(Obj.Type, "set", setType);

		String[] typeNames = { "int", "char", "bool", "set" };
		for (String typeName : typeNames) {
			Obj typeObj = Tab.find(typeName);
			typeObj.setAdr(0);
			typeObj.setLevel(-1);
		}

		String[] constNames = { "eol", "null" };
		for (String constName : constNames) {
			Obj constObj = Tab.find(constName);
			constObj.setLevel(-1);
		}

		String[] methodNames = { "chr", "ord", "len" };
		for (String methodName : methodNames) {
			Obj methodObj = Tab.find(methodName);
			Collection<Obj> locals = methodObj.getLocalSymbols();
			methodObj.setLevel(locals.size());
			for (Obj local : locals) {
				local.setLevel(1);
				local.setFpPos(1);
			}
		}

		Obj methodObj = Tab.insert(Obj.Meth, "add", Tab.noType);
		Tab.openScope();
		Obj varObj = Tab.insert(Obj.Var, "a", setType);
		varObj.setLevel(1);
		varObj.setFpPos(1);
		varObj = Tab.insert(Obj.Var, "b", Tab.intType);
		varObj.setLevel(1);
		varObj.setFpPos(1);
		varObj = Tab.insert(Obj.Var, "size", Tab.intType);
		varObj.setLevel(1);
		varObj = Tab.insert(Obj.Var, "i", Tab.intType);
		varObj.setLevel(1);
		Tab.chainLocalSymbols(methodObj);
		Tab.closeScope();
		methodObj.setLevel(2);

		methodObj = Tab.insert(Obj.Meth, "addAll", Tab.noType);
		Tab.openScope();
		varObj = Tab.insert(Obj.Var, "a", setType);
		varObj.setLevel(1);
		varObj.setFpPos(1);
		varObj = Tab.insert(Obj.Var, "b", new SpecStruct(SpecStruct.Array, Tab.intType));
		varObj.setLevel(1);
		varObj.setFpPos(1);
		varObj = Tab.insert(Obj.Var, "size", Tab.intType);
		varObj.setLevel(1);
		varObj = Tab.insert(Obj.Var, "i", Tab.intType);
		varObj.setLevel(1);
		varObj = Tab.insert(Obj.Var, "j", Tab.intType);
		varObj.setLevel(1);
		Tab.chainLocalSymbols(methodObj);
		Tab.closeScope();
		methodObj.setLevel(2);
	}

	/* Semantic Analysis */

	private Obj programObj = null;
	private boolean doesProgramHaveMain = false;
	private Struct currType = null;
	private int currLiteral = 0;
	private Struct currLiteralType = null;
	private boolean isCurrVarArray = false;
	private Struct currMethodRetType = null;
	private Obj currMethodObj = null;
	private int currMethodFormParsCnt = 0;
	private boolean doesCurrMethodHaveReturn = false;
	private int currLoopNestingLevel = 0;
	private List<Struct> currActParTypeList = new ArrayList<>();

	private int nVars = 0;

	public int getNVars() {
		return nVars;
	}

	@Override
	public void visit(Program program) {
		if (!doesProgramHaveMain) {
			report_error("program '" + programObj.getName() + "' does not contain a 'main' method", program);
		}
		nVars = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(programObj);
		Tab.closeScope();
	}

	@Override
	public void visit(ProgramName programName) {
		programObj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		programObj.setLevel(-1);
		Tab.openScope();
	}

	@Override
	public void visit(ConstDecl constDecl) {
		currType = null;
	}

	@Override
	public void visit(ConstInitValid constInitValid) {
		String constName = constInitValid.getConstName();
		Obj constObj = Tab.find(constName);
		if (constObj != Tab.noObj) {
			report_error("constant '" + constName + "' has already been declared in this scope", constInitValid);
		} else if (!currLiteralType.assignableTo(currType)) {
			report_error("constant '" + constName + "' has not been assigned a compatible literal", constInitValid);
		} else {
			Tab.insert(Obj.Con, constName, currType).setAdr(currLiteral);
		}
		currLiteral = 0;
		currLiteralType = null;
	}

	@Override
	public void visit(Type type) {
		String typeName = type.getTypeName();
		Obj typeObj = Tab.find(typeName);
		if (typeObj == Tab.noObj) {
			report_error("'" + typeName + "' is not a valid type", type);
		} else if (typeObj.getKind() != Obj.Type) {
			report_error("'" + typeName + "' is not a valid type", type);
		}
		currType = typeObj.getType();
		type.struct = currType;
	}

	@Override
	public void visit(LiteralNumberPos literalNumberPos) {
		currLiteral = literalNumberPos.getNumberLiteral();
		currLiteralType = Tab.intType;
	}

	@Override
	public void visit(LiteralNumberNeg literalNumberNeg) {
		currLiteral = -literalNumberNeg.getNumberLiteral();
		currLiteralType = Tab.intType;
	}

	@Override
	public void visit(LiteralChar literalChar) {
		currLiteral = literalChar.getCharLiteral();
		currLiteralType = Tab.charType;
	}

	@Override
	public void visit(LiteralBool literalBool) {
		Boolean boolLiteral = literalBool.getBoolLiteral();
		currLiteral = boolLiteral.equals(true) ? 1 : 0;
		currLiteralType = boolType;
	}

	@Override
	public void visit(VarDecl varDecl) {
		currType = null;
	}

	@Override
	public void visit(VarValid varValid) {
		String varName = varValid.getVarName();
		Obj varObj = Tab.noObj;
		if (currMethodObj == null) {
			// global variable
			varObj = Tab.find(varName); // returns Tab.noObj if not found
		} else {
			// local variable
			varObj = Tab.currentScope().findSymbol(varName); // returns null if not found
			varObj = varObj == null ? Tab.noObj : varObj;
		}
		if (varObj != Tab.noObj) {
			report_error("variable '" + varName + "' has already been declared in this scope", varValid);
		} else {
			Struct varType = isCurrVarArray ? new SpecStruct(SpecStruct.Array, currType) : currType;
			Tab.insert(Obj.Var, varName, varType);
		}
		isCurrVarArray = false;
	}

	@Override
	public void visit(ArrDeclBracketsExist arrDeclBrackets) {
		isCurrVarArray = true;
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		if (currMethodRetType != Tab.noType && !doesCurrMethodHaveReturn) {
			report_error("method '" + currMethodObj.getName() + "' does not contain a return statement", methodDecl);
		}
		Tab.chainLocalSymbols(currMethodObj);
		Tab.closeScope();
		currMethodObj = null;
		currMethodRetType = null;
		currMethodFormParsCnt = 0;
		doesCurrMethodHaveReturn = false;
	}

	@Override
	public void visit(MethodSignature methodSignature) {
		if (currMethodObj.getName().equals("main") && currMethodRetType == Tab.noType && currMethodFormParsCnt == 0) {
			doesProgramHaveMain = true;
		}
		currMethodObj.setLevel(currMethodFormParsCnt);
	}

	@Override
	public void visit(MethodRetTypeName methodRetTypeName) {
		String methodName = methodRetTypeName.getMethodName();
		Obj methodObj = Tab.find(methodName);
		if (methodObj != Tab.noObj) {
			report_error("method '" + methodName + "' has already been declared", methodRetTypeName);
			currMethodObj = new Obj(Obj.Meth, methodName, currMethodRetType);
			methodRetTypeName.obj = Tab.noObj;
		} else {
			currMethodObj = Tab.insert(Obj.Meth, methodName, currMethodRetType);
			methodRetTypeName.obj = currMethodObj;
		}
		Tab.openScope();
	}

	@Override
	public void visit(RetTypeNonVoid retTypeNonVoid) {
		currMethodRetType = currType;
		currType = null;
	}

	@Override
	public void visit(RetTypeVoid retTypeVoid) {
		currMethodRetType = Tab.noType;
	}

	@Override
	public void visit(FormParValid formParValid) {
		String formParName = formParValid.getFormParName();
		Obj formParObj = Tab.currentScope().findSymbol(formParName); // returns null if not found
		if (formParObj != null) {
			report_error("formal parameter '" + formParName + "' has already been declared in this scope",
					formParValid);
		} else {
			Struct formParType = isCurrVarArray ? new SpecStruct(SpecStruct.Array, currType) : currType;
			Tab.insert(Obj.Var, formParName, formParType).setFpPos(1);
		}
		currMethodFormParsCnt++;
		isCurrVarArray = false;
		currType = null;
	}

	@Override
	public void visit(StatementDoWhile statementDoWhile) {
		currLoopNestingLevel--;
	}

	@Override
	public void visit(StatementBreak statementBreak) {
		if (currLoopNestingLevel == 0) {
			report_error("break statement is not allowed outside of do-while loop", statementBreak);
		}
	}

	@Override
	public void visit(StatementContinue statementContinue) {
		if (currLoopNestingLevel == 0) {
			report_error("continue statement is not allowed outside of do-while loop", statementContinue);
		}
	}

	@Override
	public void visit(StatementReturn statementReturn) {
		if (currMethodObj == null) {
			report_error("return statement is not allowed outside of method or global function", statementReturn);
		} else {
			doesCurrMethodHaveReturn = true;
		}
	}

	@Override
	public void visit(StatementRead statementRead) {
		Obj designatorObj = statementRead.getDesignator().obj;
		String designatorName = designatorObj.getName();
		int designatorKind = designatorObj.getKind();
		Struct designatorType = designatorObj.getType();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem) {
			report_error("designator '" + designatorName + "' is not valid for read statement", statementRead);
		} else if (designatorType != Tab.intType && designatorType != Tab.charType && designatorType != boolType) {
			report_error("designator '" + designatorName + "' is not of a valid type for read statement",
					statementRead);
		}
	}

	@Override
	public void visit(StatementPrint statementPrint) {
		Struct exprType = statementPrint.getExpr().struct;
		if (exprType != Tab.intType && exprType != Tab.charType && exprType != boolType && exprType != setType) {
			report_error("expression is not of a valid type for print statement", statementPrint);
		}
	}

	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		Obj designatorObj = designatorStatementAssign.getAssignDesignator().getDesignator().obj;
		String designatorName = designatorObj.getName();
		int designatorKind = designatorObj.getKind();
		Struct designatorType = designatorObj.getType();
		Struct assignValueType = designatorStatementAssign.getAssignValue().struct;
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem) {
			report_error("designator '" + designatorName + "' is not valid for assignment", designatorStatementAssign);
		} else if (!assignValueType.assignableTo(designatorType)) {
			report_error("assign value cannot be assigned to designator '" + designatorName + "'",
					designatorStatementAssign);
		}
	}

	@Override
	public void visit(DesignatorStatementFuncCall designatorStatementFuncCall) {
		Obj designatorObj = designatorStatementFuncCall.getDesignator().obj;
		String designatorName = designatorObj.getName();
		if (designatorObj.getKind() != Obj.Meth) {
			report_error("designator '" + designatorName + "' is not valid for function call",
					designatorStatementFuncCall);
		} else {
			List<Struct> formParTypeList = new ArrayList<>();
			for (Obj local : designatorObj.getLocalSymbols()) {
				if (local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					formParTypeList.add(local.getType());
				}
			}
			try {
				if (formParTypeList.size() != currActParTypeList.size()) {
					throw new Exception("number of actual parameters in method '" + designatorName
							+ "' must match number of its formal parameters");
				}
				for (int i = 0; i < formParTypeList.size(); i++) {
					if (!currActParTypeList.get(i).assignableTo(formParTypeList.get(i))) {
						throw new Exception("actual parameter types in method '" + designatorName
								+ "' must be assignable to corresponding formal parameter types");
					}
				}
			} catch (Exception e) {
				report_error(e.getMessage(), designatorStatementFuncCall);
			}
		}
		currActParTypeList.clear();
	}

	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		Obj designatorObj = designatorStatementInc.getDesignator().obj;
		String designatorName = designatorObj.getName();
		int designatorKind = designatorObj.getKind();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem) {
			report_error("designator '" + designatorName + "' is not valid for increment", designatorStatementInc);
		} else if (!designatorObj.getType().equals(Tab.intType)) {
			report_error("designator '" + designatorName + "' is not of a valid type for increment",
					designatorStatementInc);
		}
	}

	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		Obj designatorObj = designatorStatementDec.getDesignator().obj;
		String designatorName = designatorObj.getName();
		int designatorKind = designatorObj.getKind();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem) {
			report_error("designator '" + designatorName + "' is not valid for decrement", designatorStatementDec);
		} else if (!designatorObj.getType().equals(Tab.intType)) {
			report_error("designator '" + designatorName + "' is not of a valid type for decrement",
					designatorStatementDec);
		}
	}

	@Override
	public void visit(AssignValueExpr assignValueExpr) {
		assignValueExpr.struct = assignValueExpr.getExpr().struct;
	}

	@Override
	public void visit(AssignValueSet assignValueSet) {
		Struct leftDesignatorType = assignValueSet.getDesignator().obj.getType();
		Struct rightDesignatorType = assignValueSet.getDesignator1().obj.getType();
		if (leftDesignatorType != setType || rightDesignatorType != setType) {
			report_error("operands of set operation must be of type set", assignValueSet);
			assignValueSet.struct = Tab.noType;
		} else {
			assignValueSet.struct = setType;
		}
	}

	@Override
	public void visit(DesignatorSimple designatorSimple) {
		String designatorName = designatorSimple.getDesignatorName();
		Obj designatorObj = Tab.find(designatorName);
		int designatorKind = designatorObj.getKind();
		if (designatorObj.equals(Tab.noObj)) {
			report_error("designator '" + designatorName + "' must be declared before use", designatorSimple);
			designatorSimple.obj = Tab.noObj;
		} else if (designatorKind != Obj.Con && designatorKind != Obj.Var && designatorKind != Obj.Meth) {
			report_error("designator '" + designatorName + "' is not valid for this use", designatorSimple);
			designatorSimple.obj = Tab.noObj;
		} else {
			designatorSimple.obj = designatorObj;
		}
		// printObj(designator.obj, designator);
	}

	@Override
	public void visit(DesignatorArr designatorArr) {
		String designatorName = designatorArr.getDesignatorArrName().getDesignatorName();
		Obj designatorObj = Tab.find(designatorName);
		int designatorKind = designatorObj.getKind();
		Struct designatorType = designatorObj.getType();
		if (designatorObj.equals(Tab.noObj)) {
			report_error("designator '" + designatorName + "' must be declared before use", designatorArr);
			designatorArr.obj = Tab.noObj;
		} else if (designatorKind != Obj.Var || designatorType.getKind() != SpecStruct.Array) {
			report_error("designator '" + designatorName + "' is not valid for this use", designatorArr);
			designatorArr.obj = Tab.noObj;
		} else if (designatorArr.getExpr().struct.equals(Tab.noType)) {
			report_error("array index must be of type int", designatorArr);
			designatorArr.obj = Tab.noObj;
		} else {
			designatorArr.obj = new Obj(Obj.Elem, designatorName + "[#]", designatorType.getElemType());
		}
		// printObj(designator.obj, designator);
	}

	@Override
	public void visit(DesignatorArrName designatorArrName) {
		designatorArrName.obj = Tab.find(designatorArrName.getDesignatorName());
	}

	@Override
	public void visit(ExprArithm exprArithm) {
		exprArithm.struct = exprArithm.getTermList().struct;
	}

	@Override
	public void visit(ExprMap exprMap) {
		Struct methodType = exprMap.getExprMapMethodName().struct;
		Struct arrType = exprMap.getExprMapArrName().struct;
		if (methodType.equals(Tab.noType) || arrType.equals(Tab.noType)) {
			exprMap.struct = Tab.noType;
		} else {
			exprMap.struct = Tab.intType;
		}
	}

	@Override
	public void visit(TermListAddop termListAddop) {
		Struct termListType = termListAddop.getTermList().struct;
		Struct termType = termListAddop.getTerm().struct;
		if (!termListType.equals(Tab.intType) || !termType.equals(Tab.intType)) {
			report_error("operands of additive operation must be of type int", termListAddop);
			termListAddop.struct = Tab.noType;
		} else {
			termListAddop.struct = Tab.intType;
		}
	}

	@Override
	public void visit(TermListSimple termListSimple) {
		termListSimple.struct = termListSimple.getTerm().struct;
	}

	@Override
	public void visit(Term term) {
		term.struct = term.getFactorList().struct;
	}

	@Override
	public void visit(FactorListMulop factorListMulop) {
		Struct factorListType = factorListMulop.getFactorList().struct;
		Struct signedFactorType = factorListMulop.getSignedFactor().struct;
		if (!factorListType.equals(Tab.intType) || !signedFactorType.equals(Tab.intType)) {
			report_error("operands of multiplicative operation must be of type int", factorListMulop);
			factorListMulop.struct = Tab.noType;
		} else {
			factorListMulop.struct = Tab.intType;
		}
	}

	@Override
	public void visit(FactorListSimple factorListSimple) {
		factorListSimple.struct = factorListSimple.getSignedFactor().struct;
	}

	@Override
	public void visit(SignedFactorPos signedFactorPos) {
		signedFactorPos.struct = signedFactorPos.getFactor().struct;
	}

	@Override
	public void visit(SignedFactorNeg signedFactorNeg) {
		if (!signedFactorNeg.getFactor().struct.equals(Tab.intType)) {
			report_error("unary minus cannot be applied to a non-int expression", signedFactorNeg);
			signedFactorNeg.struct = Tab.noType;
		} else {
			signedFactorNeg.struct = signedFactorNeg.getFactor().struct;
		}
	}

	@Override
	public void visit(FactorDesignator factorDesignator) {
		Obj designatorObj = factorDesignator.getDesignator().obj;
		int designatorKind = designatorObj.getKind();
		if (designatorKind != Obj.Con && designatorKind != Obj.Var && designatorKind != Obj.Elem) {
			report_error("designator '" + designatorObj.getName() + "' is not valid for this use", factorDesignator);
			factorDesignator.struct = Tab.noType;
		} else {
			factorDesignator.struct = designatorObj.getType();
		}
	}

	@Override
	public void visit(FactorFuncCall factorFuncCall) {
		Obj designatorObj = factorFuncCall.getDesignator().obj;
		String designatorName = designatorObj.getName();
		if (designatorObj.getKind() != Obj.Meth) {
			report_error("designator '" + designatorName + "' is not valid for function call", factorFuncCall);
			factorFuncCall.struct = Tab.noType;
		} else {
			List<Struct> formParTypeList = new ArrayList<>();
			for (Obj local : designatorObj.getLocalSymbols()) {
				if (local.getKind() == Obj.Var && local.getLevel() == 1 && local.getFpPos() == 1) {
					formParTypeList.add(local.getType());
				}
			}
			try {
				if (formParTypeList.size() != currActParTypeList.size()) {
					throw new Exception("number of actual parameters in method '" + designatorName
							+ "' must match number of its formal parameters");
				}
				for (int i = 0; i < formParTypeList.size(); i++) {
					if (!currActParTypeList.get(i).assignableTo(formParTypeList.get(i))) {
						throw new Exception("actual parameter types in method '" + designatorName
								+ "' must be assignable to corresponding formal parameter types");
					}
				}
				factorFuncCall.struct = designatorObj.getType();
			} catch (Exception e) {
				report_error(e.getMessage(), factorFuncCall);
				factorFuncCall.struct = Tab.noType;
			}
		}
		currActParTypeList.clear();
	}

	@Override
	public void visit(FactorNumber factorNumber) {
		factorNumber.struct = Tab.intType;
	}

	@Override
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}

	@Override
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType; // "Tab.boolType"
	}

	@Override
	public void visit(FactorArrAlloc factorArrAlloc) {
		if (!factorArrAlloc.getExpr().struct.equals(Tab.intType)) {
			report_error("array or set size expression must be of type int", factorArrAlloc);
			factorArrAlloc.struct = Tab.noType;
		} else {
			factorArrAlloc.struct = currType == setType ? setType : new SpecStruct(SpecStruct.Array, currType);
		}
		currType = null;
	}

	@Override
	public void visit(FactorGrouped factorGrouped) {
		factorGrouped.struct = factorGrouped.getExpr().struct;
	}

	@Override
	public void visit(ActPar actPar) {
		currActParTypeList.add(actPar.getExpr().struct);
	}

	@Override
	public void visit(MulopMul mulopMul) {
		mulopMul.string = mulopMul.getMul();
	}

	@Override
	public void visit(MulopDiv mulopDiv) {
		mulopDiv.string = mulopDiv.getDiv();
	}

	@Override
	public void visit(MulopMod mulopMod) {
		mulopMod.string = mulopMod.getMod();
	}

	@Override
	public void visit(AddopPlus addopPlus) {
		addopPlus.string = addopPlus.getPlus();
	}

	@Override
	public void visit(AddopMinus addopMinus) {
		addopMinus.string = addopMinus.getMinus();
	}
	
	@Override
	public void visit(SetopUnion setopUnion) {
		setopUnion.string = setopUnion.getUnion();
	}

	@Override
	public void visit(ExprMapMethodName exprMapMethodName) {
		Obj designatorObj = exprMapMethodName.getDesignator().obj;
		Struct designatorType = designatorObj.getType();
		int designatorKind = designatorObj.getKind();
		int designatorLevel = designatorObj.getLevel();
		if (designatorKind != Obj.Meth || designatorType != Tab.intType || designatorLevel != 1) {
			report_error("left operand of map operation must be function with one formal parameter of "
					+ "type int and return type of int", exprMapMethodName);
			exprMapMethodName.struct = Tab.noType;
		} else {
			for (Obj local : designatorObj.getLocalSymbols()) {
				if (local.getKind() == Obj.Var && local.getFpPos() == 1 && local.getType() != Tab.intType) {
					report_error("formal parameter of function on left operand must be of type int", exprMapMethodName);
					exprMapMethodName.struct = Tab.noType;
					break;
				} else {
					exprMapMethodName.struct = Tab.intType;
					break;
				}
			}
		}
	}

	@Override
	public void visit(ExprMapArrName exprMapArrName) {
		Struct designatorType = exprMapArrName.getDesignator().obj.getType();
		if (designatorType.getKind() != SpecStruct.Array || designatorType.getElemType() != Tab.intType) {
			report_error("right operand of map operation must be array of type int", exprMapArrName);
			exprMapArrName.struct = Tab.noType;
		} else {
			exprMapArrName.struct = Tab.intType;
		}
	}

	@Override
	public void visit(ConditionValid conditionValid) {
		conditionValid.struct = conditionValid.getCondTermList().struct;
	}

	@Override
	public void visit(ConditionError conditionError) {
		conditionError.struct = Tab.noType;
	}

	@Override
	public void visit(CondTermListOr condTermListOr) {
		Struct condTermListType = condTermListOr.getCondTermList().struct;
		Struct condTermType = condTermListOr.getCondTerm().struct;
		if (condTermListType != boolType || condTermType != boolType) {
			report_error("operands of logical OR operation must be of type bool", condTermListOr);
			condTermListOr.struct = Tab.noType;
		} else {
			condTermListOr.struct = boolType;
		}
	}

	@Override
	public void visit(CondTermListSimple condTermListSimple) {
		condTermListSimple.struct = condTermListSimple.getCondTerm().struct;
	}

	@Override
	public void visit(CondTerm condTerm) {
		condTerm.struct = condTerm.getCondFactList().struct;
	}

	@Override
	public void visit(CondFactListAnd condFactListAnd) {
		Struct condFactListType = condFactListAnd.getCondFactList().struct;
		Struct condFactType = condFactListAnd.getCondFact().struct;
		if (condFactListType != boolType || condFactType != boolType) {
			report_error("operands of logical AND operation must be of type bool", condFactListAnd);
			condFactListAnd.struct = Tab.noType;
		} else {
			condFactListAnd.struct = boolType;
		}
	}

	@Override
	public void visit(CondFactListSimple condFactListSimple) {
		condFactListSimple.struct = condFactListSimple.getCondFact().struct;
	}

	@Override
	public void visit(CondFactSimple condFactSimple) {
		if (!condFactSimple.getExpr().struct.equals(boolType)) {
			report_error("conditional expression must be of type bool", condFactSimple);
			condFactSimple.struct = Tab.noType;
		} else {
			condFactSimple.struct = boolType;
		}
	}

	@Override
	public void visit(CondFactRelop condFactRelop) {
		Struct leftExprType = condFactRelop.getExpr().struct;
		Struct rightExprType = condFactRelop.getExpr1().struct;
		String relop = condFactRelop.getRelop().string;
		if (!leftExprType.compatibleWith(rightExprType)) {
			report_error("operands of relational operation must have compatible types", condFactRelop);
			condFactRelop.struct = Tab.noType;
		} else if ((leftExprType.isRefType() || rightExprType.isRefType()) && !relop.equals("==")
				&& !relop.equals("!=")) {
			report_error("operator '" + relop + "' is not allowed for reference types", condFactRelop);
			condFactRelop.struct = Tab.noType;
		} else {
			condFactRelop.struct = boolType;
		}
	}

	@Override
	public void visit(RelopEq relopEq) {
		relopEq.string = relopEq.getEq();
	}

	@Override
	public void visit(RelopNe relopNe) {
		relopNe.string = relopNe.getNe();
	}

	@Override
	public void visit(RelopGt relopGt) {
		relopGt.string = relopGt.getGt();
	}

	@Override
	public void visit(RelopGe relopGe) {
		relopGe.string = relopGe.getGe();
	}

	@Override
	public void visit(RelopLt relopLt) {
		relopLt.string = relopLt.getLt();
	}

	@Override
	public void visit(RelopLe relopLe) {
		relopLe.string = relopLe.getLe();
	}

	@Override
	public void visit(DoWhileBegin doWhileBegin) {
		currLoopNestingLevel++;
	}

	@Override
	public void visit(DoWhileCondExists doWhileCondExists) {
		Struct condType = doWhileCondExists.getCondition().struct;
		if (condType != boolType) {
			report_error("condition in do-while loop must be of type bool", doWhileCondExists);
		}
	}

	@Override
	public void visit(RetValExists retValExists) {
		if (currMethodRetType == Tab.noType) {
			report_error("return statement in a void method or global function must not return a value", retValExists);
		} else if (!currMethodRetType.equals(retValExists.getExpr().struct)) {
			report_error("expression value is not compatible with the return type"
					+ " of the current method or global function", retValExists);
		}
	}

	@Override
	public void visit(RetValEpsilon retValEpsilon) {
		if (currMethodRetType != Tab.noType) {
			report_error("return statement in a non-void method or global function must return a value", retValEpsilon);
		}
	}

}

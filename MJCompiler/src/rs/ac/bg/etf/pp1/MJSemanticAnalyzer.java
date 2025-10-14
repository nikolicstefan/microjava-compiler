package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

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
		if (objType.getKind() == Struct.Array) {
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
			for (Obj localObj : obj.getLocalSymbols()) {
				if (!first) {
					message += ", ";
				}
				message += objToString(localObj);
				if (first) {
					first = false;
				}
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
			} else if (obj.getLevel() == 1 && obj.getFpPos() > 0) {
				message += "a formal parameter of a function";
			}
			break;
		case Obj.Meth:
			if (obj.getFpPos() == 0) {
				message += "a global function call";
			} else if (obj.getFpPos() > 0) {
				message += "a class or interface member method call";
			}
			break;
		case Obj.Elem:
			message += "an array element access";
			break;
		}

		message += ": " + objToString(obj);

		report_info(message, syntaxNode);
	}

	/* Symbol Table Initialization */

	private static final int Set = 8;
	private static final Struct boolType = new Struct(Struct.Bool); // "Tab.boolType"
	private static final Struct setType = new Struct(Set); // "Tab.setType"

	private static boolean isRefType(Struct type) {
		return type.isRefType() || type.getKind() == Struct.Interface || type.getKind() == Set;
	}

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
			Collection<Obj> localObjList = methodObj.getLocalSymbols();
			methodObj.setLevel(localObjList.size());
			for (Obj localObj : localObjList) {
				localObj.setLevel(1);
				localObj.setFpPos(1);
			}
		}

		Obj methodObj = Tab.insert(Obj.Meth, "add", Tab.noType);
		Tab.openScope();
		Obj varObj = Tab.insert(Obj.Var, "a", setType);
		varObj.setLevel(1);
		varObj.setFpPos(1);
		varObj = Tab.insert(Obj.Var, "b", Tab.intType);
		varObj.setLevel(1);
		varObj.setFpPos(2);
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
		varObj = Tab.insert(Obj.Var, "b", new Struct(Struct.Array, Tab.intType));
		varObj.setLevel(1);
		varObj.setFpPos(2);
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
	private Obj currClassObj = null;
	private String currDesignatorName = "";
	private Stack<String> prevDesignatorNameStack = new Stack<>();
	private Struct currMethodRetType = null;
	private Obj currMethodObj = null;
	private int currMethodFormParsCnt = 0;
	private boolean isOverridingCurrMethod = false;
	private boolean doesCurrMethodHaveReturn = false;
	private Obj currInterfaceObj = null;
	private Obj currInstanceObj = null;
	private Obj currMemberObj = null;
	private Stack<Obj> prevMemberObjStack = new Stack<>();
	private int currLoopNestingLevel = 0;
	private List<Struct> currActParTypeList = new ArrayList<>();
	private List<Struct> currOverrideFormParTypeList = new ArrayList<>();

	private int globalVarsCnt = 0;

	public int getGlobalVarsCnt() {
		return globalVarsCnt;
	}

	@Override
	public void visit(Program program) {
		if (!doesProgramHaveMain) {
			report_error("program '" + programObj.getName() + "' does not contain a 'main' method", program);
		}
		globalVarsCnt = Tab.currentScope().getnVars();
		Tab.chainLocalSymbols(programObj);
		Tab.closeScope();
	}

	@Override
	public void visit(ProgramName programName) {
		programObj = Tab.insert(Obj.Prog, programName.getProgramName(), Tab.noType);
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
		if (currClassObj != null && varName.equals(currClassObj.getName())) {
			report_error("field name cannot match class name '" + varName + "'", varValid);
		} else {
			if (currClassObj == null && currMethodObj == null) {
				// global variable
				varObj = Tab.find(varName); // returns Tab.noObj if not found
			} else {
				// local variable or field
				varObj = Tab.currentScope().findSymbol(varName); // returns null if not found
				varObj = varObj == null ? Tab.noObj : varObj;
			}
			if (varObj != Tab.noObj) {
				report_error("variable '" + varName + "' has already been declared in this scope", varValid);
			} else {
				Struct varType = isCurrVarArray ? new Struct(Struct.Array, currType) : currType;
				if (currClassObj != null && currMethodObj == null) {
					// field
					Tab.insert(Obj.Fld, varName, varType);
				} else {
					// variable
					varObj = Tab.insert(Obj.Var, varName, varType);
					if ((currClassObj != null || currInterfaceObj != null) && currMethodObj != null) {
						varObj.setLevel(2);
					}
				}
			}
		}
		isCurrVarArray = false;
	}

	@Override
	public void visit(ArrDeclBracketsExist arrDeclBrackets) {
		isCurrVarArray = true;
	}

	@Override
	public void visit(ClassDecl classDecl) {
		String currClassName = currClassObj.getName();
		Struct currClassType = currClassObj.getType();
		for (Obj memberObj : Tab.currentScope().getLocals().symbols()) {
			int memberKind = memberObj.getKind();
			int memberFpPos = memberObj.getFpPos();
			if (memberKind == Obj.Meth && memberFpPos == 3) {
				String methodName = memberObj.getName();
				report_error("class '" + currClassName + "' does not implement method '" + methodName
						+ "' of super-interface", classDecl);
			}
			if (memberKind == Obj.Meth && memberFpPos == 2) {
				memberObj.setFpPos(1);
			}
		}
		Tab.chainLocalSymbols(currClassType);
		Tab.closeScope();
		currClassObj = null;
	}

	@Override
	public void visit(ClassDeclName classDeclName) {
		String className = classDeclName.getClassName();
		Obj classObj = Tab.find(className);
		Struct classType = new Struct(Struct.Class);
		if (classObj != Tab.noObj) {
			report_error("class '" + className + "' has already been declared", classDeclName);
			currClassObj = new Obj(Obj.Type, className, classType);
			classDeclName.obj = Tab.noObj;
		} else {
			currClassObj = Tab.insert(Obj.Type, className, classType);
			classDeclName.obj = currClassObj;
		}
		Tab.openScope();
		Tab.insert(Obj.Fld, "vtable", Tab.intType);
	}

	@Override
	public void visit(ExtendsClauseExists extendsClauseExists) {
		Type type = extendsClauseExists.getType();
		Struct currSuperclassType = type.struct;
		int currSuperclassTypeKind = currSuperclassType.getKind();
		String currClassName = currClassObj.getName();
		if (currSuperclassTypeKind != Struct.Class && currSuperclassTypeKind != Struct.Interface) {
			report_error("class '" + currClassName + "' can only extend another class or interface",
					extendsClauseExists);
		} else {
			String currSuperclassName = type.getTypeName();
			Struct currClassType = currClassObj.getType();
			currClassType.setElementType(currSuperclassType);
			for (Obj memberObj : currSuperclassType.getMembers()) {
				String memberName = memberObj.getName();
				if (!memberName.equals(currSuperclassName) && !memberName.equals("vtable")) {
					Tab.currentScope().addToLocals(memberObj);
				}
				if (memberObj.getKind() == Obj.Meth && memberObj.getFpPos() != 3) {
					memberObj.setFpPos(2); // non-overriden inherited member method
				}
			}
		}
		currType = null;
	}

	@Override
	public void visit(ConstructorDecl constructorDecl) {
		currMethodObj.setFpPos(0); // constructor
		Tab.chainLocalSymbols(currMethodObj);
		Tab.closeScope();
		currMethodObj = null;
		currMethodFormParsCnt = 0;
	}

	@Override
	public void visit(ConstructorSignature constructorSignature) {
		currMethodObj.setLevel(currMethodFormParsCnt);
	}

	@Override
	public void visit(ConstructorDeclName constructorDeclName) {
		String constructorName = constructorDeclName.getConstructorName();
		String currClassName = currClassObj.getName();
		Obj newConstructorObj = new Obj(Obj.Meth, constructorName, Tab.noType);
		if (!constructorName.equals(currClassName)) {
			report_error("constructor name mismatch for class'" + currClassName + "'", constructorDeclName);
			currMethodObj = newConstructorObj;
			constructorDeclName.obj = Tab.noObj;
		} else {
			Obj constructorObj = Tab.currentScope().findSymbol(constructorName); // returns null if not found
			if (constructorObj != null) {
				report_error("constructor '" + constructorName + "' has already been declared", constructorDeclName);
				currMethodObj = newConstructorObj;
				constructorDeclName.obj = Tab.noObj;
			} else {
				currMethodObj = Tab.insert(Obj.Meth, constructorName, Tab.noType);
				constructorDeclName.obj = currMethodObj;
			}
		}
		Tab.openScope();
		Struct formParType = currClassObj.getType();
		Obj formParObj = Tab.insert(Obj.Var, "this", formParType);
		formParObj.setLevel(2);
		formParObj.setFpPos(++currMethodFormParsCnt);
	}

	@Override
	public void visit(InterfaceDecl interfaceDecl) {
		Struct currInterfaceType = currInterfaceObj.getType();
		Tab.chainLocalSymbols(currInterfaceType);
		Tab.closeScope();
		currInterfaceObj = null;
	}

	@Override
	public void visit(InterfaceDeclName interfaceDeclName) {
		String interfaceName = interfaceDeclName.getInterfaceName();
		Obj interfaceObj = Tab.find(interfaceName);
		Struct interfaceType = new Struct(Struct.Interface);
		if (interfaceObj != Tab.noObj) {
			report_error("interface '" + interfaceName + "' has already been declared", interfaceDeclName);
			currInterfaceObj = new Obj(Obj.Type, interfaceName, interfaceType);
		} else {
			currInterfaceObj = Tab.insert(Obj.Type, interfaceName, interfaceType);
		}
		Tab.openScope();
	}

	@Override
	public void visit(InterfaceMethodDeclSigEnd interfaceMethodDeclSigEnd) {
		currMethodObj.setFpPos(3); // not implemented interface member method
		Tab.chainLocalSymbols(currMethodObj);
		Tab.closeScope();
		currMethodObj = null;
		currMethodRetType = null;
		currMethodFormParsCnt = 0;
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		String currMethodName = currMethodObj.getName();
		if (currMethodRetType != Tab.noType && !doesCurrMethodHaveReturn) {
			report_error("method '" + currMethodName + "' does not contain a return statement", methodDecl);
		}
		if (currClassObj != null || currInterfaceObj != null) {
			currMethodObj.setFpPos(1); // class or interface member method
		}
		if (isOverridingCurrMethod) {
			Scope outerScope = Tab.currentScope().getOuter();
			SymbolDataStructure outerLocals = outerScope.getLocals();
			Obj inheritedMethodObj = outerLocals.searchKey(currMethodName);
			List<Struct> inheritedFormParTypeList = new ArrayList<>();
			for (Obj localObj : inheritedMethodObj.getLocalSymbols()) {
				if (localObj.getKind() == Obj.Var && localObj.getFpPos() > 1) {
					inheritedFormParTypeList.add(localObj.getType());
				}
			}
			try {
				String currClassName = currClassObj.getName();
				if (currOverrideFormParTypeList.size() != inheritedFormParTypeList.size()) {
					throw new Exception("number of formal parameters of overriden method '" + currMethodName
							+ "' in class '" + currClassName + "' must match the number of formal parameters "
							+ "of same-named method in superclass");
				}
				for (int i = 0; i < inheritedFormParTypeList.size(); i++) {
					if (!currOverrideFormParTypeList.get(i).equals(inheritedFormParTypeList.get(i))) {
						throw new Exception("formal parameter types of overriden method '" + currMethodName
								+ "' in class '" + currClassName + "must match the formal parameter types "
								+ "of same-named method in superclass");
					}
				}
				if (methodDecl.getMethodSignature().getMethodRetTypeName().obj != Tab.noObj) {
					if (inheritedMethodObj.getFpPos() == 2) {
						inheritedMethodObj.setFpPos(1);
					}
					outerLocals.deleteKey(currMethodName);
					outerScope.addToLocals(currMethodObj);
				}
			} catch (Exception e) {
				report_error(e.getMessage(), methodDecl);
			}
		}
		Tab.chainLocalSymbols(currMethodObj);
		Tab.closeScope();
		currMethodObj = null;
		currMethodRetType = null;
		currMethodFormParsCnt = 0;
		doesCurrMethodHaveReturn = false;
		isOverridingCurrMethod = false;
		currOverrideFormParTypeList.clear();
	}

	@Override
	public void visit(MethodSignature methodSignature) {
		if (currClassObj == null && currInterfaceObj == null && currMethodObj.getName().equals("main")
				&& currMethodRetType == Tab.noType && currMethodFormParsCnt == 0) {
			doesProgramHaveMain = true;
		}
		currMethodObj.setLevel(currMethodFormParsCnt);
	}

	@Override
	public void visit(MethodRetTypeName methodRetTypeName) {
		String methodName = methodRetTypeName.getMethodName();
		Obj methodObj = Tab.noObj;
		Obj newMethodObj = new Obj(Obj.Meth, methodName, currMethodRetType);
		if (currClassObj != null && methodName.equals(currClassObj.getName())) {
			report_error("method name cannot match class name '" + methodName + "'", methodRetTypeName);
			currMethodObj = newMethodObj;
			methodRetTypeName.obj = Tab.noObj;
		} else if (currInterfaceObj != null && methodName.equals(currInterfaceObj.getName())) {
			report_error("method name cannot match interface name '" + methodName + "'", methodRetTypeName);
			currMethodObj = newMethodObj;
			methodRetTypeName.obj = Tab.noObj;
		} else {
			if (currClassObj == null && currInterfaceObj == null) {
				// global method
				methodObj = Tab.find(methodName); // returns Tab.noObj if not found
			} else {
				// class or interface member method
				methodObj = Tab.currentScope().findSymbol(methodName); // returns null if not found
				methodObj = methodObj == null ? Tab.noObj : methodObj;
			}
			if (currClassObj != null && methodObj != Tab.noObj && methodObj.getKind() == Obj.Meth) {
				if (methodObj.getFpPos() < 2) {
					report_error("method '" + methodName + "' has already been declared", methodRetTypeName);
					methodRetTypeName.obj = Tab.noObj;
				} else {
					if (!methodObj.getType().equals(currMethodRetType)) {
						report_error(
								"return type of overridden method '" + methodName + "' in class '"
										+ currClassObj.getName()
										+ "' must match the return type of same-named method in superclass",
								methodRetTypeName);
						methodRetTypeName.obj = Tab.noObj;
					} else {
						methodRetTypeName.obj = newMethodObj;
					}
					currMethodObj = newMethodObj;
					isOverridingCurrMethod = true;
				}
			} else {
				if (methodObj != Tab.noObj) {
					report_error("method '" + methodName + "' has already been declared", methodRetTypeName);
					currMethodObj = newMethodObj;
					methodRetTypeName.obj = Tab.noObj;
				} else {
					currMethodObj = Tab.insert(Obj.Meth, methodName, currMethodRetType);
					methodRetTypeName.obj = currMethodObj;
				}
			}
		}
		Tab.openScope();
		if (currClassObj != null) {
			Obj formParObj = Tab.insert(Obj.Var, "this", currClassObj.getType());
			formParObj.setLevel(2);
			formParObj.setFpPos(++currMethodFormParsCnt);
		}
		if (currInterfaceObj != null) {
			Obj formParObj = Tab.insert(Obj.Var, "this", currInterfaceObj.getType());
			formParObj.setLevel(2);
			formParObj.setFpPos(++currMethodFormParsCnt);
		}
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
			Struct formParType = isCurrVarArray ? new Struct(Struct.Array, currType) : currType;
			formParObj = Tab.insert(Obj.Var, formParName, formParType);
			if (currClassObj != null || currInterfaceObj != null) {
				formParObj.setLevel(2);
			}
			formParObj.setFpPos(++currMethodFormParsCnt);
			if (isOverridingCurrMethod) {
				currOverrideFormParTypeList.add(formParType);
			}
		}
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
		int designatorKind = designatorObj.getKind();
		Struct designatorType = designatorObj.getType();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem && designatorKind != Obj.Fld) {
			report_error("designator '" + currDesignatorName + "' is not valid for read statement", statementRead);
		} else if (designatorType != Tab.intType && designatorType != Tab.charType && designatorType != boolType) {
			report_error("designator '" + currDesignatorName + "' is not of a valid type for read statement",
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
		Obj assignDesignatorObj = designatorStatementAssign.getAssignDesignator().getDesignator().obj;
		String assignDesignatorName = designatorStatementAssign.getAssignDesignator().string;
		int assignDesignatorKind = assignDesignatorObj.getKind();
		Struct assignDesignatorType = assignDesignatorObj.getType();
		Struct assignValueType = designatorStatementAssign.getAssignValue().struct;
		if (assignDesignatorKind != Obj.Var && assignDesignatorKind != Obj.Elem && assignDesignatorKind != Obj.Fld) {
			report_error("designator '" + assignDesignatorName + "' is not valid for assignment",
					designatorStatementAssign);
		} else if (!assignValueType.assignableTo(assignDesignatorType)
				&& (assignValueType.getKind() != Struct.Class || assignValueType.getElemType() == null
						|| !assignValueType.getElemType().assignableTo(assignDesignatorType))
				&& (assignValueType != Tab.nullType || assignDesignatorType.getKind() != Set)) {
			report_error("assign value cannot be assigned to designator '" + assignDesignatorName + "'",
					designatorStatementAssign);
		}
	}

	@Override
	public void visit(DesignatorStatementFuncCall designatorStatementFuncCall) {
		Obj designatorObj = designatorStatementFuncCall.getFuncCallMethod().obj;
		if (designatorObj.getKind() != Obj.Meth) {
			report_error("designator '" + currDesignatorName + "' is not valid for function call",
					designatorStatementFuncCall);
		} else {
			List<Struct> formParTypeList = new ArrayList<>();
			for (Obj localObj : designatorObj.getLocalSymbols()) {
				if ((designatorObj.getFpPos() == 0 || localObj.getFpPos() != 1) && localObj.getKind() == Obj.Var
						&& localObj.getFpPos() > 0) {
					formParTypeList.add(localObj.getType());
				}
			}
			try {
				if (formParTypeList.size() != currActParTypeList.size()) {
					throw new Exception("number of actual parameters in method '" + currDesignatorName
							+ "' must match the number of its formal parameters");
				}
				for (int i = 0; i < formParTypeList.size(); i++) {
					if (!currActParTypeList.get(i).assignableTo(formParTypeList.get(i))) {
						throw new Exception("actual parameter types in method '" + currDesignatorName
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
		int designatorKind = designatorObj.getKind();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem && designatorKind != Obj.Fld) {
			report_error("designator '" + currDesignatorName + "' is not valid for increment", designatorStatementInc);
		} else if (designatorObj.getType() != Tab.intType) {
			report_error("designator '" + currDesignatorName + "' is not of a valid type for increment",
					designatorStatementInc);
		}
	}

	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		Obj designatorObj = designatorStatementDec.getDesignator().obj;
		int designatorKind = designatorObj.getKind();
		if (designatorKind != Obj.Var && designatorKind != Obj.Elem && designatorKind != Obj.Fld) {
			report_error("designator '" + currDesignatorName + "' is not valid for decrement", designatorStatementDec);
		} else if (designatorObj.getType() != Tab.intType) {
			report_error("designator '" + currDesignatorName + "' is not of a valid type for decrement",
					designatorStatementDec);
		}
	}

	@Override
	public void visit(AssignDesignator assignDesignator) {
		assignDesignator.string = currDesignatorName;
	}

	@Override
	public void visit(DesignatorSimple designatorSimple) {
		currInstanceObj = null;
		currMemberObj = null;
		currDesignatorName = designatorSimple.getDesignatorName();
		Obj designatorObj = Tab.find(currDesignatorName);
		int designatorKind = designatorObj.getKind();
		if (designatorObj == Tab.noObj) {
			report_error("designator '" + currDesignatorName + "' must be declared before use", designatorSimple);
			designatorSimple.obj = Tab.noObj;
		} else if (designatorKind != Obj.Con && designatorKind != Obj.Var && designatorKind != Obj.Meth
				&& designatorKind != Obj.Fld) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", designatorSimple);
			designatorSimple.obj = Tab.noObj;
		} else {
			designatorSimple.obj = designatorObj;
		}
		// printObj(designator.obj, designator);
	}

	@Override
	public void visit(DesignatorArrSelector designatorArrSelector) {
		String prevDesignatorName = prevDesignatorNameStack.pop();
		Obj designatorArrObj = designatorArrSelector.getDesignatorArrName().obj;
		int designatorArrKind = designatorArrObj.getKind();
		Struct designatorArrType = designatorArrObj.getType();
		if (designatorArrObj == Tab.noObj) {
			report_error("designator '" + prevDesignatorName + "' must be declared before use", designatorArrSelector);
			designatorArrSelector.obj = Tab.noObj;
		} else if ((designatorArrKind != Obj.Var && designatorArrKind != Obj.Fld)
				|| designatorArrType.getKind() != Struct.Array) {
			report_error("designator '" + prevDesignatorName + "' is not valid for this use", designatorArrSelector);
			designatorArrSelector.obj = Tab.noObj;
		} else if (designatorArrSelector.getExpr().struct == Tab.noType) {
			report_error("array index must be of type int", designatorArrSelector);
			designatorArrSelector.obj = Tab.noObj;
		} else {
			if (prevDesignatorName.equals(currDesignatorName)) {
				currDesignatorName = prevDesignatorName + "[#]";
			} else {
				currDesignatorName = prevDesignatorName + "[" + currDesignatorName + "]";
			}
			designatorArrSelector.obj = new Obj(Obj.Elem, currDesignatorName, designatorArrType.getElemType());
		}
		// printObj(designator.obj, designator);
	}

	@Override
	public void visit(DesignatorMemberSelector designatorMemberSelector) {
		if (currMemberObj.getKind() == Obj.Meth && currMemberObj.getFpPos() == 0) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", designatorMemberSelector);
			designatorMemberSelector.obj = Tab.noObj;
		} else {
			designatorMemberSelector.obj = currMemberObj;
		}
		// printObj
	}

	@Override
	public void visit(DesignatorMemberSelectorArr designatorMemberSelectorArr) {
		if (currMemberObj.getKind() == Obj.Meth && currMemberObj.getFpPos() == 0) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use",
					designatorMemberSelectorArr);
			designatorMemberSelectorArr.obj = Tab.noObj;
		} else {
			designatorMemberSelectorArr.obj = currMemberObj;
		}
		// printObj
	}

	@Override
	public void visit(DesignatorArrName designatorArrName) {
		currInstanceObj = null;
		currMemberObj = null;
		currDesignatorName = designatorArrName.getDesignatorArrName();
		prevDesignatorNameStack.push(currDesignatorName);
		designatorArrName.obj = Tab.find(currDesignatorName);
	}

	@Override
	public void visit(DesignatorBaseInstance designatorBaseInstance) {
		currInstanceObj = null;
		currMemberObj = null;
		currDesignatorName = designatorBaseInstance.getDesignatorInstanceName();
		Obj instanceObj = Tab.find(currDesignatorName);
		int instanceKind = instanceObj.getKind();
		Struct instanceType = instanceObj.getType();
		if (instanceObj == Tab.noObj) {
			report_error("designator '" + currDesignatorName + "' must be declared before use", designatorBaseInstance);
			currInstanceObj = Tab.noObj;
		} else if ((instanceKind != Obj.Var && instanceKind != Obj.Fld)
				|| (instanceType.getKind() != Struct.Class && instanceType.getKind() != Struct.Interface)) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", designatorBaseInstance);
			currInstanceObj = Tab.noObj;
		} else {
			currInstanceObj = instanceObj;
		}
		designatorBaseInstance.obj = currInstanceObj;
	}

	@Override
	public void visit(DesignatorBaseInstanceArr designatorBaseInstanceArr) {
		String prevDesignatorName = prevDesignatorNameStack.pop();
		Obj instanceArrObj = designatorBaseInstanceArr.getDesignatorArrName().obj;
		int instanceArrKind = instanceArrObj.getKind();
		Struct instanceArrType = instanceArrObj.getType();
		if (instanceArrObj == Tab.noObj) {
			report_error("designator '" + prevDesignatorName + "' must be declared before use",
					designatorBaseInstanceArr);
			currInstanceObj = Tab.noObj;
		} else if ((instanceArrKind != Obj.Var && instanceArrKind != Obj.Fld)
				|| instanceArrType.getKind() != Struct.Array || (instanceArrType.getElemType().getKind() != Struct.Class
						&& instanceArrType.getElemType().getKind() != Struct.Interface)) {
			report_error("designator '" + prevDesignatorName + "' is not valid for this use",
					designatorBaseInstanceArr);
			currInstanceObj = Tab.noObj;
		} else if (designatorBaseInstanceArr.getExpr().struct == Tab.noType) {
			report_error("array index must be of type int", designatorBaseInstanceArr);
			currInstanceObj = Tab.noObj;
		} else {
			if (prevDesignatorName.equals(currDesignatorName)) {
				currDesignatorName = prevDesignatorName + "[#]";
			} else {
				currDesignatorName = prevDesignatorName + "[" + currDesignatorName + "]";
			}
			currInstanceObj = new Obj(Obj.Elem, currDesignatorName, instanceArrType.getElemType());
		}
		designatorBaseInstanceArr.obj = currInstanceObj;
	}

	@Override
	public void visit(SelectorListMember selectorListMember) {
		currInstanceObj = currMemberObj;
		Obj instanceObj = currInstanceObj;
		int instanceKind = instanceObj.getKind();
		Struct instanceType = instanceObj.getType();
		if (instanceObj == Tab.noObj) {
			currMemberObj = Tab.noObj;
		} else if ((instanceKind != Obj.Elem && instanceKind != Obj.Fld)
				|| (instanceType.getKind() != Struct.Class && instanceType.getKind() != Struct.Interface)) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", selectorListMember);
			currMemberObj = Tab.noObj;
		} else {
			String memberName = selectorListMember.getMemberName();
			currDesignatorName += "." + memberName;
			Obj memberObj = Tab.noObj;
			if (currClassObj != null && instanceType.equals(currClassObj.getType())) {
				memberObj = Tab.currentScope().getOuter().findSymbol(memberName); // returns null if not found
				memberObj = memberObj == null ? Tab.noObj : memberObj;
			} else {
				for (Obj member : instanceObj.getType().getMembers()) {
					if (member.getName().equals(memberName)) {
						memberObj = member;
						break;
					}
				}
			}
			int memberKind = memberObj.getKind();
			if (memberObj == Tab.noObj) {
				report_error("designator '" + currDesignatorName + "' must be declared before use", selectorListMember);
				currMemberObj = Tab.noObj;
			} else if (memberKind != Obj.Fld && memberKind != Obj.Meth) {
				report_error("designator '" + currDesignatorName + "' is not valid for this use", selectorListMember);
				currMemberObj = Tab.noObj;
			} else {
				currMemberObj = memberObj;
			}

		}
		selectorListMember.obj = currInstanceObj;
	}

	@Override
	public void visit(SelectorListMemberArr selectorListMemberArr) {
		String prevDesignatorName = prevDesignatorNameStack.pop();
		currInstanceObj = prevMemberObjStack.pop();
		Obj memberArrObj = currInstanceObj;
		int memberArrKind = memberArrObj.getKind();
		Struct memberArrType = memberArrObj.getType();
		if (memberArrObj == Tab.noObj) {
			report_error("designator '" + prevDesignatorName + "' must be declared before use", selectorListMemberArr);
			currMemberObj = Tab.noObj;
		} else if (memberArrKind != Obj.Fld || memberArrType.getKind() != Struct.Array) {
			report_error("designator '" + prevDesignatorName + "' is not valid for this use", selectorListMemberArr);
			currMemberObj = Tab.noObj;
		} else if (selectorListMemberArr.getExpr().struct == Tab.noType) {
			report_error("array index must be of type int", selectorListMemberArr);
			currMemberObj = Tab.noObj;
		} else {
			if (prevDesignatorName.equals(currDesignatorName)) {
				currDesignatorName = prevDesignatorName + "[#]";
			} else {
				currDesignatorName = prevDesignatorName + "[" + currDesignatorName + "]";
			}
			currMemberObj = new Obj(Obj.Elem, currDesignatorName, memberArrType.getElemType());
		}
	}

	@Override
	public void visit(SelectorListMemberSimple selectorListMemberSimple) {
		Obj instanceObj = currInstanceObj;
		int instanceKind = instanceObj.getKind();
		Struct instanceType = instanceObj.getType();
		if (instanceObj == Tab.noObj) {
			currMemberObj = Tab.noObj;
		} else if ((instanceKind != Obj.Var && instanceKind != Obj.Elem && instanceKind != Obj.Fld)
				|| (instanceType.getKind() != Struct.Class && instanceType.getKind() != Struct.Interface)) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", selectorListMemberSimple);
			currMemberObj = Tab.noObj;
		} else {
			String memberName = selectorListMemberSimple.getMemberName();
			currDesignatorName += "." + memberName;
			Obj memberObj = Tab.noObj;
			if (currClassObj != null && instanceType.equals(currClassObj.getType())) {
				memberObj = Tab.currentScope().getOuter().findSymbol(memberName); // returns null if not found
				memberObj = memberObj == null ? Tab.noObj : memberObj;
			} else {
				for (Obj member : instanceObj.getType().getMembers()) {
					if (member.getName().equals(memberName)) {
						memberObj = member;
						break;
					}
				}
			}
			int memberKind = memberObj.getKind();
			if (memberObj == Tab.noObj) {
				report_error("designator '" + currDesignatorName + "' must be declared before use",
						selectorListMemberSimple);
				currMemberObj = Tab.noObj;
			} else if (memberKind != Obj.Fld && memberKind != Obj.Meth) {
				report_info("" + memberKind, selectorListMemberSimple);
				report_error("designator '" + currDesignatorName + "' is not valid for this use",
						selectorListMemberSimple);
				currMemberObj = Tab.noObj;
			} else {
				currMemberObj = memberObj;
			}

		}
		selectorListMemberSimple.obj = currInstanceObj;
	}

	@Override
	public void visit(SelectorListMemberArrSimple selectorListMemberArrSimple) {
		String prevDesignatorName = prevDesignatorNameStack.pop();
		currInstanceObj = prevMemberObjStack.pop();
		Obj memberArrObj = currInstanceObj;
		int memberArrKind = memberArrObj.getKind();
		Struct memberArrType = memberArrObj.getType();
		if (memberArrObj == Tab.noObj) {
			report_error("designator '" + prevDesignatorName + "' must be declared before use",
					selectorListMemberArrSimple);
			currMemberObj = Tab.noObj;
		} else if (memberArrKind != Obj.Fld || memberArrType.getKind() != Struct.Array) {
			report_error("designator '" + prevDesignatorName + "' is not valid for this use",
					selectorListMemberArrSimple);
			currMemberObj = Tab.noObj;
		} else if (selectorListMemberArrSimple.getExpr().struct == Tab.noType) {
			report_error("array index must be of type int", selectorListMemberArrSimple);
			currMemberObj = Tab.noObj;
		} else {
			if (prevDesignatorName.equals(currDesignatorName)) {
				currDesignatorName = prevDesignatorName + "[#]";
			} else {
				currDesignatorName = prevDesignatorName + "[" + currDesignatorName + "]";
			}
			currMemberObj = new Obj(Obj.Elem, currDesignatorName, memberArrType.getElemType());
		}
	}

	@Override
	public void visit(MemberArrName memberArrName) {
		Obj instanceObj = currInstanceObj;
		int instanceKind = instanceObj.getKind();
		Struct instanceType = instanceObj.getType();
		String memberArrayName = memberArrName.getInstanceMemberArrName().getMemberArrName();
		if (instanceObj == Tab.noObj) {
			currMemberObj = Tab.noObj;
		} else if ((instanceKind != Obj.Var && instanceKind != Obj.Elem && instanceKind != Obj.Fld)
				|| (instanceType.getKind() != Struct.Class && instanceType.getKind() != Struct.Interface)) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", memberArrName);
			currMemberObj = Tab.noObj;
		} else {
			Obj memberObj = Tab.noObj;
			if (currClassObj != null && instanceType.equals(currClassObj.getType())) {
				memberObj = Tab.currentScope().getOuter().findSymbol(memberArrayName); // returns null if not found
				memberObj = memberObj == null ? Tab.noObj : memberObj;
			} else {
				for (Obj member : instanceObj.getType().getMembers()) {
					if (member.getName().equals(memberArrayName)) {
						memberObj = member;
						break;
					}
				}
			}
			currMemberObj = memberObj;
		}
		currDesignatorName += "." + memberArrayName;
		prevDesignatorNameStack.push(currDesignatorName);
		prevMemberObjStack.push(currMemberObj);
		memberArrName.obj = currMemberObj;
	}
	
	@Override
	public void visit(InstanceMemberArrName instanceMemberArrName) {
		if (currMemberObj != null) {
			currInstanceObj = currMemberObj;
		}
		instanceMemberArrName.obj = currInstanceObj;
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
	public void visit(ExprArithm exprArithm) {
		exprArithm.struct = exprArithm.getTermList().struct;
	}

	@Override
	public void visit(ExprMap exprMap) {
		Struct methodType = exprMap.getExprMapMethodName().struct;
		Struct arrType = exprMap.getExprMapArrName().struct;
		if (methodType == Tab.noType || arrType == Tab.noType) {
			exprMap.struct = Tab.noType;
		} else {
			exprMap.struct = Tab.intType;
		}
	}

	@Override
	public void visit(TermListAddop termListAddop) {
		Struct termListType = termListAddop.getTermList().struct;
		Struct termType = termListAddop.getTerm().struct;
		if (termListType != Tab.intType || termType != Tab.intType) {
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
		if (factorListType != Tab.intType || signedFactorType != Tab.intType) {
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
		if (signedFactorNeg.getFactor().struct != Tab.intType) {
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
		if (designatorKind != Obj.Con && designatorKind != Obj.Var && designatorKind != Obj.Elem
				&& designatorKind != Obj.Fld) {
			report_error("designator '" + currDesignatorName + "' is not valid for this use", factorDesignator);
			factorDesignator.struct = Tab.noType;
		} else {
			factorDesignator.struct = designatorObj.getType();
		}
	}

	@Override
	public void visit(FactorFuncCall factorFuncCall) {
		Obj designatorObj = factorFuncCall.getFuncCallMethod().obj;
		if (designatorObj.getKind() != Obj.Meth) {
			report_error("designator '" + currDesignatorName + "' is not valid for function call", factorFuncCall);
			factorFuncCall.struct = Tab.noType;
		} else {
			List<Struct> formParTypeList = new ArrayList<>();
			for (Obj localObj : designatorObj.getLocalSymbols()) {
				if ((designatorObj.getFpPos() == 0 || localObj.getFpPos() != 1) && localObj.getKind() == Obj.Var
						&& localObj.getFpPos() > 0) {
					formParTypeList.add(localObj.getType());
				}
			}
			try {
				if (formParTypeList.size() != currActParTypeList.size()) {
					throw new Exception("number of actual parameters in method '" + currDesignatorName
							+ "' must match the number of its formal parameters");
				}
				for (int i = 0; i < formParTypeList.size(); i++) {
					if (!currActParTypeList.get(i).assignableTo(formParTypeList.get(i))) {
						throw new Exception("actual parameter types in method '" + currDesignatorName
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
		if (factorArrAlloc.getExpr().struct != Tab.intType) {
			report_error("array or set size expression must be of type int", factorArrAlloc);
			factorArrAlloc.struct = Tab.noType;
		} else {
			factorArrAlloc.struct = currType == setType ? setType : new Struct(Struct.Array, currType);
		}
		currType = null;
	}

	@Override
	public void visit(FactorInstanceAlloc factorInstanceAlloc) {
		if (factorInstanceAlloc.getConstructorCall().obj == Tab.noObj) {
			factorInstanceAlloc.struct = Tab.noType;
		} else {
			factorInstanceAlloc.struct = currType;
		}
		currType = null;
	}

	@Override
	public void visit(FactorGrouped factorGrouped) {
		factorGrouped.struct = factorGrouped.getExpr().struct;
	}

	@Override
	public void visit(FuncCallMethod funcCallMethod) {
		funcCallMethod.obj = funcCallMethod.getDesignator().obj;
	}

	@Override
	public void visit(FuncCallInstance funcCallInstance) {
		funcCallInstance.obj = currInstanceObj;
	}

	@Override
	public void visit(ActPar actPar) {
		currActParTypeList.add(actPar.getExpr().struct);
	}

	@Override
	public void visit(ConstructorCall constructorCall) {
		Type type = constructorCall.getConstructorCallType().getType();
		String className = type.getTypeName();
		Struct classType = type.struct;
		Obj memberObj = Tab.noObj;
		Obj newConstructorObj = new Obj(Obj.Meth, className, Tab.noType);
		newConstructorObj.setAdr(-1);
		if (currClassObj != null) {
			memberObj = Tab.currentScope().getOuter().findSymbol(className); // returns null if not found
			memberObj = memberObj == null ? Tab.noObj : memberObj;
		} else {
			for (Obj member : classType.getMembers()) {
				if (member.getName().equals(className)) {
					memberObj = member;
					break;
				}
			}
		}
		if (memberObj == Tab.noObj) {
			if (currActParTypeList.size() != 0) {
				report_error("constructor '" + className + "' must be declared before use", constructorCall);
				constructorCall.obj = Tab.noObj;
			} else {
				constructorCall.obj = newConstructorObj;
			}
		} else {
			List<Struct> formParTypeList = new ArrayList<>();
			for (Obj localObj : memberObj.getLocalSymbols()) {
				if ((memberObj.getFpPos() > 0 || localObj.getFpPos() != 1) && localObj.getKind() == Obj.Var
						&& localObj.getFpPos() > 0) {
					formParTypeList.add(localObj.getType());
				}
			}
			try {
				if (formParTypeList.size() != currActParTypeList.size()) {
					throw new Exception("number of actual parameters in constructor '" + className
							+ "' must match the number of its formal parameters");
				}
				for (int i = 0; i < formParTypeList.size(); i++) {
					if (!currActParTypeList.get(i).assignableTo(formParTypeList.get(i))) {
						throw new Exception("actual parameter types in constructor '" + className
								+ "' must be assignable to corresponding formal parameter types");
					}
				}
				constructorCall.obj = memberObj;
			} catch (Exception e) {
				if (currActParTypeList.size() != 0) {
					report_error(e.getMessage(), constructorCall);
					constructorCall.obj = Tab.noObj;
				} else {
					constructorCall.obj = newConstructorObj;
				}
			}
		}
		currActParTypeList.clear();
	}

	@Override
	public void visit(ConstructorCallType constructorCallType) {
		String className = constructorCallType.getType().getTypeName();
		Obj classObj = Tab.find(className);
		if (classObj == Tab.noObj) {
			report_error("designator '" + className + "' must be declared before use", constructorCallType);
			constructorCallType.obj = Tab.noObj;
		} else if (classObj.getType().getKind() != Struct.Class) {
			report_error("instance must be of class type", constructorCallType);
			constructorCallType.obj = Tab.noObj;
		} else {
			constructorCallType.obj = classObj;
		}
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
					+ "type int and with return type of int", exprMapMethodName);
			exprMapMethodName.struct = Tab.noType;
		} else {
			for (Obj localObj : designatorObj.getLocalSymbols()) {
				if (localObj.getKind() == Obj.Var && localObj.getType() != Tab.intType) {
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
		if (designatorType.getKind() != Struct.Array || designatorType.getElemType() != Tab.intType) {
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
		if (condFactSimple.getExpr().struct != boolType) {
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
		} else if ((isRefType(leftExprType) || isRefType(rightExprType)) && !relop.equals("==")
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

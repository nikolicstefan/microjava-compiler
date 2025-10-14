package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class MJCodeGenerator extends VisitorAdaptor {

	private static final Struct boolType = Tab.find("bool").getType(); // "Tab.boolType"
	private static final Struct setType = Tab.find("set").getType(); // "Tab.setType"

	/* Standard Methods Implementation */
	
	/* Set Type Implementation */

	/* 
	 * set a;
	 * adr = a;				// address on heap
	 * arr = adr;			// array on heap
	 * arrElem0 = adr / 4;	// arr element 0 index
	 * mjArr = adr + 4;		// MJ array
	 * 
	 * ind is indexing mjArr
	 * 
	 * heap[arrElem0 + 0]		= arr[0]					 = len = N + 1;
	 * heap[arrElem0 + 1]		= arr[1]		= mjArr[0]	 = size;
	 * heap[arrElem0 + 2]		= arr[2]		= mjArr[1]	 = a[0];
	 * heap[arrElem0 + 3]		= arr[3]		= mjArr[2]	 = a[1];
	 * ...
	 * heap[arrElem0 + len + 1]	= arr[len + 1]	= mjArr[len] = a[len - 1];
	 */
	
	/*
	 * local parameters
	 * local[0] = set a;
	 * local[1] = int b; || int b[]; || int width;
	 * local[2] = int size;
	 * local[3] = int i;
	 * local[4] = int j;
	 */

	private static void loadLenOnStack(int adrVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr = set a || adr = arr
		Code.put(Code.arraylength); // val = set len = N + 1, ind = i + 1 || val = arr len
	}

	private static void loadSetSizeInVar(int adrVarInd, int sizeVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr = set a
		Code.loadConst(0); // ind = 0
		Code.put(Code.aload);
		Code.loadConst(1);
		Code.put(Code.add); // val = size + 1, ind = i + 1
		if (sizeVarInd <= 3) {
			Code.put(Code.store_n + sizeVarInd);
		} else {
			Code.put(Code.store);
			Code.put(sizeVarInd);
		} // var size = size + 1
	}

	/*
	private static void storeSetSizeFromVar(int adrVarInd, int sizeVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr = set a
		Code.loadConst(0); // ind = 0
		if (sizeVarInd <= 3) {
			Code.put(Code.load_n + sizeVarInd);
		} else {
			Code.put(Code.load);
			Code.put(sizeVarInd);
		}
		Code.loadConst(1);
		Code.put(Code.sub); // val = var size - 1, i = ind - 1
		Code.put(Code.astore); // size = var size - 1 = size + 1 - 1 = size
	}
	*/

	private static void incAndStoreSetSizeFromVar(int adrVarInd, int sizeVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr = set a
		Code.loadConst(0); // ind = 0
		if (sizeVarInd <= 3) {
			Code.put(Code.load_n + sizeVarInd);
		} else {
			Code.put(Code.load);
			Code.put(sizeVarInd);
		} // val = var size, i = ind - 1
		Code.put(Code.astore); // size = var size = size + 1
	}

	private static void incIterator(int iteratorVarInd) {
		if (iteratorVarInd <= 3) {
			Code.put(Code.load_n + iteratorVarInd);
		} else {
			Code.put(Code.load);
			Code.put(iteratorVarInd);
		} // i
		Code.loadConst(1);
		Code.put(Code.add); // val = i + 1
		if (iteratorVarInd <= 3) {
			Code.put(Code.store_n + iteratorVarInd);
		} else {
			Code.put(Code.store);
			Code.put(iteratorVarInd);
		} // i = i + 1
	}

	private static void loadElemOnStack(int adrVarInd, int indVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr
		if (indVarInd <= 3) {
			Code.put(Code.load_n + indVarInd);
		} else {
			Code.put(Code.load);
			Code.put(indVarInd);
		} // ind
		Code.put(Code.aload); // val = adr[ind]
	}

	private static void storeElemFromVar(int adrVarInd, int indVarInd, int valVarInd) {
		if (adrVarInd <= 3) {
			Code.put(Code.load_n + adrVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrVarInd);
		} // adr
		if (indVarInd <= 3) {
			Code.put(Code.load_n + indVarInd);
		} else {
			Code.put(Code.load);
			Code.put(indVarInd);
		} // ind
		if (valVarInd <= 3) {
			Code.put(Code.load_n + valVarInd);
		} else {
			Code.put(Code.load);
			Code.put(valVarInd);
		} // val
		Code.put(Code.astore); // adr[ind] = val
	}

	/*
	private static void storeElemFromArr(int adrDstVarInd, int indDstVarInd, int adrSrcVarInd, int indSrcVarInd) {
		if (adrDstVarInd <= 3) {
			Code.put(Code.load_n + adrDstVarInd);
		} else {
			Code.put(Code.load);
			Code.put(adrDstVarInd);
		} // adr = adrDst
		if (indDstVarInd <= 3) {
			Code.put(Code.load_n + indDstVarInd);
		} else {
			Code.put(Code.load);
			Code.put(indDstVarInd);
		} // ind = indDst
		loadElemOnStack(adrSrcVarInd, indSrcVarInd); // val = adrSrc[indSrc]
		Code.put(Code.astore); // adrDst[indDst] = adrSrc[indSrc]
	}
	*/

	private static void addNewSetElem() {
		int setAdrVarInd = 0; // a
		int valVarInd = 1; // b
		int sizeVarInd = 2; // var size
		int iSetVarInd = 3; // i

		/* i = 0 + 1; // ind = i + 1 */
		incIterator(iSetVarInd);

		/* var size = size + 1; // ind = i + 1 */
		loadSetSizeInVar(setAdrVarInd, sizeVarInd);

		// search new set element loop
		int searchNewSetElemLoop = Code.pc;

		/* condition check: i < size */
		Code.put(Code.load_3); // i
		Code.put(Code.load_2); // var size
		Code.putFalseJump(Code.lt, 0); // jump to try to store new set element
		int condition1FalsePatchAdr = Code.pc - 2;
		// i < size is true

		/* condition check: a[i] != b */
		loadElemOnStack(setAdrVarInd, iSetVarInd); // a[i]
		Code.put(Code.load_1); // b
		Code.putFalseJump(Code.ne, 0); // jump to end of add
		int condition2FalsePatchAdr = Code.pc - 2;
		// a[i] != b is true

		/* i++; */
		incIterator(iSetVarInd);

		Code.putJump(searchNewSetElemLoop); // jump to search new set element loop

		// try to store new set element
		Code.fixup(condition1FalsePatchAdr);

		/* condition check: size < len */
		Code.put(Code.load_2); // var size
		loadLenOnStack(setAdrVarInd); // len
		Code.putFalseJump(Code.lt, 0); // jump to end of add
		int condition3FalsePatchAdr = Code.pc - 2;
		// var size < len is true

		/* a[size] = b; */
		storeElemFromVar(setAdrVarInd, sizeVarInd, valVarInd);

		/* size++; */
		incAndStoreSetSizeFromVar(setAdrVarInd, sizeVarInd);

		// end of add
		Code.fixup(condition2FalsePatchAdr);
		Code.fixup(condition3FalsePatchAdr);
	}

	private static void addAllNewSetElems() {
		// int setAdrVarInd = 0; // a
		int arrAdrVarInd = 1; // b
		// int sizeVarInd = 2; // var size
		// int iSetVarInd = 3; // i
		int jArrVarInd = 4; // j

		// add new set element loop
		int addNewSetElemLoop = Code.pc;

		/* condition check: j < len */
		Code.put(Code.load);
		Code.put(jArrVarInd); // j
		loadLenOnStack(arrAdrVarInd); // len
		Code.putFalseJump(Code.lt, 0); // jump to end of addAll
		int conditionFalsePatchAdr = Code.pc - 2;
		// j < len is true

		/* add(a, b[j]); */
		Code.put(Code.load_n); // fp1 = a
		loadElemOnStack(arrAdrVarInd, jArrVarInd); // fp2 = b[j]
		int offset = Tab.find("add").getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);

		/* j++; */
		incIterator(jArrVarInd);

		Code.putJump(addNewSetElemLoop); // jump to add new set element loop

		// end of addAll
		Code.fixup(conditionFalsePatchAdr);
	}

	static {
		/*
		 * char chr(int i) {
		 *     return i;
		 * }
		 */
		Obj methodObj = Tab.find("chr");
		methodObj.setAdr(Code.pc);
		int fpCount = methodObj.getLevel();
		int localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		/*
		 * int ord(char ch) {
		 *     return ch;
		 * }
		 */
		methodObj = Tab.find("ord");
		methodObj.setAdr(Code.pc);
		fpCount = methodObj.getLevel();
		localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);

		/*
		 * int len(void arr[]) {
		 *     return arr.length;
		 * }
		 */
		methodObj = Tab.find("len");
		methodObj.setAdr(Code.pc);
		fpCount = methodObj.getLevel();
		localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

		/*
		 * void add(set a, int b)
		 *     int size, i;
		 * {
		 *     size = a.size;
		 *     for (i = 0; i < size; i++) {
		 *         if (a[i] == b) {
		 *             return;
		 *         }
		 *     }
		 *     if (size < a.length) {
		 *         a[size] = b;
		 *         size++;
		 *         a.size = size;
		 *     }
		 * }
		 */
		methodObj = Tab.find("add");
		methodObj.setAdr(Code.pc);
		fpCount = methodObj.getLevel();
		localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		addNewSetElem();
		Code.put(Code.exit);
		Code.put(Code.return_);

		/*
		 * void addAll(set a, int b[])
		 *     int i;
		 * {
		 *     for (i = 0; i < b.length; i++) {
		 *         add(a, b[i]);
		 *     }
		 * }
		 */
		methodObj = Tab.find("addAll");
		methodObj.setAdr(Code.pc);
		fpCount = methodObj.getLevel();
		localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		addAllNewSetElems();
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	/* Code Generation */

	private int globalVarsCnt;
	private int mainPc;
	
	private Obj currClassObj = null;
	private Obj currAssignDesignatorObj = null;
	private Obj currFuncCallMethodObj = null;
	private Obj currFuncCallInstanceObj = null;
	private Obj currInstanceAllocClassObj = null;
	
	private List<Integer> vtableInitBeginAdrList = new ArrayList<>();
	private Stack<List<Integer>> breakPatchAdrListStack = new Stack<>();
	private Stack<List<Integer>> continuePatchAdrListStack = new Stack<>();
	private List<Integer> condFactFalsePatchAdrList = new ArrayList<>();
	private List<Integer> condTermTruePatchAdrList = new ArrayList<>();
	private Stack<Integer> conditionFalsePatchAdrStack = new Stack<>();
	private Stack<Integer> elseBeginTruePatchAdrStack = new Stack<>();
	private Stack<Integer> doWhileBeginAdrStack = new Stack<>();
	
	public MJCodeGenerator(int globalVarsCnt) {
		this.globalVarsCnt = globalVarsCnt;
	}
	
	public int getGlobalVarsCnt() {
		return globalVarsCnt;
	}

	public int getMainPc() {
		return mainPc;
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		vtableInitBeginAdrList.add(Code.pc);
		Code.put(Code.enter);
		Code.put(0);
		Code.put(0);
		currClassObj.setAdr(globalVarsCnt);
		for (Obj memberObj: currClassObj.getType().getMembers()) {
			int memberFpPos = memberObj.getFpPos();
			if (memberObj.getKind() == Obj.Meth && memberFpPos == 1) {
				String memberName = memberObj.getName();
				for (int i = 0; i < memberName.length(); i++) {
					Code.loadConst(memberName.charAt(i));
					Code.put(Code.putstatic);
					Code.put2(globalVarsCnt++);
				}
				Code.loadConst(-1);
				Code.put(Code.putstatic);
				Code.put2(globalVarsCnt++);
				Code.loadConst(memberObj.getAdr());
				Code.put(Code.putstatic);
				Code.put2(globalVarsCnt++);
			}
		}
		Code.loadConst(-2);
		Code.put(Code.putstatic);
		Code.put2(globalVarsCnt++);
		Code.put(Code.exit);
		Code.put(Code.return_);
		currClassObj = null;
	}
	
	@Override
	public void visit(ClassDeclName classDeclName) {
		currClassObj = classDeclName.obj;
	}
	
	@Override
	public void visit(ConstructorDecl constructorDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ConstructorDeclName constructorDeclName) {
		Obj constructorObj = constructorDeclName.obj;
		constructorObj.setAdr(Code.pc);
		int fpCount = constructorObj.getLevel();
		int localsCount = constructorObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(MethodRetTypeName methodRetTypeName) {
		Obj methodObj = methodRetTypeName.obj;
		methodObj.setAdr(Code.pc);
		boolean isMainMethod = methodObj.getName().equals("main");
		if (isMainMethod) {
			mainPc = Code.pc;
		}
		int fpCount = methodObj.getLevel();
		int localsCount = methodObj.getLocalSymbols().size();
		Code.put(Code.enter);
		Code.put(fpCount);
		Code.put(localsCount);
		if (isMainMethod) {
			for (int vtableInitBeginAdr: vtableInitBeginAdrList) {
				int offset = vtableInitBeginAdr - Code.pc;
				Code.put(Code.call);
				Code.put2(offset);
			}
		}
	}

	// Do While Statement [BEGIN]

	@Override
	public void visit(StatementDoWhile statementDoWhile) {
		// end of do while statement [FALSE PATH]
		if (!conditionFalsePatchAdrStack.empty()) {
			Code.fixup(conditionFalsePatchAdrStack.pop());
		}
		// [PATH END]
		if (!breakPatchAdrListStack.empty()) {
			List<Integer> breakPatchAdrList = breakPatchAdrListStack.pop();
			while (!breakPatchAdrList.isEmpty()) {
				Code.fixup(breakPatchAdrList.remove(0));
			}
		}
	}

	// Do While Statement [END]

	// Break & Continue Statements [BEGIN]

	@Override
	public void visit(StatementBreak statementBreak) {
		Code.putJump(0); // jump to end of do while statement
		breakPatchAdrListStack.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(StatementContinue statementContinue) {
		Code.putJump(0); // jump to begin of do while condition
		continuePatchAdrListStack.peek().add(Code.pc - 2);
	}

	// Break & Continue Statements [END]

	@Override
	public void visit(StatementReturn statementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementRead statementRead) {
		Obj designatorObj = statementRead.getDesignator().obj;
		if (designatorObj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(designatorObj);
	}

	private void printBool() {
		// ExprStack: val = 0 || 1, width

		/* condition check: val == true */
		Code.put(Code.dup_x1);
		// ExprStack: width, val, width
		Code.put(Code.pop);
		// ExprStack: width, val
		Code.loadConst(1); // true
		// ExprStack: width, val, 1
		Code.putFalseJump(Code.eq, 0); // jump to val is false
		// ExprStack: width
		int condition1FalsePatchAdr = Code.pc - 2;
		// val == true is true
		// val is true

		// ExprStack: width

		String trueString = "true";
		for (int i = 0; i < trueString.length(); i++) {
			Code.loadConst(trueString.charAt(i));
			if (i == 0) {
				// ExprStack: width, val = 't'
				Code.put(Code.dup_x1);
				// ExprStack: val, width, val
				Code.put(Code.pop);
				// ExprStack: val, width = width
			} else {
				// ExprStack: val
				Code.loadConst(0);
				// ExprStack: val, width = 0
			}
			// ExprStack: val, width
			Code.put(Code.bprint);
			// ExprStack:
		}

		Code.putJump(0); // jump to end of print bool
		int printTrueEndPatchAdr = Code.pc - 2;

		// val is false
		Code.fixup(condition1FalsePatchAdr);

		// ExprStack: width

		String falseString = "false";
		for (int i = 0; i < falseString.length(); i++) {
			Code.loadConst(falseString.charAt(i));
			if (i == 0) {
				// ExprStack: width, val = 'f'
				Code.put(Code.dup_x1);
				// ExprStack: val, width, val
				Code.put(Code.pop);
				// ExprStack: val, width = width
			} else {
				// ExprStack: val
				Code.loadConst(0);
				// ExprStack: val, width = 0
			}
			// ExprStack: val, width
			Code.put(Code.bprint);
			// ExprStack:
		}

		// end of print bool
		Code.fixup(printTrueEndPatchAdr);

		// ExprStack:
	}

	private static void printSet() {
		// ExprStack: set, width

		/* condition check: set.size > 0 */
		Code.put(Code.dup_x1);
		// ExprStack: width, set, width
		Code.put(Code.pop);
		// ExprStack: width, set
		Code.put(Code.dup);
		// ExprStack: width, set, set
		Code.loadConst(0);
		// ExprStack: width, set, adr = set, ind = 0
		Code.put(Code.aload);
		// ExprStack: width, set, size = set.size
		Code.loadConst(0);
		// ExprStack: width, set, size, 0
		Code.putFalseJump(Code.gt, 0); // jump to end of print set
		// ExprStack: width, set
		int condition1FalsePatchAdr = Code.pc - 2;
		// set.size > 0 is true

		// ExprStack: width, set

		/* print(set[0], width); */
		Code.put(Code.dup_x1);
		// ExprStack: set, width, set
		Code.loadConst(1); // ind = i + 1
		// ExprStack: set, width, adr = set, ind = 1
		Code.put(Code.aload);
		// ExprStack: set, width, set[0]
		Code.put(Code.dup_x1);
		// ExprStack: set, set[0], width, set[0]
		Code.put(Code.pop);
		// ExprStack: set, val = set[0], width = width
		Code.put(Code.print);
		// ExprStack: set

		/* i = 1; */
		Code.loadConst(2); // ind = i + 1
		// ExprStack: set, i
		Code.put(Code.dup_x1);
		// ExprStack: i, set, i
		Code.put(Code.pop);
		// ExprStack: i, set

		// print set element loop
		int printSetElemLoop = Code.pc;

		// ExprStack: i, set

		/* condition check: i < set.size */
		Code.put(Code.dup2);
		// ExprStack: i, set, i, set
		Code.loadConst(0);
		// ExprStack: i, set, i, adr = set, ind = 0
		Code.put(Code.aload);
		// ExprStack: i, set, i, size = set.size
		Code.loadConst(1);
		// ExprStack: i, set, i, size, 1
		Code.put(Code.add); // ind = i + 1
		// ExprStack: i, set, i, size = set.size + 1
		Code.putFalseJump(Code.lt, 0); // jump to end of print set
		// ExprStack: i, set
		int condition2FalsePatchAdr = Code.pc - 2;
		// i < set.size is true

		// ExprStack: i, set

		/* print(' ', 0) */
		Code.loadConst(' ');
		// ExprStack: i, set, ' '
		Code.loadConst(0);
		// ExprStack: i, set, val = ' ', width = 0
		Code.put(Code.bprint);
		// ExprStack: i, set

		/* print(set[i], 0); */
		Code.put(Code.dup_x1);
		// ExprStack: set, i, set
		Code.put(Code.pop);
		// ExprStack: set, i
		Code.put(Code.dup2);
		// ExprStack: set, i, adr = set, ind = i
		Code.put(Code.aload);
		// ExprStack: set, i, set[i]
		Code.loadConst(0);
		// ExprStack: set, i, val = set[i], width = 0
		Code.put(Code.print);
		// ExprStack: set, i

		/* i++; */
		Code.loadConst(1);
		// ExprStack: set, i, 1
		Code.put(Code.add);
		// ExprStack: set, i = i + 1

		Code.put(Code.dup_x1);
		// ExprStack: i, set, i
		Code.put(Code.pop);
		// ExprStack: i, set

		Code.putJump(printSetElemLoop); // jump to print set element loop

		// end of print set
		Code.fixup(condition1FalsePatchAdr);
		Code.fixup(condition2FalsePatchAdr);

		// ExprStack: width || i, set

		Code.put(Code.pop);
		// ExprStack: width || i
		Code.put(Code.pop);
		// ExprStack:

		// ExprStack:
	}

	@Override
	public void visit(StatementPrint statementPrint) {
		Struct exprType = statementPrint.getExpr().struct;
		if (exprType.equals(Tab.intType)) {
			Code.put(Code.print);
		} else if (exprType.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else if (exprType.equals(boolType)) {
			printBool();
		} else if (exprType.equals(setType)) {
			printSet();
		}
	}

	@Override
	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		if (designatorStatementAssign.getAssignValue() instanceof AssignValueExpr) {
			Code.store(designatorStatementAssign.getAssignDesignator().getDesignator().obj);
		}
		currAssignDesignatorObj = null;
	}

	@Override
	public void visit(DesignatorStatementFuncCall designatorStatementFuncCall) {
		Obj methodObj = currFuncCallMethodObj;
		if (methodObj.getFpPos() == 0) {
			int offset = methodObj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		} else {
			Code.put(Code.getfield);
			Code.put2(0);
			Code.put(Code.invokevirtual);
			String methodName = methodObj.getName();
			for (int i = 0; i < methodName.length(); i++) {
				Code.put4(methodName.charAt(i));
			}
			Code.put4(-1);
		}
		if (!methodObj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
		// currFuncCallMethodObj = null;
		// currFuncCallInstanceObj = null;
	}

	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		Obj designatorObj = designatorStatementInc.getDesignator().obj;
		int designatorKind = designatorObj.getKind();
		if (designatorKind == Obj.Elem) {
			Code.put(Code.dup2);
		}
		if (designatorKind == Obj.Fld) {
			Code.put(Code.dup);
		}
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorObj);
	}

	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		Obj designatorObj = designatorStatementDec.getDesignator().obj;
		int designatorKind = designatorObj.getKind();
		if (designatorKind == Obj.Elem) {
			Code.put(Code.dup2);
		}
		if (designatorKind == Obj.Fld) {
			Code.put(Code.dup);
		}
		Code.load(designatorObj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorObj);
	}

	@Override
	public void visit(AssignDesignator assignDesignator) {
		currAssignDesignatorObj = assignDesignator.getDesignator().obj;
	}
	
	@Override
	public void visit(DesignatorSimple designatorSimple) {
		if (designatorSimple.obj.getKind() == Obj.Fld) {
			Code.put(Code.load_n); // this
		}
	}
	
	@Override
	public void visit(DesignatorArrName designatorArrName) {
		Obj designatorArrObj = designatorArrName.obj;
		if (designatorArrObj.getKind() == Obj.Fld) {
			Code.put(Code.load_n); // this
		}
		Code.load(designatorArrObj);
	}
	
	@Override
	public void visit(DesignatorBaseInstance designatorBaseInstance) {
		if (designatorBaseInstance.obj.getKind() == Obj.Fld) {
			Code.put(Code.load_n); // this
		}
	}
	
	@Override
	public void visit(SelectorListMember selectorListMember) {
		Code.load(selectorListMember.obj);
	}
		
	@Override
	public void visit(SelectorListMemberSimple selectorListMemberSimple) {
		Code.load(selectorListMemberSimple.obj);
	}
		
	@Override
	public void visit(MemberArrName memberArrName) {
		Code.load(memberArrName.obj);
	}
	
	@Override
	public void visit(InstanceMemberArrName instanceMemberArrName) {
		Code.load(instanceMemberArrName.obj);
	}

	private static void setUnion(Obj dstSetObj, Obj src1SetObj, Obj src2SetObj) {
		// ExprStack:

		/* i = 0; */
		Code.loadConst(1); // ind = i + 1
		// ExprStack: i

		// union src1 loop
		int unionSrc1Loop = Code.pc;

		// ExprStack: i

		/* condition check: i < dst.length */
		Code.put(Code.dup);
		// ExprStack: i, i
		Code.load(dstSetObj);
		// ExprStack: i, i, adr = dst
		Code.put(Code.arraylength); // ind = i + 1
		// ExprStack: i, i, len = dst.length + 1
		Code.putFalseJump(Code.lt, 0); // jump to end of union
		// ExprStack: i
		int condition1FalsePatchAdr = Code.pc - 2;
		// i < dst.length is true

		// ExprStack: i

		/* condition check: i < src1.size */
		Code.put(Code.dup);
		// ExprStack: i, i
		Code.load(src1SetObj);
		// ExprStack: i, i, src1
		Code.loadConst(0);
		// ExprStack: i, i, adr = src1, ind = 0
		Code.put(Code.aload);
		// ExprStack: i, i, size = src1.size
		Code.loadConst(1);
		// ExprStack: i, i, size, 1
		Code.put(Code.add); // ind = i + 1
		// ExprStack: i, i, size = src1.size + 1
		Code.putFalseJump(Code.lt, 0); // jump to end of union src1 loop
		// ExprStack: i
		int condition2FalsePatchAdr = Code.pc - 2;
		// i < src1.size is true

		// ExprStack: i

		/* dst[i] = src1[i]; */
		Code.load(dstSetObj);
		// ExprStack: i, dst
		Code.put(Code.dup2);
		// ExprStack: i, dst, i, dst
		Code.put(Code.pop);
		// ExprStack: i, dst, i
		Code.load(src1SetObj);
		// ExprStack: i, dst, i, src1
		Code.put(Code.dup2);
		// ExprStack: i, dst, i, src1, i, src1
		Code.put(Code.pop);
		// ExprStack: i, dst, i, adr = src1, ind = i
		Code.put(Code.aload);
		// ExprStack: i, adr = dst, ind = i, val = src1[i]
		Code.put(Code.astore);
		// ExprStack: i

		/* i++; */
		Code.loadConst(1);
		// ExprStack: i, 1
		Code.put(Code.add);
		// ExprStack: i = i + 1

		Code.putJump(unionSrc1Loop); // jump to union src1 loop

		// end of union src1 loop
		Code.fixup(condition2FalsePatchAdr);

		// ExprStack: i

		/* dst.size = i; */
		Code.load(dstSetObj);
		// ExprStack: i, dst
		Code.put(Code.dup_x1);
		// ExprStack: dst, i, dst
		Code.put(Code.pop);
		// ExprStack: dst, i
		Code.loadConst(0);
		// ExprStack: dst, i, 0
		Code.put(Code.dup_x1);
		// ExprStack: dst, 0, i, 0
		Code.put(Code.pop);
		// ExprStack: dst, 0, i
		Code.loadConst(1);
		// ExprStack: dst, 0, i, 1
		Code.put(Code.sub); // i = ind - 1
		// ExprStack: adr = dst, ind = 0, val = i = i - 1
		Code.put(Code.astore);
		// ExprStack:

		/* i = 0; */
		Code.loadConst(1); // ind = i + 1
		// ExprStack: i

		// union src2 loop
		int unionSrc2Loop = Code.pc;

		// ExprStack: i

		/* condition check: i < src2.size */
		Code.put(Code.dup);
		// ExprStack: i, i
		Code.load(src2SetObj);
		// ExprStack: i, i, src2
		Code.loadConst(0);
		// ExprStack: i, i, adr = src2, ind = 0
		Code.put(Code.aload);
		// ExprStack: i, i, size = src2.size
		Code.loadConst(1);
		// ExprStack: i, i, size, 1
		Code.put(Code.add); // ind = i + 1
		// ExprStack: i, i, size = src2.size + 1
		Code.putFalseJump(Code.lt, 0); // jump to end of union
		// ExprStack: i
		int condition3FalsePatchAdr = Code.pc - 2;
		// i < src2.size is true

		// ExprStack: i

		/* add(dst, src2[i]); */
		Code.load(dstSetObj); // fp1 = dst
		// ExprStack: i, dst
		Code.put(Code.dup2);
		// ExprStack: i, dst, i, dst
		Code.put(Code.pop);
		// ExprStack: i, dst, i
		Code.load(src2SetObj);
		// ExprStack: i, dst, i, src2
		Code.put(Code.dup_x1);
		// ExprStack: i, dst, src2, i, src2
		Code.put(Code.pop);
		// ExprStack: i, dst, adr = src2, ind = i
		Code.put(Code.aload); // fp2 = src2[i]
		// ExprStack: i, fp1 = dst, fp2 = src2[i]
		int offset = Tab.find("add").getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		// ExprStack: i

		/* i++; */
		Code.loadConst(1);
		// ExprStack: i, 1
		Code.put(Code.add);
		// ExprStack: i = i + 1

		Code.putJump(unionSrc2Loop); // jump to union src2 loop

		// end of union
		Code.fixup(condition1FalsePatchAdr);
		Code.fixup(condition3FalsePatchAdr);

		// ExprStack: i

		Code.put(Code.pop);
		// ExprStack:

		// ExprStack:
	}

	@Override
	public void visit(AssignValueSet assignValueSet) {
		Obj dstSetObj = currAssignDesignatorObj;
		Obj src1SetObj = assignValueSet.getDesignator().obj;
		Obj src2SetObj = assignValueSet.getDesignator1().obj;
		String setop = assignValueSet.getSetop().string;
		if (setop.equals("union")) {
			setUnion(dstSetObj, src1SetObj, src2SetObj);
		}
	}

	@Override
	public void visit(ExprMap exprMap) {
		// ExprStack:

		int methodAdr = exprMap.getExprMapMethodName().getDesignator().obj.getAdr();
		Obj arrObj = exprMap.getExprMapArrName().getDesignator().obj;

		/* sum = 0; */
		Code.loadConst(0);
		// ExprStack: sum

		/* i = 0; */
		Code.loadConst(0);
		// ExprStack: sum, i

		// map array loop
		int mapArrLoop = Code.pc;

		// ExprStack: sum, i

		/* condition check: i < len */
		Code.put(Code.dup_x1);
		// ExprStack: i, sum, i
		Code.put(Code.dup);
		// ExprStack: i, sum, i, i
		Code.load(arrObj);
		// ExprStack: i, sum, i, i, adr = arr
		Code.put(Code.arraylength);
		// ExprStack: i, sum, i, i, len
		Code.putFalseJump(Code.lt, 0); // jump to end of map array
		// ExprStack: i, sum, i
		int conditionFalsePatchAdr = Code.pc - 2;
		// i < len is true

		// ExprStack: i, sum, i

		/* sum += methodAdr(arr[i]); */
		Code.load(arrObj);
		// ExprStack: i, sum, i, arr
		Code.put(Code.dup_x1);
		// ExprStack: i, sum, arr, i, arr
		Code.put(Code.pop);
		// ExprStack: i, sum, adr = arr, ind = i
		Code.put(Code.aload);
		// ExprStack: i, sum, arr[i]
		int offset = methodAdr - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		// ExprStack: i, sum, ret
		Code.put(Code.add);
		// ExprStack: i, sum = sum + ret

		/* i++; */
		Code.put(Code.dup_x1);
		// ExprStack: sum, i, sum
		Code.put(Code.pop);
		// ExprStack: sum, i
		Code.loadConst(1);
		// ExprStack: sum, i, 1
		Code.put(Code.add);
		// ExprStack: sum, i = i + 1

		Code.putJump(mapArrLoop); // jump to map array loop

		// end of map array
		Code.fixup(conditionFalsePatchAdr);

		// ExprStack: i, sum, i

		Code.put(Code.pop);
		// ExprStack: i, sum
		Code.put(Code.dup_x1);
		// ExprStack: sum, i, sum
		Code.put(Code.pop);
		// ExprStack: sum, i
		Code.put(Code.pop);
		// ExprStack: sum

		// ExprStack: sum
	}

	@Override
	public void visit(TermListAddop termListAddop) {
		String addop = termListAddop.getAddop().string;
		if (addop.equals("+")) {
			Code.put(Code.add);
		} else if (addop.equals("-")) {
			Code.put(Code.sub);
		}
	}

	@Override
	public void visit(FactorListMulop factorListMulop) {
		String mulop = factorListMulop.getMulop().string;
		if (mulop.equals("*")) {
			Code.put(Code.mul);
		} else if (mulop.equals("/")) {
			Code.put(Code.div);
		} else if (mulop.equals("%")) {
			Code.put(Code.rem);
		}
	}

	@Override
	public void visit(SignedFactorNeg signedFactorNeg) {
		Code.put(Code.neg);
	}

	@Override
	public void visit(FactorDesignator factorDesignator) {
		Code.load(factorDesignator.getDesignator().obj);
	}

	@Override
	public void visit(FactorFuncCall factorFuncCall) {
		Obj methodObj = currFuncCallMethodObj;
		if (methodObj.getFpPos() == 0) {
			int offset = methodObj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		} else {
			Code.put(Code.getfield);
			Code.put2(0);
			Code.put(Code.invokevirtual);
			String methodName = methodObj.getName();
			for (int i = 0; i < methodName.length(); i++) {
				Code.put4(methodName.charAt(i));
			}
			Code.put4(-1);
		}
		// currFuncCallMethodObj = null;
		// currFuncCallInstanceObj = null;
	}

	@Override
	public void visit(FactorNumber factorNumber) {
		Code.loadConst(factorNumber.getN1());
	}

	@Override
	public void visit(FactorChar factorChar) {
		Code.loadConst(factorChar.getC1());
	}

	@Override
	public void visit(FactorBool factorBool) {
		if (factorBool.getB1()) {
			Code.loadConst(1);
		} else {
			Code.loadConst(0);
		}
	}

	@Override
	public void visit(FactorArrAlloc factorArrAlloc) {
		Struct arrType = factorArrAlloc.getType().struct;
		if (arrType.equals(setType)) {
			Code.loadConst(1); // mjArr[0] = size => len = len(set) + 1
			Code.put(Code.add);
		}
		Code.put(Code.newarray);
		if (arrType.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	@Override
	public void visit(FactorInstanceAlloc factorInstanceAlloc) {
		Code.loadConst(currInstanceAllocClassObj.getAdr());
		Code.put(Code.putfield);
		Code.put2(0);
		currInstanceAllocClassObj = null;
		currFuncCallMethodObj = null;
		currFuncCallInstanceObj = null;
	}
	
	@Override
	public void visit(FuncCallMethod funcCallMethod) {
		currFuncCallMethodObj = funcCallMethod.obj;
		int currFuncCallMethodFpPos = currFuncCallMethodObj.getFpPos();
		if (currFuncCallMethodFpPos == 1 || currFuncCallMethodFpPos == 3) {
			if (currFuncCallInstanceObj == null) {
				Code.put(Code.load_n); // this
			}
			Code.put(Code.dup);
		}
	}
	
	@Override
	public void visit(FuncCallInstance funcCallInstance) {
		currFuncCallInstanceObj = funcCallInstance.obj;
	}
	
	@Override
	public void visit(ActPar actPar) {
		int currFuncCallMethodFpPos = currFuncCallMethodObj.getFpPos();
		if (currFuncCallMethodFpPos == 1 || currFuncCallMethodFpPos == 3) {
			Code.put(Code.dup_x1);
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(ConstructorCall constructorCall) {
		Obj constructorObj = constructorCall.obj;
		if (constructorObj != Tab.noObj && constructorObj.getAdr() != -1) {
			int offset = constructorObj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}
	}
	
	@Override
	public void visit(ConstructorCallType constructorCallType) {
		currInstanceAllocClassObj = constructorCallType.obj;
		int numberOfFields = currInstanceAllocClassObj.getType().getNumberOfFields();
		int numberOfBytes = numberOfFields * 4;
		Code.put(Code.new_);
		Code.put2(numberOfBytes);
		Code.put(Code.dup);
	}

	// Condition [BEGIN]

	@Override
	public void visit(ConditionValid conditionValid) {
		// condition is false [FALSE PATH]
		// if statement: jump to else block or to end of if statement 2
		// do while statement: jump to end of do while statement
		Code.putJump(0);
		conditionFalsePatchAdrStack.push(Code.pc - 2);
		// condition is true [TRUE PATH]
		while (!condTermTruePatchAdrList.isEmpty()) {
			Code.fixup(condTermTruePatchAdrList.remove(0));
		}
		// end of condition
		// if statement: continue to then block
		// do while statement: continue to end of do while condition 1
	}

	@Override
	public void visit(CondTerm condTerm) {
		// cond term is true [TRUE PATH]
		Code.putJump(0); // jump to condition is true
		condTermTruePatchAdrList.add(Code.pc - 2);
		// cond term is false [FALSE PATH]
		while (!condFactFalsePatchAdrList.isEmpty()) {
			Code.fixup(condFactFalsePatchAdrList.remove(0));
		}
		// continue to condition is false
	}

	@Override
	public void visit(CondFactSimple condFactSimple) {
		// begin of condition [PATH BEGIN]
		Code.loadConst(1); // true
		Code.putFalseJump(Code.eq, 0); // if cond fact is false, jump to cond term is false [FALSE PATH]
		condFactFalsePatchAdrList.add(Code.pc - 2);
		// cond fact is true [TRUE PATH]
		// continue to cond term is true
	}

	@Override
	public void visit(CondFactRelop condFactRelop) {
		// begin of condition [PATH BEGIN]
		int relop;
		switch (condFactRelop.getRelop().string) {
		case "==":
			relop = Code.eq;
			break;
		case "!=":
			relop = Code.ne;
			break;
		case ">":
			relop = Code.gt;
			break;
		case ">=":
			relop = Code.ge;
			break;
		case "<":
			relop = Code.lt;
			break;
		case "<=":
			relop = Code.le;
			break;
		default:
			relop = -1; // should never happen
		}
		Code.putFalseJump(relop, 0); // if cond fact is false, jump to cond term is false [FALSE PATH]
		condFactFalsePatchAdrList.add(Code.pc - 2);
		// cond fact is true [TRUE PATH]
		// continue to cond term is true
	}

	// Condition [END]

	// If Statement [BEGIN]

	@Override
	public void visit(ElseBlockExists elseBlockExists) {
		// end of if statement 1 [TRUE PATH] [FALSE PATH]
		if (!elseBeginTruePatchAdrStack.empty()) {
			Code.fixup(elseBeginTruePatchAdrStack.pop());
		}
		// [PATH END]
	}

	@Override
	public void visit(ElseBlockEpsilon elseBlockEpsilon) {
		// end of if statement 2 [TRUE PATH] [FALSE PATH]
		if (!conditionFalsePatchAdrStack.empty()) {
			Code.fixup(conditionFalsePatchAdrStack.pop());
		}
		// [PATH END]
	}

	@Override
	public void visit(ElseBegin elseBegin) {
		// then block [TRUE PATH]
		Code.putJump(0); // jump to end of if statement 1
		elseBeginTruePatchAdrStack.push(Code.pc - 2);
		// else block [FALSE PATH]
		if (!conditionFalsePatchAdrStack.empty()) {
			Code.fixup(conditionFalsePatchAdrStack.pop());
		}
	}

	// If Statement [END]

	// Do While Statement [BEGIN]

	@Override
	public void visit(DoWhileBegin doWhileBegin) {
		// begin of do while statement
		doWhileBeginAdrStack.push(Code.pc);
		breakPatchAdrListStack.push(new ArrayList<Integer>());
		continuePatchAdrListStack.push(new ArrayList<Integer>());
	}

	@Override
	public void visit(DoWhileCondExists doWhileCondExists) {
		// end of do while condition 1 [TRUE PATH]
		if (!doWhileBeginAdrStack.empty()) {
			Code.putJump(doWhileBeginAdrStack.pop()); // jump to begin of do while statement
		}
	}

	@Override
	public void visit(DoWhileCondEpsilon doWhileCondEpsilon) {
		// end of do while condition 2 [TRUE PATH]
		if (!doWhileBeginAdrStack.empty()) {
			Code.putJump(doWhileBeginAdrStack.pop()); // jump to begin of do while statement
		}
	}

	@Override
	public void visit(DoWhileCondBegin doWhileCondBegin) {
		// begin of do while condition
		if (!continuePatchAdrListStack.empty()) {
			List<Integer> continuePatchAdrList = continuePatchAdrListStack.pop();
			while (!continuePatchAdrList.isEmpty()) {
				Code.fixup(continuePatchAdrList.remove(0));
			}
		}
	}

	// Do While Statement [END]

	@Override
	public void visit(PrintWidthExists printWidthExists) {
		Code.loadConst(printWidthExists.getN1());
	}

	@Override
	public void visit(PrintWidthEpsilon printWidthEpsilon) {
		Code.loadConst(0);
	}

}

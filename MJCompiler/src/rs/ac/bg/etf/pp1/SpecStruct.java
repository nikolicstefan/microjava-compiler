package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SpecStruct extends Struct {
	
	public static final int Set = 8;

	public SpecStruct(int kind) {
		super(kind);
	}

	public SpecStruct(int kind, Struct elemType) {
		super(kind, elemType);
	}

	public SpecStruct(int kind, SymbolDataStructure members) {
		super(kind, members);
	}
	
	@Override
	public boolean isRefType() {
		return super.isRefType() || getKind() == Set;
	}

}

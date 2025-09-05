// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class FactorListMulop extends FactorList {

    private FactorList FactorList;
    private Mulop Mulop;
    private SignedFactor SignedFactor;

    public FactorListMulop (FactorList FactorList, Mulop Mulop, SignedFactor SignedFactor) {
        this.FactorList=FactorList;
        if(FactorList!=null) FactorList.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.SignedFactor=SignedFactor;
        if(SignedFactor!=null) SignedFactor.setParent(this);
    }

    public FactorList getFactorList() {
        return FactorList;
    }

    public void setFactorList(FactorList FactorList) {
        this.FactorList=FactorList;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public SignedFactor getSignedFactor() {
        return SignedFactor;
    }

    public void setSignedFactor(SignedFactor SignedFactor) {
        this.SignedFactor=SignedFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorList!=null) FactorList.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(SignedFactor!=null) SignedFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorList!=null) FactorList.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(SignedFactor!=null) SignedFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorList!=null) FactorList.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(SignedFactor!=null) SignedFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorListMulop(\n");

        if(FactorList!=null)
            buffer.append(FactorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SignedFactor!=null)
            buffer.append(SignedFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorListMulop]");
        return buffer.toString();
    }
}

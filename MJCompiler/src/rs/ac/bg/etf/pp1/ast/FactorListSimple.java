// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class FactorListSimple extends FactorList {

    private SignedFactor SignedFactor;

    public FactorListSimple (SignedFactor SignedFactor) {
        this.SignedFactor=SignedFactor;
        if(SignedFactor!=null) SignedFactor.setParent(this);
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
        if(SignedFactor!=null) SignedFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SignedFactor!=null) SignedFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SignedFactor!=null) SignedFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorListSimple(\n");

        if(SignedFactor!=null)
            buffer.append(SignedFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorListSimple]");
        return buffer.toString();
    }
}

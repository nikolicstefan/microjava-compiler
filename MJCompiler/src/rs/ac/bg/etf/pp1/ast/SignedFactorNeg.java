// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class SignedFactorNeg extends SignedFactor {

    private String M1;
    private Factor Factor;

    public SignedFactorNeg (String M1, Factor Factor) {
        this.M1=M1;
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public String getM1() {
        return M1;
    }

    public void setM1(String M1) {
        this.M1=M1;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SignedFactorNeg(\n");

        buffer.append(" "+tab+M1);
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SignedFactorNeg]");
        return buffer.toString();
    }
}

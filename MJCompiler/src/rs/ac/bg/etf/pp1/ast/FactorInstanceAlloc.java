// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class FactorInstanceAlloc extends Factor {

    private ConstructorCall ConstructorCall;

    public FactorInstanceAlloc (ConstructorCall ConstructorCall) {
        this.ConstructorCall=ConstructorCall;
        if(ConstructorCall!=null) ConstructorCall.setParent(this);
    }

    public ConstructorCall getConstructorCall() {
        return ConstructorCall;
    }

    public void setConstructorCall(ConstructorCall ConstructorCall) {
        this.ConstructorCall=ConstructorCall;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorCall!=null) ConstructorCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorCall!=null) ConstructorCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorCall!=null) ConstructorCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorInstanceAlloc(\n");

        if(ConstructorCall!=null)
            buffer.append(ConstructorCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorInstanceAlloc]");
        return buffer.toString();
    }
}

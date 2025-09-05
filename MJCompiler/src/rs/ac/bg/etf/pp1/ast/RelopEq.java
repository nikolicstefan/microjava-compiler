// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class RelopEq extends Relop {

    private String eq;

    public RelopEq (String eq) {
        this.eq=eq;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq=eq;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelopEq(\n");

        buffer.append(" "+tab+eq);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RelopEq]");
        return buffer.toString();
    }
}

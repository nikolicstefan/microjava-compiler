// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class RelopGe extends Relop {

    private String ge;

    public RelopGe (String ge) {
        this.ge=ge;
    }

    public String getGe() {
        return ge;
    }

    public void setGe(String ge) {
        this.ge=ge;
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
        buffer.append("RelopGe(\n");

        buffer.append(" "+tab+ge);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RelopGe]");
        return buffer.toString();
    }
}

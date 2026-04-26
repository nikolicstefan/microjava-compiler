// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class AddopMinus extends Addop {

    private String minus;

    public AddopMinus (String minus) {
        this.minus=minus;
    }

    public String getMinus() {
        return minus;
    }

    public void setMinus(String minus) {
        this.minus=minus;
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
        buffer.append("AddopMinus(\n");

        buffer.append(" "+tab+minus);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddopMinus]");
        return buffer.toString();
    }
}

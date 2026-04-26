// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class MulopDiv extends Mulop {

    private String div;

    public MulopDiv (String div) {
        this.div=div;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div=div;
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
        buffer.append("MulopDiv(\n");

        buffer.append(" "+tab+div);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulopDiv]");
        return buffer.toString();
    }
}

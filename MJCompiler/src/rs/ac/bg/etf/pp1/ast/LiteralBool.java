// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class LiteralBool extends Literal {

    private String boolLiteral;

    public LiteralBool (String boolLiteral) {
        this.boolLiteral=boolLiteral;
    }

    public String getBoolLiteral() {
        return boolLiteral;
    }

    public void setBoolLiteral(String boolLiteral) {
        this.boolLiteral=boolLiteral;
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
        buffer.append("LiteralBool(\n");

        buffer.append(" "+tab+boolLiteral);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LiteralBool]");
        return buffer.toString();
    }
}

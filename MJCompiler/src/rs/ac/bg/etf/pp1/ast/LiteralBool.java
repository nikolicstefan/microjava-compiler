// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class LiteralBool extends Literal {

    private Boolean boolLiteral;

    public LiteralBool (Boolean boolLiteral) {
        this.boolLiteral=boolLiteral;
    }

    public Boolean getBoolLiteral() {
        return boolLiteral;
    }

    public void setBoolLiteral(Boolean boolLiteral) {
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

// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class LiteralNumberPos extends Literal {

    private Integer numberLiteral;

    public LiteralNumberPos (Integer numberLiteral) {
        this.numberLiteral=numberLiteral;
    }

    public Integer getNumberLiteral() {
        return numberLiteral;
    }

    public void setNumberLiteral(Integer numberLiteral) {
        this.numberLiteral=numberLiteral;
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
        buffer.append("LiteralNumberPos(\n");

        buffer.append(" "+tab+numberLiteral);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LiteralNumberPos]");
        return buffer.toString();
    }
}

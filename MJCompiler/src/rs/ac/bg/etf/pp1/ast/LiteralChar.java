// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class LiteralChar extends Literal {

    private Character charLiteral;

    public LiteralChar (Character charLiteral) {
        this.charLiteral=charLiteral;
    }

    public Character getCharLiteral() {
        return charLiteral;
    }

    public void setCharLiteral(Character charLiteral) {
        this.charLiteral=charLiteral;
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
        buffer.append("LiteralChar(\n");

        buffer.append(" "+tab+charLiteral);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LiteralChar]");
        return buffer.toString();
    }
}

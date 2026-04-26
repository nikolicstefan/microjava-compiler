// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class LiteralNumberNeg extends Literal {

    private String M1;
    private Integer numberLiteral;

    public LiteralNumberNeg (String M1, Integer numberLiteral) {
        this.M1=M1;
        this.numberLiteral=numberLiteral;
    }

    public String getM1() {
        return M1;
    }

    public void setM1(String M1) {
        this.M1=M1;
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
        buffer.append("LiteralNumberNeg(\n");

        buffer.append(" "+tab+M1);
        buffer.append("\n");

        buffer.append(" "+tab+numberLiteral);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LiteralNumberNeg]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArrName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String designatorArrName;

    public DesignatorArrName (String designatorArrName) {
        this.designatorArrName=designatorArrName;
    }

    public String getDesignatorArrName() {
        return designatorArrName;
    }

    public void setDesignatorArrName(String designatorArrName) {
        this.designatorArrName=designatorArrName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("DesignatorArrName(\n");

        buffer.append(" "+tab+designatorArrName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArrName]");
        return buffer.toString();
    }
}

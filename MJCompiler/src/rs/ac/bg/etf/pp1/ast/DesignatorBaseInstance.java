// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorBaseInstance implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String designatorInstanceName;

    public DesignatorBaseInstance (String designatorInstanceName) {
        this.designatorInstanceName=designatorInstanceName;
    }

    public String getDesignatorInstanceName() {
        return designatorInstanceName;
    }

    public void setDesignatorInstanceName(String designatorInstanceName) {
        this.designatorInstanceName=designatorInstanceName;
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
        buffer.append("DesignatorBaseInstance(\n");

        buffer.append(" "+tab+designatorInstanceName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorBaseInstance]");
        return buffer.toString();
    }
}

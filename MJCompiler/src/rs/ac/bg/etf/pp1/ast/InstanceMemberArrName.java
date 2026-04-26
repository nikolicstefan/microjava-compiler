// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class InstanceMemberArrName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String memberArrName;

    public InstanceMemberArrName (String memberArrName) {
        this.memberArrName=memberArrName;
    }

    public String getMemberArrName() {
        return memberArrName;
    }

    public void setMemberArrName(String memberArrName) {
        this.memberArrName=memberArrName;
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
        buffer.append("InstanceMemberArrName(\n");

        buffer.append(" "+tab+memberArrName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InstanceMemberArrName]");
        return buffer.toString();
    }
}

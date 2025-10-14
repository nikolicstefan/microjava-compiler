// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class MemberArrName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private InstanceMemberArrName InstanceMemberArrName;

    public MemberArrName (InstanceMemberArrName InstanceMemberArrName) {
        this.InstanceMemberArrName=InstanceMemberArrName;
        if(InstanceMemberArrName!=null) InstanceMemberArrName.setParent(this);
    }

    public InstanceMemberArrName getInstanceMemberArrName() {
        return InstanceMemberArrName;
    }

    public void setInstanceMemberArrName(InstanceMemberArrName InstanceMemberArrName) {
        this.InstanceMemberArrName=InstanceMemberArrName;
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
        if(InstanceMemberArrName!=null) InstanceMemberArrName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InstanceMemberArrName!=null) InstanceMemberArrName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InstanceMemberArrName!=null) InstanceMemberArrName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MemberArrName(\n");

        if(InstanceMemberArrName!=null)
            buffer.append(InstanceMemberArrName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MemberArrName]");
        return buffer.toString();
    }
}

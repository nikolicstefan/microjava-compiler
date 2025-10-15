// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private InterfaceDeclName InterfaceDeclName;
    private InterfaceMethodDeclList InterfaceMethodDeclList;

    public InterfaceDecl (InterfaceDeclName InterfaceDeclName, InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceDeclName=InterfaceDeclName;
        if(InterfaceDeclName!=null) InterfaceDeclName.setParent(this);
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.setParent(this);
    }

    public InterfaceDeclName getInterfaceDeclName() {
        return InterfaceDeclName;
    }

    public void setInterfaceDeclName(InterfaceDeclName InterfaceDeclName) {
        this.InterfaceDeclName=InterfaceDeclName;
    }

    public InterfaceMethodDeclList getInterfaceMethodDeclList() {
        return InterfaceMethodDeclList;
    }

    public void setInterfaceMethodDeclList(InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
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
        if(InterfaceDeclName!=null) InterfaceDeclName.accept(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceDeclName!=null) InterfaceDeclName.traverseTopDown(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceDeclName!=null) InterfaceDeclName.traverseBottomUp(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDecl(\n");

        if(InterfaceDeclName!=null)
            buffer.append(InterfaceDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodDeclList!=null)
            buffer.append(InterfaceMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDecl]");
        return buffer.toString();
    }
}

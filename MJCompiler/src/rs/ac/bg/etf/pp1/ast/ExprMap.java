// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class ExprMap extends Expr {

    private ExprMapMethodName ExprMapMethodName;
    private ExprMapArrName ExprMapArrName;

    public ExprMap (ExprMapMethodName ExprMapMethodName, ExprMapArrName ExprMapArrName) {
        this.ExprMapMethodName=ExprMapMethodName;
        if(ExprMapMethodName!=null) ExprMapMethodName.setParent(this);
        this.ExprMapArrName=ExprMapArrName;
        if(ExprMapArrName!=null) ExprMapArrName.setParent(this);
    }

    public ExprMapMethodName getExprMapMethodName() {
        return ExprMapMethodName;
    }

    public void setExprMapMethodName(ExprMapMethodName ExprMapMethodName) {
        this.ExprMapMethodName=ExprMapMethodName;
    }

    public ExprMapArrName getExprMapArrName() {
        return ExprMapArrName;
    }

    public void setExprMapArrName(ExprMapArrName ExprMapArrName) {
        this.ExprMapArrName=ExprMapArrName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprMapMethodName!=null) ExprMapMethodName.accept(visitor);
        if(ExprMapArrName!=null) ExprMapArrName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprMapMethodName!=null) ExprMapMethodName.traverseTopDown(visitor);
        if(ExprMapArrName!=null) ExprMapArrName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprMapMethodName!=null) ExprMapMethodName.traverseBottomUp(visitor);
        if(ExprMapArrName!=null) ExprMapArrName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprMap(\n");

        if(ExprMapMethodName!=null)
            buffer.append(ExprMapMethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprMapArrName!=null)
            buffer.append(ExprMapArrName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprMap]");
        return buffer.toString();
    }
}

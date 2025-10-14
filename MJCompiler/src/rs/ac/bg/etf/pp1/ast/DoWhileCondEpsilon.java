// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class DoWhileCondEpsilon extends DoWhileCond {

    private DoWhileCondBegin DoWhileCondBegin;

    public DoWhileCondEpsilon (DoWhileCondBegin DoWhileCondBegin) {
        this.DoWhileCondBegin=DoWhileCondBegin;
        if(DoWhileCondBegin!=null) DoWhileCondBegin.setParent(this);
    }

    public DoWhileCondBegin getDoWhileCondBegin() {
        return DoWhileCondBegin;
    }

    public void setDoWhileCondBegin(DoWhileCondBegin DoWhileCondBegin) {
        this.DoWhileCondBegin=DoWhileCondBegin;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileCondBegin!=null) DoWhileCondBegin.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileCondBegin!=null) DoWhileCondBegin.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileCondBegin!=null) DoWhileCondBegin.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileCondEpsilon(\n");

        if(DoWhileCondBegin!=null)
            buffer.append(DoWhileCondBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileCondEpsilon]");
        return buffer.toString();
    }
}

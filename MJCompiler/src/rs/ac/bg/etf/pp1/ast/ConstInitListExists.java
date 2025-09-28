// generated with ast extension for cup
// version 0.8
// 26/8/2025 16:11:53


package rs.ac.bg.etf.pp1.ast;

public class ConstInitListExists extends ConstInitList {

    private ConstInit ConstInit;
    private ConstInitList ConstInitList;

    public ConstInitListExists (ConstInit ConstInit, ConstInitList ConstInitList) {
        this.ConstInit=ConstInit;
        if(ConstInit!=null) ConstInit.setParent(this);
        this.ConstInitList=ConstInitList;
        if(ConstInitList!=null) ConstInitList.setParent(this);
    }

    public ConstInit getConstInit() {
        return ConstInit;
    }

    public void setConstInit(ConstInit ConstInit) {
        this.ConstInit=ConstInit;
    }

    public ConstInitList getConstInitList() {
        return ConstInitList;
    }

    public void setConstInitList(ConstInitList ConstInitList) {
        this.ConstInitList=ConstInitList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstInit!=null) ConstInit.accept(visitor);
        if(ConstInitList!=null) ConstInitList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstInit!=null) ConstInit.traverseTopDown(visitor);
        if(ConstInitList!=null) ConstInitList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstInit!=null) ConstInit.traverseBottomUp(visitor);
        if(ConstInitList!=null) ConstInitList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstInitListExists(\n");

        if(ConstInit!=null)
            buffer.append(ConstInit.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstInitList!=null)
            buffer.append(ConstInitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstInitListExists]");
        return buffer.toString();
    }
}

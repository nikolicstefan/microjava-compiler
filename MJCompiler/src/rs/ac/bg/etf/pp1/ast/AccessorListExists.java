// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class AccessorListExists extends AccessorList {

    private Accessor Accessor;
    private AccessorList AccessorList;

    public AccessorListExists (Accessor Accessor, AccessorList AccessorList) {
        this.Accessor=Accessor;
        if(Accessor!=null) Accessor.setParent(this);
        this.AccessorList=AccessorList;
        if(AccessorList!=null) AccessorList.setParent(this);
    }

    public Accessor getAccessor() {
        return Accessor;
    }

    public void setAccessor(Accessor Accessor) {
        this.Accessor=Accessor;
    }

    public AccessorList getAccessorList() {
        return AccessorList;
    }

    public void setAccessorList(AccessorList AccessorList) {
        this.AccessorList=AccessorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Accessor!=null) Accessor.accept(visitor);
        if(AccessorList!=null) AccessorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Accessor!=null) Accessor.traverseTopDown(visitor);
        if(AccessorList!=null) AccessorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Accessor!=null) Accessor.traverseBottomUp(visitor);
        if(AccessorList!=null) AccessorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AccessorListExists(\n");

        if(Accessor!=null)
            buffer.append(Accessor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AccessorList!=null)
            buffer.append(AccessorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AccessorListExists]");
        return buffer.toString();
    }
}

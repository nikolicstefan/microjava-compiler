// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorMemberSelector extends Designator {

    private DesignatorBaseInstance DesignatorBaseInstance;
    private SelectorList SelectorList;

    public DesignatorMemberSelector (DesignatorBaseInstance DesignatorBaseInstance, SelectorList SelectorList) {
        this.DesignatorBaseInstance=DesignatorBaseInstance;
        if(DesignatorBaseInstance!=null) DesignatorBaseInstance.setParent(this);
        this.SelectorList=SelectorList;
        if(SelectorList!=null) SelectorList.setParent(this);
    }

    public DesignatorBaseInstance getDesignatorBaseInstance() {
        return DesignatorBaseInstance;
    }

    public void setDesignatorBaseInstance(DesignatorBaseInstance DesignatorBaseInstance) {
        this.DesignatorBaseInstance=DesignatorBaseInstance;
    }

    public SelectorList getSelectorList() {
        return SelectorList;
    }

    public void setSelectorList(SelectorList SelectorList) {
        this.SelectorList=SelectorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorBaseInstance!=null) DesignatorBaseInstance.accept(visitor);
        if(SelectorList!=null) SelectorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorBaseInstance!=null) DesignatorBaseInstance.traverseTopDown(visitor);
        if(SelectorList!=null) SelectorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorBaseInstance!=null) DesignatorBaseInstance.traverseBottomUp(visitor);
        if(SelectorList!=null) SelectorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorMemberSelector(\n");

        if(DesignatorBaseInstance!=null)
            buffer.append(DesignatorBaseInstance.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SelectorList!=null)
            buffer.append(SelectorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorMemberSelector]");
        return buffer.toString();
    }
}

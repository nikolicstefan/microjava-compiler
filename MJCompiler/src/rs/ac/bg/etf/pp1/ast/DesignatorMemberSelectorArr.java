// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class DesignatorMemberSelectorArr extends Designator {

    private DesignatorBaseInstanceArr DesignatorBaseInstanceArr;
    private SelectorList SelectorList;

    public DesignatorMemberSelectorArr (DesignatorBaseInstanceArr DesignatorBaseInstanceArr, SelectorList SelectorList) {
        this.DesignatorBaseInstanceArr=DesignatorBaseInstanceArr;
        if(DesignatorBaseInstanceArr!=null) DesignatorBaseInstanceArr.setParent(this);
        this.SelectorList=SelectorList;
        if(SelectorList!=null) SelectorList.setParent(this);
    }

    public DesignatorBaseInstanceArr getDesignatorBaseInstanceArr() {
        return DesignatorBaseInstanceArr;
    }

    public void setDesignatorBaseInstanceArr(DesignatorBaseInstanceArr DesignatorBaseInstanceArr) {
        this.DesignatorBaseInstanceArr=DesignatorBaseInstanceArr;
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
        if(DesignatorBaseInstanceArr!=null) DesignatorBaseInstanceArr.accept(visitor);
        if(SelectorList!=null) SelectorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorBaseInstanceArr!=null) DesignatorBaseInstanceArr.traverseTopDown(visitor);
        if(SelectorList!=null) SelectorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorBaseInstanceArr!=null) DesignatorBaseInstanceArr.traverseBottomUp(visitor);
        if(SelectorList!=null) SelectorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorMemberSelectorArr(\n");

        if(DesignatorBaseInstanceArr!=null)
            buffer.append(DesignatorBaseInstanceArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SelectorList!=null)
            buffer.append(SelectorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorMemberSelectorArr]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class SelectorListMember extends SelectorList {

    private SelectorList SelectorList;
    private String memberName;

    public SelectorListMember (SelectorList SelectorList, String memberName) {
        this.SelectorList=SelectorList;
        if(SelectorList!=null) SelectorList.setParent(this);
        this.memberName=memberName;
    }

    public SelectorList getSelectorList() {
        return SelectorList;
    }

    public void setSelectorList(SelectorList SelectorList) {
        this.SelectorList=SelectorList;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName=memberName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SelectorList!=null) SelectorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SelectorList!=null) SelectorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SelectorList!=null) SelectorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SelectorListMember(\n");

        if(SelectorList!=null)
            buffer.append(SelectorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+memberName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SelectorListMember]");
        return buffer.toString();
    }
}

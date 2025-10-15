// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class SelectorListMemberArr extends SelectorList {

    private SelectorList SelectorList;
    private MemberArrName MemberArrName;
    private Expr Expr;

    public SelectorListMemberArr (SelectorList SelectorList, MemberArrName MemberArrName, Expr Expr) {
        this.SelectorList=SelectorList;
        if(SelectorList!=null) SelectorList.setParent(this);
        this.MemberArrName=MemberArrName;
        if(MemberArrName!=null) MemberArrName.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public SelectorList getSelectorList() {
        return SelectorList;
    }

    public void setSelectorList(SelectorList SelectorList) {
        this.SelectorList=SelectorList;
    }

    public MemberArrName getMemberArrName() {
        return MemberArrName;
    }

    public void setMemberArrName(MemberArrName MemberArrName) {
        this.MemberArrName=MemberArrName;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SelectorList!=null) SelectorList.accept(visitor);
        if(MemberArrName!=null) MemberArrName.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SelectorList!=null) SelectorList.traverseTopDown(visitor);
        if(MemberArrName!=null) MemberArrName.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SelectorList!=null) SelectorList.traverseBottomUp(visitor);
        if(MemberArrName!=null) MemberArrName.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SelectorListMemberArr(\n");

        if(SelectorList!=null)
            buffer.append(SelectorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MemberArrName!=null)
            buffer.append(MemberArrName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SelectorListMemberArr]");
        return buffer.toString();
    }
}

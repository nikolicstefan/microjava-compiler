// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class AssignValueSet extends AssignValue {

    private AssignValueSetSrc AssignValueSetSrc;
    private Setop Setop;
    private AssignValueSetSrc AssignValueSetSrc1;

    public AssignValueSet (AssignValueSetSrc AssignValueSetSrc, Setop Setop, AssignValueSetSrc AssignValueSetSrc1) {
        this.AssignValueSetSrc=AssignValueSetSrc;
        if(AssignValueSetSrc!=null) AssignValueSetSrc.setParent(this);
        this.Setop=Setop;
        if(Setop!=null) Setop.setParent(this);
        this.AssignValueSetSrc1=AssignValueSetSrc1;
        if(AssignValueSetSrc1!=null) AssignValueSetSrc1.setParent(this);
    }

    public AssignValueSetSrc getAssignValueSetSrc() {
        return AssignValueSetSrc;
    }

    public void setAssignValueSetSrc(AssignValueSetSrc AssignValueSetSrc) {
        this.AssignValueSetSrc=AssignValueSetSrc;
    }

    public Setop getSetop() {
        return Setop;
    }

    public void setSetop(Setop Setop) {
        this.Setop=Setop;
    }

    public AssignValueSetSrc getAssignValueSetSrc1() {
        return AssignValueSetSrc1;
    }

    public void setAssignValueSetSrc1(AssignValueSetSrc AssignValueSetSrc1) {
        this.AssignValueSetSrc1=AssignValueSetSrc1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignValueSetSrc!=null) AssignValueSetSrc.accept(visitor);
        if(Setop!=null) Setop.accept(visitor);
        if(AssignValueSetSrc1!=null) AssignValueSetSrc1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignValueSetSrc!=null) AssignValueSetSrc.traverseTopDown(visitor);
        if(Setop!=null) Setop.traverseTopDown(visitor);
        if(AssignValueSetSrc1!=null) AssignValueSetSrc1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignValueSetSrc!=null) AssignValueSetSrc.traverseBottomUp(visitor);
        if(Setop!=null) Setop.traverseBottomUp(visitor);
        if(AssignValueSetSrc1!=null) AssignValueSetSrc1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignValueSet(\n");

        if(AssignValueSetSrc!=null)
            buffer.append(AssignValueSetSrc.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Setop!=null)
            buffer.append(Setop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AssignValueSetSrc1!=null)
            buffer.append(AssignValueSetSrc1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignValueSet]");
        return buffer.toString();
    }
}

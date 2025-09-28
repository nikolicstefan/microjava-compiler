// generated with ast extension for cup
// version 0.8
// 26/8/2025 16:11:53


package rs.ac.bg.etf.pp1.ast;

public class AssignValueSet extends AssignValue {

    private Designator Designator;
    private Setop Setop;
    private Designator Designator1;

    public AssignValueSet (Designator Designator, Setop Setop, Designator Designator1) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Setop=Setop;
        if(Setop!=null) Setop.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Setop getSetop() {
        return Setop;
    }

    public void setSetop(Setop Setop) {
        this.Setop=Setop;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Setop!=null) Setop.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Setop!=null) Setop.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Setop!=null) Setop.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignValueSet(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Setop!=null)
            buffer.append(Setop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignValueSet]");
        return buffer.toString();
    }
}

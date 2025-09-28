// generated with ast extension for cup
// version 0.8
// 26/8/2025 16:11:53


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssign extends DesignatorStatement {

    private AssignDesignator AssignDesignator;
    private Assignop Assignop;
    private AssignValue AssignValue;

    public DesignatorStatementAssign (AssignDesignator AssignDesignator, Assignop Assignop, AssignValue AssignValue) {
        this.AssignDesignator=AssignDesignator;
        if(AssignDesignator!=null) AssignDesignator.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.AssignValue=AssignValue;
        if(AssignValue!=null) AssignValue.setParent(this);
    }

    public AssignDesignator getAssignDesignator() {
        return AssignDesignator;
    }

    public void setAssignDesignator(AssignDesignator AssignDesignator) {
        this.AssignDesignator=AssignDesignator;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public AssignValue getAssignValue() {
        return AssignValue;
    }

    public void setAssignValue(AssignValue AssignValue) {
        this.AssignValue=AssignValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignDesignator!=null) AssignDesignator.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(AssignValue!=null) AssignValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignDesignator!=null) AssignDesignator.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(AssignValue!=null) AssignValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignDesignator!=null) AssignDesignator.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(AssignValue!=null) AssignValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementAssign(\n");

        if(AssignDesignator!=null)
            buffer.append(AssignDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AssignValue!=null)
            buffer.append(AssignValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssign]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 14/9/2025 12:1:24


package rs.ac.bg.etf.pp1.ast;

public class FactorFuncCall extends Factor {

    private FuncCallMethod FuncCallMethod;
    private ActPars ActPars;

    public FactorFuncCall (FuncCallMethod FuncCallMethod, ActPars ActPars) {
        this.FuncCallMethod=FuncCallMethod;
        if(FuncCallMethod!=null) FuncCallMethod.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public FuncCallMethod getFuncCallMethod() {
        return FuncCallMethod;
    }

    public void setFuncCallMethod(FuncCallMethod FuncCallMethod) {
        this.FuncCallMethod=FuncCallMethod;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncCallMethod!=null) FuncCallMethod.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncCallMethod!=null) FuncCallMethod.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncCallMethod!=null) FuncCallMethod.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorFuncCall(\n");

        if(FuncCallMethod!=null)
            buffer.append(FuncCallMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorFuncCall]");
        return buffer.toString();
    }
}

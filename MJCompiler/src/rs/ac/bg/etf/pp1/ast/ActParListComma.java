// generated with ast extension for cup
// version 0.8
// 8/8/2025 21:3:19


package rs.ac.bg.etf.pp1.ast;

public class ActParListComma extends ActParList {

    private ActPar ActPar;
    private ActParList ActParList;

    public ActParListComma (ActPar ActPar, ActParList ActParList) {
        this.ActPar=ActPar;
        if(ActPar!=null) ActPar.setParent(this);
        this.ActParList=ActParList;
        if(ActParList!=null) ActParList.setParent(this);
    }

    public ActPar getActPar() {
        return ActPar;
    }

    public void setActPar(ActPar ActPar) {
        this.ActPar=ActPar;
    }

    public ActParList getActParList() {
        return ActParList;
    }

    public void setActParList(ActParList ActParList) {
        this.ActParList=ActParList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPar!=null) ActPar.accept(visitor);
        if(ActParList!=null) ActParList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPar!=null) ActPar.traverseTopDown(visitor);
        if(ActParList!=null) ActParList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPar!=null) ActPar.traverseBottomUp(visitor);
        if(ActParList!=null) ActParList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParListComma(\n");

        if(ActPar!=null)
            buffer.append(ActPar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParList!=null)
            buffer.append(ActParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParListComma]");
        return buffer.toString();
    }
}

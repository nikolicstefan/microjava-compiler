// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class VarValid extends Var {

    private String varName;
    private ArrDeclBrackets ArrDeclBrackets;

    public VarValid (String varName, ArrDeclBrackets ArrDeclBrackets) {
        this.varName=varName;
        this.ArrDeclBrackets=ArrDeclBrackets;
        if(ArrDeclBrackets!=null) ArrDeclBrackets.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public ArrDeclBrackets getArrDeclBrackets() {
        return ArrDeclBrackets;
    }

    public void setArrDeclBrackets(ArrDeclBrackets ArrDeclBrackets) {
        this.ArrDeclBrackets=ArrDeclBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrDeclBrackets!=null) ArrDeclBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrDeclBrackets!=null) ArrDeclBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrDeclBrackets!=null) ArrDeclBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarValid(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ArrDeclBrackets!=null)
            buffer.append(ArrDeclBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarValid]");
        return buffer.toString();
    }
}

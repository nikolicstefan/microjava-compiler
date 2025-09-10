// generated with ast extension for cup
// version 0.8
// 8/8/2025 21:3:19


package rs.ac.bg.etf.pp1.ast;

public class FormParValid extends FormPar {

    private Type Type;
    private String formParName;
    private ArrDeclBrackets ArrDeclBrackets;

    public FormParValid (Type Type, String formParName, ArrDeclBrackets ArrDeclBrackets) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formParName=formParName;
        this.ArrDeclBrackets=ArrDeclBrackets;
        if(ArrDeclBrackets!=null) ArrDeclBrackets.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormParName() {
        return formParName;
    }

    public void setFormParName(String formParName) {
        this.formParName=formParName;
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
        if(Type!=null) Type.accept(visitor);
        if(ArrDeclBrackets!=null) ArrDeclBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ArrDeclBrackets!=null) ArrDeclBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ArrDeclBrackets!=null) ArrDeclBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParValid(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formParName);
        buffer.append("\n");

        if(ArrDeclBrackets!=null)
            buffer.append(ArrDeclBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParValid]");
        return buffer.toString();
    }
}

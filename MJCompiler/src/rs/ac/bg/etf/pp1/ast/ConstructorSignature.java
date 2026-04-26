// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class ConstructorSignature implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ConstructorDeclName ConstructorDeclName;
    private FormPars FormPars;

    public ConstructorSignature (ConstructorDeclName ConstructorDeclName, FormPars FormPars) {
        this.ConstructorDeclName=ConstructorDeclName;
        if(ConstructorDeclName!=null) ConstructorDeclName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
    }

    public ConstructorDeclName getConstructorDeclName() {
        return ConstructorDeclName;
    }

    public void setConstructorDeclName(ConstructorDeclName ConstructorDeclName) {
        this.ConstructorDeclName=ConstructorDeclName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorDeclName!=null) ConstructorDeclName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorSignature(\n");

        if(ConstructorDeclName!=null)
            buffer.append(ConstructorDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorSignature]");
        return buffer.toString();
    }
}

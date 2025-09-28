// generated with ast extension for cup
// version 0.8
// 26/8/2025 16:11:53


package rs.ac.bg.etf.pp1.ast;

public class ConstInitValid extends ConstInit {

    private String constName;
    private Literal Literal;

    public ConstInitValid (String constName, Literal Literal) {
        this.constName=constName;
        this.Literal=Literal;
        if(Literal!=null) Literal.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Literal getLiteral() {
        return Literal;
    }

    public void setLiteral(Literal Literal) {
        this.Literal=Literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Literal!=null) Literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Literal!=null) Literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Literal!=null) Literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstInitValid(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Literal!=null)
            buffer.append(Literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstInitValid]");
        return buffer.toString();
    }
}

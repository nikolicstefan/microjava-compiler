// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class FuncCallMethod implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Designator Designator;
    private FuncCallInstance FuncCallInstance;

    public FuncCallMethod (Designator Designator, FuncCallInstance FuncCallInstance) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FuncCallInstance=FuncCallInstance;
        if(FuncCallInstance!=null) FuncCallInstance.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FuncCallInstance getFuncCallInstance() {
        return FuncCallInstance;
    }

    public void setFuncCallInstance(FuncCallInstance FuncCallInstance) {
        this.FuncCallInstance=FuncCallInstance;
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
        if(Designator!=null) Designator.accept(visitor);
        if(FuncCallInstance!=null) FuncCallInstance.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FuncCallInstance!=null) FuncCallInstance.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FuncCallInstance!=null) FuncCallInstance.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCallMethod(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FuncCallInstance!=null)
            buffer.append(FuncCallInstance.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCallMethod]");
        return buffer.toString();
    }
}

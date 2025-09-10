// generated with ast extension for cup
// version 0.8
// 8/8/2025 21:3:19


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends Statement {

    private LoopStart LoopStart;
    private Statement Statement;
    private LoopCond LoopCond;

    public StatementDoWhile (LoopStart LoopStart, Statement Statement, LoopCond LoopCond) {
        this.LoopStart=LoopStart;
        if(LoopStart!=null) LoopStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.LoopCond=LoopCond;
        if(LoopCond!=null) LoopCond.setParent(this);
    }

    public LoopStart getLoopStart() {
        return LoopStart;
    }

    public void setLoopStart(LoopStart LoopStart) {
        this.LoopStart=LoopStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public LoopCond getLoopCond() {
        return LoopCond;
    }

    public void setLoopCond(LoopCond LoopCond) {
        this.LoopCond=LoopCond;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LoopStart!=null) LoopStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(LoopCond!=null) LoopCond.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LoopStart!=null) LoopStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(LoopCond!=null) LoopCond.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LoopStart!=null) LoopStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(LoopCond!=null) LoopCond.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(LoopStart!=null)
            buffer.append(LoopStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LoopCond!=null)
            buffer.append(LoopCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}

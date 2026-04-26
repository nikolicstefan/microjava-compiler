// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends Statement {

    private DoWhileBegin DoWhileBegin;
    private Statement Statement;
    private DoWhileCond DoWhileCond;

    public StatementDoWhile (DoWhileBegin DoWhileBegin, Statement Statement, DoWhileCond DoWhileCond) {
        this.DoWhileBegin=DoWhileBegin;
        if(DoWhileBegin!=null) DoWhileBegin.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.DoWhileCond=DoWhileCond;
        if(DoWhileCond!=null) DoWhileCond.setParent(this);
    }

    public DoWhileBegin getDoWhileBegin() {
        return DoWhileBegin;
    }

    public void setDoWhileBegin(DoWhileBegin DoWhileBegin) {
        this.DoWhileBegin=DoWhileBegin;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public DoWhileCond getDoWhileCond() {
        return DoWhileCond;
    }

    public void setDoWhileCond(DoWhileCond DoWhileCond) {
        this.DoWhileCond=DoWhileCond;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileBegin!=null) DoWhileBegin.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(DoWhileCond!=null) DoWhileCond.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileBegin!=null) DoWhileBegin.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(DoWhileCond!=null) DoWhileCond.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileBegin!=null) DoWhileBegin.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(DoWhileCond!=null) DoWhileCond.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(DoWhileBegin!=null)
            buffer.append(DoWhileBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoWhileCond!=null)
            buffer.append(DoWhileCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Statement {

    private Expr Expr;
    private PrintRepCnt PrintRepCnt;

    public StatementPrint (Expr Expr, PrintRepCnt PrintRepCnt) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.PrintRepCnt=PrintRepCnt;
        if(PrintRepCnt!=null) PrintRepCnt.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public PrintRepCnt getPrintRepCnt() {
        return PrintRepCnt;
    }

    public void setPrintRepCnt(PrintRepCnt PrintRepCnt) {
        this.PrintRepCnt=PrintRepCnt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(PrintRepCnt!=null) PrintRepCnt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(PrintRepCnt!=null) PrintRepCnt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(PrintRepCnt!=null) PrintRepCnt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintRepCnt!=null)
            buffer.append(PrintRepCnt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}

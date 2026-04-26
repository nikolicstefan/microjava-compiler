// generated with ast extension for cup
// version 0.8
// 23/3/2026 10:49:42


package rs.ac.bg.etf.pp1.ast;

public class DoWhileCondExists extends DoWhileCond {

    private DoWhileCondBegin DoWhileCondBegin;
    private Condition Condition;
    private PostIterStmt PostIterStmt;

    public DoWhileCondExists (DoWhileCondBegin DoWhileCondBegin, Condition Condition, PostIterStmt PostIterStmt) {
        this.DoWhileCondBegin=DoWhileCondBegin;
        if(DoWhileCondBegin!=null) DoWhileCondBegin.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.PostIterStmt=PostIterStmt;
        if(PostIterStmt!=null) PostIterStmt.setParent(this);
    }

    public DoWhileCondBegin getDoWhileCondBegin() {
        return DoWhileCondBegin;
    }

    public void setDoWhileCondBegin(DoWhileCondBegin DoWhileCondBegin) {
        this.DoWhileCondBegin=DoWhileCondBegin;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public PostIterStmt getPostIterStmt() {
        return PostIterStmt;
    }

    public void setPostIterStmt(PostIterStmt PostIterStmt) {
        this.PostIterStmt=PostIterStmt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileCondBegin!=null) DoWhileCondBegin.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(PostIterStmt!=null) PostIterStmt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileCondBegin!=null) DoWhileCondBegin.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(PostIterStmt!=null) PostIterStmt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileCondBegin!=null) DoWhileCondBegin.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(PostIterStmt!=null) PostIterStmt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileCondExists(\n");

        if(DoWhileCondBegin!=null)
            buffer.append(DoWhileCondBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PostIterStmt!=null)
            buffer.append(PostIterStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileCondExists]");
        return buffer.toString();
    }
}

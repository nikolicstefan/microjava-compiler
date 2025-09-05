// generated with ast extension for cup
// version 0.8
// 5/8/2025 20:13:24


package rs.ac.bg.etf.pp1.ast;

public class PrintRepCntExists extends PrintRepCnt {

    private Integer N1;

    public PrintRepCntExists (Integer N1) {
        this.N1=N1;
    }

    public Integer getN1() {
        return N1;
    }

    public void setN1(Integer N1) {
        this.N1=N1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintRepCntExists(\n");

        buffer.append(" "+tab+N1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintRepCntExists]");
        return buffer.toString();
    }
}

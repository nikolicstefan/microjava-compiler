// generated with ast extension for cup
// version 0.8
// 26/8/2025 16:11:53


package rs.ac.bg.etf.pp1.ast;

public class MulopMod extends Mulop {

    private String mod;

    public MulopMod (String mod) {
        this.mod=mod;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod=mod;
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
        buffer.append("MulopMod(\n");

        buffer.append(" "+tab+mod);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulopMod]");
        return buffer.toString();
    }
}

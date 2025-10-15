// generated with ast extension for cup
// version 0.8
// 14/9/2025 14:36:5


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMethodDeclListSig extends InterfaceMethodDeclList {

    private MethodSignature MethodSignature;
    private InterfaceMethodDeclSigEnd InterfaceMethodDeclSigEnd;
    private InterfaceMethodDeclList InterfaceMethodDeclList;

    public InterfaceMethodDeclListSig (MethodSignature MethodSignature, InterfaceMethodDeclSigEnd InterfaceMethodDeclSigEnd, InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.InterfaceMethodDeclSigEnd=InterfaceMethodDeclSigEnd;
        if(InterfaceMethodDeclSigEnd!=null) InterfaceMethodDeclSigEnd.setParent(this);
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public InterfaceMethodDeclSigEnd getInterfaceMethodDeclSigEnd() {
        return InterfaceMethodDeclSigEnd;
    }

    public void setInterfaceMethodDeclSigEnd(InterfaceMethodDeclSigEnd InterfaceMethodDeclSigEnd) {
        this.InterfaceMethodDeclSigEnd=InterfaceMethodDeclSigEnd;
    }

    public InterfaceMethodDeclList getInterfaceMethodDeclList() {
        return InterfaceMethodDeclList;
    }

    public void setInterfaceMethodDeclList(InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(InterfaceMethodDeclSigEnd!=null) InterfaceMethodDeclSigEnd.accept(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(InterfaceMethodDeclSigEnd!=null) InterfaceMethodDeclSigEnd.traverseTopDown(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(InterfaceMethodDeclSigEnd!=null) InterfaceMethodDeclSigEnd.traverseBottomUp(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMethodDeclListSig(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodDeclSigEnd!=null)
            buffer.append(InterfaceMethodDeclSigEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodDeclList!=null)
            buffer.append(InterfaceMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMethodDeclListSig]");
        return buffer.toString();
    }
}

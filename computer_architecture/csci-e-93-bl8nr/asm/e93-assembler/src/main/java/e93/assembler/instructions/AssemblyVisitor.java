package e93.assembler.ast;

public interface AssemblyVisitor {
    void visit(Add add);
    void visit(Sub sub);
    void visit(Nand nand);
    void visit(Slt slt);
    void visit(Sgt sgt);
    void visit(Set set);
    void visit(Sll sll);
    void visit(Sra sra);
    void visit(Srl srl);
    void visit(Ori ori);
    void visit(Boz boz);
//    void visit(J j);
    void visit(Sw sw);
    void visit(Lw lw);
    void visit(Lui lui);
}

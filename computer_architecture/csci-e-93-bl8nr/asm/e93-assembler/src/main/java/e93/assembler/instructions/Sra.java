package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Sra extends Instruction {

    public Sra() {
        setOpcode(OpCode.SRA);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Slt extends Instruction {

    public Slt() {
        setOpcode(OpCode.SLT);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
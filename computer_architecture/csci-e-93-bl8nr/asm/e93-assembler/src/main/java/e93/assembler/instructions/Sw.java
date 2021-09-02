package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Sw extends Instruction {

    public Sw() {
        setOpcode(OpCode.SW);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
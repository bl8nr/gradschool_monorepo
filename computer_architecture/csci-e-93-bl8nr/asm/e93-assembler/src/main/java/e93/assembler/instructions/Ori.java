package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Ori extends Instruction {

    public Ori() {
        setOpcode(OpCode.ORI);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
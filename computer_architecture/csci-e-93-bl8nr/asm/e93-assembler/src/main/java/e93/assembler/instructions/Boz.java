package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Boz extends Instruction {

    public Boz() {
        setOpcode(OpCode.BOZ);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Sgt extends Instruction {

    public Sgt() {
        setOpcode(OpCode.SGT);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
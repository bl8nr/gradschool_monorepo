package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Set extends Instruction {

    public Set() {
        setOpcode(OpCode.SET);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
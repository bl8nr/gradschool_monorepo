package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Lui extends Instruction {

    public Lui() {
        setOpcode(OpCode.LUI);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
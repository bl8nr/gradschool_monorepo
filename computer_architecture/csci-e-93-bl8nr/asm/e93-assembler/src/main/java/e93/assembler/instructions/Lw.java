package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Lw extends Instruction {

    public Lw() {
        setOpcode(OpCode.LW);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
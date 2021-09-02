package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Nand extends Instruction {

    public Nand() {
        setOpcode(OpCode.NAND);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
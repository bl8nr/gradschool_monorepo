package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Srl extends Instruction {

    public Srl() {
        setOpcode(OpCode.SRL);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
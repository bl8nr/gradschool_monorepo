package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Sll extends Instruction {

    public Sll() {
        setOpcode(OpCode.SLL);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
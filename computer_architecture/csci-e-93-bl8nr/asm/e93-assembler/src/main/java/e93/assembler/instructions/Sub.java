package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Sub extends Instruction {

    public Sub() {
        setOpcode(OpCode.SUB);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
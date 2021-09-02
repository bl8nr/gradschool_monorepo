package e93.assembler.ast;

import e93.assembler.OpCode;
import e93.assembler.Instruction;

public class Add extends Instruction {

    public Add() {
        setOpcode(OpCode.ADD);
    }

    @Override
    public void accept(final AssemblyVisitor assemblyVisitor) {
        assemblyVisitor.visit(this);
    }
}
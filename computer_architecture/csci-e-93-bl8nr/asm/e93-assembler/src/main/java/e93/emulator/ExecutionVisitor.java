package e93.emulator;

import e93.assembler.ast.Add;
import e93.assembler.ast.Sub;
import e93.assembler.ast.Nand;
import e93.assembler.ast.Slt;
import e93.assembler.ast.Sgt;
import e93.assembler.ast.Set;
import e93.assembler.ast.Sll;
import e93.assembler.ast.Sra;
import e93.assembler.ast.Srl;
import e93.assembler.ast.Ori;
import e93.assembler.ast.Boz;
//import e93.assembler.ast.J;
import e93.assembler.ast.Sw;
import e93.assembler.ast.Lw;
import e93.assembler.ast.Lui;


import e93.assembler.ast.AssemblyVisitor;

import javax.annotation.Generated;

public class ExecutionVisitor implements AssemblyVisitor {

    private int pc = 0;
    private MemorySubsystem memorySubsystem;
    private int[] registers = new int[16];

    public ExecutionVisitor(MemorySubsystem memorySubsystem, int[] registers) {
        this.memorySubsystem = memorySubsystem;
        this.registers = registers;
    }

//    @Override
//    public void visit(final And and) {
//        int op1 = registers[and.getR1()];
//        int op2 = registers[and.getR2()];
//        int result = op1 & op2;
//        registers[and.getR1()] = result;
//        incrementPc();
//    }

    // addition
    @Override
    public void visit(final Add add) {

    }

    // subtraction
    @Override
    public void visit(final Sub sub) {

    }

    // NAND gate
    @Override
    public void visit(final Nand nand) {

    }

    // set on less than
    @Override
    public void visit(final Slt slt) {

    }

    // set on greater than
    @Override
    public void visit(final Sgt sgt) {

    }

    // set on equal to
    @Override
    public void visit(final Set set) {

    }

    // shift left logical
    @Override
    public void visit(final Sll sll) {

    }

    // shift right arithmetic
    @Override
    public void visit(final Sra sra) {

    }

    // shift right logical
    @Override
    public void visit(final Srl srl) {

    }

    // or immediate
    @Override
    public void visit(final Ori ori) {

    }

    // branch on zero
    @Override
    public void visit(final Boz boz) {

    }

    // jump
//    @Override
//    public void visit(final J j) {
//
//    }
//
    // store word
    @Override
    public void visit(final Sw sw) {

    }

    // load word
    @Override
    public void visit(final Lw lw) {

    }

    // load upper immediate
    @Override
    public void visit(final Lui lui) {

    }

//    @Override
//    public void visit(final JumpImmediate jumpImmediate) {
//        incrementPc();
//        int high7bits = pc & (0xff<<9);
//        // immediate has already been shifted
//        int low9bits = jumpImmediate.getImmediate();
//        pc = high7bits | low9bits;
//    }

//    @Override
//    public void visit(final LoadWord loadWord) {
//        int address = registers[loadWord.getR2()];
//        int value = memorySubsystem.readInt(address);
//        registers[loadWord.getR1()] = value;
//        incrementPc();
//    }

//    @Override
//    public void visit(final StoreWord storeWord) {
//        int address = registers[storeWord.getR2()];
//        int value = registers[storeWord.getR1()];
//        memorySubsystem.writeInt(address, value);
//        incrementPc();
//    }

    private void incrementPc() {
        pc += 2;
    }

    @Generated("by IDE")
    public int getPc() {
        return pc;
    }

    @Generated("by IDE")
    public ExecutionVisitor setPc(final int pc) {
        this.pc = pc;
        return this;
    }

    public int[] getRegisters() {
        return registers.clone();
    }
}

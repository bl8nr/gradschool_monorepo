package e93.assembler;

import e93.assembler.ast.AssemblyVisitor;

import javax.annotation.Generated;
import java.util.Objects;

public abstract class Instruction {
    /**
     * The address (or value of the PC) of the instruction is set by the assembler.
     * This should be done at a point where you're sure that the instruction
     * won't change. For example, if it's a pseudo instruction, it may
     * generate 2 or 3 other instructions that take its place and would therefore
     * shift the addresses of all of the other instructions.
     *
     * The address may be used when encoding an instruction.
     */
    private int address;

    /**
     * The OpCode for the instructions. All instructions have OpCodes.
     */
    private OpCode opcode;

    /**
     * Add register entries for the instructions. There are a possible 3 regisers depending on the insturction type
     * rs, register source. rt, register target. rd, register destination
     */
    private int r1;
    private int r2;
    private int r3;

    /**
     * Some instructions may also make use of an immediate value
     */
    private int immediate;

    /**
     * Some instructions have a label. This is a reference to some other instruction
     * that they're jumping or branching to.
     *
     * In cases where the assembly programmer used a label in their instruction,
     * the Assembler is responsible for replacing this label reference with the
     * correct immediate. The way this is done is outlined in the principles of
     * operation document for your instruction set.
     *
     * For example, when handling a branch instruction, the assembler may take
     * address of the label and compute the difference between it and PC + 2.
     * When handling a jump instruction, it may take the address of the label and
     * compute a value for the lower 12 bits that can be OR'd in with the high
     * order bits of PC + 2.
     */
    private String label;

    private int lineNumber;

    private String sourceLine;

    private String errorMessage;

    public static Instruction error(String errorMessage) {
        return new Instruction() {
            @Override
            public void accept(final AssemblyVisitor assemblyVisitor) {

            }
        }.setErrorMessage(errorMessage);
    }


















    /**
     * Determine the validity of an instruction by seeing if it has an opcode or not
     * every instruction must have an opcode
     * @return if opcode is null (doesn't exist) as boolean
     */
    public boolean isValid() {
        return opcode != null;
    }

    /**
     * Get the address where this instruction is at, used by the program counter
     * every instruction has an address
     * @return address as integer
     */
    @Generated("by IDE")
    public int getAddress() {
        return address;
    }

    /**
     * Set the address where this instruction is at, used by the program counter
     * every instruction has an address
     * @param address as integer
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setAddress(final int address) {
        this.address = address;
        return this;
    }

    /**
     * Get the opcode that determines what operation the CPU is supposed to execute
     * every instruction has an opcode
     * @return opcode as OpCode
     */
    @Generated("by IDE")
    public OpCode getOpcode() {
        return opcode;
    }

    /**
     * Set the opcode that determines what operation the CPU is supposed to execute
     * every instruction has an opcode
     * @param opcode as OpCode
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setOpcode(final OpCode opcode) {
        this.opcode = opcode;
        return this;
    }

    /**
     * Get r1, the first register specified in an instruction, rs
     * used in instruction type R-Type and I-Type
     * @return r1 as integer
     */
    @Generated("by IDE")
    public int getR1() {
        return r1;
    }

    /**
     * Set r1, the first register specified in an instruction, rs
     * used in instruction type R-Type and I-Type
     * @param r1 as integer
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setR1(final int r1) {
        this.r1 = r1;
        return this;
    }

    /**
     * Get r2, the second register specified in an instruction, rt
     * used in instruction type R-Type
     * @return r2 as integer
     */
    @Generated("by IDE")
    public int getR2() {
        return r2;
    }

    /**
     * Set r2, the second register specified in an instruction, rt
     * used in instruction type R-Type
     * @param r2 as integer
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setR2(final int r2) {
        this.r2 = r2;
        return this;
    }

    /**
     * Get r3, the third register specified in an instruction, rd
     * used in instruction type R-Type
     * @return r3 as integer
     */
    @Generated("by IDE")
    public int getR3() { return r3; }

    /**
     * Set r3, the third register specified in an instruction, rd
     * used in isntruction type R-Type
     * @param r3 as integer
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setR3(final int r3) {
        this.r3 = r3;
        return this;
    }

    /**
     * Get immediate, the immediate value specified in instructions
     * used in instruction type I-Type
     * @return immediate as integer
     */
    @Generated("by IDE")
    public int getImmediate() {
        return immediate;
    }

    /**
     * Set immediate, the immediate value specified in instructions
     * used in instruction type I-Type
     * @param immediate as integer
     * @return this instruction object instantiation
     */
    @Generated("by IDE")
    public Instruction setImmediate(final int immediate) {
        this.immediate = immediate;
        return this;
    }




























    @Generated("by IDE")
    public String getLabel() {
        return label;
    }

    @Generated("by IDE")
    public Instruction setLabel(final String label) {
        this.label = label;
        return this;
    }

    @Generated("by IDE")
    public int getLineNumber() {
        return lineNumber;
    }

    @Generated("by IDE")
    public Instruction setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    @Generated("by IDE")
    public String getSourceLine() {
        return sourceLine;
    }

    @Generated("by IDE")
    public Instruction setSourceLine(final String sourceLine) {
        this.sourceLine = sourceLine;
        return this;
    }

    @Generated("by IDE")
    public String getErrorMessage() {
        return errorMessage;
    }

    @Generated("by IDE")
    public Instruction setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public abstract void accept(AssemblyVisitor assemblyVisitor);

    @Override
    @Generated("by IDE")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Instruction that = (Instruction) o;
        return address == that.address &&
                r1 == that.r1 &&
                r2 == that.r2 &&
                immediate == that.immediate &&
                lineNumber == that.lineNumber &&
                opcode == that.opcode &&
                Objects.equals(label, that.label) &&
                Objects.equals(sourceLine, that.sourceLine) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    @Generated("by IDE")
    public int hashCode() {
        return Objects.hash(address, opcode, r1, r2, r3, immediate, label, lineNumber, sourceLine, errorMessage);
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "address=" + address +
                ", opcode=" + opcode +
                ", r1=" + r1 +
                ", r2=" + r2 +
                ", r3=" + r3 +
                ", immediate=" + immediate +
                ", label='" + label + '\'' +
                ", lineNumber=" + lineNumber +
                ", sourceLine='" + sourceLine + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}

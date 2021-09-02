package e93.assembler;

import e93.assembler.ast.AssemblyVisitor;

import javax.annotation.Generated;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public abstract class PseudoInstruction {
    // pseudoinstructions have no address since they create instructions which each individually have their own address
    // pseudoinstructions do not have opcodes since they are no actual instructions

    /**
     * Add register entries for the pseudoinstructions. There are a possible 3 regisers depending on the pseudoinsturction
     * type rs, register source. rt, register target. rd, register destination.
     */
    private int r1;
    private int r2;
    private int r3;

    /**
     * Some pseudoinstructions may also make use of an immediate value
     */
    private int immediate;

    /**
     * Array list of instructions that will be added by the assembler
     */
    private List<Instruction> instructions = new ArrayList<>();

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

    public static PseudoInstruction error(String errorMessage) {
        return new PseudoInstruction() {
            // null
        }.setErrorMessage(errorMessage);
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
    public PseudoInstruction setR1(final int r1) {
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
    public PseudoInstruction setR2(final int r2) {
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
    public PseudoInstruction setR3(final int r3) {
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
    public PseudoInstruction setImmediate(final int immediate) {
        this.immediate = immediate;
        return this;
    }

    /**
     * Set instruction collection, a collection of instructions that make up a pseudo instruction
     * set by the pseudo instruction constructor
     * @return this pseudo instruction object instantiation
     */




























    @Generated("by IDE")
    public String getLabel() {
        return label;
    }

    @Generated("by IDE")
    public PseudoInstruction setLabel(final String label) {
        this.label = label;
        return this;
    }

    @Generated("by IDE")
    public int getLineNumber() {
        return lineNumber;
    }

    @Generated("by IDE")
    public PseudoInstruction setLineNumber(final int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    @Generated("by IDE")
    public String getSourceLine() {
        return sourceLine;
    }

    @Generated("by IDE")
    public PseudoInstruction setSourceLine(final String sourceLine) {
        this.sourceLine = sourceLine;
        return this;
    }

    @Generated("by IDE")
    public String getErrorMessage() {
        return errorMessage;
    }

    @Generated("by IDE")
    public PseudoInstruction setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}

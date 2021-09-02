package e93.assembler.test;

import e93.assembler.Assembler;
import e93.assembler.Instruction;
import e93.assembler.OpCode;
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
import e93.assembler.ast.Lui;

import e93.assembler.ast.Sw;
import e93.assembler.ast.Lw;
//import e93.assembler.ast.ORi;
//import e93.assembler.ast.JumpImmediate;
//import e93.assembler.ast.LoadWord;
//import e93.assembler.ast.StoreWord;
import org.junit.Test;
import org.junit.Ignore;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author markford
 *
 * modifid and extended by Brett Bloethner for CSCI-E93 Problem Set 4
 */
public class AssemblerTest {

    private Assembler assembler = new Assembler();

    /************************
     * INSTRUCTION TYPE TESTS
     ************************/

    /**
     * test that the instruction type R-Type is working properly
     * R-Type instructions feature a register source, target, and destination
     * @throws Exception
     */
    @Test
    public void parseRTypeInstruction() {
        Instruction actual = assembler.parse("ADD, $r1, $r2, $r3");
        assertEquals(new Add()
                .setOpcode(OpCode.ADD)
                .setR1(1)
                .setR2(2)
                .setR3(3),
        actual);
    }

    /**
     * test that the instruction type I-Type is working properly
     * I-Type instructions feature a register sourcetarget and an 8-bit immediate value
     * @throws Exception
     */
    @Test
    public void parseITypeInstruction() {
        Instruction actual = assembler.parse("ORI, $r1, 12344");
        assertEquals(new Ori()
                .setR1(1)
                .setImmediate(12344),
        actual);
    }

    /**
     * test that the instruction type F-Type is working properly
     * @throws Exception
     */

    /**
     * test that the instruction type J-Type is working properly
     * @throws Exception
     */


    /*******************
     * INSTRUCTION TESTS
     *******************/

    // ADD instruction should create a new R-Type ADD instruction
    @Test
    public void parseAdd() {
        Instruction actual = assembler.parse("ADD, $r1, $r2, $r3");
        assertEquals(new Add()
                .setOpcode(OpCode.ADD)
                .setR1(1)
                .setR2(2)
                .setR3(3),
        actual);
    }

    // SUB instruction should create a new R-Type SUB instruction
    @Test
    public void parseSub() {
        Instruction actual = assembler.parse("SUB, $r1, $r2, $r3");
        assertEquals(new Sub()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
        actual);
    }

    // NAND instruction should create a new R-Type NAND instruction
    @Test
    public void parseNand() {
        Instruction actual = assembler.parse("NAND, $r1, $r2, $r3");
        assertEquals(new Nand()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SLT instruction should create a new R-Type Set Less Than instruction
    @Test
    public void parseSlt() {
        Instruction actual = assembler.parse("SLT, $r1, $r2, $r3");
        assertEquals(new Slt()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SGT instruction should create a new R-Type Set Greater Than instruction
    @Test
    public void parseSgt() {
        Instruction actual = assembler.parse("SGT, $r1, $r2, $r3");
        assertEquals(new Sgt()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SET instruction should create a new R-Type Set Equal To instruction
    @Test
    public void parseSet() {
        Instruction actual = assembler.parse("SET, $r1, $r2, $r3");
        assertEquals(new Set()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SLL instruction should create a new R-Type Shift Left Logical instruction
    @Test
    public void parseSll() {
        Instruction actual = assembler.parse("SLL, $r1, $r2, $r3");
        assertEquals(new Sll()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SRA instruction should create a new R-Type Shift Right Arithmetic instruction
    @Test
    public void parseSra() {
        Instruction actual = assembler.parse("SRA, $r1, $r2, $r3");
        assertEquals(new Sra()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // SRL instruction should create a new R-Type Shift Right Logical instruction
    @Test
    public void parseSrl() {
        Instruction actual = assembler.parse("SRL, $r1, $r2, $r3");
        assertEquals(new Srl()
                        .setR1(1)
                        .setR2(2)
                        .setR3(3),
                actual);
    }

    // ORI should create a new I-Type OR Immediate instruction
    @Test
    public void parseOri() {
        Instruction actual = assembler.parse("ORI, $r2, 43563");
        assertEquals(new Ori()
                .setR1(2)
                .setImmediate(43563),
        actual);
    }

    // BOZ should create a new I-Type Branch On Zero instruction
    @Test
    public void parseBoz() {
        Instruction actual = assembler.parse("BOZ, $r8, 666");
        assertEquals(new Boz()
                        .setR1(8)
                        .setImmediate(666),
                actual);
    }

    // LUI should create a new I-Type Load Upper Immediate instruction
    @Test
    public void parseLui() {
        Instruction actual = assembler.parse("LUI, $r6, 664356");
        assertEquals(new Lui()
                        .setR1(6)
                        .setImmediate(664356),
                actual);
    }

    // SW instruction should create a new R-Type Store Word instruction
    @Test
    public void parseSw() {
        Instruction actual = assembler.parse("SW, $r6, 0, $r8");
        assertEquals(new Sw()
                        .setR1(6)
                        .setR3(8),
                actual);
    }

    // LW instruction should create a new R-Type Load Word instruction
    @Test
    public void parseLw() {
        Instruction actual = assembler.parse("LW, $r9, 0, $r4");
        assertEquals(new Lw()
                        .setR1(9)
                        .setR3(4),
                actual);
    }

    // J should create a new J-Type Jump instruction
//    @Test
//    public void parseJumpImmediate() throws Exception {
//        Instruction instruction = assembler.parse("J, 0x123");
//        assertEquals(new JumpImmediate().setOpcode(OpCode.J).setImmediate(0x123<<1), instruction);
//    }

    /*****************************
     * SYNTAX AND FORMATTING TESTS
     *****************************/
    @Test
    public void invalidFormat() throws Exception {
        Instruction instruction = assembler.parse("AND $r1 $r2");
        assertFalse(instruction.isValid());
    }

    /****************
     * REGISTER TESTS
     ****************/

    // registers should have a dollar sign in front of them
    // otherwise error
    @Test
    public void invalidRegisterFormat() throws Exception {
        Instruction instruction = assembler.parse("AND, r1, r2");
        assertFalse(instruction.isValid());
    }

    // register number should be withing the 0-15 range allocated by the 16-bit design
    // otherwise error
    @Test
    public void invalidRegister() throws Exception {
        Instruction instruction = assembler.parse("AND, $r16, r2");
        assertFalse(instruction.isValid());
    }

    /**
     * ENCODING AND DECODING TESTS
     */
    @Ignore
    @Test
    public void encodeTwoRegisterType() throws Exception {
        Instruction instruction = assembler.parse("AND, $r1, $r2");
        int encoded = Assembler.encode(instruction);

        // instruction in binary == 0001000100100001
        // opc   r1   r2  func
        // 0001 0001 0010 0001
        // convert to hex
        //   1    1    2    1
        assertEquals(0x1121, encoded);
    }

//    @Ignore
//    @Test
//    public void decodeTwoRegisterType() throws Exception {
//        Instruction expected = assembler.parse("AND, $r1, $r2");
//        int encoded = Assembler.encode(expected);
//        assertEquals(expected, Assembler.decode(encoded));
//    }

    @Test
    public void parse() throws Exception {
        String program = "AND, $r1, $r0, $r3\n" +
                         "ADDI, $r1, 123";
        List<Instruction> instructions = assembler.parse(new StringReader(program));
        assertEquals(2, instructions.size());
    }
}

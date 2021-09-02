package e93.assembler;

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
import e93.assembler.ast.Sw;
import e93.assembler.ast.Lw;
import e93.assembler.ast.Lui;
//import e93.assembler.ast.J;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * original @author markford
 * modified by Brett Bloethner for CSCI-E93 Problem Set 4
 */
public class Assembler {

    /**
     * Max number of registers in the system. This system is 16-bit so this
     * ensures that out of range non-existent registers aren't targeted.
     */
    private static final int MAX_REGISTERS = 16;

    /**
     * A mask used to extract the register value from an encoded instruction.
     * This should be enough bits to mask all possible values of the register.
     */
    private static final int REGISTER_MASK = 0xf;

    private static final int IMMEDIATE_MASK = 0xff;

    /**
     * Parses a line of assembly and returns an instruction that the system will
     * know how to execute.
     *
     * At this point, the instruction may refer to a label that needs to be
     * resolved.
     *
     * Note: the format of the instruction is the very basic format that was
     *       outlined in section. It uses commas to separate each of the values
     *       for the instruction. This is a little verbose but very easy to parse.
     *       The most common mistake is to forget a comma somewhere.
     *
     * @param line raw line of assembly to parse into an instruction
     * @return an instruction
     * @throws IllegalArgumentException if the line cannot be parsed.
     */
    public Instruction parse(String line) {
        String[] split = line.split(",");
        if (split.length > 4) {
            return Instruction.error("unknown instruction");
        }
        try {
            String type = split[0].trim();
            switch (type) {
                case "SUB": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Sub()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "ADD": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Add()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "NAND": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Nand()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SLT": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Slt()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SGT": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Sgt()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SET": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Set()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SLL": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Sll()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SRA": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Sra()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "SRL": {
                    int r1 = toRegister(split[1].trim());
                    int r2 = toRegister(split[2].trim());
                    int r3 = toRegister(split[3].trim());
                    return new Srl()
                            .setR1(r1)
                            .setR2(r2)
                            .setR3(r3);
                }
                case "ORI": {
                    int r1 = toRegister(split[1].trim());
                    int immediate = toImmediate(split[2].trim());
                    return new Ori()
                            .setR1(r1)
                            .setImmediate(immediate);
                }
                case "BOZ": {
                    int r1 = toRegister(split[1].trim());
                    int immediate = toImmediate(split[2].trim());
                    return new Boz()
                            .setR1(r1)
                            .setImmediate(immediate);
                }
                case "LUI": {
                    int r1 = toRegister(split[1].trim());
                    int immediate = toImmediate(split[2].trim());
                    return new Lui()
                            .setR1(r1)
                            .setImmediate(immediate);
                }
                case "SW": {
                    int r1 = toRegister(split[1].trim());
                    // int r2 should be zero
                    int r3 = toRegister(split[3].trim());
                    return new Sw()
                            .setR1(r1)
                            .setR3(r3);
                }
                case "LW": {
                    int r1 = toRegister(split[1].trim());
                    // int r2 should be zero
                    int r3 = toRegister(split[3].trim());
                    return new Lw()
                            .setR1(r1)
                            .setR3(r3);
                }
                default:
                    return Instruction.error("unknown instruction. Are you missing a comma?");
            }
        } catch (InvalidRegisterException e) {
            return Instruction.error("Invalid register " + e.getValue());
        }
    }

    /**
     * Encodes an instruction in order to write it to a MIF file or similar.
     *
     * At this point, your assembler should have resolved any labels to numeric
     * values in order to encode the instruction as all numbers.
     *
     * @param instruction an instruction that's had its label resolved (assuming it had one)
     * @return integer version of the instruction
     */
    public static int encode(Instruction instruction) {

        assert instruction.getLabel() == null;

        switch(instruction.getOpcode()) {
            /*
                        check your work here: http://www.binaryhexconverter.com/decimal-to-binary-converter
                     */
            case ADD:
            case SUB:
            case NAND:
            case SLT:
            case SGT:
            case SET:
            case SLL:
            case SRA:
            case SRL:
            case SW:
            case LW:
                return
                        // opcode is bits 15..12
                        instruction.getOpcode().getValue() << 12 |
                        // r1 is bits 11..8
                        instruction.getR1() << 8 |
                        // r2 is bits 7..4
                        instruction.getR2() << 4 |
                        // func is bits 3..0
                        instruction.getR3()
                        ;
            case ORI:
            case BOZ:
            case LUI:
                return
                        // opcode is bits 15..12
                        instruction.getOpcode().getValue() << 12 |
                        // r1 is bits 11..8
                        instruction.getR1() << 8 |
                        // immediate is bits 7..0
                        instruction.getImmediate()
                        ;

            // break;
//            case SW:
//            case LW:
//                return instruction.getOpcode().getValue()<<12 |
//                        instruction.getR1() << 8 |
//                        instruction.getR2() << 4;
//            case J:
//                return instruction.getOpcode().getValue()<<12 |
//                        instruction.getImmediate();
        }
        throw new IllegalArgumentException("unhandled instruction:" + instruction);
    }

    /**
     * Decodes an instruction from its encoded form. You'll need something like
     * this for when you write the emulator.
     *
     * @param encoded numeric form of the instruction that we'll decode
     * @return Instruction
     * @throws IllegalArgumentException if we don't know how to decode it
     */
//    public static Instruction decode(int encoded) {
//        // the opcode is always in 15..12 but the rest of the instruction is
//        // unknown until we know what the opcode is so get that first!
//        int value = encoded >> 12;
//        OpCode opCode = OpCode.fromEncoded(value);
//        switch (opCode) {
//            case ADD:
//                // get the function code to figure out what type of ALU operation
//                // it is. The function code is the lower two bits which I can get
//                // by AND'ing the number 3 (which is 11 in binary)
//                int functionCode = encoded & 0x3;
//                switch(functionCode) {
//                    case ALUFunctionCodes.AND:
//                        // r1 is always in 11..8
//                        // shift right to drop the low order bits and then mask with
//                        // REGISTER_MASK in order to get all of the
//                        // bits for the register number
//                        int r1 = (encoded >> 8) & REGISTER_MASK;
//                        // r2 is always in 7..4
//                        // shift right to drop the low order bits and then mask with
//                        // REGISTER_MASK in order to get all of the
//                        // bits for the register number
//                        int r2 = (encoded >> 4) & REGISTER_MASK;
//                        int r3 = (encoded) & REGISTER_MASK;
//                        return new Add()
//                                .setOpcode(opCode)
//                                .setR1(r1)
//                                .setR2(r2)
//                                .setR3(r3);
//                }
//
////            case SW:
////                return new StoreWord().setOpcode(opCode)
////                        .setR1((encoded >> 8) & REGISTER_MASK)
////                        .setR2((encoded >> 4) & REGISTER_MASK);
////            case LW:
////                return new LoadWord().setOpcode(opCode)
////                        .setR1((encoded >> 8) & REGISTER_MASK)
////                        .setR2((encoded >> 4) & REGISTER_MASK);
////            case J:
////                return new JumpImmediate().setOpcode(opCode)
////                        .setImmediate((encoded & 0xfff)>>1);
//
//        }
//        throw new IllegalArgumentException("unhandled encoded instruction:" + encoded);
//    }

    /**
     * Parses the source into a list of Instructions.
     * @param reader Source for the program
     * @return list of instructions that are ready to encode
     * @throws IOException when there's an error reading a line
     */
    public List<Instruction> parse(Reader reader) throws IOException {
        List<Instruction> instructions = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String line;
        int lineNumber = 0;
        while ((line = br.readLine()) != null) {
            lineNumber++;
            if (line.startsWith("#")) {
                // it's a comment, ignore it
                continue;
            }

            if (line.startsWith(".")) {
                // its a declaration;
            }

            if (line.startsWith("#DEFINE")) {
                // there is a label on this line
            }

            // todo need to handle labels here
            // if label exists then

            Instruction instruction = parse(line);
            instruction.setLineNumber(lineNumber);
            instruction.setSourceLine(line);
            instructions.add(instruction);
        }

        // iterate throught instructions here and add the instruction address

        return instructions;
    }

    /**
     * Helper method that converts a reference to a register to an int
     * @param s reference to a register in the format we're expecting
     * @return number of the register which is within range
     * @throws IllegalArgumentException if we can't parse it or it's out of range
     */
    private int toRegister(String s) throws InvalidRegisterException {
        if (!s.startsWith("$r")) {
            throw new InvalidRegisterException(s);
        }
        int register = Integer.parseInt(s.substring(2));
        if (register > MAX_REGISTERS-1) {
            throw new InvalidRegisterException(s);
        }
        return register;
    }

    /**
     * Helper method that converts an immediate value to an integer
     * @param s string version of a decimal number to use as an immediate
     * @return converted integer
     * @throws NumberFormatException if an unknown format
     */
    private int toImmediate(String s) {
        // todo - you may want to support hex
        // todo - assert the register value can be encoded

        // With respect to the encoding assertion, keep in mind that you may only
        // have 8 bits for an immediate value in your instruction (more for a
        // JUMP). You may allow the programmer to use an immediate value outside
        // of this range but that requires your assembler to handle it. For
        // example, the assembler might see a large immediate value and then
        // generate a couple of instructions to handle it. It might use a
        // temporary register and LUI and similar instructions to put the large
        // immediate into the temporary register and then rewrite the instruction
        // to use this temporary register instead of an immediate.
        if (s.startsWith("0x")) {
            return Integer.parseInt(s.substring(2), 16);
        } else {
            return Integer.parseInt(s);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("must pass name of input file");
            return;
        }

        File file = new File(args[0]);
        if (!file.isFile()) {
            System.err.println("file not found or not readable:" + args[0]);
            return;
        }

        try (FileReader fileReader = new FileReader(file)) {
            Assembler assembler = new Assembler();

            List<Instruction> instructions = assembler.parse(fileReader);

            // this isn't a MIF file format but rather a simple toString() on each of the instructions
            for(Instruction instruction : instructions) {
                System.out.println(instruction);
            }

            //System.out.println(MifWriter.writeToString(instructions));
        }
    }
}

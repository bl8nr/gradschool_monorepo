package e93.assembler.test;

import e93.assembler.Assembler;
import e93.assembler.Instruction;
import e93.assembler.MifWriter;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static e93.assembler.IOUtils.asString;
import static org.junit.Assert.assertEquals;

public class MifWriterTest {
    @Ignore
    @Test
    public void simple() throws IOException {
        List<Instruction> instructions = new Assembler().parse(new StringReader("ADD, $r1, $r2, $r3"));
        String actual = MifWriter.writeToString(instructions);
        String expected = asString("/sample.mif");
        assertEquals(expected, actual);

    }
}
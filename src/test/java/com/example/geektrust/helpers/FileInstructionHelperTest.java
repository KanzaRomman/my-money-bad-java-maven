package com.example.geektrust.helpers;

import com.example.geektrust.enums.Command;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileInstructionHelperTest {

    @Test
    void readLinesFromFile_shouldReadLinesFromFile() throws IOException {
        Path filePath = Paths.get("sample_input/input1.txt");
        Stream<String> fileLines = FileInstructionHelper.readLinesFromFile(filePath);

        assertNotNull(fileLines);
    }

    @Test
    void getTrimmedLinesAsList_shouldReturnTrimmedLinesAsList() {
        Stream<String> rawFileLines = Stream.of("  ADD SIP 1000.0 2000.0  ", "   REBALANCE   ");
        List<String> trimmedLines = FileInstructionHelper.getTrimmedLinesAsList(rawFileLines);

        assertNotNull(trimmedLines);
        assertEquals(2, trimmedLines.size());
        assertEquals("ADD SIP 1000.0 2000.0", trimmedLines.get(0));
        assertEquals("REBALANCE", trimmedLines.get(1));
    }

    @Test
    void getInstructionFromFileLine_shouldReturnInstructionArray() {
        String fileLine = "BUY 500.0";
        String[] instruction = FileInstructionHelper.getInstructionFromFileLine(fileLine);

        assertNotNull(instruction);
        assertEquals(2, instruction.length);
        assertEquals("BUY", instruction[0]);
        assertEquals("500.0", instruction[1]);
    }

    @Test
    void extractCommandFromInstruction_shouldReturnCommandEnum() {
        String[] instruction = {"ALLOCATE", "1000.0", "2000.0"};
        Command command = FileInstructionHelper.extractCommandFromInstruction(instruction);

        assertEquals(Command.ALLOCATE, command);
    }

    @Test
    void extractValuesFromInstruction_shouldReturnValuesArray() {
        String[] instruction = {"SIP", "1000.0", "2000.0"};
        String[] values = FileInstructionHelper.extractValuesFromInstruction(instruction);

        assertNotNull(values);
        assertEquals(2, values.length);
        assertEquals("1000.0", values[0]);
        assertEquals("2000.0", values[1]);
    }

    @Test
    void extractNumericValuesFromInstruction_shouldReturnNumericValuesList() {
        String[] instruction = {"SIP", "1000.0", "2000.0", "Invalid", "500.0"};
        List<Double> numericValues = FileInstructionHelper.extractNumericValuesFromInstruction(instruction);

        assertNotNull(numericValues);
        assertEquals(3, numericValues.size());
        assertEquals(1000.0, numericValues.get(0));
        assertEquals(2000.0, numericValues.get(1));
        assertEquals(500.0, numericValues.get(2));
    }
}
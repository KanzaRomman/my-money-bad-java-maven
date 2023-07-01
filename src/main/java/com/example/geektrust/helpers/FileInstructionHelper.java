package com.example.geektrust.helpers;

import com.example.geektrust.Main.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileInstructionHelper {

    private FileInstructionHelper() {
        throw new IllegalStateException("Helper Class");
    }

    public static Stream<String> readLinesFromFile(Path filePath) throws IOException {
        return Files.lines(filePath);
    }

    public static List<String> getTrimmedLinesAsList(Stream<String> rawFileLines) {
        return rawFileLines.map(String::trim).collect(Collectors.toList());
    }

    public static String[] getInstructionFromFileLine(String fileLine) {
        return fileLine.trim().split(" ");
    }

    public static Command extractCommandFromInstruction(String[] instruction) {
        return Command.valueOf(instruction[0]);
    }

    public static String[] getValuesFromInstruction(String[] instruction) {
        return Arrays.copyOfRange(instruction, 1, instruction.length);
    }

}
package com.example.geektrust;

import com.example.geektrust.command.CommandHandler;
import com.example.geektrust.dtos.Portfolio;
import com.example.geektrust.enums.Command;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.example.geektrust.helpers.FileInstructionHelper.extractCommandFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.extractValuesFromInstruction;
import static com.example.geektrust.helpers.FileInstructionHelper.getInstructionFromFileLine;
import static com.example.geektrust.helpers.FileInstructionHelper.getTrimmedLinesAsList;
import static com.example.geektrust.helpers.FileInstructionHelper.readLinesFromFile;


public class Main {

    public static void main(String[] args) {

        Path filePath = new File(args[0]).toPath();
        try (Stream<String> rawFileLines = readLinesFromFile(filePath)) {
            List<String> fileLines = getTrimmedLinesAsList(rawFileLines);
            Portfolio portfolio = new Portfolio();

            for (String fileLine : fileLines) {
                String[] instruction = getInstructionFromFileLine(fileLine);
                Command command = extractCommandFromInstruction(instruction);
                String[] instructionValues = extractValuesFromInstruction(instruction);

                CommandHandler commandHandler = command.getCommandHandler();
                commandHandler.handleCommand(portfolio, instructionValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.example.geektrust.command;

import com.example.geektrust.dtos.Portfolio;

public interface CommandHandler {
    void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    );
}

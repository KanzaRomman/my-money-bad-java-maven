package com.example.geektrust.command;

import com.example.geektrust.dtos.Portfolio;


public class SipCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(
            Portfolio portfolio,
            String[] instructionValues
    ) {
        portfolio.addSipToPortfolio(instructionValues);
    }
}

package com.example.geektrust.enums;

import com.example.geektrust.command.AllocateCommandHandler;
import com.example.geektrust.command.BalanceCommandHandler;
import com.example.geektrust.command.ChangeCommandHandler;
import com.example.geektrust.command.CommandHandler;
import com.example.geektrust.command.RebalanceCommandHandler;
import com.example.geektrust.command.SipCommandHandler;

public enum Command {
    ALLOCATE(new AllocateCommandHandler()),
    SIP(new SipCommandHandler()),
    CHANGE(new ChangeCommandHandler()),
    BALANCE(new BalanceCommandHandler()),
    REBALANCE(new RebalanceCommandHandler());

    private final CommandHandler commandHandler;

    Command(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}

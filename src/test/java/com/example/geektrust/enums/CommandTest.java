package com.example.geektrust.enums;

import com.example.geektrust.command.AllocateCommandHandler;
import com.example.geektrust.command.BalanceCommandHandler;
import com.example.geektrust.command.ChangeCommandHandler;
import com.example.geektrust.command.CommandHandler;
import com.example.geektrust.command.RebalanceCommandHandler;
import com.example.geektrust.command.SipCommandHandler;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;


class CommandTest {
    @Test
    void getCommandHandler_shouldReturnCorrectHandler() {
        CommandHandler allocateHandler = new AllocateCommandHandler();
        CommandHandler sipHandler = new SipCommandHandler();
        CommandHandler changeHandler = new ChangeCommandHandler();
        CommandHandler balanceHandler = new BalanceCommandHandler();
        CommandHandler rebalanceHandler = new RebalanceCommandHandler();

        EqualsBuilder.reflectionEquals(allocateHandler, Command.ALLOCATE.getCommandHandler());
        EqualsBuilder.reflectionEquals(sipHandler, Command.SIP.getCommandHandler());
        EqualsBuilder.reflectionEquals(changeHandler, Command.CHANGE.getCommandHandler());
        EqualsBuilder.reflectionEquals(balanceHandler, Command.BALANCE.getCommandHandler());
        EqualsBuilder.reflectionEquals(rebalanceHandler, Command.REBALANCE.getCommandHandler());
    }
}
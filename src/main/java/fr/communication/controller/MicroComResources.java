package fr.communication.controller;


import fr.communication.domain.Operation;
import fr.communication.domain.dto.AccountDto;
import fr.communication.domain.dto.OperationCommand;
import fr.communication.service.MicroComService;
import fr.communication.service.OperationService;
import fr.communication.utils.NoSuchResourceException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/")
public class MicroComResources {

    private final MicroComService MicroComService;
    private final OperationService operationService;

    public MicroComResources(MicroComService MicroComService, OperationService operationService) {
        this.MicroComService = MicroComService;
        this.operationService = operationService;
    }

    @ApiOperation(value = "printAccountState",notes = "return given account state and recent operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = AccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}")
    public AccountDto printAccountState(@PathVariable long accountId) throws NoSuchResourceException {
        return MicroComService.printStatement(accountId);
    }

    @ApiOperation(value = "showOperationsList",notes = "lists all given account operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",responseContainer = "list",response = Operation.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}/history")
    public List<Operation> showOperationsList(@PathVariable long accountId) throws NoSuchResourceException {
        return MicroComService.listAllOperations(accountId);
    }


    @ApiOperation(value = "deposit",notes = "perfom a deposit on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",response = AccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/deposit")
    public AccountDto deposit(@PathVariable long accountId,
                        @RequestBody OperationCommand operationCommand) throws NoSuchResourceException {
       return operationService.doDeposit(accountId,operationCommand.getAmount());
    }


    @ApiOperation(value = "withdrawall",notes = "perfom a withdrawal on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success",response = AccountDto.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/withdrawal")
    public AccountDto withdrawal(@PathVariable long accountId,
                           @RequestBody OperationCommand operationCommand) throws NoSuchResourceException {
        return operationService.doWithdrawal(accountId,operationCommand.getAmount());
    }
}

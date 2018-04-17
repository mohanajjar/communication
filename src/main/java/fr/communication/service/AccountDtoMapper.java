package fr.communication.service;

import fr.communication.domain.MicroCom;
import fr.communication.domain.Operation;
import fr.communication.domain.dto.AccountDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDtoMapper {

    public AccountDto mapEntityToDto(MicroCom account){
        AccountDto dto = new AccountDto();
        dto.setBalance(account.getBalance());
        List<Operation> recentOps = account.getOperations()
                .stream()
                .sorted(Comparator.comparing(Operation::getDate).reversed())
                .limit(5).collect(Collectors.toList());
        dto.setLatestOperations(recentOps);
        return dto;
    }
}

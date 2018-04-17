package fr.communication.service;


import fr.communication.domain.MicroCom;
import fr.communication.domain.Operation;
import fr.communication.domain.dto.AccountDto;
import fr.communication.repository.MicroComRepository;
import fr.communication.repository.OperationRepository;
import fr.communication.utils.NoSuchResourceException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MicroComService {

    private final MicroComRepository MicroComRepository;
    private final AccountDtoMapper accountDtoMapper;

    public MicroComService(MicroComRepository MicroComRepository, AccountDtoMapper accountDtoMapper) {
        this.MicroComRepository = MicroComRepository;
        this.accountDtoMapper = accountDtoMapper;
    }

    /**
     *
     * @param accountId account identifier
     * @return all operations on a given account
     * @throws NoSuchResourceException
     */
    public List<Operation> listAllOperations(long accountId) throws NoSuchResourceException {
        Optional<MicroCom> optionalMicroCom = MicroComRepository.findById(accountId);
        if(!optionalMicroCom.isPresent()){
            throw new NoSuchResourceException(": "+accountId);
        }
        return optionalMicroCom.get().operations;
    }

    /**
     *
     * @param accountId account identifier
     * @return  the account state including latest operations
     * @throws NoSuchResourceException
     */
    public AccountDto printStatement(long accountId) throws NoSuchResourceException {
        Optional<MicroCom> optionalMicroCom = MicroComRepository.findById(accountId);
        if(!optionalMicroCom.isPresent()){
            throw new NoSuchResourceException(": "+accountId);
        }
        return accountDtoMapper.mapEntityToDto(optionalMicroCom.get());
    }
}

package fr.communication.service;

import com.google.common.annotations.VisibleForTesting;
import fr.communication.domain.MicroCom;
import fr.communication.domain.Operation;
import fr.communication.domain.OperationType;
import fr.communication.domain.dto.AccountDto;
import fr.communication.repository.MicroComRepository;
import fr.communication.repository.OperationRepository;
import fr.communication.utils.NoSuchResourceException;
import fr.communication.utils.NoSuchResourceException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class OperationService {

    private final OperationRepository operationRepository;
    private final MicroComRepository MicroComRepository;
    private AccountDtoMapper dtoMapper;

    public OperationService(OperationRepository operationRepository, MicroComRepository MicroComRepository, AccountDtoMapper dtoMapper) {
        this.operationRepository = operationRepository;
        this.MicroComRepository = MicroComRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * debits the specified amount on the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchResourceException
     */
    public AccountDto doWithdrawal(long accountId, long amount) throws NoSuchResourceException {
        Operation operation = createAndPerformOperation(accountId,amount,OperationType.WITHDRAWAL);
        MicroCom MicroCom = MicroComRepository.findById(accountId).get();
        MicroCom.getOperations().add(operation);
        return dtoMapper.mapEntityToDto(MicroCom);
    }


    /**
     * deposit the specified amount into the specified account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @throws NoSuchResourceException
     */
    public AccountDto doDeposit(long accountId, long amount) throws NoSuchResourceException {
        Operation operation = createAndPerformOperation(accountId,amount,OperationType.DEPOSIT);
        MicroCom MicroCom = MicroComRepository.findById(accountId).get();
        MicroCom.getOperations().add(operation);
        return dtoMapper.mapEntityToDto(MicroCom);
    }


    /**
     * create and perform the specified operation on the given account
     * @param accountId the account identifier
     * @param amount the amount of the transaction
     * @param operationType the transaction type(debit or credit)
     * @return newly created operation
     * @throws NoSuchResourceException
     */
      @VisibleForTesting
      Operation createAndPerformOperation(long accountId, long amount, OperationType operationType) throws NoSuchResourceException {
        Optional<MicroCom> optionalMicroCom = MicroComRepository.findById(accountId);
        if(!optionalMicroCom.isPresent()){
            throw new NoSuchResourceException(": "+accountId);
        }
        MicroCom account = optionalMicroCom.get();
        int opType = operationType.equals(OperationType.WITHDRAWAL) ? -1 : 1;
        Operation operation = new Operation();
        operation.setAmount(opType*amount);
        operation.setDate(Instant.now());
        operation.setAccount(account);
        operation.setType(operationType);
        account.balance+=opType*amount;
        operationRepository.save(operation);
        return operation;
    }
}

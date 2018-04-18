package fr.communication.service;


import fr.communication.domain.MicroCom;
import fr.communication.domain.Operation;
import fr.communication.domain.OperationType;
import fr.communication.domain.dto.AccountDto;
import fr.communication.repository.MicroComRepository;
import fr.communication.utils.NoSuchResourceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MicroComServiceTest {

    @Mock
    private MicroComRepository MicroComRepository;

    @Mock
    private AccountDtoMapper accountDtoMapper;

    @InjectMocks
    private MicroComService MicroComService;

    private List<Operation> operations;
    private MicroCom account ;
    @Before
    public void setUp(){
        account = new MicroCom();
        account.setBalance(5000);
        account.setId(12L);
        operations = new ArrayList<>();
        operations.add(new Operation(Instant.now(), OperationType.DEPOSIT,10000,account));
        account.setOperations(operations);
    }

    @Test(expected = NoSuchResourceException.class)
    public void listAllOperations_should_throw_exception_for_no_such_account() throws Exception {
        when(MicroComRepository.findById(anyLong())).thenReturn(Optional.empty());
        MicroComService.listAllOperations(12L);
        Assert.fail("should have thrown NoSuchResourceException ");
    }


    @Test
    public void listAllOperations_should_successfully_return_all_account_operations() throws NoSuchResourceException {
        when(MicroComRepository.findById(12L)).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(MicroCom.class))).thenCallRealMethod();
        List<Operation> operations = MicroComService.listAllOperations(12L);
        assertThat(operations).isNotEmpty();
        assertThat(operations).hasSize(1);
    }

    @Test(expected = NoSuchResourceException.class)
    public void printStatement_should_throw_exception_for_no_such_account() throws NoSuchResourceException {
        when(MicroComRepository.findById(anyLong())).thenReturn(Optional.empty());
        MicroComService.printStatement(12L);
        Assert.fail("should have thrown NoSuchResourceException ");
    }

    @Test
    public void printStatement_should_successfully_return_current_account_balance() throws NoSuchResourceException {
        when(MicroComRepository.findById(anyLong())).thenReturn(Optional.of(account));
        when(accountDtoMapper.mapEntityToDto(any(MicroCom.class))).thenCallRealMethod();
        AccountDto accountDto = MicroComService.printStatement(12L);
        assertThat(accountDto.getBalance()).isEqualTo(account.getBalance());
        assertThat(accountDto.getLatestOperations()).isNotEmpty();
        assertThat(accountDto.getLatestOperations()).hasSameSizeAs(account.getOperations());

        Operation operation = new Operation(Instant.now().minusSeconds(10000), OperationType.DEPOSIT,10000,account);
        account.getOperations().add(operation);
        when(MicroComRepository.findById(anyLong())).thenReturn(Optional.of(account));
        accountDto = MicroComService.printStatement(12L);
        assertThat(accountDto.getLatestOperations()).hasSize(2);
        assertThat(accountDto.getLatestOperations()).isSortedAccordingTo(Comparator.comparing(Operation::getDate).reversed());

    }




}

package fr.communication.domain.dto;

import fr.communication.domain.Operation;

import java.util.List;

public class AccountDto {

    private long balance;

    private List<Operation> latestOperations;

    public AccountDto() {
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Operation> getLatestOperations() {
        return latestOperations;
    }

    public void setLatestOperations(List<Operation> latestOperations) {
        this.latestOperations = latestOperations;
    }
}

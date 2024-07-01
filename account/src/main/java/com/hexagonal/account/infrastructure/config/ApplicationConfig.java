package com.hexagonal.account.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexagonal.account.application.services.AccountService;
import com.hexagonal.account.application.services.TransactionService;
import com.hexagonal.account.application.useCases.account.CreateAccountUseCaseImpl;
import com.hexagonal.account.application.useCases.account.DeleteAccountUseCaseImpl;
import com.hexagonal.account.application.useCases.account.RetrieveAccountUseCaseImpl;
import com.hexagonal.account.application.useCases.account.UpdateAccountUseCaseImpl;
import com.hexagonal.account.application.useCases.transactions.CreateTransactionUseCaseImpl;
import com.hexagonal.account.application.useCases.transactions.DeleteTransactionUseCaseImpl;
import com.hexagonal.account.application.useCases.transactions.RetrieveTransactionUseCaseImpl;
import com.hexagonal.account.application.useCases.transactions.UpdateTransactionUseCaseImpl;
import com.hexagonal.account.domain.ports.out.AccountRepositoryPort;
import com.hexagonal.account.domain.ports.out.TransactionRepositoryPort;
import com.hexagonal.account.infrastructure.repositories.AccountRepository;
import com.hexagonal.account.infrastructure.repositories.TransactionRepository;

@Configuration
public class ApplicationConfig {
    @Bean
    public AccountService accountService(AccountRepositoryPort accountRepositoryPort) {
        return new AccountService(
                new CreateAccountUseCaseImpl(accountRepositoryPort),
                new DeleteAccountUseCaseImpl(accountRepositoryPort),
                new RetrieveAccountUseCaseImpl(accountRepositoryPort),
                new UpdateAccountUseCaseImpl(accountRepositoryPort));
    }

    @Bean
    public TransactionService transactionService(TransactionRepositoryPort transactionRepositoryPort,
            AccountRepository accountRepository) {
        return new TransactionService(
                new CreateTransactionUseCaseImpl(transactionRepositoryPort, accountRepository),
                new DeleteTransactionUseCaseImpl(transactionRepositoryPort, accountRepository),
                new RetrieveTransactionUseCaseImpl(transactionRepositoryPort, accountRepository),
                new UpdateTransactionUseCaseImpl(transactionRepositoryPort));
    }

    @Bean
    public AccountRepositoryPort accountRepositoryPort(AccountRepository accountRepository) {
        return accountRepository;
    }

    @Bean
    public TransactionRepositoryPort transactionRepositoryPort(TransactionRepository transactionRepository) {
        return transactionRepository;
    }
}

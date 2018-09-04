package com.transfer.service;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.transfer.domain.Account;
import com.transfer.repository.AccountRepository;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    @InjectMocks
    AccountService service = new AccountServiceImpl();

    @Mock
    AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Account> accountArgCaptor;

    Random random = new Random();
    private static final Account EMPTY_ACCOUNT = new Account();
    private static final String EMPTY_ACCOUNT_NAME = "";

    @Test(expected = RuntimeException.class)
    public final void testFindOneFailsforValidationCheckEmpty() {
	// GIVEN

	// WHEN
	service.findOne(EMPTY_ACCOUNT_NAME);

	// THEN
	// Expected exception
    }

    @Test
    public final void testFindOneReturnsEmpty() {
	// GIVEN
	String accountName = randomAlphanumeric(10);
	when(accountRepository.findById(any(String.class))).thenReturn(Optional.of(EMPTY_ACCOUNT));

	// WHEN
	Account response = service.findOne(accountName);

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).findById(eq(accountName));
	// Assertions
	assertThat(EMPTY_ACCOUNT, is(equalTo(response)));
    }

    @Test
    public final void testFindOneReturnsExpectedAccount() {
	// GIVEN
	String accountName = randomAlphanumeric(10);
	Account account = anAccount();
	when(accountRepository.findById(any(String.class))).thenReturn(Optional.of(account));

	// WHEN
	Account response = service.findOne(accountName);

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).findById(eq(accountName));
	// Assertions
	assertThat(account, is(equalTo(response)));
    }

    @Test(expected = RuntimeException.class)
    public final void testExistsFailsforValidationCheckEmpty() {
	// GIVEN

	// WHEN
	service.exists(EMPTY_ACCOUNT_NAME);

	// THEN
	// Expected exception
    }

    @Test
    public final void testExistsAccountDoesExists() {
	// GIVEN
	String accountName = randomAlphanumeric(10);
	when(accountRepository.existsById(any(String.class))).thenReturn(false);

	// WHEN
	boolean response = service.exists(accountName);

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).existsById(eq(accountName));
	// Assertions
	assertThat(false, is(equalTo(response)));
    }

    @Test
    public final void testExistsAccountDoesNotExists() {
	// GIVEN
	String accountName = randomAlphanumeric(10);
	when(accountRepository.existsById(any(String.class))).thenReturn(true);

	// WHEN
	boolean response = service.exists(accountName);

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).existsById(eq(accountName));
	// Assertions
	assertThat(true, is(equalTo(response)));
    }

    @Test(expected = RuntimeException.class)
    public final void testCreateAccountFailsForValidationName() {
	// GIVEN

	// WHEN
	service.createAccount(EMPTY_ACCOUNT_NAME, anAmount());

	// THEN
	// Expected exception
    }

    @Test(expected = RuntimeException.class)
    public final void testCreateAccountFailsForValidationNullAmount() {
	// GIVEN
	String accountName = randomAlphanumeric(10);

	// WHEN
	service.createAccount(accountName, null);

	// THEN
	// Expected exception
    }

    @Test(expected = RuntimeException.class)
    public final void testCreateAccountFailsForValidationNegativeAmount() {
	// GIVEN
	String accountName = randomAlphanumeric(10);

	// WHEN
	service.createAccount(accountName, anAmount().negate());

	// THEN
	// Expected exception
    }

    @Test
    public final void testCreateAccountSavesAnAccount() {
	// GIVEN
	String accountName = randomAlphanumeric(10);
	BigDecimal amount = anAmount();

	// WHEN
	service.createAccount(accountName, amount);

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).save(any(Account.class));
	verify(accountRepository).save(accountArgCaptor.capture());

	// Assertions
	assertThat(accountName, is(equalTo(accountArgCaptor.getAllValues().get(0).getName())));
	assertThat(amount, is(equalTo(accountArgCaptor.getAllValues().get(0).getBalance())));
    }

    @Test
    public final void testFindAll() {
	// GIVEN
	Iterable<Account> listAccount = anAccountList();
	when(accountRepository.findAll()).thenReturn(listAccount);

	// WHEN
	Iterable<Account> response = service.findAll();

	// THEN
	// Expected invocations
	verify(accountRepository, times(1)).findAll();
	// Assertions
	assertThat(listAccount, is(equalTo(response)));
    }

    private Account anAccount() {
	Account account = new Account();
	account.setBalance(new BigDecimal(random.nextInt(1000000)));
	account.setName(randomAlphanumeric(10));
	return account;
    }

    private BigDecimal anAmount() {
	return new BigDecimal(random.nextInt(1000000));
    }

    private Iterable<Account> anAccountList() {

	int size = random.nextInt(10);
	List<Account> listAccount = new ArrayList<Account>(size);
	int count = 0;
	while (count < size) {
	    count++;
	    Account account = new Account();
	    account.setBalance(new BigDecimal(random.nextInt(1000000)));
	    account.setName(randomAlphanumeric(10));
	    listAccount.add(account);
	}
	return listAccount;
    }
}
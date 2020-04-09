package br.sc.senac.bank.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bank-system")
public class ServiceBank {

	Map<Long, Account> accountList = new HashMap<Long, Account>();

	@PutMapping("/add/{numberAccount}/{userAccountName}/{balanceAccount}")
	void addAccount(@PathVariable long numberAccount, @PathVariable String userAccountName,
			@PathVariable double balanceAccount) {
		Account account = new Account(numberAccount, userAccountName, balanceAccount);
		accountList.put(numberAccount, account);
	}

	@GetMapping("/info/{numberAccount}")
	ResponseEntity<Account> getAccount(@PathVariable long numberAccount) {
		boolean isExistAccount = accountList.containsKey(numberAccount);
		if (isExistAccount) {
			Account account = accountList.get(numberAccount);
			return new ResponseEntity<>(account, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/deposit/{numberAccount}/{valueDeposit}")
	ResponseEntity<Account> depositAccount(@PathVariable long numberAccount, @PathVariable double valueDeposit) {
		boolean isExistAccount = accountList.containsKey(numberAccount);
		if (isExistAccount) {
			Account account = accountList.get(numberAccount);
			account.depositAccount(valueDeposit);
			return new ResponseEntity<>(account, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/withdraw/{numberAccount}/{valueWithdraw}")
	ResponseEntity<Account> withdrawAccount(@PathVariable long numberAccount, @PathVariable double valueWithdraw) {
		boolean isExistAccount = accountList.containsKey(numberAccount);
		if (isExistAccount) {
			Account account = accountList.get(numberAccount);
			boolean isPossibleWithdraw = account.withDrawAccount(valueWithdraw);
			if (isPossibleWithdraw) {
				return new ResponseEntity<>(account, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
package br.sc.senac.bank.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	ResponseEntity<String> withdrawAccount(@PathVariable long numberAccount, @PathVariable double valueWithdraw) {
		boolean isExistAccount = accountList.containsKey(numberAccount);
		if (isExistAccount) {
			Account account = accountList.get(numberAccount);
			boolean isPossibleWithdraw = account.withDrawAccount(valueWithdraw);
			if (isPossibleWithdraw) {
				return new ResponseEntity<>("Saque realizado!", HttpStatus.OK);
			}
			return new ResponseEntity<>("Valor de saque maior que saldo atual da conta", HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>("Conta de número: " + numberAccount + " não encontrado", HttpStatus.NOT_FOUND);
	}
	
	@PatchMapping("/transfer/{numberAccountRealizeTransfer}/{valueTransfer}/{numberAccountReceiveTransfer}")
	ResponseEntity<String> transfer(@PathVariable long numberAccountRealizeTransfer, @PathVariable double valueTransfer, @PathVariable long numberAccountReceiveTransfer) {
		boolean isExistAccountRealizeTransfer = accountList.containsKey(numberAccountRealizeTransfer);
		if (isExistAccountRealizeTransfer) {
			Account accountRealizeTransfer = accountList.get(numberAccountRealizeTransfer);
			boolean isExistAccountReceiveTransfer = accountList.containsKey(numberAccountReceiveTransfer);
			if (isExistAccountReceiveTransfer) {
				Account accountReceiveTransfer = accountList.get(numberAccountReceiveTransfer);
				boolean isTransfer = accountRealizeTransfer.transfer(accountReceiveTransfer, valueTransfer);
				if (isTransfer) {
					return new ResponseEntity<String>("Transferência realizada", HttpStatus.OK);
				}
				return new ResponseEntity<String>("Transferência mal sucedida", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<String>("Conta de número: " + numberAccountReceiveTransfer + " não encontrado", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Conta de número: " + numberAccountRealizeTransfer + " não encontrado", HttpStatus.NOT_FOUND);
	}
}
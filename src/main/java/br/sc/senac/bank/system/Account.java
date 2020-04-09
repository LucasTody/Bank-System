package br.sc.senac.bank.system;

public class Account {

	private long numberAccount;
	private String userAccountName;
	private double balanceAccount;
	
	Account(long numberAccount, String userAccountName, double balanceAccount){
		this.numberAccount = numberAccount;
		this.userAccountName = userAccountName;
		this.balanceAccount = balanceAccount;
	}
	
	public void depositAccount(double valueDeposit) {
		this.balanceAccount += valueDeposit;
	}
	
	public boolean withDrawAccount(double valueWithdraw) {
		if(valueWithdraw <= this.balanceAccount){
			this.balanceAccount -= valueWithdraw;
			return true;
		}
		return false;
	}

	public long getNumberAccount() {
		return numberAccount;
	}

	public String getUserAccountName() {
		return userAccountName;
	}

	public double getBalanceAccount() {
		return balanceAccount;
	}
	
	public boolean transfer(Account accountReceiveTransfer, double valueTransfer) {
		boolean isPossibleTransfer = this.withDrawAccount(valueTransfer);
		if(isPossibleTransfer) {
			accountReceiveTransfer.depositAccount(valueTransfer);
			return true;
		}
		return false;
	}
}

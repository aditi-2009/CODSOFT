public class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public String deposit(double amount) {
        return account.deposit(amount)
                ? "Deposit Successful"
                : "Invalid Amount";
    }

    public String withdraw(double amount) {
        return account.withdraw(amount)
                ? "Withdrawal Successful"
                : "Insufficient Balance or Invalid Amount";
    }

    public String checkBalance() {
        return "Current Balance: ₹" + account.getBalance();
    }
}
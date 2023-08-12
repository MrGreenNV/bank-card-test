package ru.averkiev.bankcardtest;

/**
 * Класс представляет собой основной функционал для взаимодействия с кредитными картами.
 * @author mrGreenNV
 */
public abstract class CreditCard extends BankCard {
    /**
     * Лимит кредитной карты.
     */
    private double creditLimit;

    /**
     * Кредитные средства.
     */
    private double creditFunds;

    /**
     * Позволяет создать кредитную карту с кредитным лимитом по умолчанию.
     */
    public CreditCard() {
        super();
        this.creditLimit = 5_000;       // Значение кредитного лимита по умолчанию.
        this.creditFunds = this.creditLimit;
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и кредитным лимитом по умолчанию.
     * @param initialBalance начальный баланс счета.
     */
    public CreditCard(double initialBalance) {
        super(initialBalance);
        this.creditLimit = 5_000;       // Значение кредитного лимита по умолчанию.
        this.creditFunds = this.creditLimit;
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и указанным кредитным лимитом.
     * @param initialBalance стартовый баланс счета.
     * @param initialCreditLimit указанный кредитный лимит карты.
     */
    public CreditCard(double initialBalance, double initialCreditLimit) {
        super(initialBalance);
        this.creditLimit = initialCreditLimit;
        this.creditFunds = this.creditLimit;
    }

    /**
     * Позволяет установить размер кредитного лимита по карте.
     * @param creditLimit кредитный лимит.
     */
    protected void setCreditLimit(double creditLimit) {
        if (creditLimit >= 0) {
            this.creditLimit = creditLimit;
        }
    }

    /**
     * Позволяет внести денежные средства на счет, учитывая размер кредитных средств.
     * @param amount сумма пополнения.
     */
    @Override
    protected void deposit(double amount) {
        double debt = this.creditLimit - this.creditFunds;
        if (debt > 0) {
            if (amount <= debt) {                   // Если размер платежа меньше или равен задолженности по кредитному счету.
                this.creditFunds += amount;
                System.out.println("Внесенная сумма: " + amount + " ушла на погашение кредита.");
                showAvailableFunds();
            } else {                                // Если размер платежа больше задолженности по кредитному счету.
                this.creditFunds = this.creditLimit;
                amount -= debt;
                super.deposit(amount);
                System.out.println("Часть внесенной суммы: " + debt + " ушла на погашение кредита.");
                showAvailableFunds();
            }
        } else {
            super.deposit(amount);
            System.out.println("Счет пополнен на сумму: " + amount + ".");
            showAvailableFunds();
        }
    }

    /**
     * Позволяет произвести платеж с учетом кредитных средств.
     * @param amount сумма оплаты.
     * @return true, если платеж успешно проведен, иначе - false.
     */
    @Override
    protected Boolean pay(double amount) {
        if (amount < 0) {
            System.out.println("Некорректная сумма платежа.");
            return false;
        }
        if (super.getBalance() >= amount) {         // Если собственных средств достаточно для проведения платежа.
            if (super.pay(amount)) {
                System.out.println("Платеж на сумму: " + amount + " успешно проведен.");
                showAvailableFunds();
                return true;
            } else {
                return false;
            }
        } else {
            if (amount <= (getBalance() + this.creditFunds)) {
                if (super.pay(getBalance())) {
                    amount -= getBalance();
                    this.creditFunds -= amount;
                    System.out.println("Платеж на сумму: " + amount + " успешно проведен.");
                    showAvailableFunds();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Позволяет получить информацию о состоянии кредитной карты.
     */
    @Override
    protected void showAvailableFunds() {
        System.out.println("Кредитный лимит: " + creditLimit + ".");
        System.out.println("Кредитные средства: " + creditFunds + ".");
        super.showAvailableFunds();
    }
}

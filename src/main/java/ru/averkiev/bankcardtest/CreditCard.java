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
    protected final void setCreditLimit(double creditLimit) {
        if (creditLimit >= 0 && creditLimit >= (this.creditLimit - this.creditFunds)) {
            this.creditFunds += creditLimit - this.creditLimit;
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
                System.out.println("Внесенная сумма: " + amount + " ушла на погашение задолженности.");
            } else {                                // Если размер платежа больше задолженности по кредитному счету.
                this.creditFunds = this.creditLimit;
                amount -= debt;
                super.deposit(amount);
                System.out.println("Часть внесенной суммы: " + debt + " ушла на погашение задолженности.");
            }
        } else {
            super.deposit(amount);
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
            return super.pay(amount);
        } else {
            if (amount <= (getBalance() + this.creditFunds)) {
                double balance = getBalance();
                if (super.pay(balance)) {
                    amount -= balance;
                    this.creditFunds -= amount;
                    System.out.println("Остаток платежа: " + amount + " списан за счет кредитных средств.");
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Для проведения платежа недостаточно денежных средств.");
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

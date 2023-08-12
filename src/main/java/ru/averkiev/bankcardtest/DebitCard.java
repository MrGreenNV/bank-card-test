package ru.averkiev.bankcardtest;

/**
 * Класс представляет собой основной функционал для взаимодействия с дебетовыми картами.
 * @author mrGreenNV
 */
public abstract class DebitCard extends BankCard {

    /**
     * Позволяет создать дебетовую карту с нулевым балансом счета.
     */
    public DebitCard() {
        super();
    }

    /**
     * Позволяет создать карту с указанным значением счета.
     * @param initialBalance стартовый баланс дебетовой карты.
     */
    public DebitCard(double initialBalance) {
        super(initialBalance);
    }

    @Override
    protected void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    protected Boolean pay(double amount) {
        if (super.pay(amount)) {
            System.out.println("Платеж на сумму: " + amount + " успешно проведен.");
            return true;
        } else {
            return false;
        }
    }
}

package ru.averkiev.bankcardtest;

/**
 * Класс представляет собой основные функции для взаимодействия с банковской картой, такие как пополнение, оплата и
 * получение информации по счету, включая баланс.
 * @author mrGreenNV
 */
public abstract class BankCard {
    /**
     * Баланс счета.
     */
    private double balance;

    /**
     * Позволяет создать счет с указанным стартовым балансом.
     * @param initialBalance значение стартового баланса.
     */
    public BankCard(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Позволяет создать счет с нулевым балансом.
     */
    public BankCard() {
        this.balance = 0;
    }

    /**
     * Производит пополнение счета на указанную сумму.
     * @param amount сумма пополнения.
     */
    protected void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Счет успешно пополнен на сумму: " + amount + ". Текущий баланс: " + this.balance);
        }

        System.out.println("Ошибка при пополнении счета.");
    }

    /**
     * Производит оплату со счета, если на счете достаточно средств.
     * @param amount сумма оплаты.
     * @return true, если платеж успешно проведен, иначе false.
     */
    protected Boolean pay(double amount) {

        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true;
        }

        System.out.println("Ошибка при проведении платежа.");
        return false;
    }

    /**
     * Возвращает текущий баланс счета.
     * @return значение баланса.
     */
    protected double getBalance() {
        return this.balance;
    }

    /**
     * Позволяет получить информацию о карте.
     */
    protected void showAvailableFunds() {
        System.out.println("Собственные средства: " + this.balance + ".");
    }
}
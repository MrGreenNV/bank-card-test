package ru.averkiev.bankcardtest;

/**
 * Класс представляет собой функционал дебетовой карты с накоплением средств от суммы пополнения.
 * @author mrGreenNV
 */
public final class CapitalDebitCard extends DebitCard {

    /**
     * Размер начислений при пополнении дебетовой карты.
     */
    private static final double CAPITAL_RATE = 0.00005;

    /**
     * Текущие накопления за счет пополнений.
     */
    private double currentCapital = 0;

    /**
     * Позволяет создать дебетовую карту с нулевым балансом.
     */
    public CapitalDebitCard() {
        super();
    }

    /**
     * Позволяет создать дебетовую карту с заданным балансом.
     * @param initialBalance стартовый баланс счета.
     */
    public CapitalDebitCard(double initialBalance) {
        super(initialBalance);
    }

    /**
     * Позволяет пополнить счет с учетом накопления средств.
     * @param amount сумма пополнения.
     */
    @Override
    protected void deposit(double amount) {
        double capital = amount * CAPITAL_RATE;
        super.deposit(amount + capital);
        this.currentCapital += capital;
    }

    /**
     * Позволяет получить информацию о счёте дебетовой карты с учетом накоплений.
     */
    @Override
    protected void showAvailableFunds() {
        super.showAvailableFunds();
        System.out.println("В том числе накопления за счёт пополнения: " + this.currentCapital + ".");
    }
}

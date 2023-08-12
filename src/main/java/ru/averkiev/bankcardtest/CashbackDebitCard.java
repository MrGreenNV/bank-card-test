package ru.averkiev.bankcardtest;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Класс представляет собой функционал дебетовой карты с потенциальным кэшбэком.
 * @author mrGreenNV
 */
public final class CashbackDebitCard extends DebitCard {

    /**
     * Размер потенциального кэшбэка.
     */
    private static final double CASHBACK_RATE = 0.05;

    /**
     * Минимальные траты для накопления кэшбэка.
     */
    private static final double MIN_AMOUNT_FOR_CASHBACK = 5_000;

    /**
     * Период за который происходит учет трат для расчета кэшбэка.
     */
    private static final int PERIOD_IN_DAYS = LocalDate.now().getDayOfMonth();

    /**
     * Текущие траты за определенный период.
     */
    private double amountInCurrentPeriod = 0;

    /**
     * Дата обнуления расчетного периода для кэшбэка.
     */
    private LocalDate counterResetDate = LocalDate.now();

    /**
     * Накопленный кэшбэк.
     */
    private double cashback = 0;

    /**
     * Позволяет создать дебетовую карту с нулевым балансом.
     */
    public CashbackDebitCard() {
        super();
    }

    /**
     * Позволяет создать дебетовую карту с заданным балансом.
     * @param initialBalance стартовый баланс счета.
     */
    public CashbackDebitCard(double initialBalance) {
        super(initialBalance);
    }

    /**
     * Позволяет провести оплату по дебетовой карте с учетом начисления кэшбэка.
     * @param amount сумма оплаты.
     * @return true, если платеж успешно совершен, иначе false.
     */
    @Override
    protected Boolean pay(double amount) {
        if (super.pay(amount)) {
            LocalDate now = LocalDate.now();
            if (ChronoUnit.DAYS.between(now, counterResetDate) > PERIOD_IN_DAYS) {
                this.counterResetDate = now;
                this.amountInCurrentPeriod = 0;
            }
            if (amountInCurrentPeriod >= MIN_AMOUNT_FOR_CASHBACK) {
                double cashback = amount * CASHBACK_RATE;
                this.cashback += cashback;
                System.out.println("С покупки на сумму: " + amount + " возвращено кэшбэком - " + cashback + ".");
            } else if (amount > MIN_AMOUNT_FOR_CASHBACK) {
                double cashback = (amount - MIN_AMOUNT_FOR_CASHBACK) * CASHBACK_RATE;
                this.cashback += cashback;
                System.out.println("С покупки на сумму: " + amount + " возвращено кэшбэком - " + cashback + ".");
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Позволяет получить информацию о состоянии дебетовой карты с учетом потенциального кэшбэка.
     */
    @Override
    protected void showAvailableFunds() {
        super.showAvailableFunds();
        System.out.println("Накопленный кэшбэк: " + cashback + ".");
    }

    /**
     * Позволяет вывести кэшбэк на счет.
     */
    public void getCashback() {
        super.deposit(cashback);
        System.out.println("Кэшбэк в размере: " + this.cashback + " успешно получен.");
        this.cashback = 0;
    }
}
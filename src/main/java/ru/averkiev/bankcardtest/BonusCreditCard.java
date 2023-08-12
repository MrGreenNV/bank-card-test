package ru.averkiev.bankcardtest;

/**
 * Класс представляет собой функционал кредитной карты с бонусной программой.
 * @author mrGreenNV
 */
public final class BonusCreditCard extends CreditCard {

    /**
     * Размер бонуса от покупки.
     */
    private static final double BONUS_RATE = 0.01;

    /**
     * Накопленные бонусные баллы.
     */
    private double bonus = 0;

    /**
     * Позволяет создать кредитную карту с кредитным лимитом по умолчанию.
     */
    public BonusCreditCard() {
        super();
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и кредитным лимитом по умолчанию.
     * @param initialBalance начальный баланс счета.
     */
    public BonusCreditCard(double initialBalance) {
        super(initialBalance);
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и указанным кредитным лимитом.
     * @param initialBalance стартовый баланс счета.
     * @param initialCreditLimit указанный кредитный лимит карты.
     */
    public BonusCreditCard(double initialBalance, double initialCreditLimit) {
        super(initialBalance, initialCreditLimit);
    }

    /**
     * Позволяет вывести бонусы на счет.
     */
    public void getBonus() {
        super.deposit(bonus);
        System.out.println("Бонусы в размере: " + this.bonus + " успешно получены.");
        this.bonus = 0;
    }

    /**
     * Позволяет провести оплату по кредитной карте с учетом накопления бонусов.
     * @param amount сумма оплаты.
     * @return true, если платеж успешен, иначе - false.
     */
    @Override
    protected Boolean pay(double amount) {
        if (super.pay(amount)) {
            this.bonus += amount * BONUS_RATE;
            System.out.println("С покупки на сумму: " + amount + " накоплено бонусов - " + this.bonus + ".");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Позволяет получить информацию о состоянии кредитной карты с учетом бонусной программы.
     */
    @Override
    protected void showAvailableFunds() {
        super.showAvailableFunds();
        System.out.println("Бонусные средства: " + this.bonus + ".");
    }
}


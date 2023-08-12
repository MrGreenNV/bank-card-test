package ru.averkiev.bankcardtest;

/**
 * @author mrGreenNV
 */
public class TravelCreditCard extends CreditCard {

    /**
     * Траты для накопления одной мили.
     */
    private static final double SPENDING_TO_ACCUMULATE_ONE_MILE = 5_000;

    /**
     * Накопленные мили.
     */
    private double currentCountMiles = 0;

    /**
     * Позволяет создать кредитную карту с кредитным лимитом по умолчанию.
     */
    public TravelCreditCard() {
        super();
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и кредитным лимитом по умолчанию.
     * @param initialBalance начальный баланс счета.
     */
    public TravelCreditCard(double initialBalance) {
        super(initialBalance);
    }

    /**
     * Позволяет создать кредитную карту со стартовым балансом счета и указанным кредитным лимитом.
     * @param initialBalance стартовый баланс счета.
     * @param initialCreditLimit указанный кредитный лимит карты.
     */
    public TravelCreditCard(double initialBalance, double initialCreditLimit) {
        super(initialBalance, initialCreditLimit);
    }

    /**
     * Позволяет провести оплату по кредитной карте с учетом накопления миль.
     * @param amount сумма оплаты.
     * @return true, если платеж успешен, иначе - false.
     */
    @Override
    protected Boolean pay(double amount) {
        if (super.pay(amount)) {
            double miles = amount / SPENDING_TO_ACCUMULATE_ONE_MILE;
            this.currentCountMiles += miles;
            System.out.println("С покупки на сумму: " + amount + " накоплено миль - " + miles + ".");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Позволяет получить информацию о состоянии кредитной карты с учетом накопления миль.
     */
    @Override
    protected void showAvailableFunds() {
        super.showAvailableFunds();
        System.out.println("Накопленные мили: " + this.currentCountMiles + ".");
    }

    /**
     * Позволяет потратить мили.
     * @param miles количество миль для списания.
     * @return true, если мили успешно списаны, иначе false.
     */
    public Boolean spendMiles(double miles) {
        if (miles < 0) {
            System.out.println("Некорректное значение миль.");
            return false;
        }

        if (miles > this.currentCountMiles) {
            System.out.println("Недостаточно миль.");
            return false;
        }

        this.currentCountMiles -= miles;
        System.out.println("Успешное списание " + miles + " миль(и).");
        return true;
    }
}

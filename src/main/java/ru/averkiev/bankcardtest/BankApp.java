package ru.averkiev.bankcardtest;

/**
 * Основной класс для запуска программы. В данном классе для примера созданы различные карты для проверки функциональности.
 * Однако большинство тестов функциональности выполнено в соответствующих классах для тестирования.
 * @author mrGreenNV
 */
public class BankApp {

    public static void main(String[] args) {

        // Проверка функциональности кредитной карты с накоплением бонусов.
        BonusCreditCard bonusCard = new BonusCreditCard();
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.pay(3_000);
        System.out.println();

        bonusCard.setCreditLimit(15_000);
        bonusCard.pay(7_000);
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.deposit(9_000);
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.deposit(12_000);
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.pay(30_000);
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.pay(20_000);
        bonusCard.showAvailableFunds();
        System.out.println();

        bonusCard.getBonus();
        bonusCard.showAvailableFunds();
        System.out.println();

        // Проверка функциональности кредитной карты с накоплением миль.
        TravelCreditCard travelCard = new TravelCreditCard(100_000, 300_000);
        travelCard.showAvailableFunds();
        System.out.println();

        travelCard.pay(350_000);
        travelCard.showAvailableFunds();
        System.out.println();

        travelCard.spendMiles(50);
        travelCard.showAvailableFunds();
        System.out.println();

        travelCard.spendMiles(30);
        travelCard.showAvailableFunds();
        System.out.println();

        travelCard.deposit(500_000);
        travelCard.showAvailableFunds();
        System.out.println();

        travelCard.setCreditLimit(1_000_000);
        travelCard.showAvailableFunds();
        System.out.println();

        // Проверка функциональности дебетовой карты с накопительной программой.
        CapitalDebitCard capitalCard = new CapitalDebitCard();
        capitalCard.showAvailableFunds();
        System.out.println();

        capitalCard.pay(500);
        capitalCard.showAvailableFunds();
        System.out.println();

        capitalCard.deposit(2_000_000);
        capitalCard.showAvailableFunds();
        System.out.println();

        capitalCard.deposit(5_000_000);
        capitalCard.showAvailableFunds();
        System.out.println();

        capitalCard.pay(7_000_150);
        capitalCard.showAvailableFunds();
        System.out.println();

        // Проверка функциональности дебетовой карты с потенциальным кэшбэком.
        CashbackDebitCard cashbackCard = new CashbackDebitCard(300_000);
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.pay(3_000);
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.pay(2_005);
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.pay(244_995);
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.getCashback();
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.getCashback();
        cashbackCard.showAvailableFunds();
        System.out.println();

        cashbackCard.pay(100_000);
        cashbackCard.showAvailableFunds();
        System.out.println();
    }
}
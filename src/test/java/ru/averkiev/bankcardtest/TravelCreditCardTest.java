package ru.averkiev.bankcardtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тестовый класс для проверки функциональности класса TravelCreditCard.
 * @author mrGreenNV
 */
class TravelCreditCardTest {

    /**
     * Объявление тестируемой карты с потенциальным кэшбэком.
     */
    TravelCreditCard card;

    /**
     * Выполнение метода перед каждым тестом для создания новой карты.
     */
    @BeforeEach
    public void setUp() {
        card = new TravelCreditCard();
    }

    /**
     * Проверяет создание карты со стартовым балансом.
     */
    @Test
    public void testCreateTravelCreditCardWithInitialBalance() {

        // Создание тестовых данных.
        double initialBalance = 1_000;

        // Вызов проверяемого метода.
        TravelCreditCard travelCreditCard = new TravelCreditCard(initialBalance);

        // Проверка результатов.
        Assertions.assertEquals(initialBalance, travelCreditCard.getBalance());
    }

    /**
     * Проверяет создание карты со стартовым балансом и указанным кредитным лимитом.
     */
    @Test
    public void testCreateTravelCreditCardWithInitialBalanceAndCreditLimit() {

        // Создание тестовых данных.
        double initialBalance = 1_000;
        double initialCreditLimit = 5_000;

        // Вызов проверяемого метода.
        TravelCreditCard travelCreditCard = new TravelCreditCard(initialBalance, initialCreditLimit);

        // Проверка результатов.
        Assertions.assertEquals(initialBalance, travelCreditCard.getBalance());
    }

    /**
     * Проверяет правильность пополнения баланса с валидной суммой пополнения.
     */
    @Test
    public void testDeposit_ValidAmount() {

        // Создание тестовых данных.
        double amount = 300.;
        String expectedOutput = "Счет успешно пополнен на сумму: " + amount + ".";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.deposit(amount);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(amount, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность пополнения баланса с невалидной суммой пополнения.
     */
    @Test
    public void testDeposit_InvalidAmount() {

        // Создание тестовых данных.
        double amount = -300.;
        String expectedOutput = "Ошибка при пополнении счета.";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.deposit(amount);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(0, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность пополнения баланса с валидной суммой пополнения при наличии задолженности, превышающей
     * сумму пополнения.
     */
    @Test
    public void testDeposit_ValidAmount_WithDebtExceedingPaymentAmount() {

        // Создание тестовых данных.
        card.pay(1_000);
        double amount = 300.;
        String expectedOutput = "Внесенная сумма: " + amount + " ушла на погашение задолженности.";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.deposit(amount);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(0, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность пополнения баланса с валидной суммой пополнения при наличии задолженности, не превышающей
     * сумму пополнения.
     */
    @Test
    public void testDeposit_ValidAmount_WithDebtNotExceedingPaymentAmount() {

        // Создание тестовых данных.
        card.pay(1_000);
        double amount = 1_300.;
        double debt = 1_000;
        String expectedOutput = "Счет успешно пополнен на сумму: " + (amount - debt) + ".\n" +
                "Часть внесенной суммы: " + debt + " ушла на погашение задолженности.";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.deposit(amount);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(amount - debt, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете достаточно.
     */
    @Test
    public void testPay_ValidAmount_EnoughFunds() {
        // Создание тестовых данных.
        double amountDeposit = 1_000;
        double amountPay = 600;
        double miles = 600. / 5_000;
        card.deposit(amountDeposit);
        String expectedOutput = "Платеж на сумму: " + amountPay + " успешно проведен.\n" +
                "С покупки на сумму: " + amountPay + " накоплено миль - " + miles + ".";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.pay(amountPay);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals((amountDeposit - amountPay), card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(true, result);
    }

    /**
     * Проверяет правильность совершения платежа с невалидной суммой.
     */
    @Test
    public void testPay_InvalidAmount() {
        // Создание тестовых данных.
        double amountDeposit = 1_000;
        double amountPay = -600;
        card.deposit(amountDeposit);
        String expectedOutput = "Некорректная сумма платежа.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.pay(amountPay);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(amountDeposit, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(false, result);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда платеж возможен с использованием кредитных средств.
     */
    @Test
    public void testPay_ValidAmount_EnoughCreditFunds() {
        // Создание тестовых данных.
        double amountDeposit = 1_000;
        double amountPay = 1_600;
        double miles = amountPay / 5_000;
        card.deposit(amountDeposit);
        String expectedOutput = "Платеж на сумму: " + amountDeposit + " успешно проведен.\n" +
                "Остаток платежа: " + (amountPay - amountDeposit) + " списан за счет кредитных средств.\n" +
                "С покупки на сумму: " + amountPay + " накоплено миль - " + miles + ".";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.pay(amountPay);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(0, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(true, result);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда на платеж не хватает средств.
     */
    @Test
    public void testPay_ValidAmount_EnoughNotCreditFunds() {
        // Создание тестовых данных.
        double amountDeposit = 1_000;
        double amountPay = 11_600;
        card.deposit(amountDeposit);
        String expectedOutput = "Для проведения платежа недостаточно денежных средств.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.pay(amountPay);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(amountDeposit, card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(false, result);
    }

    /**
     * Проверяет правильность вывода информации о состоянии счета дебетовой карты с накоплениями.
     */
    @Test
    public void testShowAvailableFunds() {

        // Создание тестовых данных.
        double amountDeposit = 10_000;
        double amountPay = 12_000;
        double miles = amountPay / 5_000;
        double defaultCreditLimit = 5_000;
        card.deposit(amountDeposit);
        card.pay(amountPay);
        String expectedOutput = "Кредитный лимит: " + defaultCreditLimit + ".\n"
                + "Кредитные средства: " + (defaultCreditLimit - (amountPay - amountDeposit)) + ".\n"
                + "Собственные средства: " + 0.0 + ".\n"
                + "Накопленные мили: " + miles + ".";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.showAvailableFunds();

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет корректность списания миль с бонусного счета, при наличии достаточного количества миль для списания.
     */
    @Test
    public void testSpendMiles() {

        // Создание тестовых данных.
        double amountDeposit = 100_000;
        double amountPay = 50_000;
        double countSpendMiles = 7;
        card.deposit(amountDeposit);
        card.pay(amountPay);
        String expectedOutput = "Успешное списание " + countSpendMiles + " миль(и).";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.spendMiles(countSpendMiles);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(true, result);
    }

    /**
     * Проверяет корректность списания миль с бонусного счета, при отсутствии достаточного количества миль для списания.
     */
    @Test
    public void testSpendMiles_NotMilesForSpend() {

        // Создание тестовых данных.
        double amountDeposit = 100_000;
        double amountPay = 50_000;
        double countSpendMiles = 15;
        card.deposit(amountDeposit);
        card.pay(amountPay);
        String expectedOutput = "Недостаточно миль.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.spendMiles(countSpendMiles);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(false, result);
    }

    /**
     * Проверяет корректность списания миль с бонусного счета, при невалидном значении списываемых миль.
     */
    @Test
    public void testSpendMiles_InvalidMiles() {

        // Создание тестовых данных.
        double amountDeposit = 100_000;
        double amountPay = 50_000;
        double countSpendMiles = -15;
        card.deposit(amountDeposit);
        card.pay(amountPay);
        String expectedOutput = "Некорректное значение миль.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = card.spendMiles(countSpendMiles);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(expectedOutput, actualOutput);
        Assertions.assertEquals(false, result);
    }
}
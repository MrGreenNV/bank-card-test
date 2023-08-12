package ru.averkiev.bankcardtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тестовый класс для проверки функциональности класса CapitalDebitCard.
 * @author mrGreenNV
 */
class CapitalDebitCardTest {

    /**
     * Объявление тестируемой карты.
     */
    CapitalDebitCard card;

    /**
     * Выполнение метода перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        card = new CapitalDebitCard();
    }

    /**
     * Проверяет создание карты со стартовым балансом.
     */
    @Test
    public void testCreateCapitalDebitCardWithInitialBalance() {

        // Создание тестовых данных.
        double initialBalance = 1_000;

        // Вызов проверяемого метода.
        CapitalDebitCard capitalDebitCard = new CapitalDebitCard(initialBalance);

        // Проверка результатов.
        Assertions.assertEquals(initialBalance, capitalDebitCard.getBalance());
    }

    /**
     * Проверяет правильность пополнения баланса с валидной суммой пополнения.
     */
    @Test
    public void testDeposit_ValidAmount() {

        // Создание тестовых данных.
        double amount = 300.;
        double currentCapital = amount * 0.00005;
        String expectedOutput = "Счет успешно пополнен на сумму: " + (amount + currentCapital) + ".";
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
        Assertions.assertEquals(amount + amount * 0.00005, card.getBalance());
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
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете достаточно.
     */
    @Test
    public void testPay_ValidAmount_Enough_Funds() {

        // Создание тестовых данных.
        CapitalDebitCard capitalDebitCard = new CapitalDebitCard(1_000);
        double amount = 500.;
        String expectedOutput = "Платеж на сумму: " + amount + " успешно проведен.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = capitalDebitCard.pay(amount);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(500, capitalDebitCard.getBalance());
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете недостаточно.
     */
    @Test
    public void testPay_ValidAmount_Enough_Not_Funds() {

        // Создание тестовых данных.
        CapitalDebitCard capitalDebitCard = new CapitalDebitCard(1_000);
        String expectedOutput = "Ошибка при проведении платежа.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = capitalDebitCard.pay(1_500);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(1_000, capitalDebitCard.getBalance());
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с невалидной суммой, когда средств на счете достаточно.
     */
    @Test
    public void testPay_InvalidAmount_Enough_Funds() {

        // Создание тестовых данных.
        CapitalDebitCard capitalDebitCard = new CapitalDebitCard(1_000);
        String expectedOutput = "Ошибка при проведении платежа.";
        String actualOutput;
        PrintStream originalOut = System.out;
        Boolean result;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            result = capitalDebitCard.pay(-500);

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals(1000, capitalDebitCard.getBalance());
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность вывода информации о состоянии счета дебетовой карты с накоплениями.
     */
    @Test
    public void testShowAvailableFunds() {

        // Создание тестовых данных.
        double amount = 500;
        double currentCapital = amount * 0.00005;
        card.deposit(amount);
        String expectedOutput = "Собственные средства: " + (amount + currentCapital) + ".\n"
                + "В том числе накопления за счёт пополнения: " + currentCapital + ".";
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
}
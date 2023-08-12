package ru.averkiev.bankcardtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Тестовый класс для проверки функциональности класса CashbackDebitCard.
 * @author mrGreenNV
 */
class CashbackDebitCardTest {

    /**
     * Объявление тестируемой карты с потенциальным кэшбэком.
     */
    CashbackDebitCard card;

    /**
     * Выполнение метода перед каждым тестом для создания новой карты.
     */
    @BeforeEach
    public void serUp() {
        card = new CashbackDebitCard();
    }

    /**
     * Проверяет создание карты со стартовым балансом.
     */
    @Test
    public void testCreateCashbackDebitCardWithInitialBalance() {

        // Создание тестовых данных.
        double initialBalance = 1_000;

        // Вызов проверяемого метода.
        CashbackDebitCard cashbackDebitCard = new CashbackDebitCard(initialBalance);

        // Проверка результатов.
        Assertions.assertEquals(initialBalance, cashbackDebitCard.getBalance());
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
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете достаточно и возможен кэшбэк.
     */
    @Test
    public void testPay_ValidAmount_EnoughFunds_WithCashback() {

        // Создание тестовых данных.
        double amountDeposit = 10_000;
        double amountPay = 7_000;
        double cashback = 2_000 * 0.05;
        card.deposit(amountDeposit);

        String expectedOutput = "Платеж на сумму: " + amountPay + " успешно проведен." + "\n" +
                "С покупки на сумму: " + amountPay + " возвращено кэшбэком - " + cashback + ".";
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
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете достаточно и невозможен кэшбэк.
     */
    @Test
    public void testPay_ValidAmount_EnoughFunds_WithoutCashback() {

        // Создание тестовых данных.
        double amountDeposit = 3_000;
        double amountPay = 1_000;
        card.deposit(amountDeposit);

        String expectedOutput = "Платеж на сумму: " + amountPay + " успешно проведен.";
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
        Assertions.assertEquals(true, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с валидной суммой, когда средств на счете недостаточно.
     */
    @Test
    public void testPay_ValidAmount_NotEnoughFunds() {

        // Создание тестовых данных.
        double amountDeposit = 3_000;
        double amountPay = 5_000;
        card.deposit(amountDeposit);

        String expectedOutput = "Ошибка при проведении платежа.";
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
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность совершения платежа с невалидной суммой.
     */
    @Test
    public void testPay_InvalidAmount() {

        // Создание тестовых данных.
        double amountDeposit = 3_000;
        double amountPay = -1_000;
        card.deposit(amountDeposit);

        String expectedOutput = "Ошибка при проведении платежа.";
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
        Assertions.assertEquals(false, result);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет корректность вывода кэшбэка на счет.
     */
    @Test
    public void testGetCashback() {
        // Создание тестовых данных.
        double amountDeposit = 10_000;
        double amountPay = 7_000;
        double cashback = 2_000 * 0.05;
        card.deposit(amountDeposit);
        card.pay(amountPay);

        String expectedOutput = "Счет успешно пополнен на сумму: " + cashback + ".\n" +
                "Кэшбэк в размере: " + cashback + " успешно получен.";
        String actualOutput;
        PrintStream originalOut = System.out;

        // Вызов тестируемого метода с захватом потока вывода в консоль.
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            card.getCashback();

            actualOutput = outputStream.toString().trim();
        } finally {
            System.setOut(originalOut);
        }

        // Проверка результатов.
        Assertions.assertEquals((amountDeposit - amountPay + cashback), card.getBalance());
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Проверяет правильность вывода информации о состоянии счета дебетовой карты с накоплениями.
     */
    @Test
    public void testShowAvailableFunds() {

        // Создание тестовых данных.
        double amountDeposit = 10_000;
        double amountPay = 7_000;
        double cashback = 2_000 * 0.05;
        card.deposit(amountDeposit);
        card.pay(amountPay);
        String expectedOutput = "Собственные средства: " + (amountDeposit - amountPay) + ".\n"
                + "Накопленный кэшбэк: " + cashback + ".";
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
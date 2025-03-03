import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.math.BigInteger;

public class Main {
    public static BigInteger x1 = BigInteger.ZERO;
    public static BigInteger x2 = BigInteger.ZERO;
    public static BigInteger x3 = BigInteger.ZERO;
    public static BigInteger x4 = BigInteger.ZERO;
    public static BigInteger x5 = BigInteger.ZERO;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Введите выражение для вычисления или exit, чтобы завершить программу: ");
            String elements = scan.nextLine().strip();
            if (elements.equals("exit")) {
                System.out.println("Код завершён");
                break;
            }
            if (elements.isBlank()) {
                System.out.println("Ошибка: Пустой ввод");
                continue;
            }

            if (!countSpace(elements)) {
                System.out.println("Ошибка: Слишком много пробелов в выражении");
                continue;
            }

            String[] strings = elements.split(" ");
            if (isX(strings[0]) && strings.length == 1) {
                printX(strings[0]);
                continue;
            }
            if (isX(strings[0]) && strings[1].equals("=")) {
                calculatorWithX(strings);
                continue;
            }
            if (strings.length <= 2) {
                System.out.println("Ошибка: Недостаточное количество элементов (пробелов/чисел/операторов)");
                continue;
            }
            calculator(strings, "main");
        }
        scan.close();
    }

    public static boolean countSpace(String str) {
        str = str.strip();
        int space = 0;
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (symbol == ' ') {
                space++;
                if (space == 2) {
                    return false;
                }
            } else {
                space = 0;
            }
        }
        return true;
    }

    public static boolean isNumber(String str) {
        str = str.strip();
        if (str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (i == 0 && symbol == '-') {
                System.out.println("Ошибка: Отрицательное число на вводе");
                return false;
            }
            if (!Character.isDigit(symbol)) {
                return false;
            }
        }
        return true;
    }

    public static boolean noSpace(String str) {
        str = str.strip();
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (!(Character.isDigit(symbol) || isOperator("" + symbol))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOperator(String str) {
        str = str.strip();
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }

    public static BigInteger operation(BigInteger number, BigInteger nextNumber, String operator) {
        return switch (operator) {
            case "+" -> number.add(nextNumber);
            case "-" -> number.subtract(nextNumber);
            case "*" -> number.multiply(nextNumber);
            case "/" -> {
                if (nextNumber.equals(BigInteger.ZERO)) {
                    System.out.println("Ошибка: На ноль делить нельзя");
                    yield null;
                }
                yield number.divide(nextNumber);
            }
            default -> {
                System.out.println("Ошибка: Неизвестный оператор");
                yield null;
            }
        };
    }

    public static boolean isX(String string) {
        return switch (string.strip()) {
            case "x1", "x4", "x2", "x3", "x5" -> true;
            default -> false;
        };
    }

    public static void calculatorWithX(String[] strings) {
        if (strings.length < 3) {
            System.out.println("Ошибка: Недостаточно элементов для вычисления");
            return;
        }

        if (strings.length == 3 && isX(strings[2])) {
            BigInteger value = getX(strings[2]); // Получаем значение переменной
            if (value == null) {
                System.out.println("Ошибка: Неверное имя переменной");
                return;
            }
            xValue(strings[0], value); // Устанавливаем значение переменной
            System.out.println("Ответ: " + value);
            return;
        }

        int countX = 0;
        int countEquals = 0;
        for (String string : strings) {
            if (string.equals("=") && countEquals == 0) {
                countEquals++;
            } else if (isX(string) && countEquals == 1) {
                countX++;
            }else if (string.equals("=") && countEquals >= 1 && countX >= 1) {
                System.out.println("Ошибка: нельзя присваивать значение нескольким переменным");
                return;
            }
        }

        if (strings.length == 3 && isX(strings[2])) {
            switch (strings[2].strip()) {
                case "x1":
                    System.out.println("Ответ: " + x1);
                    return;
                case "x2":
                    System.out.println("Ответ: " + x2);
                    return;
                case "x3":
                    System.out.println("Ответ: " + x3);
                    return;
                case "x4":
                    System.out.println("Ответ: " + x4);
                    return;
                case "x5":
                    System.out.println("Ответ: " + x5);
                    return;
                default:
                    System.out.println("Ошибка: Неверное имя переменной");
                    return;
            }
        }

        BigInteger result = calculator(Arrays.copyOfRange(strings, 2, strings.length), "x");
        if (result == null) {
            return;
        }

        xValue(strings[0], result);
        System.out.println("Ответ: " + result);
    }

    public static void printX(String str) {
        switch (str.strip()) {
            case "x1" -> System.out.println(x1);
            case "x2" -> System.out.println(x2);
            case "x3" -> System.out.println(x3);
            case "x4" -> System.out.println(x4);
            case "x5" -> System.out.println(x5);
            default -> System.out.println("Ошибка: Неправильная переменная");
        }
    }

    public static BigInteger getX(String x) {
        return switch (x.strip()) {
            case "x1" -> x1;
            case "x2" -> x2;
            case "x3" -> x3;
            case "x4" -> x4;
            case "x5" -> x5;
            default -> null;
        };
    }

    private static void xValue(String x, BigInteger value) {
        switch (x.strip()) {
            case "x1" -> x1 = value;
            case "x2" -> x2 = value;
            case "x3" -> x3 = value;
            case "x4" -> x4 = value;
            case "x5" -> x5 = value;
            default -> System.out.println("Ошибка: Неверное имя переменной");
        }
    }

    public static BigInteger calculator(String[] strings, String func) {
        Stack<BigInteger> stackX = new Stack<>();
        int countNumbers = 0;
        boolean error = true;
        int countOperators = 0;
        BigInteger answer = BigInteger.ZERO;
        if (strings.length == 1 && isNumber(strings[0])) {
            return new BigInteger(strings[0]);
        }
        for (String string : strings) {
            if (isNumber(string)) {
                stackX.push(new BigInteger(string));
                countNumbers++;
            } else if (isX(string)) {
                switch (string) {
                    case "x1" -> stackX.push(x1);
                    case "x2" -> stackX.push(x2);
                    case "x3" -> stackX.push(x3);
                    case "x4" -> stackX.push(x4);
                    case "x5" -> stackX.push(x5);
                }
                countNumbers++;
            } else if (isOperator(string)) {
                countOperators++;
                if (countOperators >= countNumbers) {
                    error = false;
                    System.out.println("Ошибка: Некорректная постфиксная запись");
                    break;
                }
                BigInteger number = stackX.pop();
                BigInteger nextNumber = stackX.pop();
                BigInteger result = operation(nextNumber, number, string);
                if (result == null) {
                    error = false;
                    break;
                }
                answer = result;
                stackX.push(answer);
            } else if (noSpace(string)) {
                System.out.println("Ошибка: Недостаточное количество пробелов");
                error = false;
                break;
            } else {
                System.out.println("Ошибка: Неизвестный символ");
                error = false;
                break;
            }
        }
        if (stackX.size() > 1 && error) {
            System.out.println("Ошибка: Недостаточное количество арифметических операторов");
            error = false;
        }
        if (error && func.equals("x")) {
            return answer;
        }
        if (error && func.equals("main")) {
            System.out.println("Ответ: " + answer);
            return answer;
        }
        return null;
    }
}
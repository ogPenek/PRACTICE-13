import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DiaryApp {
    static final int MAX_ENTRIES = 50;
    static LocalDate[] dates = new LocalDate[MAX_ENTRIES];
    static String[] entries = new String[MAX_ENTRIES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== МІЙ ЩОДЕННИК ===");
            System.out.println("1. Додати запис");
            System.out.println("2. Видалити запис");
            System.out.println("3. Переглянути всі записи");
            System.out.println("4. Вийти");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEntry(scanner);
                    break;
                case "2":
                    deleteEntry(scanner);
                    break;
                case "3":
                    viewEntries();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }

    static void addEntry(Scanner scanner) {
        if (getEntryCount() >= MAX_ENTRIES) {
            System.out.println("Щоденник заповнений!");
            return;
        }

        System.out.print("Введіть дату (рррр-мм-дд): ");
        String dateInput = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Неправильний формат дати!");
            return;
        }

        System.out.println("Введіть текст запису (завершіть введення рядком 'END'):");
        StringBuilder entryText = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("END")) break;
            entryText.append(line).append("\n");
        }

        for (int i = 0; i < MAX_ENTRIES; i++) {
            if (dates[i] == null) {
                dates[i] = date;
                entries[i] = entryText.toString().trim();
                System.out.println("Запис додано!");
                return;
            }
        }
    }

    static void deleteEntry(Scanner scanner) {
        System.out.print("Введіть дату запису для видалення (рррр-мм-дд): ");
        String dateInput = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Неправильний формат дати!");
            return;
        }

        for (int i = 0; i < MAX_ENTRIES; i++) {
            if (dates[i] != null && dates[i].equals(date)) {
                dates[i] = null;
                entries[i] = null;
                System.out.println("Запис видалено.");
                return;
            }
        }
        System.out.println("Запис за вказаною датою не знайдено.");
    }

    static void viewEntries() {
        boolean found = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < MAX_ENTRIES; i++) {
            if (dates[i] != null) {
                System.out.println("\nДата: " + formatter.format(dates[i]));
                System.out.println("Запис:\n" + entries[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Записів немає.");
        }
    }

    static int getEntryCount() {
        int count = 0;
        for (LocalDate d : dates) {
            if (d != null) count++;
        }
        return count;
    }
}

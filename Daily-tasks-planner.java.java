import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.time.LocalDate;
import java.time.YearMonth;

class Task {
    private String description;
    private String priority;
    private String dueTime;
    private boolean isCompleted;
    private LocalDate dueDate;

    public Task(String description, String priority, String dueTime, LocalDate dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueTime = dueTime;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[Task: " + description + ", Priority: " + priority + ", Due: " + dueDate +
                ", Status: " + (isCompleted ? "Completed" : "Incomplete") + "]";
    }
}

class Planner {
    private ArrayList<Task> tasks;

    public Planner() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description, String priority, String dueTime, LocalDate dueDate) {
        tasks.add(new Task(description, priority, dueTime, dueDate));
        System.out.println("Task added successfully!");
    }

    public void viewTasks(boolean showCompleted) {
        System.out.println("\nTasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (showCompleted || !task.isCompleted()) {
                System.out.println((i + 1) + ". " + task);
            }
        }
    }

    public void updateTask(int taskNumber, String description, String priority, String dueTime, LocalDate dueDate) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.setDescription(description);
            task.setPriority(priority);
            task.setDueTime(dueTime);
            task.setDueDate(dueDate);
            System.out.println("Task updated successfully!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    public void deleteTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.remove(taskNumber - 1);
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Invalid task number!");
        }
    }

    public void markTaskAsCompleted(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsCompleted();
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Invalid task number!");
        }
    }
}

public class Main {
    private static final String USERNAME = "tuhin";
    private static final String PASSWORD = "12345";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        String inputPassword;

        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        if (console != null) {
            char[] passwordChars = console.readPassword("Enter password: ");
            inputPassword = new String(passwordChars);
        } else {
            System.out.print("Enter password (input will be visible): ");
            inputPassword = scanner.nextLine();
        }

        if (!inputUsername.equals(USERNAME) || !inputPassword.equals(PASSWORD)) {
            System.out.println("Invalid username or password!");
            return;
        }

        System.out.println("Login successful!");
        Planner planner = new Planner();

        System.out.println("Welcome to Daily Tasks Planner!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. View Incomplete Tasks");
            System.out.println("4. Update Task");
            System.out.println("5. Delete Task");
            System.out.println("6. Mark Task as Completed");
            System.out.println("7. View Calendar");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    String[] taskDetails = promptTaskDetails(scanner);
                    System.out.print("Enter due date (YYYY-MM-DD): ");
                    LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                    planner.addTask(taskDetails[0], taskDetails[1], taskDetails[2], dueDate);
                }
                case 2 -> planner.viewTasks(true);
                case 3 -> planner.viewTasks(false);
                case 4 -> {
                    System.out.print("Enter task number to update: ");
                    int updateNumber = scanner.nextInt();
                    scanner.nextLine();
                    String[] taskDetails = promptTaskDetails(scanner);
                    System.out.print("Enter new due date (YYYY-MM-DD): ");
                    LocalDate newDueDate = LocalDate.parse(scanner.nextLine());
                    planner.updateTask(updateNumber, taskDetails[0], taskDetails[1], taskDetails[2], newDueDate);
                }
                case 5 -> {
                    System.out.print("Enter task number to delete: ");
                    int deleteNumber = scanner.nextInt();
                    scanner.nextLine();
                    planner.deleteTask(deleteNumber);
                }
                case 6 -> {
                    System.out.print("Enter task number to mark as completed: ");
                    int completeNumber = scanner.nextInt();
                    scanner.nextLine();
                    planner.markTaskAsCompleted(completeNumber);
                }
                case 7 -> viewCalendar(scanner);
                case 8 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static String[] promptTaskDetails(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        String priority;
        while (true) {
            System.out.print("Enter task priority (High/Medium/Low): ");
            priority = scanner.nextLine();
            if (priority.equalsIgnoreCase("High") || priority.equalsIgnoreCase("Medium") || priority.equalsIgnoreCase("Low")) {
                break;
            }
            System.out.println("Invalid priority! Please enter High, Medium, or Low.");
        }
        System.out.print("Enter task due time: ");
        String dueTime = scanner.nextLine();
        return new String[]{description, priority, dueTime};
    }

    public static void viewCalendar(Scanner scanner) {
        System.out.print("Enter year (e.g., 2024): ");
        int year = scanner.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        System.out.println("\nCalendar for " + yearMonth.getMonth() + " " + year);
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");

        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();
        int currentDay = 1;

        int leadingSpaces = (dayOfWeekValue % 7);

        for (int i = 0; i < leadingSpaces; i++) {
            System.out.print("    ");
        }

        for (int i = leadingSpaces; i < 7; i++) {
            System.out.printf("%3d ", currentDay++);
        }
        System.out.println();

        while (currentDay <= daysInMonth) {
            for (int i = 0; i < 7 && currentDay <= daysInMonth; i++) {
                System.out.printf("%3d ", currentDay++);
            }
            System.out.println();
        }

        System.out.print("Enter day (1-" + daysInMonth + "): ");
        int day = scanner.nextInt();
        if (day < 1 || day > daysInMonth) {
            System.out.println("Invalid day! Defaulting to the first day of the month.");
            day = 1;
        }
    }
}

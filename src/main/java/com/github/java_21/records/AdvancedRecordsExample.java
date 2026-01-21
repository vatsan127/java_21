package com.github.java_21.records;

import java.util.List;
import java.util.Objects;

/**
 * ADVANCED RECORDS FEATURES
 * 
 * This file demonstrates:
 * 1. Records implementing interfaces
 * 2. Generic records
 * 3. Nested records
 * 4. Pattern matching with records (Java 21)
 * 5. Records with builders
 * 6. Real-world examples
 */
public class AdvancedRecordsExample {

    // Example 1: Record implementing interface
    interface Describable {
        String getDescription();
    }

    interface Priceable {
        double getPrice();
    }

    record Product(String name, double price, String category)
            implements Describable, Priceable {

        public Product {
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
        }

        @Override
        public String getDescription() {
            return name + " (" + category + ")";
        }

        @Override
        public double getPrice() {
            return price;
        }

        public Product applyDiscount(double percentage) {
            double newPrice = price * (1 - percentage / 100);
            return new Product(name, newPrice, category);
        }
    }

    // Example 2: Generic records
    record Pair<T, U>(T first, U second) {
        public Pair<U, T> swap() {
            return new Pair<>(second, first);
        }
    }

    record Result<T>(T value, boolean success, String message) {
        public static <T> Result<T> success(T value) {
            return new Result<>(value, true, "Success");
        }

        public static <T> Result<T> failure(String message) {
            return new Result<>(null, false, message);
        }

        public boolean isSuccess() {
            return success;
        }

        public boolean isFailure() {
            return !success;
        }
    }

    record Box<T>(T content) {
        public <U> Box<U> map(java.util.function.Function<T, U> mapper) {
            return new Box<>(mapper.apply(content));
        }
    }

    // Example 3: Nested records
    record Order(String orderId, Customer customer, List<Item> items, Status status) {

        record Customer(String name, String email, Address address) {
        }

        record Address(String street, String city, String zipCode) {
            @Override
            public String toString() {
                return street + ", " + city + " " + zipCode;
            }
        }

        record Item(String productName, int quantity, double unitPrice) {
            public double total() {
                return quantity * unitPrice;
            }
        }

        enum Status {
            PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
        }

        public double totalAmount() {
            return items.stream()
                    .mapToDouble(Item::total)
                    .sum();
        }

        public int totalItems() {
            return items.stream()
                    .mapToInt(Item::quantity)
                    .sum();
        }
    }

    // Example 4: Record with builder pattern
    record Employee(String firstName, String lastName, String email,
            String department, double salary, int yearsOfService) {

        public Employee {
            Objects.requireNonNull(firstName, "First name required");
            Objects.requireNonNull(lastName, "Last name required");
            if (salary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String firstName;
            private String lastName;
            private String email;
            private String department;
            private double salary;
            private int yearsOfService;

            public Builder firstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public Builder lastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public Builder department(String department) {
                this.department = department;
                return this;
            }

            public Builder salary(double salary) {
                this.salary = salary;
                return this;
            }

            public Builder yearsOfService(int yearsOfService) {
                this.yearsOfService = yearsOfService;
                return this;
            }

            public Employee build() {
                return new Employee(firstName, lastName, email,
                        department, salary, yearsOfService);
            }
        }

        public String fullName() {
            return firstName + " " + lastName;
        }
    }

    // Example 5: Comparable record
    record Score(String playerName, int points) implements Comparable<Score> {
        @Override
        public int compareTo(Score other) {
            // Sort by points descending, then by name ascending
            int pointsCompare = Integer.compare(other.points, this.points);
            if (pointsCompare != 0) {
                return pointsCompare;
            }
            return this.playerName.compareTo(other.playerName);
        }
    }

    // Example 6: Record for API response
    record ApiResponse<T>(T data, int statusCode, String message,
            List<String> errors) {

        public ApiResponse {
            errors = errors == null ? List.of() : List.copyOf(errors);
        }

        public static <T> ApiResponse<T> ok(T data) {
            return new ApiResponse<>(data, 200, "OK", List.of());
        }

        public static <T> ApiResponse<T> created(T data) {
            return new ApiResponse<>(data, 201, "Created", List.of());
        }

        public static <T> ApiResponse<T> error(int statusCode, String message,
                List<String> errors) {
            return new ApiResponse<>(null, statusCode, message, errors);
        }

        public boolean isSuccess() {
            return statusCode >= 200 && statusCode < 300;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }

    // Example 7: Sealed interface with records (Java 17+)
    sealed interface Shape permits Circle, Rectangle, Triangle {
        double area();
    }

    record Circle(double radius) implements Shape {
        public Circle {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    record Rectangle(double width, double height) implements Shape {
        public Rectangle {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Dimensions must be positive");
            }
        }

        @Override
        public double area() {
            return width * height;
        }
    }

    record Triangle(double base, double height) implements Shape {
        public Triangle {
            if (base <= 0 || height <= 0) {
                throw new IllegalArgumentException("Dimensions must be positive");
            }
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ADVANCED RECORDS FEATURES ===\n");

        demonstrateInterfaces();
        demonstrateGenerics();
        demonstrateNestedRecords();
        demonstrateBuilder();
        demonstrateComparable();
        demonstrateApiResponse();
        demonstrateSealedInterface();
        demonstratePatternMatching();
    }

    private static void demonstrateInterfaces() {
        System.out.println("1. RECORDS IMPLEMENTING INTERFACES:");
        System.out.println("------------------------------------");

        Product laptop = new Product("Laptop", 999.99, "Electronics");
        System.out.println("Product: " + laptop);
        System.out.println("  Description: " + laptop.getDescription());
        System.out.println("  Price: $" + laptop.getPrice());

        Product discounted = laptop.applyDiscount(10);
        System.out.println("\nAfter 10% discount: " + discounted);
        System.out.println("  New price: $" + String.format("%.2f", discounted.getPrice()));

        System.out.println();
    }

    private static void demonstrateGenerics() {
        System.out.println("2. GENERIC RECORDS:");
        System.out.println("-------------------");

        Pair<String, Integer> pair1 = new Pair<>("Age", 25);
        System.out.println("Pair: " + pair1);
        System.out.println("  First: " + pair1.first());
        System.out.println("  Second: " + pair1.second());

        Pair<Integer, String> swapped = pair1.swap();
        System.out.println("Swapped: " + swapped);

        Result<Integer> success = Result.success(42);
        System.out.println("\nSuccess result: " + success);
        System.out.println("  Is success: " + success.isSuccess());
        System.out.println("  Value: " + success.value());

        Result<String> failure = Result.failure("Something went wrong");
        System.out.println("\nFailure result: " + failure);
        System.out.println("  Is failure: " + failure.isFailure());
        System.out.println("  Message: " + failure.message());

        Box<Integer> box = new Box<>(10);
        Box<String> stringBox = box.map(n -> "Number: " + n);
        System.out.println("\nBox mapping: " + box + " -> " + stringBox);

        System.out.println();
    }

    private static void demonstrateNestedRecords() {
        System.out.println("3. NESTED RECORDS:");
        System.out.println("------------------");

        var address = new Order.Address("123 Main St", "Springfield", "12345");
        var customer = new Order.Customer("John Doe", "john@example.com", address);

        var items = List.of(
                new Order.Item("Laptop", 1, 999.99),
                new Order.Item("Mouse", 2, 29.99),
                new Order.Item("Keyboard", 1, 79.99));

        var order = new Order("ORD-001", customer, items, Order.Status.PROCESSING);

        System.out.println("Order: " + order.orderId());
        System.out.println("  Customer: " + order.customer().name());
        System.out.println("  Email: " + order.customer().email());
        System.out.println("  Address: " + order.customer().address());
        System.out.println("  Status: " + order.status());
        System.out.println("\nItems:");
        for (var item : order.items()) {
            System.out.printf("  - %s x%d @ $%.2f = $%.2f%n",
                    item.productName(), item.quantity(),
                    item.unitPrice(), item.total());
        }
        System.out.println("\nTotal items: " + order.totalItems());
        System.out.println("Total amount: $" + String.format("%.2f", order.totalAmount()));

        System.out.println();
    }

    private static void demonstrateBuilder() {
        System.out.println("4. BUILDER PATTERN:");
        System.out.println("-------------------");

        Employee employee = Employee.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@company.com")
                .department("Engineering")
                .salary(95000)
                .yearsOfService(5)
                .build();

        System.out.println("Employee: " + employee.fullName());
        System.out.println("  Email: " + employee.email());
        System.out.println("  Department: " + employee.department());
        System.out.println("  Salary: $" + String.format("%.2f", employee.salary()));
        System.out.println("  Years of service: " + employee.yearsOfService());

        System.out.println();
    }

    private static void demonstrateComparable() {
        System.out.println("5. COMPARABLE RECORDS:");
        System.out.println("----------------------");

        List<Score> scores = new java.util.ArrayList<>(List.of(
                new Score("Alice", 150),
                new Score("Bob", 200),
                new Score("Charlie", 150),
                new Score("David", 175)));

        System.out.println("Original scores:");
        scores.forEach(s -> System.out.println("  " + s));

        java.util.Collections.sort(scores);

        System.out.println("\nSorted scores (by points desc, then name):");
        scores.forEach(s -> System.out.println("  " + s));

        System.out.println();
    }

    private static void demonstrateApiResponse() {
        System.out.println("6. API RESPONSE RECORDS:");
        System.out.println("------------------------");

        ApiResponse<String> success = ApiResponse.ok("Data retrieved successfully");
        System.out.println("Success response: " + success);
        System.out.println("  Is success: " + success.isSuccess());
        System.out.println("  Data: " + success.data());

        ApiResponse<Object> error = ApiResponse.error(
                400,
                "Bad Request",
                List.of("Invalid email", "Password too short"));
        System.out.println("\nError response:");
        System.out.println("  Status: " + error.statusCode());
        System.out.println("  Message: " + error.message());
        System.out.println("  Errors: " + error.errors());
        System.out.println("  Has errors: " + error.hasErrors());

        System.out.println();
    }

    private static void demonstrateSealedInterface() {
        System.out.println("7. SEALED INTERFACE WITH RECORDS:");
        System.out.println("----------------------------------");

        List<Shape> shapes = List.of(
                new Circle(5),
                new Rectangle(10, 5),
                new Triangle(8, 6));

        System.out.println("Calculating areas:");
        for (Shape shape : shapes) {
            System.out.printf("  %s -> Area: %.2f%n", shape, shape.area());
        }

        System.out.println();
    }

    private static void demonstratePatternMatching() {
        System.out.println("8. PATTERN MATCHING (Java 21):");
        System.out.println("-------------------------------");

        Object obj1 = new Pair<>("Name", "Alice");
        Object obj2 = new Circle(10);
        Object obj3 = "Just a string";

        System.out.println("Pattern matching examples:");

        // Pattern matching with instanceof
        if (obj1 instanceof Pair<?, ?>(var first, var second)) {
            System.out.println("  Pair found: first=" + first + ", second=" + second);
        }

        // Pattern matching in switch (Java 21)
        String description = describeObject(obj2);
        System.out.println("  " + description);

        description = describeObject(obj3);
        System.out.println("  " + description);

        System.out.println();
    }

    // Helper method for pattern matching
    private static String describeObject(Object obj) {
        return switch (obj) {
            case Circle(double r) -> "Circle with radius " + r;
            case Rectangle(double w, double h) ->
                "Rectangle " + w + "x" + h;
            case Triangle(double b, double h) ->
                "Triangle with base " + b + " and height " + h;
            case String s -> "String: " + s;
            case null -> "null value";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }
}

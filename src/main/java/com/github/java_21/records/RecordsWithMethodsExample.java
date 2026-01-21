package com.github.java_21.records;

import java.util.List;

/**
 * RECORDS WITH CONSTRUCTORS AND METHODS
 * 
 * This file demonstrates:
 * 1. Compact constructors
 * 2. Canonical constructors
 * 3. Additional constructors
 * 4. Instance methods
 * 5. Static methods and fields
 * 6. Overriding accessor methods
 */
public class RecordsWithMethodsExample {

    // Example 1: Compact constructor for validation
    record Person(String name, int age) {
        // Compact constructor - no parameter list
        public Person {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be blank");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150");
            }
            // Normalize the name
            name = name.trim();
        }
    }

    // Example 2: Record with instance methods
    record Rectangle(double width, double height) {
        // Compact constructor for validation
        public Rectangle {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Dimensions must be positive");
            }
        }

        // Instance methods
        public double area() {
            return width * height;
        }

        public double perimeter() {
            return 2 * (width + height);
        }

        public boolean isSquare() {
            return width == height;
        }

        public Rectangle scale(double factor) {
            return new Rectangle(width * factor, height * factor);
        }
    }

    // Example 3: Record with static methods and fields
    record Circle(double radius) {
        private static final double PI = 3.14159265359;

        public Circle {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive");
            }
        }

        // Static factory method
        public static Circle ofDiameter(double diameter) {
            return new Circle(diameter / 2);
        }

        public static Circle unit() {
            return new Circle(1.0);
        }

        // Instance methods
        public double area() {
            return PI * radius * radius;
        }

        public double circumference() {
            return 2 * PI * radius;
        }

        public double diameter() {
            return 2 * radius;
        }
    }

    // Example 4: Additional constructors
    record Book(String title, String author, int year, String isbn) {
        // Compact constructor for validation
        public Book {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title required");
            }
            if (author == null || author.isBlank()) {
                throw new IllegalArgumentException("Author required");
            }
        }

        // Additional constructor - must delegate to canonical
        public Book(String title, String author) {
            this(title, author, 0, "");
        }

        // Another additional constructor
        public Book(String title, String author, int year) {
            this(title, author, year, "");
        }
    }

    // Example 5: Overriding accessor methods
    record Email(String address) {
        public Email {
            if (address == null || !address.contains("@")) {
                throw new IllegalArgumentException("Invalid email address");
            }
            address = address.toLowerCase().trim();
        }

        // Override accessor to always return lowercase
        @Override
        public String address() {
            return address.toLowerCase();
        }

        // Additional methods
        public String domain() {
            return address.substring(address.indexOf('@') + 1);
        }

        public String username() {
            return address.substring(0, address.indexOf('@'));
        }
    }

    // Example 6: Overriding toString()
    record Money(double amount, String currency) {
        public Money {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }
            if (currency == null || currency.isBlank()) {
                throw new IllegalArgumentException("Currency required");
            }
            currency = currency.toUpperCase();
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", amount, currency);
        }

        public Money add(Money other) {
            if (!this.currency.equals(other.currency)) {
                throw new IllegalArgumentException("Cannot add different currencies");
            }
            return new Money(this.amount + other.amount, this.currency);
        }
    }

    // Example 7: Defensive copying for mutable components
    record Team(String name, List<String> members) {
        public Team {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Team name required");
            }
            // Defensive copy - make immutable
            members = List.copyOf(members);
        }

        public int size() {
            return members.size();
        }

        public boolean hasMember(String member) {
            return members.contains(member);
        }
    }

    // Example 8: Complex validation and normalization
    record Temperature(double value, String unit) {
        public Temperature {
            if (unit == null) {
                throw new IllegalArgumentException("Unit required");
            }
            unit = unit.toUpperCase();
            if (!unit.equals("C") && !unit.equals("F") && !unit.equals("K")) {
                throw new IllegalArgumentException("Unit must be C, F, or K");
            }
            // Validate based on unit
            if (unit.equals("K") && value < 0) {
                throw new IllegalArgumentException("Kelvin cannot be negative");
            }
        }

        public Temperature toCelsius() {
            return switch (unit) {
                case "C" -> this;
                case "F" -> new Temperature((value - 32) * 5 / 9, "C");
                case "K" -> new Temperature(value - 273.15, "C");
                default -> throw new IllegalStateException();
            };
        }

        public Temperature toFahrenheit() {
            Temperature celsius = toCelsius();
            if (celsius.unit.equals("F"))
                return celsius;
            return new Temperature(celsius.value * 9 / 5 + 32, "F");
        }

        public Temperature toKelvin() {
            Temperature celsius = toCelsius();
            if (celsius.unit.equals("K"))
                return celsius;
            return new Temperature(celsius.value + 273.15, "K");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== RECORDS WITH CONSTRUCTORS AND METHODS ===\n");

        demonstrateCompactConstructor();
        demonstrateInstanceMethods();
        demonstrateStaticMethods();
        demonstrateAdditionalConstructors();
        demonstrateOverriddenAccessors();
        demonstrateDefensiveCopying();
        demonstrateComplexRecord();
    }

    private static void demonstrateCompactConstructor() {
        System.out.println("1. COMPACT CONSTRUCTOR:");
        System.out.println("-----------------------");

        try {
            Person valid = new Person("  Alice  ", 30);
            System.out.println("Valid person: " + valid);
            System.out.println("  Name (trimmed): '" + valid.name() + "'");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nTrying invalid data:");
        try {
            Person invalid = new Person("", 30);
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Blank name: " + e.getMessage());
        }

        try {
            Person invalid = new Person("Bob", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Negative age: " + e.getMessage());
        }

        System.out.println();
    }

    private static void demonstrateInstanceMethods() {
        System.out.println("2. INSTANCE METHODS:");
        System.out.println("--------------------");

        Rectangle rect = new Rectangle(10, 5);
        System.out.println("Rectangle: " + rect);
        System.out.println("  Area: " + rect.area());
        System.out.println("  Perimeter: " + rect.perimeter());
        System.out.println("  Is square: " + rect.isSquare());

        Rectangle square = new Rectangle(5, 5);
        System.out.println("\nSquare: " + square);
        System.out.println("  Is square: " + square.isSquare());

        Rectangle scaled = rect.scale(2);
        System.out.println("\nScaled rectangle (2x): " + scaled);
        System.out.println("  New area: " + scaled.area());

        System.out.println();
    }

    private static void demonstrateStaticMethods() {
        System.out.println("3. STATIC METHODS:");
        System.out.println("------------------");

        Circle c1 = new Circle(5);
        System.out.println("Circle with radius 5: " + c1);
        System.out.println("  Area: " + String.format("%.2f", c1.area()));
        System.out.println("  Circumference: " + String.format("%.2f", c1.circumference()));

        Circle c2 = Circle.ofDiameter(10);
        System.out.println("\nCircle from diameter 10: " + c2);
        System.out.println("  Radius: " + c2.radius());

        Circle unit = Circle.unit();
        System.out.println("\nUnit circle: " + unit);
        System.out.println("  Area: " + String.format("%.2f", unit.area()));

        System.out.println();
    }

    private static void demonstrateAdditionalConstructors() {
        System.out.println("4. ADDITIONAL CONSTRUCTORS:");
        System.out.println("---------------------------");

        Book book1 = new Book("1984", "George Orwell", 1949, "978-0451524935");
        System.out.println("Full constructor: " + book1);

        Book book2 = new Book("Animal Farm", "George Orwell");
        System.out.println("Title + Author: " + book2);

        Book book3 = new Book("Brave New World", "Aldous Huxley", 1932);
        System.out.println("Title + Author + Year: " + book3);

        System.out.println();
    }

    private static void demonstrateOverriddenAccessors() {
        System.out.println("5. OVERRIDDEN ACCESSOR METHODS:");
        System.out.println("--------------------------------");

        Email email = new Email("  Alice@EXAMPLE.COM  ");
        System.out.println("Email: " + email);
        System.out.println("  Address (normalized): " + email.address());
        System.out.println("  Username: " + email.username());
        System.out.println("  Domain: " + email.domain());

        Money price = new Money(99.99, "usd");
        System.out.println("\nMoney: " + price);
        System.out.println("  Currency (normalized): " + price.currency());

        Money total = price.add(new Money(50.00, "USD"));
        System.out.println("  After adding 50.00 USD: " + total);

        System.out.println();
    }

    private static void demonstrateDefensiveCopying() {
        System.out.println("6. DEFENSIVE COPYING:");
        System.out.println("---------------------");

        List<String> memberList = new java.util.ArrayList<>();
        memberList.add("Alice");
        memberList.add("Bob");
        memberList.add("Charlie");

        Team team = new Team("Developers", memberList);
        System.out.println("Team: " + team);
        System.out.println("  Size: " + team.size());

        // Try to modify original list
        memberList.add("David");
        System.out.println("\nAfter modifying original list:");
        System.out.println("  Original list size: " + memberList.size());
        System.out.println("  Team size: " + team.size() + " (unchanged!)");
        System.out.println("  ✅ Defensive copy protects immutability");

        System.out.println();
    }

    private static void demonstrateComplexRecord() {
        System.out.println("7. COMPLEX RECORD (Temperature):");
        System.out.println("---------------------------------");

        Temperature celsius = new Temperature(25, "C");
        System.out.println("Temperature: " + celsius);

        Temperature fahrenheit = celsius.toFahrenheit();
        System.out.println("  In Fahrenheit: " + fahrenheit);

        Temperature kelvin = celsius.toKelvin();
        System.out.println("  In Kelvin: " + kelvin);

        Temperature freezing = new Temperature(32, "F");
        System.out.println("\nFreezing point (F): " + freezing);
        System.out.println("  In Celsius: " + freezing.toCelsius());
        System.out.println("  In Kelvin: " + freezing.toKelvin());

        System.out.println();
    }
}

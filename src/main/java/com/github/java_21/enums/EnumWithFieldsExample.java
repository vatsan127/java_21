package com.github.java_21.enums;

/**
 * ENUM WITH FIELDS AND METHODS
 * 
 * This file demonstrates:
 * 1. Enums with constructors
 * 2. Enums with fields (instance variables)
 * 3. Enums with methods
 * 4. Enums with multiple fields
 * 5. Real-world examples
 */
public class EnumWithFieldsExample {

    // Example 1: Enum with single field
    enum Size {
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L"),
        EXTRA_LARGE("XL"),
        XXL("XXL");

        private final String abbreviation;

        // Constructor MUST be private or package-private
        Size(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        @Override
        public String toString() {
            return name() + " (" + abbreviation + ")";
        }
    }

    // Example 2: Enum with multiple fields
    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS(4.869e+24, 6.0518e6),
        EARTH(5.976e+24, 6.37814e6),
        MARS(6.421e+23, 3.3972e6),
        JUPITER(1.900e+27, 7.1492e7),
        SATURN(5.688e+26, 6.0268e7),
        URANUS(8.686e+25, 2.5559e7),
        NEPTUNE(1.024e+26, 2.4746e7);

        private final double mass; // in kilograms
        private final double radius; // in meters

        // Universal gravitational constant (m^3 kg^-1 s^-2)
        private static final double G = 6.67300E-11;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double getMass() {
            return mass;
        }

        public double getRadius() {
            return radius;
        }

        // Instance method using fields
        public double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        // Instance method that takes parameter
        public double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }

    // Example 3: Real-world example - Pizza sizes with pricing
    enum PizzaSize {
        SMALL(8, 9.99),
        MEDIUM(12, 14.99),
        LARGE(16, 19.99),
        EXTRA_LARGE(20, 24.99);

        private final int diameter; // in inches
        private final double basePrice;

        PizzaSize(int diameter, double basePrice) {
            this.diameter = diameter;
            this.basePrice = basePrice;
        }

        public int getDiameter() {
            return diameter;
        }

        public double getBasePrice() {
            return basePrice;
        }

        public double getArea() {
            double radius = diameter / 2.0;
            return Math.PI * radius * radius;
        }

        public double getPricePerSquareInch() {
            return basePrice / getArea();
        }

        @Override
        public String toString() {
            return String.format("%s (%d\", $%.2f)", name(), diameter, basePrice);
        }
    }

    // Example 4: HTTP Status codes
    enum HttpStatus {
        OK(200, "OK"),
        CREATED(201, "Created"),
        BAD_REQUEST(400, "Bad Request"),
        UNAUTHORIZED(401, "Unauthorized"),
        FORBIDDEN(403, "Forbidden"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
        SERVICE_UNAVAILABLE(503, "Service Unavailable");

        private final int code;
        private final String message;

        HttpStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return code >= 200 && code < 300;
        }

        public boolean isClientError() {
            return code >= 400 && code < 500;
        }

        public boolean isServerError() {
            return code >= 500 && code < 600;
        }

        // Static method to find by code
        public static HttpStatus fromCode(int code) {
            for (HttpStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status code: " + code);
        }

        @Override
        public String toString() {
            return code + " " + message;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ENUM WITH FIELDS AND METHODS ===\n");

        demonstrateSizes();
        demonstratePlanets();
        demonstratePizzaSizes();
        demonstrateHttpStatus();
    }

    private static void demonstrateSizes() {
        System.out.println("1. SIZE ENUM:");
        System.out.println("-------------");

        for (Size size : Size.values()) {
            System.out.println("  " + size);
        }

        System.out.println("\nGetting abbreviation:");
        Size mySize = Size.LARGE;
        System.out.println("  My size: " + mySize.name());
        System.out.println("  Abbreviation: " + mySize.getAbbreviation());

        System.out.println();
    }

    private static void demonstratePlanets() {
        System.out.println("2. PLANET ENUM:");
        System.out.println("---------------");

        double earthWeight = 75.0; // kg
        System.out.println("If you weigh " + earthWeight + " kg on Earth:\n");

        for (Planet planet : Planet.values()) {
            double weight = planet.surfaceWeight(earthWeight);
            System.out.printf("  %-10s: %.2f kg (gravity: %.2f m/s²)%n",
                    planet.name(), weight, planet.surfaceGravity());
        }

        System.out.println();
    }

    private static void demonstratePizzaSizes() {
        System.out.println("3. PIZZA SIZE ENUM:");
        System.out.println("-------------------");

        System.out.println("Available pizza sizes:");
        for (PizzaSize size : PizzaSize.values()) {
            System.out.println("  " + size);
        }

        System.out.println("\nPrice per square inch analysis:");
        for (PizzaSize size : PizzaSize.values()) {
            System.out.printf("  %-12s: $%.4f per sq.in (Area: %.2f sq.in)%n",
                    size.name(),
                    size.getPricePerSquareInch(),
                    size.getArea());
        }

        // Find best value
        PizzaSize bestValue = PizzaSize.SMALL;
        for (PizzaSize size : PizzaSize.values()) {
            if (size.getPricePerSquareInch() < bestValue.getPricePerSquareInch()) {
                bestValue = size;
            }
        }
        System.out.println("\n  🏆 Best value: " + bestValue.name());

        System.out.println();
    }

    private static void demonstrateHttpStatus() {
        System.out.println("4. HTTP STATUS ENUM:");
        System.out.println("--------------------");

        HttpStatus[] testStatuses = {
                HttpStatus.OK,
                HttpStatus.NOT_FOUND,
                HttpStatus.INTERNAL_SERVER_ERROR
        };

        for (HttpStatus status : testStatuses) {
            System.out.println("  Status: " + status);
            System.out.println("    Code: " + status.getCode());
            System.out.println("    Success: " + status.isSuccess());
            System.out.println("    Client Error: " + status.isClientError());
            System.out.println("    Server Error: " + status.isServerError());
            System.out.println();
        }

        // Using static method to find by code
        System.out.println("Finding status by code:");
        HttpStatus found = HttpStatus.fromCode(404);
        System.out.println("  Code 404: " + found);

        System.out.println();
    }
}

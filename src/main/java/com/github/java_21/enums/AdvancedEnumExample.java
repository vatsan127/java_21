package com.github.java_21.enums;

import java.util.EnumSet;
import java.util.EnumMap;

/**
 * ADVANCED ENUM FEATURES
 * 
 * This file demonstrates:
 * 1. Enums with abstract methods
 * 2. Enums implementing interfaces
 * 3. EnumSet and EnumMap
 * 4. Enum with static methods
 * 5. Complex real-world examples
 */
public class AdvancedEnumExample {

    // Example 1: Enum with abstract methods
    enum Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        MULTIPLY("*") {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {
                if (y == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return x / y;
            }
        },
        POWER("^") {
            @Override
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        };

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        // Abstract method - each constant must implement
        public abstract double apply(double x, double y);

        @Override
        public String toString() {
            return symbol;
        }
    }

    // Example 2: Interface for enums
    interface Describable {
        String getDescription();

        String getCategory();
    }

    // Enum implementing interface
    enum PaymentMethod implements Describable {
        CREDIT_CARD("Credit Card", "Card", 2.9),
        DEBIT_CARD("Debit Card", "Card", 1.5),
        PAYPAL("PayPal", "Digital", 3.5),
        BANK_TRANSFER("Bank Transfer", "Direct", 0.5),
        CASH("Cash", "Physical", 0.0),
        CRYPTOCURRENCY("Cryptocurrency", "Digital", 1.0);

        private final String description;
        private final String category;
        private final double feePercentage;

        PaymentMethod(String description, String category, double feePercentage) {
            this.description = description;
            this.category = category;
            this.feePercentage = feePercentage;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getCategory() {
            return category;
        }

        public double getFeePercentage() {
            return feePercentage;
        }

        public double calculateFee(double amount) {
            return amount * (feePercentage / 100);
        }

        public double getTotalAmount(double amount) {
            return amount + calculateFee(amount);
        }
    }

    // Example 3: Enum with static utility methods
    enum Direction {
        NORTH(0, "N"),
        EAST(90, "E"),
        SOUTH(180, "S"),
        WEST(270, "W");

        private final int degrees;
        private final String abbreviation;

        Direction(int degrees, String abbreviation) {
            this.degrees = degrees;
            this.abbreviation = abbreviation;
        }

        public int getDegrees() {
            return degrees;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        // Static method to get opposite direction
        public static Direction getOpposite(Direction dir) {
            return switch (dir) {
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case EAST -> WEST;
                case WEST -> EAST;
            };
        }

        // Static method to rotate
        public static Direction rotate(Direction dir, int quarterTurns) {
            Direction[] directions = values();
            int currentIndex = dir.ordinal();
            int newIndex = (currentIndex + quarterTurns) % directions.length;
            if (newIndex < 0) {
                newIndex += directions.length;
            }
            return directions[newIndex];
        }

        // Instance method to turn right
        public Direction turnRight() {
            return rotate(this, 1);
        }

        // Instance method to turn left
        public Direction turnLeft() {
            return rotate(this, -1);
        }
    }

    // Example 4: Complex enum for user permissions
    enum Permission {
        READ("Read", 1),
        WRITE("Write", 2),
        DELETE("Delete", 4),
        EXECUTE("Execute", 8),
        ADMIN("Admin", 16);

        private final String description;
        private final int bitMask;

        Permission(String description, int bitMask) {
            this.description = description;
            this.bitMask = bitMask;
        }

        public String getDescription() {
            return description;
        }

        public int getBitMask() {
            return bitMask;
        }

        // Check if permission set contains this permission
        public static boolean hasPermission(int permissionSet, Permission permission) {
            return (permissionSet & permission.bitMask) != 0;
        }

        // Add permission to set
        public static int addPermission(int permissionSet, Permission permission) {
            return permissionSet | permission.bitMask;
        }

        // Remove permission from set
        public static int removePermission(int permissionSet, Permission permission) {
            return permissionSet & ~permission.bitMask;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ADVANCED ENUM FEATURES ===\n");

        demonstrateAbstractMethods();
        demonstrateInterface();
        demonstrateEnumCollections();
        demonstrateStaticMethods();
        demonstratePermissions();
    }

    private static void demonstrateAbstractMethods() {
        System.out.println("1. ENUM WITH ABSTRACT METHODS:");
        System.out.println("-------------------------------");

        double x = 10.0;
        double y = 5.0;

        System.out.println("Calculating with x=" + x + ", y=" + y + ":\n");

        for (Operation op : Operation.values()) {
            try {
                double result = op.apply(x, y);
                System.out.printf("  %s %s %s = %.2f%n", x, op.getSymbol(), y, result);
            } catch (ArithmeticException e) {
                System.out.printf("  %s %s %s = ERROR: %s%n", x, op.getSymbol(), y, e.getMessage());
            }
        }

        // Using in expressions
        System.out.println("\nComplex calculation:");
        double result = Operation.PLUS.apply(
                Operation.MULTIPLY.apply(2, 3),
                Operation.DIVIDE.apply(10, 2));
        System.out.println("  (2 * 3) + (10 / 2) = " + result);

        System.out.println();
    }

    private static void demonstrateInterface() {
        System.out.println("2. ENUM IMPLEMENTING INTERFACE:");
        System.out.println("--------------------------------");

        double purchaseAmount = 100.0;
        System.out.println("Purchase amount: $" + purchaseAmount + "\n");

        for (PaymentMethod method : PaymentMethod.values()) {
            System.out.printf("  %-18s [%s]%n", method.getDescription(), method.getCategory());
            System.out.printf("    Fee: $%.2f (%.1f%%)%n",
                    method.calculateFee(purchaseAmount),
                    method.getFeePercentage());
            System.out.printf("    Total: $%.2f%n%n",
                    method.getTotalAmount(purchaseAmount));
        }

        System.out.println();
    }

    private static void demonstrateEnumCollections() {
        System.out.println("3. ENUMSET AND ENUMMAP:");
        System.out.println("-----------------------");

        // EnumSet - efficient set implementation for enums
        System.out.println("EnumSet examples:");

        EnumSet<Direction> cardinalDirections = EnumSet.of(
                Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        System.out.println("  Cardinal directions: " + cardinalDirections);

        EnumSet<PaymentMethod> digitalPayments = EnumSet.of(
                PaymentMethod.PAYPAL, PaymentMethod.CRYPTOCURRENCY);
        System.out.println("  Digital payments: " + digitalPayments);

        EnumSet<PaymentMethod> allPayments = EnumSet.allOf(PaymentMethod.class);
        System.out.println("  All payment methods: " + allPayments.size() + " methods");

        // EnumMap - efficient map implementation for enum keys
        System.out.println("\nEnumMap examples:");

        EnumMap<Direction, String> directionInstructions = new EnumMap<>(Direction.class);
        directionInstructions.put(Direction.NORTH, "Go straight");
        directionInstructions.put(Direction.SOUTH, "Turn around");
        directionInstructions.put(Direction.EAST, "Turn right");
        directionInstructions.put(Direction.WEST, "Turn left");

        System.out.println("  Navigation instructions:");
        directionInstructions.forEach((dir, instruction) -> System.out.println("    " + dir + ": " + instruction));

        System.out.println();
    }

    private static void demonstrateStaticMethods() {
        System.out.println("4. STATIC METHODS IN ENUMS:");
        System.out.println("---------------------------");

        Direction current = Direction.NORTH;
        System.out.println("Starting direction: " + current);

        System.out.println("\nOpposite direction:");
        System.out.println("  " + Direction.getOpposite(current));

        System.out.println("\nRotations:");
        System.out.println("  Turn right: " + current.turnRight());
        System.out.println("  Turn left: " + current.turnLeft());
        System.out.println("  Rotate 2 quarters: " + Direction.rotate(current, 2));

        System.out.println("\nFull rotation from NORTH:");
        Direction dir = Direction.NORTH;
        for (int i = 0; i < 4; i++) {
            System.out.println("  Step " + i + ": " + dir);
            dir = dir.turnRight();
        }

        System.out.println();
    }

    private static void demonstratePermissions() {
        System.out.println("5. PERMISSION SYSTEM:");
        System.out.println("---------------------");

        // Create permission set
        int userPermissions = 0;

        // Add permissions
        userPermissions = Permission.addPermission(userPermissions, Permission.READ);
        userPermissions = Permission.addPermission(userPermissions, Permission.WRITE);

        System.out.println("User permissions:");
        for (Permission perm : Permission.values()) {
            boolean has = Permission.hasPermission(userPermissions, perm);
            System.out.println("  " + perm.getDescription() + ": " + (has ? "✓" : "✗"));
        }

        System.out.println("\nAdding EXECUTE permission...");
        userPermissions = Permission.addPermission(userPermissions, Permission.EXECUTE);

        System.out.println("Updated permissions:");
        for (Permission perm : Permission.values()) {
            boolean has = Permission.hasPermission(userPermissions, perm);
            System.out.println("  " + perm.getDescription() + ": " + (has ? "✓" : "✗"));
        }

        System.out.println();
    }
}

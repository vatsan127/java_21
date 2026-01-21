package com.github.java_21.enums;

/**
 * BASIC ENUM EXAMPLES
 * 
 * This file demonstrates:
 * 1. Simple enum declaration
 * 2. Using enum constants
 * 3. Built-in enum methods (values(), valueOf(), ordinal(), name())
 * 4. Enum in switch statements
 * 5. Comparing enums
 */
public class BasicEnumExample{

// Example 1: Simple Enum Declaration
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

// Example 2: Enum for Seasons
enum Season {
    SPRING, SUMMER, FALL, WINTER
}

// Example 3: Enum for Traffic Light
enum TrafficLight {
    RED, YELLOW, GREEN

    }

    public static void main(String[] args) {
        System.out.println("=== BASIC ENUM EXAMPLES ===\n");

        // Using enum constants
        demonstrateBasicUsage();

        // Built-in methods
        demonstrateBuiltInMethods();

        // Switch statements
        demonstrateSwitchStatements();

        // Comparing enums
        demonstrateComparison();
    }

    private static void demonstrateBasicUsage() {
        System.out.println("1. BASIC USAGE:");
        System.out.println("----------------");

        // Declaring enum variables
        Day today = Day.MONDAY;
        Day tomorrow = Day.TUESDAY;

        System.out.println("Today is: " + today);
        System.out.println("Tomorrow is: " + tomorrow);

        // Enums are type-safe
        // Day invalidDay = "MONDAY"; // ❌ Compilation error!

        System.out.println();
    }

    private static void demonstrateBuiltInMethods() {
        System.out.println("2. BUILT-IN METHODS:");
        System.out.println("--------------------");

        // values() - returns array of all enum constants
        System.out.println("All days of the week:");
        Day[] allDays = Day.values();
        for (Day day : allDays) {
            System.out.println("  - " + day);
        }

        System.out.println("\nAll seasons:");
        for (Season season : Season.values()) {
            System.out.println("  - " + season);
        }

        // valueOf() - converts string to enum
        System.out.println("\nUsing valueOf():");
        Day friday = Day.valueOf("FRIDAY");
        System.out.println("  Friday: " + friday);

        // Note: valueOf() throws IllegalArgumentException if name doesn't match
        try {
            Day invalid = Day.valueOf("FUNDAY");
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Error: 'FUNDAY' is not a valid day!");
        }

        // ordinal() - returns position (0-based index)
        System.out.println("\nOrdinal values (position in enum):");
        for (Day day : Day.values()) {
            System.out.println("  " + day + " -> ordinal: " + day.ordinal());
        }

        // name() - returns the name of the constant
        System.out.println("\nUsing name():");
        Day monday = Day.MONDAY;
        System.out.println("  Name: " + monday.name());
        System.out.println("  toString(): " + monday.toString()); // Same as name() by default

        System.out.println();
    }

    private static void demonstrateSwitchStatements() {
        System.out.println("3. SWITCH STATEMENTS:");
        System.out.println("---------------------");

        // Traditional switch
        Day today = Day.WEDNESDAY;
        System.out.println("Traditional switch for " + today + ":");
        switch (today) {
            case MONDAY:
                System.out.println("  Start of work week!");
                break;
            case WEDNESDAY:
                System.out.println("  Hump day!");
                break;
            case FRIDAY:
                System.out.println("  TGIF!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("  Weekend!");
                break;
            default:
                System.out.println("  Regular day");
        }

        // Java 21 switch expression
        System.out.println("\nJava 21 switch expression:");
        String dayType = switch (today) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
        };
        System.out.println("  " + today + " is a " + dayType);

        // Traffic light example
        TrafficLight light = TrafficLight.RED;
        String action = switch (light) {
            case RED -> "Stop";
            case YELLOW -> "Slow down";
            case GREEN -> "Go";
        };
        System.out.println("  Traffic light is " + light + " -> " + action);

        System.out.println();
    }

    private static void demonstrateComparison() {
        System.out.println("4. COMPARING ENUMS:");
        System.out.println("-------------------");

        Day day1 = Day.MONDAY;
        Day day2 = Day.MONDAY;
        Day day3 = Day.FRIDAY;

        // Using == (recommended for enums)
        System.out.println("Using == operator:");
        System.out.println("  MONDAY == MONDAY: " + (day1 == day2));
        System.out.println("  MONDAY == FRIDAY: " + (day1 == day3));

        // Using .equals() (also works, but == is preferred)
        System.out.println("\nUsing .equals():");
        System.out.println("  MONDAY.equals(MONDAY): " + day1.equals(day2));
        System.out.println("  MONDAY.equals(FRIDAY): " + day1.equals(day3));

        // Using compareTo() - compares based on ordinal values
        System.out.println("\nUsing compareTo():");
        System.out.println("  MONDAY.compareTo(FRIDAY): " + day1.compareTo(day3));
        System.out.println("  FRIDAY.compareTo(MONDAY): " + day3.compareTo(day1));
        System.out.println("  MONDAY.compareTo(MONDAY): " + day1.compareTo(day2));

        // Ordering based on declaration order
        System.out.println("\nDays in order:");
        Day[] days = { Day.FRIDAY, Day.MONDAY, Day.WEDNESDAY };
        java.util.Arrays.sort(days); // Sorts by ordinal (declaration order)
        for (Day day : days) {
            System.out.println("  " + day);
        }

        System.out.println();
    }
}

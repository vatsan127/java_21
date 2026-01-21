package com.github.java_21.records;

/**
 * BASIC RECORDS EXAMPLES
 * 
 * This file demonstrates:
 * 1. Simple record declaration
 * 2. Creating and using records
 * 3. Automatic methods (equals, hashCode, toString)
 * 4. Accessor methods
 * 5. Records vs traditional classes
 */
public class BasicRecordsExample {

    // Example 1: Simple record
    record Point(int x, int y) {
    }

    // Example 2: Record with more components
    record Person(String name, int age, String email) {
    }

    // Example 3: Traditional class for comparison
    static final class PersonClass {
        private final String name;
        private final int age;
        private final String email;

        public PersonClass(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof PersonClass that))
                return false;
            return age == that.age &&
                    name.equals(that.name) &&
                    email.equals(that.email);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + age;
            result = 31 * result + email.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "PersonClass{name='" + name + "', age=" + age +
                    ", email='" + email + "'}";
        }
    }

    // Example 4: Record for product
    record Product(String name, double price, String category) {
    }

    // Example 5: Record for coordinates
    record Coordinate(double latitude, double longitude) {
    }

    public static void main(String[] args) {
        System.out.println("=== BASIC RECORDS EXAMPLES ===\n");

        demonstrateSimpleRecord();
        demonstrateAccessorMethods();
        demonstrateAutomaticMethods();
        demonstrateRecordVsClass();
        demonstrateImmutability();
    }

    private static void demonstrateSimpleRecord() {
        System.out.println("1. SIMPLE RECORD DECLARATION:");
        System.out.println("------------------------------");

        // Creating a record instance
        Point p1 = new Point(10, 20);

        System.out.println("Created point: " + p1);
        System.out.println("Point class: " + p1.getClass().getSimpleName());
        System.out.println("Is record: " + p1.getClass().isRecord());

        // Creating a Person record
        Person person = new Person("Alice", 30, "alice@example.com");
        System.out.println("\nCreated person: " + person);

        // Creating a Product record
        Product product = new Product("Laptop", 999.99, "Electronics");
        System.out.println("Created product: " + product);

        System.out.println();
    }

    private static void demonstrateAccessorMethods() {
        System.out.println("2. ACCESSOR METHODS:");
        System.out.println("--------------------");

        Point point = new Point(5, 10);

        // Records use accessor methods, NOT getters
        System.out.println("Accessing components:");
        System.out.println("  x coordinate: " + point.x()); // NOT point.getX()
        System.out.println("  y coordinate: " + point.y()); // NOT point.getY()

        Person person = new Person("Bob", 25, "bob@example.com");
        System.out.println("\nPerson details:");
        System.out.println("  Name: " + person.name()); // NOT person.getName()
        System.out.println("  Age: " + person.age()); // NOT person.getAge()
        System.out.println("  Email: " + person.email()); // NOT person.getEmail()

        // Note the difference in method names
        System.out.println("\n⚠️  Important: Records use name() not getName()");

        System.out.println();
    }

    private static void demonstrateAutomaticMethods() {
        System.out.println("3. AUTOMATIC METHODS:");
        System.out.println("---------------------");

        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);
        Point p3 = new Point(15, 25);

        // toString() - automatically generated
        System.out.println("toString() method:");
        System.out.println("  p1: " + p1);
        System.out.println("  Format: RecordName[component1=value1, component2=value2]");

        // equals() - value-based equality
        System.out.println("\nequals() method (value-based):");
        System.out.println("  p1.equals(p2): " + p1.equals(p2) + " (same values)");
        System.out.println("  p1.equals(p3): " + p1.equals(p3) + " (different values)");
        System.out.println("  p1 == p2: " + (p1 == p2) + " (different objects)");

        // hashCode() - consistent with equals
        System.out.println("\nhashCode() method:");
        System.out.println("  p1.hashCode(): " + p1.hashCode());
        System.out.println("  p2.hashCode(): " + p2.hashCode() + " (same as p1)");
        System.out.println("  p3.hashCode(): " + p3.hashCode() + " (different)");

        // Using in collections
        System.out.println("\nUsing records in collections:");
        java.util.Set<Point> points = new java.util.HashSet<>();
        points.add(p1);
        points.add(p2); // Won't be added (equals p1)
        points.add(p3);
        System.out.println("  Set size: " + points.size() + " (p1 and p2 are equal)");

        System.out.println();
    }

    private static void demonstrateRecordVsClass() {
        System.out.println("4. RECORD VS TRADITIONAL CLASS:");
        System.out.println("--------------------------------");

        // Record version
        Person recordPerson = new Person("Charlie", 35, "charlie@example.com");

        // Class version
        PersonClass classPerson = new PersonClass("Charlie", 35, "charlie@example.com");

        System.out.println("Record version:");
        System.out.println("  Declaration: record Person(String name, int age, String email) { }");
        System.out.println("  Lines of code: 1");
        System.out.println("  toString(): " + recordPerson);
        System.out.println("  Access: person.name()");

        System.out.println("\nClass version:");
        System.out.println("  Lines of code: ~50 (with equals, hashCode, toString)");
        System.out.println("  toString(): " + classPerson);
        System.out.println("  Access: person.getName()");

        System.out.println("\n✅ Records: Less code, same functionality!");

        System.out.println();
    }

    private static void demonstrateImmutability() {
        System.out.println("5. IMMUTABILITY:");
        System.out.println("----------------");

        Person person = new Person("David", 40, "david@example.com");

        System.out.println("Original person: " + person);
        System.out.println("\n⚠️  Records are immutable - all fields are final");
        System.out.println("⚠️  You cannot change values after creation");

        // To "modify" a record, create a new instance
        System.out.println("\nTo change values, create a new instance:");
        Person olderPerson = new Person(person.name(), person.age() + 1, person.email());
        System.out.println("  Original: " + person);
        System.out.println("  Modified: " + olderPerson);

        // Demonstrating with coordinates
        Coordinate location = new Coordinate(40.7128, -74.0060);
        System.out.println("\nLocation: " + location);
        System.out.println("  Latitude: " + location.latitude());
        System.out.println("  Longitude: " + location.longitude());

        // Create new coordinate for different location
        Coordinate newLocation = new Coordinate(51.5074, -0.1278);
        System.out.println("New location: " + newLocation);

        System.out.println();
    }
}

# Java 21 Learning Project

Welcome to your Java 21 learning journey! This project is structured to help you learn Java concepts systematically.

---

## 🗺️ Learning Roadmap

### Currently Learning
- ✅ **Enums** - `com.github.java_21.enums`

### Upcoming Topics
(Roadmap to be created as you progress)

---

## 📚 Project Structure & Instructions

### Package Organization

For each topic covered in this learning project, follow this standardized structure:

- Create a dedicated package for each topic
- **Package naming convention**: `com.github.java_21.[topic-name]`
- **Examples**: `com.github.java_21.enums`, `com.github.java_21.streams`, `com.github.java_21.collections`

### Required Files Per Topic

Each topic package must contain:

#### 1. Notes File (`[topic]-notes.txt`)
- **Purpose**: Document theoretical concepts, syntax, and key points
- **Content should include**:
  - Topic overview and purpose
  - Syntax and usage patterns
  - Best practices and common pitfalls
  - Important rules and constraints
  - Real-world use cases
  - Comparison with related concepts (if applicable)

#### 2. Example Java Files
- Create practical, runnable Java classes with `main()` methods
- Each file demonstrates specific aspects of the topic
- Use clear, descriptive class names (e.g., `BasicEnumExample`, `AdvancedEnumExample`)
- Include comprehensive comments explaining the code
- Organize examples by complexity level (Basic → Intermediate → Advanced)
- Each example should be independently runnable

#### 3. Practice Exercises (Optional)
- Create exercise files for hands-on practice
- Include TODO comments for learners to complete
- Provide solution files in a separate `solutions` sub-package

### Topic Structure Example
```
com.github.java_21.[topic]/
├── [topic]-notes.txt              # Comprehensive notes and theory
├── BasicExample.java              # 1️⃣ Fundamental concepts
├── IntermediateExample.java       # 2️⃣ Practical applications (optional)
├── AdvancedExample.java           # 3️⃣ Advanced techniques (optional)
└── solutions/                     # Practice exercise solutions (optional)
```

**Current Enums Package:**
```
com.github.java_21.enums/
├── enum-notes.txt                   # Complete enum theory and reference
├── BasicEnumExample.java            # 1️⃣ Simple enums, built-in methods, switch
├── EnumWithFieldsExample.java       # 2️⃣ Enums with fields, constructors, methods
└── AdvancedEnumExample.java         # 3️⃣ Abstract methods, interfaces, EnumSet/Map
```

---

## 🚀 Getting Started

1. Navigate to the topic you want to learn (currently: `com.github.java_21.enums`)
2. Start with the `[topic]-notes.txt` file to understand theory
3. Review the example Java files in order (indicated by emojis 1️⃣ 2️⃣ 3️⃣):
   - Start with `BasicExample.java` for fundamentals
   - Progress to `IntermediateExample.java` for practical applications
   - Explore `AdvancedExample.java` for complex techniques
4. Run each example file to see the output:
   ```bash
   # Compile and run (from project root)
   javac src/main/java/com/github/java_21/enums/BasicEnumExample.java
   java -cp src/main/java com.github.java_21.enums.BasicEnumExample
   
   # Or use Maven/your IDE to run the main methods
   ```
5. Modify the examples and experiment with variations
6. Add your own notes and observations to the notes file

---

## 📖 Learning Workflow

1. **Read** the notes file to understand theory
2. **Study** the example classes to see concepts in action
3. **Practice** by modifying examples or completing exercises
4. **Experiment** by creating your own variations
5. **Document** your learnings and observations in the notes file

---

## 💡 Code Standards

- **Naming**: Use clear, descriptive names for classes, methods, and variables
- **Comments**: Add explanatory comments for complex logic
- **Documentation**: Use JavaDoc for public classes and methods
- **Formatting**: Follow standard Java formatting conventions

---

## 🎯 Learning Tips

- **Consistency**: Study regularly, even if for short periods
- **Active Learning**: Don't just read—write code and experiment
- **Documentation**: Keep notes as you learn
- **Incremental**: Master one topic before moving to the next
- **Practical**: Try to relate concepts to real-world scenarios

---

## 📝 Progress Tracking

- Mark completed topics in the roadmap above
- Add personal notes and insights to topic notes files
- Keep a learning journal if desired

---

**Happy Learning! 🚀**

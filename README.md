# JACHAR

[![Latest Release](https://img.shields.io/github/v/release/Bri-idk/Jachar?color=2ea44f&label=Latest%20Release&logo=github)](https://github.com/Bri-idk/Jachar/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/Bri-idk/Jachar/total?color=blueviolet)](https://github.com/Bri-idk/Jachar/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> **Jachar is an open-source, lightweight text editor written in Java and built with JavaFX & Maven.** Created as a hands-on initiative to master Java fundamentals, Object-Oriented Programming (OOP), and modern desktop GUI frameworks, this project is designed with modularity and simplicity in mind.

---

---
## So what is this project?

<strong>

Okay so, that was the "formal" description of the project, but the reality is that this is purely a learning project. It might evolve, but right now my main goal is simply to build up the knowledge to tackle more complex software later on.

You’re helping me immensely just by checking it out and leaving feedback—this definitely isn't a tool you need, but getting your input is something I genuinely need to grow as a developer.

I’m pouring a lot of time into this and loving every minute of it, so seeing people stop by to help out really keeps me motivated.

Huge thanks for your time, and a special shoutout to the Together Java community for all the support!

Btw if you would like to read a more personal documentation pls go to de readme on the docs Folder or see my GitHub pages view. 

</strong>

---

## 📥 Download Executables

You can download pre-compiled standalone versions directly from Releases:

[![Download Latest Release](https://img.shields.io/badge/Download-Latest_Release-2ea44f?style=for-the-badge&logo=github)](https://github.com/Bri-idk/Jachar/releases/latest)

- **Windows**: Download `JaChar.zip` (includes standalone native `.exe` generated with `jpackage`, no Java installation required).
- **Executable JAR**: Download `Jachar-1.0-SNAPSHOT-jar-with-dependencies.jar` (run on any platform with `java -jar <filename>.jar`).

---

## Features (Current MVP - NEW 0.3)

- **Modern JavaFX 22 GUI**: Rebuilt using `Application`, `Stage`, `Scene`, `BorderPane`, and `TextArea`.
- **Decoupled Architecture**: File I/O operations (`FileManager`) are completely separated from UI presentation (`WindowManager` and `Stylizer`).
- **Dynamic CSS Theme Engine**: Real-time atomic switching between Dark and Light themes via external `.css` stylesheets without UI flicker.
- **Dynamic OS Font & Size Picker**: Interactively select font families installed on your OS via `Font.getFamilies()` and adjust font sizes with native `ChoiceDialog`.
- **Smart Save Workflow**:
  - Direct save if a file is already opened/known.
  - Automatic *Save As* dialog prompt via JavaFX `FileChooser` if working on a new untitled note.
- **Clean Canvas Reset**: "New File" instantly clears the editor and resets the active file path.

---

## 🛠️ Tech Stack & Dependencies

- **Language**: Java 21 (JDK 21+)
- **Build Tool**: Apache Maven (`pom.xml`)
- **GUI Framework**: JavaFX 22.0.2 (`org.openjfx:javafx-controls`)
- **I/O Engine**: Java NIO (`java.nio.file.Files`, `java.nio.file.Path`)
- **Maven Plugins**:
  - `maven-compiler-plugin` (Target JDK 21)
  - `maven-assembly-plugin` (Generates Fat JAR with dependencies)
- **CI/CD Pipeline**: GitHub Actions (`windows-latest`) using `jpackage` to automate standalone `.exe` releases.

---

## 🚀 How to Run & Build from Source

### 1. Clone the repository
```bash
git clone https://github.com/Bri-idk/Jachar.git
cd Jachar
```

### 2. Run directly in development (Maven)
```bash
mvn javafx:run
```

### 3. Build the Executable Fat JAR
```bash
mvn clean package
```
This produces two JARs in the `target/` directory:
- `target/editor-texto-1.0-SNAPSHOT-jar-with-dependencies.jar` (Executable Fat JAR with JavaFX + CSS included).

Run it with:
```bash
java -jar target/editor-texto-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## 📜 Project History & Evolution

### Base Project (Console / CLI)
- **Initial commit** -> Setup with the MIT license.
- **Project base** -> Minimal functional CLI base for writing/appending to text files via terminal.
- **Code modularized & read function added** -> Extracted functions, added line-by-line editing in memory (`Files.readAllLines`), and input validation.

### UI Versions

#### Swing Era:
- **v0.1**: Initial functional UI with Open, Save, and New File buttons backed by a clean 26-line `FileManager`.
- **v0.2**: Migrated to native `FileDialog`, added dark/light theme switching with Java varargs, and improved Windows rendering.
- **v0.3**: Added classic desktop menu bar (`JMenuBar` / `JMenu`), customized menu UI delegates with `BasicMenuBarUI`, and added GitHub Actions CI/CD pipeline using JDK 21 `jpackage` to automate standalone Windows `.exe` releases.

#### JavaFX Era:
- **NEW 0.3**: Major architecture evolution!
  - Adopted Maven (`pom.xml`) for modern build lifecycle and dependency management.
  - Migrated GUI to **JavaFX 22.0.2** (`Application`, `Stage`, `Scene`).
  - Rebuilt `Stylizer` into a CSS-based theme manager with atomic stylesheet swaps (`dark.css` / `light.css`).
  - Integrated dynamic font and font-size dialogs using `ChoiceDialog` and `Font.getFamilies()`.
  - Updated GitHub Actions CI/CD to package Maven output into standalone Windows `.exe` releases.

---

## 💬 Looking for Feedback & Code Review!

Since this is a learning project, I would love to hear feedback from other developers! Specifically:

1. **JavaFX & CSS Separation**: How clean is the theme management in `Stylizer` using external CSS stylesheets?
2. **State & I/O Decoupling**: Thoughts on keeping `FileManager` purely static and decoupled from JavaFX components?
3. **Packaging & Architecture**: Any suggestions for JavaFX modularization, scaling layout components, or custom CSS styling?

Feel free to open an **Issue**, submit a **Pull Request**, or share your thoughts!
# JACHAR

> **Jachar is an open-source, lightweight text editor written in pure Java.** Created as a hands-on initiative to master Java fundamentals and Object-Oriented Programming (OOP), this project is designed with modularity and simplicity in mind—serving as a solid foundation for future enhancements, tooling integrations, and interface improvements.

---

## Features (Current MVP)

- **Pure Java (Zero Dependencies)**: Built entirely using standard Java 21 (`java.nio.file` and `javax.swing`).
- **Dynamic File Explorer**: Native file chooser (`JFileChooser`) to open and save files anywhere on your system.
- **Smart Save Workflow**:
  - Direct save if a file is already opened/known.
  - Automatic *Save As* dialog prompt if working on a new untitled note.
- **Clean Canvas Reset**: "Nuevo archivo" instantly clears the editor and resets the state.
- **Decoupled Architecture**: File I/O operations (`FileManager`) are completely separated from the UI logic (`WindowManager`).

---

## Tech Stack & Requirements

- **Language**: Java 21 (JDK 21+)
- **GUI Framework**: Java Swing & AWT (Standard Library)
- **I/O Engine**: Java NIO (`Files`, `Path`)
- **IDE**: IntelliJ IDEA (or any Java-compatible editor)

---

## How to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Bri-idk/Jachar.git
   cd Jachar
   ```

2. **Open & Run**:
   - Open the project in **IntelliJ IDEA** (or your favorite IDE).
   - Ensure the Project SDK is set to **JDK 21**.
   - Run `src/Main.java`.

3. **Or run via terminal**:
   ```bash
   javac -d out src/io/*.java src/ui/*.java src/Main.java
   java -cp out Main
   ```

---

## Project History & Evolution

### Base Project (Console / CLI)
- **Initial commit** -> Setup with the MIT license.
- **Project base** -> Minimal functional base for writing/appending to text files via CLI. Everything begins somewhere, I guess.
- **Code modularized & read function added** -> Extracted functions, added line-by-line editing in memory (`Files.readAllLines`), and input validation.
- **Branching** -> Created `uiBranch` for the visual version, and kept the terminal version logic on `terminal_Version` for later on making a full Vim/Nano-like CLI editor.

### UI Version
This version of the software is fully oriented to making a graphical UI using **Java Swing / AWT**.
This was honestly a crazy learning curve, but now I understand how the Event Dispatch Thread, listeners, and components work together!

As you can see if you read the code or check the commit history, I deleted part of the old console code (like the `utils` scanner package and line-by-line edit prompts) because on this version it was pointless and unnecessary—the `JTextArea` itself *is* the editor. But that code will live on in the `terminal_Version` branch!

#### UI Version Change History:
- **First Push & Merge**: Fully functional UI with Open, Save (smart direct save vs dialog), and New File buttons, backed by a clean 26-line `FileManager`. I mean, it might look a little bit retro/simple, but it works!

---

## 💬 Looking for Feedback & Code Review!

Since this is a learning project, I would love to hear feedback from other developers! Specifically:

1. **Decoupling**: How clean is the separation between `FileManager` (I/O) and `WindowManager` (Swing UI)?
2. **State Management**: How would you manage the active file state (`currentPath`) as the editor scales?
3. **Swing Best Practices**: Any suggestions on layouts, event listeners, or threading?

Feel free to open an **Issue**, submit a **Pull Request**, or share your thoughts!
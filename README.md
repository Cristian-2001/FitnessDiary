# Fitness Diary 💪

> An Android fitness tracking application for managing workouts, exercises, diets, and nutrition with SQLite database integration

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com)
[![SQLite](https://img.shields.io/badge/Database-SQLite-blue.svg)](https://www.sqlite.org)

---

## 🎯 Overview

Fitness Diary is a native Android application developed as part of an Object-Oriented Programming course (2021-2022). It demonstrates clean architecture principles with comprehensive workout and nutrition tracking capabilities.

## ✨ Key Features

- 🏋️ **Workout Management** - Create, view, and track custom workouts with multiple exercises
- 💪 **Exercise Database** - Browse and filter exercises by difficulty and muscle group
- 🥗 **Diet Planning** - Build meal plans with nutritional tracking across four daily meals
- 📊 **Food Database** - Comprehensive nutritional information using Swiss nutritional values database
- ⏱️ **Timer Integration** - Built-in countdown timer for exercise rest periods
- 👤 **User Profiles** - Track personal metrics including height and weight

---

## 🛠️ Technologies & Libraries

### Core Technologies

| Technology | Purpose |
|------------|---------|
| **Android SDK** | Native Android development with Material Design |
| **SQLite** | Local database management |
| **Java** | Primary programming language |
| **XML** | UI layout definitions |

### Development Tools

- **Android Studio** - Primary IDE
- **DB Browser for SQLite** - Database design and management
- **ESF Database Migration Toolkit** - Database migration utilities
- **StarUML** - UML class diagram modeling ([view diagram](UML/UMLProgettoPalestra.mdj))

### Data Sources

- 🇨🇭 [Swiss Nutritional Values Database](https://valorinutritivi.ch/it/downloads/) - Comprehensive food nutrition data
- 🇨🇦 [Manitoba Government Exercise Database](https://www.edu.gov.mb.ca/k12/cur/physhlth/frame_found_gr11/rm/resist_train_planner.xls) - Resistance training exercise library

---

## 🎨 Interesting Techniques

The codebase demonstrates several Android development patterns:

- **SQLite Database Design** - Custom schema with relational tables linking users, workouts, exercises, diets, meals, and foods
- **Activity Lifecycle Management** - Proper handling of Android activity states and data persistence
- **RecyclerView Adapters** - Efficient list rendering for exercises, workouts, and food items
- **Material Design** - Following Android's design guidelines for UI components

---

## 📁 Project Structure

```
fitness-diary/
│
├── 📂 .idea/                 # Android Studio IDE configuration
├── 📂 app/                   # Main application module
├── 📂 gradle/                # Gradle wrapper configuration
├── 📂 UML/                   # UML class diagrams
│
├── 📄 FitnessDiary.pdf       # Project documentation
├── 📄 build.gradle           # Root build configuration
├── 📄 settings.gradle        # Project settings
└── 📄 gradle.properties      # Gradle properties
```

### 📌 Notable Directories

| Directory | Description |
|-----------|-------------|
| **app/** | Contains all application source code, resources, layouts, and the SQLite database implementation |
| **UML/** | Includes StarUML diagram files showing the complete class structure with relationships between User, Workout, Exercise, Diet, Meal, and Food entities |
| **.idea/** | Android Studio configuration including inspection profiles with comprehensive lint checks enabled |

---

## 🏗️ Architecture

The application follows an object-oriented design with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│                    User                      │
│  • name, height, weight                      │
└──────────┬────────────────────┬──────────────┘
           │                    │
           │ 1:n                │ 1:n
           ▼                    ▼
    ┌─────────────┐      ┌─────────────┐
    │  Workout    │      │    Diet     │
    │  • nome     │      │  • nome     │
    │  • tempo    │      └──────┬──────┘
    │  • calorie  │             │ n:4
    └──────┬──────┘             ▼
           │ n:n          ┌─────────────┐
           ▼              │    Pasto    │
    ┌─────────────┐       │  • nome     │
    │  Esercizio  │       │  • calorie  │
    │  • nome     │       └──────┬──────┘
    │  • durata   │              │ n:n
    │  • serie    │              ▼
    │  • reps     │       ┌─────────────┐
    └─────────────┘       │  Alimento   │
                          │  • valori   │
                          │    nutritivi│
                          └─────────────┘
```

See the [UML class diagram](UML/UMLProgettoPalestra.mdj) for complete entity relationships and attributes.

---

## 👥 Contributors

This project was developed by:

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/Cristian-2001.png" width="100px;" alt="Casali Cristian"/><br />
      <sub><b>Casali Cristian</b></sub>
    </td>
    <td align="center">
      <img src="https://github.com/aldoflotta.png" width="100px;" alt="Flotta Aldo"/><br />
      <sub><b>Flotta Aldo</b></sub>
    </td>
  </tr>
</table>

*Object-Oriented Programming Course 2021-2022*

---

## 📄 License

This project was developed for educational purposes as part of a university course.

---

## 🙏 Acknowledgments

Special thanks to:
- The Swiss Federal Food Safety and Veterinary Office for providing the nutritional values database
- Manitoba Education for the comprehensive exercise database

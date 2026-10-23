# DJRS — Personal Music Rewinds

A Java application that reads your music listening data and turns it into personalized **"Rewinds"**: custom recaps of the songs, artists, and genres you listened to over a chosen date range.

> 🚧 **Prototype:** the app currently runs as a command-line interface. The controller layer is built so a JavaFX UI can be added on top.

## Features
- **User accounts:** sign up and log in, with hashed passwords and a shared user session
- **Rewinds:** create, edit, view, and delete rewinds for a custom date range
- **Song search:** find songs in a rewind by title or artist
- **Data import:** load listening history from files (CSV import in progress)
- **Local storage:** user and music data saved as JSON with Gson

## Tech Stack
Java 17 · Maven · Gson · JUnit 5

## Project Structure
```
src/main/java/com/djrs/
├── controller/   # App logic for login, dashboard, create/edit, and view screens
├── io/           # File import, JSON storage, input validation, password hashing
├── model/        # Song, Artist, Album, Genre, Rewind, User, UserSession
└── view/cli/     # Command-line screens
data/             # Sample users, music data, and saved rewinds
```

## Getting Started
```bash
mvn compile      # Build
mvn exec:java    # Run
mvn test         # Test
```

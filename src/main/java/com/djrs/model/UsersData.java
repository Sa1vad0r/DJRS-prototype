package com.djrs.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UsersData {
    private final Gson gson; // used for json operations
    private ArrayList<User> users;
    private String fileName;
    private static final String DEFAULT_FILE_NAME = "usersData.json";

    public UsersData() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        users = new ArrayList<>();
        fileName = DEFAULT_FILE_NAME;
    }

    public UsersData(String fileName) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.users = new ArrayList<>();

        if (!fileName.contains(".json")) {
            fileName += ".json";
        }
        this.fileName = fileName;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            this.fileName = fileName;
        }
    }

    public String getFullPath() {
        return "data/" + fileName;
    }

    public void loadUsersData() {
        users.clear(); // Clear current array list to replace any existing data

        try (Reader reader = Files.newBufferedReader(Paths.get(getFullPath()))) {
            // Import data from json
            // JSON fields and classes fields MUST BE THE SAME (name-wise)
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (IOException e) {
            System.out.println("[ERROR]: I/O error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public boolean saveDataToFile() {
        File file = new File(getFullPath());
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR]: I/O error occurred while writing to the file.");
            e.printStackTrace();
        }

        return false;
    }

    public void addUser(User user) {
        // User object must exist and must not be a duplicate
        if (user != null && !users.contains(user)) {
            users.add(user);
        }
    }

    public boolean deleteUser(User user) {
        if (user == null || users.contains(user))
            return false;

        users.remove(user);

        return saveDataToFile();
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }

        return null;
    }

    private String readFileToString() {
        String jsonString = null;
        File file = new File(getFullPath());

        try {
            jsonString = new BufferedReader(new FileReader(file))
                    .lines()
                    .map(String::join) // Join all of the strings inside of the json file
                    .collect(Collectors.joining("\n"));

        } catch (FileNotFoundException e) {
            System.out.println("[ERROR]: Error occurred while reading the file.");
            e.printStackTrace();
        }

        return jsonString;
    }

    // Good practice to make toString for model classes
    @Override
    public String toString() {
        StringBuilder usersList = new StringBuilder("UsersData\n[users:\n");

        for (User user : users) {
            usersList.append("  ").append(user.toString()).append("\n");
        }
        usersList.append(" ]");

        return usersList.toString();
    }
}

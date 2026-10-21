package com.djrs.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersData {
    private ArrayList<User> users;
    private String fileName;
    private static final String DEFAULT_FILE_NAME = "usersData.json";

    public UsersData() {
        this.users = new ArrayList<>();
        this.fileName = DEFAULT_FILE_NAME;
    }

    public UsersData(String fileName) {
        this.users = new ArrayList<>();
        this.fileName = fileName;
        // setFileName(fileName);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        if (users != null) {
            this.users = users;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return;
        }
        this.fileName = fileName;
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public void loadUsersData() {
        File file = new File("data/" + fileName);

        try (Scanner scanner = new Scanner(file)){
            scanner.nextLine(); // Skip the header line of csv file
            // while (scanner.hasNextLine()) {
            //     String line = scanner.nextLine().trim();
            //     User user = convertUserJSONToUser(line);
            //     addAidShip(aidShip);
            // }
        }
        catch (IOException e) {
            System.out.println("[ERROR]: Failed to load users data.");
            e.printStackTrace();
        }
    }

    public User findUser(String displayName) {
        for (User user : users) {
            if (user.getDisplayName().equals(displayName)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserExists(String displayName) {
        return findUser(displayName) != null;
    }

    public boolean updateUser(User user) {
        if (!isUserExists(user.getDisplayName())) return false;

        User existingUser = findUser(user.getDisplayName());
        if (!deleteUser(user)) return false;

        addUser(user);

        return saveDataToFile();
    }

    public boolean deleteUser(User user) {
        User existingUser = findUser(user.getDisplayName());

        if (existingUser == null) return false;

        users.remove(existingUser);

        return saveDataToFile();
    }

    private boolean saveDataToFile() {
        File file = new File("data/" + fileName);

        try (FileWriter writer = new FileWriter(file)){
            // Write to json
        } catch (IOException e) {
            System.out.println("[ERROR]: I/O error while saving users data.");
            e.printStackTrace();
        }

        return true;
    }

    private User convertUserJSONToUser(String userJSONField) {
        // Parse userJSONField to User object
        return null;
    }

    private String convertUserToJSONField(User user) {
        // Return a string representation of the user object converted into a json field
        return "";
    }

    // Good practice to make toString for model classes
    @Override
    public String toString() {
        StringBuilder usersList = new StringBuilder("UsersData\n[users:\n");

        for (User user : users) {
            usersList.append("  ").append(user.toString()).append("\n");
        }
        usersList.append("]");

        return usersList.toString();
    }
}
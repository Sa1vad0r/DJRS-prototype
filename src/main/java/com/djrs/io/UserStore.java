package com.djrs.io;

import com.djrs.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Commented out UserStore for now. Delete if not used.
// public class UserStore {
//     private static final String USERS_FILE = "data/UsersData.json";
//
//     // NOTE: hand-rolled string parsing, just to understand how it works under the hood.
//     // Breaks on commas/escaped quotes inside values, nested objects, etc.
//     // For the real project, swap this for a JSON library (Gson/Jackson) instead.
//     public static User findUserByUsername(String username) {
//         try {
//             String content = new String(Files.readAllBytes(Paths.get(USERS_FILE))).trim();
//             content = content.substring(1, content.length() - 1); // strip outer [ ]
//
//             String[] records = content.split("\\}\\s*,\\s*\\{");
//
//             for (String record : records) {
//                 record = record.replace("{", "").replace("}", "");
//                 Map<String, String> fields = new HashMap<>();
//
//                 for (String pair : record.split(",")) {
//                     String[] kv = pair.split(":", 2);
//                     if (kv.length != 2) continue;
//                     String key = kv[0].trim().replaceAll("^\"|\"$", "");
//                     String value = kv[1].trim().replaceAll("^\"|\"$", "");
//                     fields.put(key, value);
//                 }
//
//                 if (username.equals(fields.get("username"))) {
//                     String displayName = (fields.getOrDefault("firstName", "") + " "
//                             + fields.getOrDefault("lastName", "")).trim();
//                     return new User(fields.get("id"), displayName, fields.get("password"));
//                 }
//             }
//         } catch (IOException e) {
//             System.out.println("[ERROR]: Failed to load users data.");
//             e.printStackTrace();
//         }
//         return null;
//     }
// }

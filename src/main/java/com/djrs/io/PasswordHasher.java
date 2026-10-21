package com.djrs.io;

public class PasswordHasher {
    /**
     *  Generates a secure hash for a given password (Used ONLY during user registration).
     * @param password The password to be hashed.
     * @return
     */
    public static String hash(String password) {
        // TODO: Implement complex hashing logic here (e.g., using BCrypt).
        // This would use something like BCryptPasswordEncoder().encode(password);
        System.out.println("[Hasher]: Hashing password...");
        return "DUMMY_BRCRYPT_HASH_" + password.hashCode();
    }

    public static boolean checkPassword(String inputPassword, String storedHash) {
        // TODO: Implement hash checking (e.g., using BCrypt).
        // This would use something like BCryptPasswordEncoder().matches(inputPassword, storedHash);
        System.out.println("[Hasher]: Verifying password...");

        // Mock logic: If the input password is "password", we pretend it matches a hash.
        if ("password".equals(inputPassword)) {
            return true;
        }
        return false;
    }
}

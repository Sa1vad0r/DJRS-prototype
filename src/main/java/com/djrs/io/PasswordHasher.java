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
        // No real hashing for this project — plain equality check against the stored password.
        System.out.println("[Hasher]: Verifying password...");
        return inputPassword.equals(storedHash);
    }
}

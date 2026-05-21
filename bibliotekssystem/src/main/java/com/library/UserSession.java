package com.library;

public class UserSession {
    private static LibraryUser loggedInUser = null;

    public static void logIn(LibraryUser user) {
        loggedInUser = user;
    }

    public static void logOut() {
        loggedInUser = null;
    }

    public static LibraryUser getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static boolean isAdmin() {
        return loggedInUser != null && "ADMIN".equalsIgnoreCase(loggedInUser.getUserCategory());
    }
}
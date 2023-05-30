package net.kordian.shortener.auth;

public record RegisterRequest(String firstName, String lastName, String email, String password) {
}

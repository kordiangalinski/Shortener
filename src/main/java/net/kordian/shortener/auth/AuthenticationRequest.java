package net.kordian.shortener.auth;

public record AuthenticationRequest(String email, String password) {
}

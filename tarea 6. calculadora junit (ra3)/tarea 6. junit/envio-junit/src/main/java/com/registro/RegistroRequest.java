package com.registro;
public record RegistroRequest(
    String username,
    String password,
    String email,
    int edad
) {}
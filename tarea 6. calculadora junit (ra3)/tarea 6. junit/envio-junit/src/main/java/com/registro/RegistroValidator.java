package com.registro;

public class RegistroValidator {

    public static void validar(RegistroRequest req) {
        if (req == null)
            throw new IllegalArgumentException("request no puede ser null");
        validarUsername(req.username());
        validarPassword(req.password());
        validarEmail(req.email());
        validarEdad(req.edad());
    }

    private static void validarUsername(String u) {
        if (u == null || u.isBlank())
            throw new IllegalArgumentException("username obligatorio");
        if (u.length() < 3 || u.length() > 15)
            throw new IllegalArgumentException("username debe tener entre 3 y 15");
        if (u.startsWith("_"))
            throw new IllegalArgumentException("username no puede empezar por _");
        if (!u.matches("[A-Za-z0-9_]+"))
            throw new IllegalArgumentException("username contiene caracteres no válidos");
    }

    private static void validarPassword(String p) {
        if (p == null || p.isBlank())
            throw new IllegalArgumentException("password obligatoria");
        if (p.length() < 8)
            throw new IllegalArgumentException("password demasiado corta");
        boolean upper  = p.matches(".*[A-Z].*");
        boolean lower  = p.matches(".*[a-z].*");
        boolean digit  = p.matches(".*\\d.*");
        boolean symbol = p.matches(".*[!@#$%^&*].*");
        if (!(upper && lower && digit && symbol))
            throw new IllegalArgumentException("password no cumple complejidad");
    }

    private static void validarEmail(String e) {
        if (e == null || e.isBlank())
            throw new IllegalArgumentException("email obligatorio");
        if (e.contains(" "))
            throw new IllegalArgumentException("email inválido");
        int at     = e.indexOf('@');
        int lastAt = e.lastIndexOf('@');
        if (at <= 0 || at != lastAt)
            throw new IllegalArgumentException("email inválido");
        int dot = e.indexOf('.', at);
        if (dot <= at + 1 || dot == e.length() - 1)
            throw new IllegalArgumentException("email inválido");
    }

    private static void validarEdad(int edad) {
        if (edad < 16 || edad > 120)
            throw new IllegalArgumentException("edad fuera de rango");
    }
}
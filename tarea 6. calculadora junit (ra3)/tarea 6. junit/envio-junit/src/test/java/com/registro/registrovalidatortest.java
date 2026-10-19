package com.registro;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("RegistroValidator – Suite de validación profesional")
class RegistroValidatorTest {

    // Helper: ejecuta validación y devuelve el mensaje de error
    private String mensajeError(RegistroRequest req) {
        IllegalArgumentException ex =
            assertThrows(IllegalArgumentException.class,
                () -> RegistroValidator.validar(req));
        return ex.getMessage();
    }

    // ==================== R1: REQUEST NULL ====================
    @Test
    @DisplayName("Si request es null → lanza error con mensaje claro")
    void requestNull() {
        assertEquals("request no puede ser null", mensajeError(null));
    }

    // ==================== R2–R4: USERNAME ====================
    @Nested
    @DisplayName("Validación de username")
    class UsernameTests {

        @ParameterizedTest(name = "Caso {index}: username=\"{0}\" → \"{1}\"")
        @CsvSource({
            "'',                   username obligatorio",
            "' ',                  username obligatorio",
            "ab,                   username debe tener entre 3 y 15",
            "abcdefghijklmnop,     username debe tener entre 3 y 15",
            "_pepe,                username no puede empezar por _",
            "pepe-1,               username contiene caracteres no válidos"
        })
        @DisplayName("Usernames inválidos devuelven mensaje correcto")
        void usernamesInvalidos(String username, String msg) {
            RegistroRequest req =
                new RegistroRequest(username.trim(), "Aa1!aaaa", "a@b.com", 20);
            assertEquals(msg.trim(), mensajeError(req));
        }

        @Test
        @DisplayName("Username válido → no lanza excepción")
        void usernameValido() {
            RegistroRequest req =
                new RegistroRequest("pepe_1", "Aa1!aaaa", "a@b.com", 20);
            assertDoesNotThrow(() -> RegistroValidator.validar(req));
        }
    }

    // ==================== R5–R6: PASSWORD ====================
    @Nested
    @DisplayName("Validación de password")
    class PasswordTests {

        @ParameterizedTest(name = "Caso {index}: password=\"{0}\" → \"{1}\"")
        @CsvSource({
            "'',           password obligatoria",
            "Aa1!aaa,      password demasiado corta",
            "aa1!aaaa,     password no cumple complejidad",
            "AA1!AAAA,     password no cumple complejidad",
            "Aaaaaaaa,     password no cumple complejidad",
            "Aa!aaaaa,     password no cumple complejidad"
        })
        @DisplayName("Passwords inválidas devuelven mensaje correcto")
        void passwordsInvalidas(String password, String msg) {
            RegistroRequest req =
                new RegistroRequest("pepe_1", password.trim(), "a@b.com", 20);
            assertEquals(msg.trim(), mensajeError(req));
        }

        @Test
        @DisplayName("Password válida → no lanza excepción")
        void passwordValida() {
            RegistroRequest req =
                new RegistroRequest("pepe_1", "Aa1!aaaa", "a@b.com", 20);
            assertDoesNotThrow(() -> RegistroValidator.validar(req));
        }
    }

    // ==================== R7–R9: EMAIL ====================
    @Nested
    @DisplayName("Validación de email")
    class EmailTests {

        @ParameterizedTest(name = "Caso {index}: email=\"{0}\" → \"{1}\"")
        @CsvSource({
            "'',              email obligatorio",
            "sinArroba.com,   email inválido",
            "@sinlocal.com,   email inválido",
            "dos@@correo.com, email inválido",
            "sin.punto@com,   email inválido",
            "a@b.,            email inválido",
            "a @b.com,        email inválido"
        })
        @DisplayName("Emails inválidos devuelven mensaje correcto")
        void emailsInvalidos(String email, String msg) {
            RegistroRequest req =
                new RegistroRequest("pepe_1", "Aa1!aaaa", email.trim(), 20);
            assertEquals(msg.trim(), mensajeError(req));
        }

        @Test
        @DisplayName("Email válido → no lanza excepción")
        void emailValido() {
            RegistroRequest req =
                new RegistroRequest("pepe_1", "Aa1!aaaa", "ana@correo.com", 20);
            assertDoesNotThrow(() -> RegistroValidator.validar(req));
        }
    }

    // ==================== R10: EDAD ====================
    @Nested
    @DisplayName("Validación de edad")
    class EdadTests {

        @ParameterizedTest(name = "Caso {index}: edad={0} → \"{1}\"")
        @CsvSource({
            "15,  edad fuera de rango",
            "121, edad fuera de rango",
            "0,   edad fuera de rango",
            "-1,  edad fuera de rango"
        })
        @DisplayName("Edades inválidas devuelven mensaje correcto")
        void edadesInvalidas(int edad, String msg) {
            RegistroRequest req =
                new RegistroRequest("pepe_1", "Aa1!aaaa", "a@b.com", edad);
            assertEquals(msg.trim(), mensajeError(req));
        }

        @Test
        @DisplayName("Edades límite válidas (16 y 120) → no lanzan excepción")
        void edadesLimiteValidas() {
            assertDoesNotThrow(() ->
                RegistroValidator.validar(
                    new RegistroRequest("pepe_1", "Aa1!aaaa", "a@b.com", 16)));
            assertDoesNotThrow(() ->
                RegistroValidator.validar(
                    new RegistroRequest("pepe_1", "Aa1!aaaa", "a@b.com", 120)));
        }
    }

    // ==================== CASOS VÁLIDOS GLOBALES ====================
    @Nested
    @DisplayName("Casos completamente válidos")
    class CasosValidosTests {

        @ParameterizedTest(name = "Caso {index}: {0} / {1} / {2} / edad={3}")
        @CsvSource({
            "pepe_1,   Aa1!aaaa,       a@b.com,        20",
            "usuario1, Passw0rd!,      user@gmail.com, 30",
            "ana,      Segur@1234,     ana@test.es,    16",
            "max15chars1234, Aa1!aaaa, x@y.org,        120"
        })
        @DisplayName("Requests válidos no lanzan excepción")
        void requestsValidos(String u, String p, String e, int edad) {
            RegistroRequest req = new RegistroRequest(u, p, e, edad);
            assertDoesNotThrow(() -> RegistroValidator.validar(req));
        }
    }
}
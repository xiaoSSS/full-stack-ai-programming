package com.xiaoss.starter.utils.exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ExecUtils {

    private ExecUtils() {
    }

    public static String exec(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            byte[] output = process.getInputStream().readAllBytes();
            int exit = process.waitFor();
            if (exit != 0) {
                throw new IllegalStateException("Command exited with code " + exit + ": " + String.join(" ", command));
            }
            return new String(output, StandardCharsets.UTF_8);
        } catch (IOException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Command execution failed: " + String.join(" ", command), ex);
        }
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class GestionErroresProcesos {
    public static void main(String[] args) {
        // En Windows, 'type' es un comando interno de la consola CMD,
        // por lo que debemos ejecutarlo usando "cmd.exe", "/c"
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "type", "archivo_inexistente.txt");

        // Opcional: Redirigir la salida de error a un fichero de log en disco
        // pb.redirectError(new File("errores.log"));

        Process proceso = null;
        try {
            System.out.println("Iniciando subproceso en Windows...");
            proceso = pb.start();

            // Capturar la salida estándar de errores (STDERR)
            StringBuilder erroresBuffer = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(proceso.getErrorStream()))) {
                String linea;
                while ((linea = errorReader.readLine()) != null) {
                    erroresBuffer.append(linea).append("\n");
                }
            }

            // Esperar al proceso con un tiempo máximo de seguridad
            boolean finalizado = proceso.waitFor(5, TimeUnit.SECONDS);

            if (!finalizado) {
                System.err.println("TIMEOUT: El proceso superó el tiempo máximo. Forzando cierre...");
                proceso.destroyForcibly();
            } else {
                int exitCode = proceso.exitValue();
                if (exitCode == 0) {
                    System.out.println("El proceso se ejecutó correctamente.");
                } else {
                    System.err.println("ERROR NATIVO (Código " + exitCode + "):");
                    System.err.print(erroresBuffer.toString());
                }
            }

        } catch (IOException e) {
            // Error al lanzar el comando (no se encuentra cmd.exe, sin permisos, etc.)
            System.err.println("EXCEPCIÓN E/S: No se pudo lanzar el subproceso -> " + e.getMessage());
        } catch (InterruptedException e) {
            // El hilo principal de Java fue interrumpido durante la espera
            System.err.println("EXCEPCIÓN INTERRUPCIÓN: El hilo principal fue interrumpido -> " + e.getMessage());
            Thread.currentThread().interrupt(); // Restablecer estado de interrupción
        } catch (IllegalThreadStateException e) {
            // Intento ilegal de consultar el estado de un proceso que no ha terminado
            System.err.println("EXCEPCIÓN ESTADO: Intento de acceso a un proceso en ejecución -> " + e.getMessage());
        } finally {
            // Asegurar limpieza de recursos si el proceso sigue vivo por algún motivo
            if (proceso != null && proceso.isAlive()) {
                proceso.destroyForcibly();
            }
        }
    }
}

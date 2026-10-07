package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DiagosticoRed {

    public static void main(String[] args) {
        String logFileName = "registro_ping.log";
        File logFile = new File(logFileName);

        try{

            escribirCabezeraLog(logFile);

            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("win")){
                pb = new ProcessBuilder("ping", "-n", "4", "127.0.0.1");
            } else {
                pb = new ProcessBuilder("ping", "-c", "4", "127.0.0.1");
            }

            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
            pb.redirectError(ProcessBuilder.Redirect.appendTo(logFile));

            Process proceso = pb.start();

            System.out.println("======== METADATOS DEL PROCESO HIJO ========");
            System.out.println("PID del proceso: " + proceso.pid());
            System.out.println("Actualmente activo? " + proceso.isAlive());

            // Esperar a que el proceso finalice su tarea

            int exitCode = proceso.waitFor();

            System.out.println("\n=== RESULTADO DE LA EJECUCIÓN ===");
            System.out.println("¿Proceso activo tras el waitFor()? " + proceso.isAlive());
            System.out.println("Código de finalización (Exit Code): " + exitCode + (exitCode == 0 ? " (OK)" : "(Error)"));

            System.out.println("Confirmación: La salida se ha registrado correctamente en " + logFile.getAbsolutePath() + " .");
        } catch (IOException e){
            System.out.println("Error de E/S al ejecutar el proceso o escribir el log: " + e.getMessage());
        } catch (InterruptedException e){
            System.out.println("El proceso ha sido interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void escribirCabezeraLog (File file) throws IOException{

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))){
            writer.println("\n =========================== ");
            writer.println("EJECUCIÓN DE DIAGNÓSTICO DE RED: " + timestamp);
            writer.println("\n =========================== ");

        }
    }
}

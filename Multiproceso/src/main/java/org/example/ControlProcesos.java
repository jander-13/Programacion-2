package org.example;

import java.io.IOException;

public class ControlProcesos {

    public static void main(String[] args)  throws IOException, InterruptedException{
        // En Windows se invoca 'timeout' a través de cmd.exe para evitar bloqueos de entrada interactiva
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "timeout /t 10 /nobreak");
        Process p = pb.start();

        // Obtener el identificador de proceso (PID) nativo
        long pid = p.pid();

        // Obtener información extendida del proceso
        ProcessHandle.Info info = p.info();

        System.out.println("PID asignado: " + pid);
        System.out.println("Usuario ejecutor: " + info.user().orElse("Desconocido"));
        System.out.println("Comando: " + info.command().orElse("N/A"));

        System.out.println("Inicio: " + info.startInstant());



        // Pequeña pausa de 2 segundos para comprobar su ejecución
        Thread.sleep(2000);

        // Destrucción limpia o forzada en Windows
        if (p.isAlive()) {
            p.destroy(); // En Windows llama a TerminateProcess (cierre ordenado)
            // p.destroyForcibly(); // Forzado inmediato (no da tiempo a liberar recursos)
            System.out.println("El proceso fue terminado desde Java.");
        }
    }
}


package org.example.ejemplo3;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class InteraccionProceso {
    public static void main(String[] args) throws Exception {
        // Ejemplo: Lanzar el intérprete de Python o un subproceso interactivo
        ProcessBuilder pb = new ProcessBuilder("python", "-u", "-c",
                "import sys; nombre = sys.stdin.readline(); print('Hola ' + nombre)");

        Process p = pb.start();
        // 1. Escribir en la entrada del hijo usando getOutputStream()
        try (PrintWriter writer = new PrintWriter(p.getOutputStream())) {
            writer.println("Carlos"); // Simulamos que el usuario escribe "Carlos" + Enter
            writer.flush(); // Forzamos el envío inmediato
        }
        // 2. Leer la respuesta del hijo
        try (Scanner scanner = new Scanner(p.getInputStream())) {
            if (scanner.hasNextLine()) {
                System.out.println("Respuesta recibida: " + scanner.nextLine());
            }
        }

        p.waitFor();
    }
}

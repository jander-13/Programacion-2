package org.example.ejemplo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class LectorProceso {
    public static void main(String[] args) {
        // Detectar SO para elegir el comando adecuado
        boolean esWindows =
                System.getProperty("os.name").toLowerCase().contains("win");
        List<String> comando = esWindows
                ? List.of("cmd.exe", "/c", "dir")
                : List.of("ls", "-la");
        // 1. Configurar el proceso
        ProcessBuilder pb = new ProcessBuilder(comando);
        try {
            // 2. Iniciar el proceso
            Process proceso = pb.start();
            // 3. Capturar la salida del hijo (getInputStream)
            try (BufferedReader reader = new BufferedReader(new
                    InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = reader.readLine()) != null) {

                    System.out.println("[SALIDA HIJO] " + linea);
                }
            }
            // 4. Esperar a que el proceso termine y obtener código de salida (0 = Éxito)
            int codigoSalida = proceso.waitFor();
            System.out.println("Proceso finalizado con código: " +
                    codigoSalida);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.example.ejemplo2;

import java.io.File;
import java.io.IOException;

public class RedireccionProceso {
    public static void main(String[] args) throws IOException,
            InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("ping", "127.0.0.1");
        // Opción A: Heredar la consola del Padre (se imprime directamente en pantalla)
        // pb.inheritIO();
        // Opción B: Redirigir la salida directamente a un archivo en disco
        File log = new File("salida_ping.txt");
        pb.redirectOutput(log);
        Process p = pb.start();
        p.waitFor();
    }
}

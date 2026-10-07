package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Pipelines1 {

    public static void main(String[] args) {
        File ficheroEntrada = new File("alumnos.txt");
        File ficheroSalida = new File("alumnos_matematicas.txt");

        // 1. Proceso 1: Lee el contenido del fichero con el comando interno 'type' de Windows
        ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "type", ficheroEntrada.getAbsolutePath());

        // 2. Proceso 2: Filtra las líneas que contienen la palabra "Matemáticas"
        ProcessBuilder pb2 = new ProcessBuilder("findstr", "Matematicas");

        // 3. Proceso 3: Ordena alfabéticamente los resultados recibidos
        ProcessBuilder pb3 = new ProcessBuilder("sort");

        pb3.redirectOutput(ProcessBuilder.Redirect.to(ficheroSalida));

        pb1.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb2.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb3.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            System.out.println("Ejecutuando Pipeline");

            List<Process> procesos = ProcessBuilder.startPipeline(Arrays.asList(pb1,pb2,pb3));

            Process ultimoProceso = procesos.get(procesos.size() - 1);
            int codigoSalida = ultimoProceso.waitFor();

            if (codigoSalida == 0) {
                System.out.println("Archivo generado: " + ficheroSalida.getName());
            } else {
                System.err.println("Fallo en el pipeline: " + codigoSalida);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando procesos: " + e.getMessage());
        }



    }
}

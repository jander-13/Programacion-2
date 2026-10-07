package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BuclePipelines {

    public static void main(String[] args) {
        File ficheroEntrada = new File("alumnos.txt");

        Set<String> asignaturas = detectarAsignaturasUnicas(ficheroEntrada);

        System.out.println("Asignaturas encontradas: " + asignaturas);

        for (String asignatura: asignaturas) {
            ejecutarPipelineParaAsignatura(ficheroEntrada, asignatura);
        }
    }


    private static void ejecutarPipelineParaAsignatura(File entrada, String asignatura) {
        File ficheroSalida = new File("alumnos_"+asignatura+".txt");

        // 1. Proceso 1: Lee el contenido del fichero con el comando interno 'type' de Windows
        ProcessBuilder pb1 = new ProcessBuilder("cmd.exe", "/c", "type", entrada.getAbsolutePath());

        // 2. Proceso 2: Filtra las líneas que contienen la asignatura que se le pasa como parámetro
        ProcessBuilder pb2 = new ProcessBuilder("findstr", asignatura);

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

    private static Set<String> detectarAsignaturasUnicas(File fichero) {
        Set<String> asignaturas = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                if (partes.length == 3) {
                    asignaturas.add(partes[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el fichero " + e.getMessage());
        }
        return asignaturas;
    }
}


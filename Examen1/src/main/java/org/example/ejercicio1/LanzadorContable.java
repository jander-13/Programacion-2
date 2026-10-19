package org.example.ejercicio1;

public class LanzadorContable {
    public static void main(String[] args) {
        String[] archivos = {"empresaA.dat", "empresaB_CORRUPTO.dat", "empresaC.dat"};

        for (String archivo : archivos) {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "target/classes", "org.example.ejercicio1.ProcesadorContable", archivo);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            try {
                Process proceso = pb.start();
                int codigoSalida = proceso.waitFor();

                if (codigoSalida == 0) {
                    System.out.println("Archivo " + archivo + " procesado correctamente.");
                } else if (codigoSalida == 2) {
                    System.err.println("Archivo " + archivo + " está corrupto. No se puede procesar.");
                } else {
                    System.err.println("Error al procesar el archivo " + archivo + ". Código de salida: " + codigoSalida);
                }
            } catch (Exception e) {
                System.err.println("Error ejecutando el proceso para el archivo " + archivo + ": " + e.getMessage());
            }
        }

    }
}

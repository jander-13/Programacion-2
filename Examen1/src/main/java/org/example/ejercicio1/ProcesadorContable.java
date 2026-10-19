package org.example.ejercicio1;

public class ProcesadorContable {

    static void main(String[] args) {
        if (args.length < 1){
            System.err.println("Debe proporcionarse el nombre del archivo contable como argumento.");
            System.exit(1);
        }

        String fichero = args[0];
        System.out.println("Procesando archivo: " + fichero);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("El procesamiento fue interrumpido.");
            Thread.currentThread().interrupt();
            System.exit(3);
        }


        if (fichero.contains("CORRUPTO")) {
            System.err.println("Error: El archivo contable está corrupto. No se puede procesar.");
            System.exit(2);
        }

        System.out.println("Archivo procesado correctamente.");
        System.exit(0);

    }

}

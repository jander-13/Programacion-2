package org.example;

import java.io.File;
import java.io.IOException;

public class ejecicio1 {
    static void main(String[] args) {
/*
        // 1. Configurar el comando ping (funciona igual en Windows, Mac y Linux
        ProcessBuilder pb = new ProcessBuilder("ping", "8.8.8.8");
        // Nota: En Windows puedes usar solo "ping", "8.8.8.8"

        //2. Definir el archivo donde se guardará la respuesta
        File archivoSalida = new File("resultado_ping.txt");

        //3. Redirigir la salida del proceso al archivo de texto
        pb.redirectOutput(archivoSalida);

        try{
            System.out.println("Iniciando la prueba de red...");

            //4. Iniciar proceso
            Process proceso = pb.start();

            //5. Esperar a que el ping termine
            int codigoSalida = proceso.waitFor();

            //6. Confirmar el resultado al usuario
            if (codigoSalida == 0){
                System.out.println("¡Proceso completado con exito!");
                System.out.println("Revisa el archivo generado en: " + archivoSalida.getAbsolutePath());
            } else {
                System.out.println("El proceso falló con codigo de error: " + codigoSalida);
            }

        } catch (IOException | InterruptedException e){
            System.out.println("Error al intentar ejecutar el comando.");
            e.printStackTrace();
        } */

        int resultado = hacerPingYGuardar("8.8.8.8", "resultado_ping.txt");

        switch (resultado){
            case 0 -> System.out.println("Éxito!!");
            case 1 -> System.out.println("Error: No se puede contactar con la IP");
            case -1 -> System.out.println("Error del sistema al ejecutar el proceso");
        }
    }

    public static int hacerPingYGuardar (String ip, String nombreArchivo){
        ProcessBuilder pb = new ProcessBuilder("ping", ip);
        pb.redirectOutput(new File(nombreArchivo));

        try{
            Process proceso = pb.start();
            int codigoSalida = proceso.waitFor();

            return (codigoSalida == 0) ? 0 : 1;
        } catch (IOException | InterruptedException e){
            return -1;
        }
    }
}

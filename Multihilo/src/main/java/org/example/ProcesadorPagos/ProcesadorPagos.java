package org.example.ProcesadorPagos;

import java.util.Random;

public class ProcesadorPagos {

    // Variable con almacenamiento exclusivo para CADA hilo
    private static ThreadLocal<String> sesionUsuario = new ThreadLocal<>();

    public static void main(String[] args) {

        // Manejador global de excepciones para hilos en java.lang
        Thread.setDefaultUncaughtExceptionHandler((hilo, excepcion) ->
                System.err.println("Capturado en " + hilo.getName() + ": " + excepcion.getMessage())
        );

        Runnable tareaPago = () -> {
            String nombreHilo = Thread.currentThread().getName();

            // Cada hilo guarda su propia información aislada
            sesionUsuario.set("CLIENTE-ID-" + nombreHilo.toUpperCase());
            System.out.println("[INICIO] " + nombreHilo + " procesando pago para " + sesionUsuario.get());

            try {
                int tiempoProcesamiento = 1000 + new Random().nextInt(9000);
                Thread.sleep(tiempoProcesamiento);
            } catch (InterruptedException e) {
                System.err.println(nombreHilo + " fue interrumpido.");
            }

            System.out.println("[FIN] " + nombreHilo + " completó con éxito el pago de " + sesionUsuario.get());
        };

        long tiempoInicio = System.currentTimeMillis();

        Thread[] hilos = new Thread[5];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(tareaPago, "Hilo-"+(i+1));
            hilos[i].start();
        }

        for (Thread h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long tiempoFin = System.currentTimeMillis();
        System.out.println("\n --> Tiempo total en paralelo: " + (tiempoFin + tiempoInicio) + " ms.");


    }
}

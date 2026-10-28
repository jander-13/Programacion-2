package org.example.DemoProductorConsumidor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DemoProductorConsumidor {

    public static void main(String[] args) {

        // Cola bloqueante con capacidad MÁXIMA de 2 elementos
        BlockingQueue<String> colaBuffer = new ArrayBlockingQueue<>(2);

        // Hilo Productor (Rápido)
        Thread productor = new Thread(() -> {
            try {
                String[] productos = {"Pan", "Leche", "Huevos", "Queso"};
                for (String p : productos) {
                    System.out.println("Productor intentando poner: " + p);
                    colaBuffer.put(p); // Se BLOQUEA si la cola tiene 2 elementos
                    System.out.println("Productor puso en la cola: " + p);
                    System.out.println("Estado de la cola: "+obtenerEstadoCola(colaBuffer));
                    Thread.sleep(300); // Tarda solo 0.3 segundos
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Hilo Consumidor (Lento)
        Thread consumidor = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    Thread.sleep(1500); // Tarda 1.5 segundos en procesar cada elemento
                    String elemento = colaBuffer.take(); // Se BLOQUEA si la cola está vacía
                    System.out.println("Consumidor procesó y quitó: " + elemento);
                    System.out.println("Estado de la cola: "+obtenerEstadoCola(colaBuffer));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();
        consumidor.start();
    }

    // Método auxiliar para obtener una representación en String del estado actual de la cola
    private static String obtenerEstadoCola(BlockingQueue<String> cola) {
        return "Cola " + cola.toString() + " [Tamaño: " + cola.size() + "/" + (cola.size() + cola.remainingCapacity()) + "]";
    }
}

package org.example.Procesamiento_de_pedidos;

import java.util.concurrent.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ProcesadorPedidosApp {

    private static final int CAPACIDAD_COLA = 5;
    private static final int TOTAL_PEDIDOS = 50;
    private static final int NUM_TRABAJADORES = 5;

 private static final Pedido PEDIDO_FIN = new Pedido(-1,0);

    static void main(String[] args) {

        // Cola bloqueante compartida
        BlockingQueue<Pedido> colaPedidos = new ArrayBlockingQueue<>(CAPACIDAD_COLA);

        System.out.println("Iniciando sistema de procesamiento de pedidos");

        long tiempoInicio = System.currentTimeMillis();

        // Crear un pool de 5 hilos consumidores
        ExecutorService trabajadores = Executors.newFixedThreadPool(NUM_TRABAJADORES);

        // Lanzamos del 5 hilos trabajadores
        for (int i = 0; i <= NUM_TRABAJADORES; i++) {
            trabajadores.execute(() -> {
                String nombreHilo = Thread.currentThread().getName();
                try {
                    while (true) {
                        Pedido pedido = colaPedidos.take();

                        if (pedido == PEDIDO_FIN) {
                            break;
                        }

                        System.out.println("[" + nombreHilo + "] Procesando pedido $" + pedido.getId() + " | En la cola quedan: " + colaPedidos.size());

                        TimeUnit.MILLISECONDS.sleep(10000);

                        System.out.println("[" + nombreHilo + "] Finalizado pedido $" + pedido.getId());
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread productor = new Thread(() -> {
            try {
                for (int i = 1; i <= TOTAL_PEDIDOS; i++) {
                    Pedido nuevoPedido = new Pedido(i, 10 + (i * 2.5));

                    colaPedidos.put(nuevoPedido);
                    System.out.println("[PRODUCTOR] Encolado pedido $" + nuevoPedido.getId() + " | Tamaño de la cola: " + colaPedidos.size() + "/" + CAPACIDAD_COLA);

                    TimeUnit.MILLISECONDS.sleep(10);
                }

                for (int i = 1; i < NUM_TRABAJADORES; i++) {
                    colaPedidos.put(PEDIDO_FIN);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        productor.start();

        try{
            productor.join();
            trabajadores.shutdown();
            trabajadores.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        long tiempoFin = System.currentTimeMillis();

        System.out.println("\n----------------------------");
        System.out.println("Todos los hilos han terminado");
        System.out.println("Tiempo transcurrido: " + (tiempoFin - tiempoInicio) + " ms");

    }
}

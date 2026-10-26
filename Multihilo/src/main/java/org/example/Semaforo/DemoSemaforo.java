package org.example.Semaforo;

import java.util.concurrent.Semaphore;

public class DemoSemaforo {
    public static void main(String[] args) {

        // Semaforo con solo 2 permisos (2 plazas de aparcamiento)
        Semaphore parking = new Semaphore(2);

        for (int i = 1; i <= 4; i++){
            final int idCoche = i;
            new Thread(() -> {
                try {
                    System.out.println("Coche " + idCoche + " intentando entrar al parking...");

                    parking.acquire();  // Solicita un permiso (Se BLOQUEA si no hay plazas)
                    System.out.println("Coche " + idCoche + " ha APARCADO.");

                    Thread.sleep(2000); // Permanece aparcado 2 segundos

                    System.out.println("Coche " + idCoche + " sale del parking.");

                    parking.release(); // Devuelve el permiso liberando una plaza
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}

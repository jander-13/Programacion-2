package org.example.Estacion_de_inspeccion;

import java.util.concurrent.Semaphore;

public class EstacionITVApp {

    // TODO: Crear semáforo para la revisión mecánica (2 plazas)
    // TODO: Crear semáforo para la prueba de gases (1 plaza)

    public static void main(String[] args) {
        int numVehiculos = 6;

        Semaphore rev_mecanica = new Semaphore(2);
        Semaphore prueba_gases = new Semaphore(1);

        System.out.println("==================================================");
        System.out.println(">>> Apertura de la Estación ITV (6 vehículos)...");
        System.out.println("==================================================\n");

        for (int i = 1; i <= numVehiculos; i++) {
            final int idVehiculo = i;
            new Thread(() -> {
                try {
                    System.out.println("Vehículo #" + idVehiculo + " llega a la ITV.");

                    rev_mecanica.acquire();
                    System.out.println("Vehiculo #" + idVehiculo + " solicita revision mecanica.");

                    Thread.sleep(1500);

                    System.out.println("Vehiculo #" + idVehiculo + " finaliza revision mecanica.");
                    rev_mecanica.release();

                    prueba_gases.acquire();
                    System.out.println("Vehiculo #" + idVehiculo + " solicita prueba de gases.");

                    Thread.sleep(1000);

                    System.out.println("Vehiculo #" + idVehiculo + " finaliza prueba de gases.");
                    prueba_gases.release();

                    // -----------------------------------------------------------------
                    // TAREA DEL ALUMNO:
                    // 1. Pedir permiso para Fase 1 (Mecánica).
                    // 2. Simular tiempo de revisión (Thread.sleep(1500)).
                    // 3. Liberar recurso de Fase 1.
                    // 4. Pedir permiso para Fase 2 (Gases).
                    // 5. Simular tiempo de prueba (Thread.sleep(1000)).
                    // 6. Liberar recurso de Fase 2.
                    // -----------------------------------------------------------------

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}


package org.example;

import java.io.IOException;
import java.util.Scanner;

public class ProcesosBasica {
    static void main() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Selecciona una opción: ");

        if (sc.hasNextInt()){
            option = sc.nextInt();
            System.out.println("---------------------------");

            switch (option){
                case 1:
                    crearNuevoProceso();
                    break;
                case 2:
                    listarProcesos();
                    break;
                case 3:
                    matarProcesoPorPid(sc);
                    break;
                case 4:
                    System.out.println("Saliendo del programa... Limpiando procesos activos");
                    limpiarTodoLosProcesos();
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");

            }
        } else {
            System.out.println("Por favor, introduce un número entero.");
            sc.next();
        }




    }

    private static void crearNuevoProceso(){
        ProcessBuilder pb = new ProcessBuilder("ping", "127.0.0.1", "-n", "100");

        try{
            Process nuevoProceso = pb.start();
            listaProcesos.add(nuevoProceso);

            System.out.println("¡Proceso creado con éxito!");
            System.out.println("PID asignado por Windows: " + e.getMessage());
            System.out.println("Estado inicial: " + (nuevoProceso.isAlive() ? "VIVO : TERMINADO"));
        } catch (IOException e) {
            System.out.println("Error al intentar crear el proceso: ");
        }
    }

    private static void matarProcesoPorPid(Scanner sc){
        if (listarProcesos.isEmpty()){
            System.out.println("No hay procesos en la lista.");
            return;
        }

        System.out.println("Introduce el PID del proceso al finalizar: ");
        long pidBuscado = sc.nextLong();
        boolean encontrado = false;

        for (Process p : listaProcesos){
            if (p.pid() == pidBuscado){
                encontrado = true;

                if (p.isAlive()) {
                    p.destroy();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }

                    if (!p.isAlive()) {
                        System.out.println("El proceso con PID " + pidBuscado + " ha sido finalizado.");
                    } else {
                        p.destroyForcibly();
                        System.out.println("El proceso con PID " + pidBuscado + " ha sido forzado a cerrar.");
                    }
                } else {
                    System.out.println("El proceso con PID " + pidBuscado + " ya estaba TERMINADO.");
                }
                break;
            }
        }
    }
}

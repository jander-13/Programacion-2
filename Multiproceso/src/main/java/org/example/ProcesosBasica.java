package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcesosBasica {

    private static List<Process> listaProcesos = new ArrayList<>();


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            mostrarMenu();
            System.out.print("Selecciona una opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                System.out.println("----------------------------------------");

                switch (opcion) {
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
                        System.out.println("Saliendo del programa... Limpiando procesos activos.");
                        limpiarTodosLosProcesos();
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } else {
                System.out.println("Por favor, introduce un número entero.");
                sc.next(); // Limpiar la entrada incorrecta
            }
            System.out.println("----------------------------------------\n");

        } while (opcion != 4);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== GESTOR DE PROCESOS (WINDOWS) ===");
        System.out.println("1. Crear un nuevo proceso en segundo plano");
        System.out.println("2. Listar procesos y ver cuántos están vivos");
        System.out.println("3. Matar un proceso por su PID");
        System.out.println("4. Salir");
    }

    // 1. CREAR PROCESO
    private static void crearNuevoProceso() {
        ProcessBuilder pb = new ProcessBuilder("ping", "127.0.0.1", "-n", "100");

        try {
            Process nuevoProceso = pb.start();
            listaProcesos.add(nuevoProceso);

            System.out.println("¡Proceso creado con éxito!");
            System.out.println("PID asignado por Windows: " + nuevoProceso.pid());
            System.out.println("Estado inicial: " + (nuevoProceso.isAlive() ? "VIVO" : "TERMINADO"));

        } catch (IOException e) {
            System.err.println("Error al intentar crear el proceso: " + e.getMessage());
        }
    }

    // 2. LISTAR Y CONTAR PROCESOS VIVOS
    private static void listarProcesos() {
        if (listaProcesos.isEmpty()) {
            System.out.println("No se ha creado ningún proceso todavía.");
            return;
        }

        int contadorVivos = 0;
        System.out.println("--- LISTA DE PROCESOS REGISTRADOS ---");

        for (int i = 0; i < listaProcesos.size(); i++) {
            Process p = listaProcesos.get(i);
            boolean vivo = p.isAlive();

            if (vivo) {
                contadorVivos++;
            }

            System.out.println("[" + (i + 1) + "] PID: " + p.pid() + " | Estado: " + (vivo ? "VIVO" : "TERMINADO"));
        }

        System.out.println("\nProcesos vivos actualmente: " + contadorVivos + " de " + listaProcesos.size());
    }

    // 3. MATAR PROCESO POR SU PID
    private static void matarProcesoPorPid(Scanner sc) {
        if (listaProcesos.isEmpty()) {
            System.out.println("No hay procesos en la lista.");
            return;
        }

        System.out.print("Introduce el PID del proceso a finalizar: ");
        long pidBuscado = sc.nextLong();
        boolean encontrado = false;

        for (Process p : listaProcesos) {
            if (p.pid() == pidBuscado) {
                encontrado = true;

                if (p.isAlive()) {
                    p.destroy(); // Envía la señal de finalización

                    try {
                        Thread.sleep(100); // Pausa breve para que Windows procese el cierre
                    } catch (InterruptedException e) {}

                    if (!p.isAlive()) {
                        System.out.println("El proceso con PID " + pidBuscado + " ha sido finalizado.");
                    } else {
                        p.destroyForcibly(); // Cierre forzado si no responde
                        System.out.println("El proceso con PID " + pidBuscado + " ha sido forzado a cerrar.");
                    }
                } else {
                    System.out.println("El proceso con PID " + pidBuscado + " ya estaba TERMINADO.");
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún proceso con el PID " + pidBuscado + ".");
        }
    }

    // 4. LIMPIEZA AL SALIR
    private static void limpiarTodosLosProcesos() {
        for (Process p : listaProcesos) {
            if (p.isAlive()) {
                p.destroy();
            }
        }
    }
}

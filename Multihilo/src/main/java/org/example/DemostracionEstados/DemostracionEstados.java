package org.example.DemostracionEstados;

public class DemostracionEstados {

    public static void main(String[] args) throws InterruptedException {

        // Objeto que usaremos como cerrojo para forzar el estado BLOCKED
        Object cerrojo = new Object();

        // Hilo secundario
        Thread hilo = new Thread(() -> {
            try {
                // Forzamos el estado TIMED_WAITING (espera con tiempo determinado)
                Thread.sleep(1000);

                // Intentamos entrar en un bloque sincronizado
                synchronized (cerrojo) {
                    // Si el hilo principal tiene el cerrojo, nos quedaremos en BLOCKED
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 1. Estado NEW
        System.out.println("1. Estado inicial (instanciado): " + hilo.getState());

        // 2. Estado RUNNABLE
        hilo.start();
        System.out.println("2. Estado tras start(): " + hilo.getState());

        // Pausa breve del main para dar tiempo a que el hilo entre en sleep()
        Thread.sleep(300);

        // 3. Estado TIMED_WAITING
        System.out.println("3. Estado mientras duerme (sleep): " + hilo.getState());

        // El hilo principal bloquea el cerrojo para interceptar al hilo secundario
        synchronized (cerrojo) {
            // Esperamos a que pase el sleep del hilo secundario (1000ms - 300ms = ~700ms)
            Thread.sleep(800);

            // 4. Estado BLOCKED
            System.out.println("4. Estado intentando acceder a un recurso bloqueado: " + hilo.getState());
        } // Liberamos el cerrojo

        // Esperamos a que el hilo secundario termine completamente
        hilo.join();

        // 5. Estado TERMINATED
        System.out.println("5. Estado tras finalizar su ejecución: " + hilo.getState());
    }

}

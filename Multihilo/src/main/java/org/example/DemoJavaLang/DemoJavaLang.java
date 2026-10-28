package org.example.DemoJavaLang;

public class DemoJavaLang {

    // Variable con almacenamiento exclusivo para CADA hilo
    private static ThreadLocal<String> sesionUsuario = new ThreadLocal<>();

    public static void main(String[] args) {

        // Manejador global de excepciones para hilos en java.lang
        Thread.setDefaultUncaughtExceptionHandler((hilo, excepcion) ->
                System.err.println("Capturado en " + hilo.getName() + ": " + excepcion.getMessage())
        );

        Runnable tarea = () -> {
            String nombreHilo = Thread.currentThread().getName();

            // Cada hilo guarda su propia información aislada
            sesionUsuario.set("ID-SESION-" + nombreHilo.toUpperCase());
            System.out.println(nombreHilo + " trabajando con " + sesionUsuario.get());

            // Simulación de un error en un hilo
            if (nombreHilo.equals("Hilo-2")) {
                throw new RuntimeException("¡Fallo de conexión en la tarea!");
            }
        };

        Thread h1 = new Thread(tarea, "Hilo-1");
        Thread h2 = new Thread(tarea, "Hilo-2");

        h1.start();
        h2.start();
    }
}

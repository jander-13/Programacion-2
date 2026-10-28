package org.example.InterfazRunnable;

public class InterfazRunnable {
    public static void main(String[] args) {
        System.out.println("Probando la Interfaz Runnable");

        TareaImpresion tarea1 = new TareaImpresion("Factura.pdf");
        TareaImpresion tarea2 = new TareaImpresion("Foto.png");

        Thread h1 = new Thread(tarea1,"Hilo-Impresora-1");
        Thread h2 = new Thread(tarea2,"Hilo-Impresora-2");

        h1.start();
        h2.start();

        Thread hiloAnonimo = new Thread(() -> {
            System.out.println("Estoy dentro de un hilo creado con expresiones Lambda!");
        });

        hiloAnonimo.start();
    }
}

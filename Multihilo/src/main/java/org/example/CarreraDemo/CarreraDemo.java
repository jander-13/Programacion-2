package org.example.CarreraDemo;

public class CarreraDemo {

    public static void main(String[] args) {

        // 2. Creamos las instancias de Runnable
        Corredor c1 = new Corredor("Tortuga ");
        Corredor c2 = new Corredor("Liebre ");
        Corredor c3 = new Corredor("Gato ");

        // 3. Pasamos cada Runnable a un objeto Thread
        Thread hilo1 = new Thread(c1);
        Thread hilo2 = new Thread(c2);
        Thread hilo3 = new Thread(c3);

        System.out.println("--- ¡COMIENZA LA CARRERA! ---");

        // 4. Iniciamos los hilos con .start() (¡NO con .run()!)
        hilo1.start();
        hilo2.start();
        hilo3.start();
    }
}

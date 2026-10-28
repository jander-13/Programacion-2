package org.example.CarreraDemo;

import java.util.Random;

public class Corredor  implements  Runnable {

    private String nombre;
    private Random random = new Random();

    public Corredor(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int paso = 1; paso <= 5; paso++) {
            System.out.println(nombre + " va por el paso " + paso);

            try {
                // Simulamos un tiempo de esfuerzo aleatorio entre 200 y 700 ms
                Thread.sleep(random.nextInt(500) + 200);
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido.");
            }
        }
        System.out.println("¡" + nombre + " HA LLEGADO A LA META!");
    }
}

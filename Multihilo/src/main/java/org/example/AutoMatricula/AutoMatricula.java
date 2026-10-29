package org.example.AutoMatricula;

import java.util.concurrent.ThreadLocalRandom;

public class AutoMatricula {

    static void main() {
        String[] dnis = {
                "12345678Z",
                "87654321X",
                "45678901G",
                "23456789D",
                "34567890V",
                "56789012B",
                "67890123N",
                "78901234H"
        };

        System.out.println("INICIO DEL PROCESO DE MATRICULA");
        for (int i = 0; dnis.length > i; i++){
            Thread hilo = new Thread(new TareaAlumno(dnis[i]), "Alumno" + i + " - " + dnis[i]);
            hilo.start();

            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}

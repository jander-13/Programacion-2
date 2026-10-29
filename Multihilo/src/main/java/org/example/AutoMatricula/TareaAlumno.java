package org.example.AutoMatricula;

import java.util.concurrent.ThreadLocalRandom;

public class TareaAlumno  implements Runnable{

    private final String dni;
    private final int MAX_INTENTOS = 3;
    private final int TIEMPO_BASE_ESPERA_MS = 1000;


    public TareaAlumno(String dni){
        this.dni = dni;
    }



    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();

        ServidorMatriculacion servidor = ServidorMatriculacion.getInstancia();

        try{
            System.out.println("[" + nombreHilo + "] Conectando al servicio de matricula");

            // 1. Entrar en la sala de espera

//            if (!servidor.entrarEnSalaEspera(this.dni)){
//                System.out.println("[" + nombreHilo + "] Servidor saturado" );
//                return;
//            }

            boolean dentroCola = false;
            int tiempoEspera = TIEMPO_BASE_ESPERA_MS;

            for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
                dentroCola = servidor.entrarEnSalaEspera(dni);

                if (dentroCola){
                    System.out.println("[" + nombreHilo + "] Ha accedido a la sala de espera" );
                    break;
                }

                if (intento < MAX_INTENTOS){
                    int jitter = ThreadLocalRandom.current().nextInt(0, 300);
                    int tiempoTotalEspera = tiempoEspera + jitter;

                    System.out.println("[" + nombreHilo + "] Servidor saturado (Intento " + intento + "/" + MAX_INTENTOS + "). Reintentando en " + tiempoTotalEspera + "ms");

                    Thread.sleep(tiempoTotalEspera);

                    // Duplicar el tiempo de espera
                    tiempoEspera *= 2;
                }
            }

            if (!dentroCola){
                System.out.println("[" + nombreHilo + "] Desistiendo.");
                return;
            }

            System.out.println("[" + nombreHilo + "] Ha accedido a la sala de espera");

            System.out.println("[" + nombreHilo + "] Ha accedido a la sala de espera" );

            // 2. validar expediente

            System.out.println("[" + nombreHilo + "] Validando expediente" );
            servidor.validarExpediente(this.dni);

            // 3. intentarReservarPlaza
            System.out.println("[" + nombreHilo + "] Solicitando plaza" );
            boolean reservado = servidor.intentarReservarPlaza();

            if (reservado){
                System.out.println("[" + nombreHilo + "] Ha conseguido plaza. Plazas restantes: " + servidor.getPlazasDisponibles() );
            } else {
                System.out.println("[" + nombreHilo + "] No hay plazas disponibles");
            }



        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("[" + nombreHilo + "] Proceso interrumpido");
        }
    }
}

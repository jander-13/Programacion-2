package org.example.AutoMatricula;

import java.util.concurrent.*;

public class ServidorMatriculacion {

    private static final ServidorMatriculacion INSTANCIA = new ServidorMatriculacion();

    // Recursos concurrentes encapsulados dentro del servicio
    private final BlockingQueue salaEspera = new ArrayBlockingQueue(4);
    private final Semaphore servidorValidacion = new Semaphore(2);
    private final Semaphore mutexPlazas = new Semaphore(1);

    private int plazasDisponibles = 3;

    private ServidorMatriculacion() {};

    public static ServidorMatriculacion getInstancia() {
        return INSTANCIA;
    }
    // Paso 1 --> Paso por la cola de espera
    public boolean entrarEnSalaEspera(String dni) throws InterruptedException{
        return salaEspera.offer(dni, 1500, TimeUnit.MILLISECONDS);
    }

    public void validarExpediente(String dni){
        servidorValidacion.acquire();
        try{
            salaEspera.remove(dni);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
        }finally {
            servidorValidacion.release();
        }
    }


    public boolean intentarReservarPlaza(){
        mutexPlazas.acquire();
        try {
            if (plazasDisponibles > 0) {
                plazasDisponibles--;
                return true;
            }
            return false;
        } finally {
            mutexPlazas.release();
        }
    }


    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }
}

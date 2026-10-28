package org.example.AutoMatricula;

public class TareaAlumno  implements Runnable{

    private final String dni;

    public TareaAlumno(String dni){
        this.dni = dni;
    }

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();

        try{
            System.out.println("[" + nombreHilo + "] Conectando al servicio de matricula");

            // 1. Entrar en la sala de espera

            if (!servidor.entrarEnSalaEspera(this.dni)){
                System.out.println("[" + nombreHilo + "] Servidor saturado" );
                return;
            }

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
                System.out.println("[" + nombreHilo + "] No hay plazas disponibles"
            }



        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("[" + nombreHilo + "] Proceso interrumpido");
        }
    }
}

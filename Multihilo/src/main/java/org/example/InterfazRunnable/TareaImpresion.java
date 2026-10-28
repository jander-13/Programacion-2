package org.example.InterfazRunnable;

public class TareaImpresion implements Runnable {

    private String documento;

    public TareaImpresion(String documento) {
        this.documento = documento;
    }

    @Override
    public void run() {
        System.out.println("Imprimiendo: " + documento + " en el hilo " + Thread.currentThread().getName());
    }
}

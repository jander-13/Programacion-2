package org.example.Procesamiento_de_pedidos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Pedido {
    private final int id;
    private final double importe;
    private boolean procesado;

    public Pedido(int id, double importe) {
        this.id = id;
        this.importe = importe;
        this.procesado = false;
    }

    public int getId() { return id; }
    public double getImporte() { return importe; }
    public boolean isProcesado() { return procesado; }
    public void setProcesado(boolean procesado) { this.procesado = procesado; }
}



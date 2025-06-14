package com.curso.state;



public interface PedidoState {

    void sucessoAoPagar();
    void enviaPedido();
    void cancelarPedido();
    String getState();
}

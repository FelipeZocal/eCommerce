package com.curso.state;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class CanceladoState implements PedidoState{

    private Pedido pedido;

    public CanceladoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getState() {
        return "Pedido cancelado";
    }

    @Override
    public void sucessoAoPagar() {
        throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que foi cancelado.");
    }

    @Override
    public void cancelarPedido() {
        throw new OperacaoNaoPermitidaException("Este pedido já foi cancelado.");
    }

    @Override
    public void enviaPedido() {
        throw new OperacaoNaoPermitidaException("Não é possível enviar um pedido que foi cancelado.");
    }
}

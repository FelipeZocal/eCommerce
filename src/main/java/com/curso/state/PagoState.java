package com.curso.state;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class PagoState implements PedidoState {

    private Pedido pedido;

    public PagoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getState() {
        return "Pedido pago";
    }

    @Override
    public void sucessoAoPagar() {
        throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que já está pago.");
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setState(new CanceladoState(pedido));
    }

    @Override
    public void enviaPedido() {
        this.pedido.setState(new EnviadoState(pedido));
    }
}


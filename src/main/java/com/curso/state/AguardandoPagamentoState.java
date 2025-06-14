package com.curso.state;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class AguardandoPagamentoState implements PedidoState {

    private Pedido pedido;

    public AguardandoPagamentoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getState() {
        return "Aguardando pagamento";
    }

    @Override
    public void sucessoAoPagar() {
        this.pedido.setState(new PagoState(pedido));
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setState(new CanceladoState(pedido));
    }

    @Override
    public void enviaPedido() {
        throw new OperacaoNaoPermitidaException("Não é possível enviar um pedido que não foi pago.");
    }
}

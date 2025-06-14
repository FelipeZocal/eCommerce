package com.curso.state;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class EnviadoState implements PedidoState{

        private Pedido pedido;

        public EnviadoState(Pedido pedido) {
            this.pedido = pedido;
        }

        @Override
        public String getState() {
            return "Pedido enviado";
        }

        @Override
        public void sucessoAoPagar() {
            throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que já foi enviado.");
        }

        @Override
        public void cancelarPedido() {
            throw new OperacaoNaoPermitidaException("Não é possível cancelar um pedido que já foi enviado.");
        }

        @Override
        public void enviaPedido() {
            throw new OperacaoNaoPermitidaException("O pedido já foi enviado.");
        }
    }

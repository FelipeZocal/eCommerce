package com.curso.services;

import com.curso.domains.dtos.PedidoDTO;
import com.curso.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class DBService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private PedidoService pedidoService;

    public void initDB() {
        PedidoDTO pedido1 = new PedidoDTO();
        pedido1.setCliente("Cliente1");
        pedido1.setValorPedido(new BigDecimal("3200.00"));
        pedido1.setModoFrete("Aereo");
        pedido1.setStateAtual("Aguardando pagamento");

        PedidoDTO pedido2 = new PedidoDTO();
        pedido2.setCliente("Cliente2");
        pedido2.setValorPedido(new BigDecimal("2500.00"));
        pedido2.setModoFrete("Terrestre");
        pedido2.setStateAtual("Aguardando pagamento");

        pedidoService.create(pedido1);
        pedidoService.create(pedido2);
    }
}
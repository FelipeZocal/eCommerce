package com.curso.services;

import com.curso.domains.Pedido;
import com.curso.domains.dtos.PedidoDTO;
import com.curso.freteStrategy.FreteAereo;
import com.curso.freteStrategy.FreteStrategy;
import com.curso.freteStrategy.FreteTerrestre;
import com.curso.repositories.PedidoRepository;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    public List<PedidoDTO> findAll() {
        return pedidoRepo.findAll().stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    public Pedido findById(UUID idPedido) {
        return pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não identificado! ID: " + idPedido));
    }

    public Pedido create(PedidoDTO dto) {
        dto.setIdPedido(null);
        return montaEAtualizaPedido(dto);
    }

    public Pedido update(UUID idPedido, PedidoDTO dto) {
        Pedido pedidoExistente = findById(idPedido);

        pedidoExistente.setCliente(dto.getCliente());
        pedidoExistente.setModoFrete(dto.getModoFrete());
        pedidoExistente.setValorTotal(dto.getValorTotal());
        pedidoExistente.setStateAtual(dto.getStateAtual());

        pedidoExistente.setFrete(getFreteStrategy(dto.getModoFrete()));
        BigDecimal valorFrete = calculaFrete(pedidoExistente);
        pedidoExistente.setValorFrete(valorFrete);
        pedidoExistente.setValorTotal(dto.getValorFrete().add(valorFrete));

        return pedidoRepo.save(pedidoExistente);
    }

    public void delete(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedidoRepo.delete(pedido);
    }

    public Pedido pagarPedido(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.sucessoAoPagar();
        pedido.setStateAtual(pedido.getState().getState());
        return pedidoRepo.save(pedido);
    }

    public Pedido cancelaPedido(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.cancelarPedido();
        pedido.setStateAtual(pedido.getState().getState());
        return pedidoRepo.save(pedido);
    }

    public Pedido enviaPedido(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.enviaPedido();
        pedido.setStateAtual(pedido.getState().getState());
        return pedidoRepo.save(pedido);
    }

    public FreteStrategy getFreteStrategy(String tipo) {
        return switch (tipo) {
            case "Aereo" -> new FreteAereo();
            case "Terrestre" -> new FreteTerrestre();
            default -> throw new IllegalArgumentException("Tipo de frete não existente " + tipo);
        };
    }

    public BigDecimal calculaFrete(Pedido pedido) {
        return pedido.getFrete().calcular(pedido.getValorPedido());
    }

    private Pedido montaEAtualizaPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setCliente(dto.getCliente());
        pedido.setModoFrete(dto.getModoFrete());
        pedido.setValorPedido(dto.getValorPedido());
        pedido.setStateAtual(dto.getStateAtual());

        FreteStrategy frete = getFreteStrategy(dto.getModoFrete());
        pedido.setFrete(frete);

        BigDecimal freteCalculado = calculaFrete(pedido);
        pedido.setValorFrete(freteCalculado);
        pedido.setValorTotal(dto.getValorPedido().add(freteCalculado));

        Long ultNumero = pedidoRepo.findMaxNumPedido();
        if (ultNumero == null) {
            ultNumero = 0L;
        }
        pedido.setNumPedido(ultNumero + 1);

        return pedidoRepo.save(pedido);

    }

}

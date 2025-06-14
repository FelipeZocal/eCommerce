package com.curso.domains.dtos;

import com.curso.domains.Pedido;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class PedidoDTO {

    private UUID idPedido;

    private Long numPedido;

    @NotBlank(message = "O campo cliente não pode estar vazio")
    private String cliente;

    @NotNull(message = "O campo valorPedido não pode ser nulo")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal valorPedido;

    private BigDecimal valorFrete;

    private BigDecimal valorTotal;

    @NotNull(message = "O campo modoFrete não pode ser nulo")
    private String modoFrete;

    @NotNull(message = "O campo stateAtual não pode ser nulo")
    private String stateAtual;

    public PedidoDTO() { }

    public PedidoDTO(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.numPedido = pedido.getNumPedido();
        this.cliente = pedido.getCliente();
        this.valorPedido = pedido.getValorPedido();
        this.valorFrete = pedido.getValorFrete();
        this.valorTotal = pedido.getValorTotal();
        this.modoFrete = pedido.getModoFrete();
        this.stateAtual = pedido.getStateAtual();
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public Long getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Long numPedido) {
        this.numPedido = numPedido;
    }

    public @NotBlank(message = "O campo cliente não pode estar vazio") String getCliente() {
        return cliente;
    }

    public void setCliente(@NotBlank(message = "O campo cliente não pode estar vazio") String cliente) {
        this.cliente = cliente;
    }

    public @NotNull(message = "O campo valorPedido não pode ser nulo") @Digits(integer = 10, fraction = 2) BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(@NotNull(message = "O campo valorPedido não pode ser nulo") @Digits(integer = 10, fraction = 2) BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public @NotNull(message = "O campo modoFrete não pode ser nulo") String getModoFrete() {
        return modoFrete;
    }

    public void setModoFrete(@NotNull(message = "O campo modoFrete não pode ser nulo") String modoFrete) {
        this.modoFrete = modoFrete;
    }

    public @NotNull(message = "O campo stateAtual não pode ser nulo") String getStateAtual() {
        return stateAtual;
    }

    public void setStateAtual(@NotNull(message = "O campo stateAtual não pode ser nulo") String stateAtual) {
        this.stateAtual = stateAtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoDTO pedidoDTO = (PedidoDTO) o;
        return Objects.equals(idPedido, pedidoDTO.idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPedido);
    }
}

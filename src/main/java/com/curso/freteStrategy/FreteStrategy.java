package com.curso.freteStrategy;

import java.math.BigDecimal;

public interface FreteStrategy {


    public BigDecimal calcular(BigDecimal valorPedido);
    String getFrete();
}

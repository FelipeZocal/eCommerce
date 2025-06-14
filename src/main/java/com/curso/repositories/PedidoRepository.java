package com.curso.repositories;

import com.curso.domains.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>{

        @Query("SELECT MAX(p.numPedido) FROM Pedido p")
        Long findMaxNumPedido();

    }


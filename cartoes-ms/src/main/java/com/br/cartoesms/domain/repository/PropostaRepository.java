package com.br.cartoesms.domain.repository;

import com.br.cartoesms.domain.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    List<Proposta> findByCliente_Email(String email);
}

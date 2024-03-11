package com.br.emissorms.domain.repository;

import com.br.emissorms.domain.model.Proposta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends MongoRepository<Proposta, String> {
}

package com.br.emissorms.domain.repository;

import com.br.emissorms.domain.model.EmissaoCartao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmissaoCartaoRepository extends MongoRepository<EmissaoCartao, String> {
}

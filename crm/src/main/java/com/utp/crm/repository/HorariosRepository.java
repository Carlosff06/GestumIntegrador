package com.utp.crm.repository;

import com.utp.crm.model.Horarios;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HorariosRepository extends ReactiveMongoRepository<Horarios, String> {

}

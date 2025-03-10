package com.agregio.adaptersoutput;

import com.agregio.adaptersoutput.entities.ParkEntity;
import com.agregio.adaptersoutput.repositories.JdbcParkRepository;
import com.agregio.domain.coredomain.Park;
import com.agregio.domain.repository.ParkRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ComponentScan
public class ParkEntityRepository implements ParkRepository {

    private final JdbcParkRepository jdbcParkRepository;

    public ParkEntityRepository(JdbcParkRepository jdbcParkRepository) {
        this.jdbcParkRepository = jdbcParkRepository;
    }

    @Override
    public Long createPark(Park park) {
        ParkEntity parkEntity = new ParkEntity(park);
        return jdbcParkRepository.save(parkEntity).toPark().id();
    }

    public Optional<Park> findById(Long id) {
        return jdbcParkRepository.findById(id)
                .map(ParkEntity::toPark);
    }
}

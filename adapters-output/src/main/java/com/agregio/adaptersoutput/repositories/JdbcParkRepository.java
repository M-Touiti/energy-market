package com.agregio.adaptersoutput.repositories;

import com.agregio.adaptersoutput.entities.BlockEntity;
import com.agregio.adaptersoutput.entities.ParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdbcParkRepository extends JpaRepository<ParkEntity, Long> {
}

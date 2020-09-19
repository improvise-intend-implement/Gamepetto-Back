package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MapRepository extends JpaRepository<MapEntity, Long> {
	Set<MapEntity> findAllByGameIdAndIdIn(Long gameId, Set<Long> ids);
}

package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

}

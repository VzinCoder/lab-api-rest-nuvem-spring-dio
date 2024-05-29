package com.vzincoder.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Room;
import java.util.List;



@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    public Optional<Room> findByNumberAndFloor(int number,int floor);
    
    public List<Room> findByFloor(int floor);
}

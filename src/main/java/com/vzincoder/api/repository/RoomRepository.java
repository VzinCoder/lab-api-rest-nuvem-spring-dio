package com.vzincoder.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    public Optional<Room> findByNumberAndFloor(int number, int floor);

    public List<Room> findByFloor(int floor);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT res.room.id FROM Reserve res WHERE res.status = 'RESERVED' AND res.dateCheckIn <= :dateCheckOut AND res.dateCheckOut >= :dateCheckIn)")
    public List<Room> findAllRoomsAvailable(LocalDate dateCheckIn, LocalDate dateCheckOut);

}

package com.vzincoder.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vzincoder.api.domain.Client;
import com.vzincoder.api.domain.Reserve;
import com.vzincoder.api.domain.ReserveStatus;
import com.vzincoder.api.domain.Room;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

    public List<Reserve> findByClient(Client client);

    public List<Reserve> findByRoom(Room room);

    @Query("SELECT r FROM Reserve r WHERE r.room.id = :roomId AND r.status = :status AND "
            + "(r.dateCheckIn <= :dateCheckOut AND r.dateCheckOut >= :dateCheckIn)")

    public List<Reserve> findByRoomAndStatusAndDateCheckInAndDateCheckOut(@Param("roomId") int roomId,
            @Param("status") ReserveStatus status, @Param("dateCheckIn") LocalDate dateCheckIn,
            @Param("dateCheckOut") LocalDate dateCheckOut);

}
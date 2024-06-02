package com.vzincoder.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzincoder.api.domain.Room;
import com.vzincoder.api.dto.RoomCreateDTO;
import com.vzincoder.api.dto.RoomDTO;
import com.vzincoder.api.dto.RoomReservationRequest;
import com.vzincoder.api.exception.DataIntegrityException;
import com.vzincoder.api.exception.DateInvalid;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomDTO getRoomById(int id) {
        Optional<Room> roomFound = roomRepository.findById(id);

        if (roomFound.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        return convertToDTO(roomFound.get());
    }

    public RoomDTO getRoomNumberAndFloor(int numberRoom, int floor) {
        Optional<Room> roomFound = roomRepository.findByNumberAndFloor(numberRoom, floor);

        if (roomFound.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        return convertToDTO(roomFound.get());
    }

    public List<RoomDTO> getAllBedrooms() {
        List<Room> bedrooms = roomRepository.findAll();

        return bedrooms.stream().map(this::convertToDTO).toList();
    }

    public List<RoomDTO> getBedroomsFloor(int floor) {
        List<Room> bedrooms = roomRepository.findByFloor(floor);
        return bedrooms.stream().map(this::convertToDTO).toList();
    }

    public List<RoomDTO> getRoomsAvaible(RoomReservationRequest roomReservationRequest){
        LocalDate dateCheckIn = roomReservationRequest.getCheckInDate();
        LocalDate dateCheckOut = roomReservationRequest.getCheckOutDate();

        if(dateCheckOut.isBefore(dateCheckIn)){
            throw new DateInvalid("date check-out invalid");
        }

        return roomRepository.findAllRoomsAvailable(dateCheckIn, dateCheckOut).stream().map(this::convertToDTO).toList();
    }

    public RoomDTO createRoom(RoomCreateDTO roomCreateDTO) {
        try {
           Room createdRom = roomRepository.save(convertRoomCreateDTOToRoom(roomCreateDTO));
           return convertToDTO(createdRom);
        } catch (Exception e) {
            throw new DataIntegrityException("This number is already being used for a room on this floor");
        }
    }

    public void deleteRoom(int id) {
        Optional<Room> roomFound = roomRepository.findById(id);

        if (roomFound.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        roomRepository.delete(roomFound.get());
    }

    public void deleteRoomNumberAndFloor(int number,int floor) {
        Optional<Room> roomFound = roomRepository.findByNumberAndFloor(number, floor);

        if (roomFound.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        roomRepository.delete(roomFound.get());
    }

    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Optional<Room> roomFound = roomRepository.findById(roomDTO.getId());


        if (roomFound.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        try {
            Room updatedRoom = roomFound.get();
            updatedRoom.setNumber(roomDTO.getNumber());
            updatedRoom.setFloor(roomDTO.getFloor());
            updatedRoom.setDescribe(roomDTO.getDescribe());
            updatedRoom.setPriceDay(roomDTO.getPriceDay());
            updatedRoom.setQty_bathroom(roomDTO.getQty_bathroom());
            updatedRoom.setQty_bed(roomDTO.getQty_bed());
            return convertToDTO(roomRepository.save(updatedRoom));
        } catch (Exception e) {
            throw new DataIntegrityException("This number is already being used for a room on this floor");
        }
    }

    public RoomDTO convertToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setFloor(room.getFloor());
        roomDTO.setNumber(room.getNumber());
        roomDTO.setDescribe(room.getDescribe());
        roomDTO.setPriceDay(room.getPriceDay());
        roomDTO.setQty_bathroom(room.getQty_bathroom());
        roomDTO.setQty_bed(room.getQty_bed());
        return roomDTO;
    }

    public Room convertToRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setFloor(roomDTO.getFloor());
        room.setNumber(roomDTO.getNumber());
        room.setDescribe(roomDTO.getDescribe());
        room.setPriceDay(roomDTO.getPriceDay());
        room.setQty_bathroom(roomDTO.getQty_bathroom());
        room.setQty_bed(roomDTO.getQty_bed());
        return room;
    }


    public Room convertRoomCreateDTOToRoom(RoomCreateDTO roomCreateDTO){
        Room room = new Room();
        room.setFloor(roomCreateDTO.getFloor());
        room.setNumber(roomCreateDTO.getNumber());
        room.setDescribe(roomCreateDTO.getDescribe());
        room.setPriceDay(roomCreateDTO.getPriceDay());
        room.setQty_bathroom(roomCreateDTO.getQty_bathroom());
        room.setQty_bed(roomCreateDTO.getQty_bed());
        return room;
    }
}

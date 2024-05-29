package com.vzincoder.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vzincoder.api.dto.RoomCreateDTO;
import com.vzincoder.api.dto.RoomDTO;
import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/room")
public class RoomController {
    

    @Autowired
    private RoomService roomService;


    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable int id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/number/{number}/floor/{floor}")
    public ResponseEntity<RoomDTO> getRoomNumberAndFloor(@PathVariable int number, @PathVariable int floor) {
        return ResponseEntity.ok(roomService.getRoomNumberAndFloor(number,floor));
    }

    @GetMapping("/floor/{floor}")
    public ResponseEntity<List<RoomDTO>> getBedroomsFloor(@PathVariable int floor) {
        return ResponseEntity.ok(roomService.getBedroomsFloor(floor));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> bedrooRoomDTOs = roomService.getAllBedrooms();
        return ResponseEntity.ok(bedrooRoomDTOs);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomCreateDTO roomCreateDTO) {
        RoomDTO createdRoom = roomService.createRoom(roomCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable int id,@Valid @RequestBody RoomCreateDTO roomCreateDTO) {
        RoomDTO updateRoom = new RoomDTO();
        updateRoom.setId(id);
        updateRoom.setFloor(roomCreateDTO.getFloor());
        updateRoom.setNumber(roomCreateDTO.getNumber());
        updateRoom.setDescribe(roomCreateDTO.getDescribe());
        updateRoom.setPriceDay(roomCreateDTO.getPriceDay());
        updateRoom.setQty_bathroom(roomCreateDTO.getQty_bathroom());
        updateRoom.setQty_bed(roomCreateDTO.getQty_bed());
        return ResponseEntity.ok(roomService.updateRoom(updateRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(new MessageResponseDTO("Room with ID " + id + " deleted successfully"));
    }


    @DeleteMapping("/number/{number}/floor/{floor}")
    public ResponseEntity<MessageResponseDTO> deleteRoomNumberAndFloor(@PathVariable int number, @PathVariable int floor) {
        roomService.deleteRoomNumberAndFloor(number, floor);
        return ResponseEntity.ok(new MessageResponseDTO("Room with number " + number + " and floor "+floor+" deleted successfully"));
    }
}

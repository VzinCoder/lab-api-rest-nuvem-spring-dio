package com.vzincoder.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vzincoder.api.dto.MessageResponseDTO;
import com.vzincoder.api.dto.ReserveCreateDTO;
import com.vzincoder.api.dto.ReserveDTO;
import com.vzincoder.api.service.ReserveService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;


    @PostMapping
    public ResponseEntity<ReserveDTO> createReserve(@Valid @RequestBody ReserveCreateDTO reserveCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reserveService.createReserve(reserveCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ReserveDTO>> getAllReserve(){
        return ResponseEntity.ok(reserveService.getAllReserve());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReserveDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(reserveService.getReserve(id));
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<List<ReserveDTO>> getByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(reserveService.getReserveByClient(cpf));
    }

    @GetMapping("/room/{number}/{floor}")
    public ResponseEntity<List<ReserveDTO>> getByCpf(@PathVariable("number") int number,@PathVariable("floor") int floor){
        return ResponseEntity.ok(reserveService.getReserveByRoom(number, floor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReserveDTO> updateReserve(@PathVariable int id,@Valid @RequestBody ReserveCreateDTO reserveCreateDTO){
        return ResponseEntity.ok(reserveService.updaReserve(id, reserveCreateDTO));
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<ReserveDTO> cancelReserve(@PathVariable int id){
        return ResponseEntity.ok(reserveService.cancelReserve(id));
    }

    @PatchMapping("/finalize/{id}")
    public ResponseEntity<ReserveDTO> finalizeReserve(@PathVariable int id){
        return ResponseEntity.ok(reserveService.finalizeReserve(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteReserve(@PathVariable int id){
        reserveService.deleteReserve(id);
        return ResponseEntity.ok(new MessageResponseDTO("Reserve with ID " + id + " deleted successfully"));
    }
}

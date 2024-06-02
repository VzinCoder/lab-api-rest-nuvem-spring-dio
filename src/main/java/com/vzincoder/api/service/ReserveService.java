package com.vzincoder.api.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vzincoder.api.domain.Client;
import com.vzincoder.api.domain.Employee;
import com.vzincoder.api.domain.Reserve;
import com.vzincoder.api.domain.ReserveStatus;
import com.vzincoder.api.domain.Room;
import com.vzincoder.api.dto.EmployeeReserveDTO;
import com.vzincoder.api.dto.ReserveCreateDTO;
import com.vzincoder.api.dto.ReserveDTO;
import com.vzincoder.api.exception.DateInvalid;
import com.vzincoder.api.exception.EntityNotFoundException;
import com.vzincoder.api.repository.ClientRepository;
import com.vzincoder.api.repository.EmployeeRepository;
import com.vzincoder.api.repository.ReserveRepository;
import com.vzincoder.api.repository.RoomRepository;

@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoomService roomService;

    public ReserveDTO createReserve(ReserveCreateDTO reserveCreateDTO) {
        Reserve newReserve = convertCreateDTO(reserveCreateDTO);

        if (newReserve.getDateCheckIn().isBefore(LocalDate.now())) {
            throw new DateInvalid("date checkIn invalid");
        }

        if (newReserve.getDateCheckOut().isBefore(LocalDate.now())) {
            throw new DateInvalid("date checkOut invalid");
        }

        if (newReserve.getDateCheckOut().isBefore(newReserve.getDateCheckIn())) {
            throw new DateInvalid("date checkOut invalid");
        }

        if (!checkDateValid(newReserve.getRoom().getId(), newReserve.getDateCheckIn(), newReserve.getDateCheckOut())) {
            throw new DateInvalid(
                    "Sorry, the selected room is already booked for the chosen dates. Please select different dates or choose another available room.");

        }

        return convertToDto(reserveRepository.save(newReserve));
    }

    public ReserveDTO getReserve(int id) {
        Optional<Reserve> reserve = reserveRepository.findById(id);

        if (reserve.isEmpty()) {
            throw new EntityNotFoundException("Reserve not afound");
        }

        return convertToDto(reserve.get());
    }

    public List<ReserveDTO> getReserveByClient(String cpf) {
        Optional<Client> client = clientRepository.findByCpf(cpf);

        if (client.isEmpty()) {
            throw new EntityNotFoundException("client not afound");
        }

        return reserveRepository.findByClient(client.get()).stream().map(this::convertToDto).toList();
    }

    public List<ReserveDTO> getReserveByRoom(int number, int floor) {
        Optional<Room> room = roomRepository.findByNumberAndFloor(number, floor);

        if (room.isEmpty()) {
            throw new EntityNotFoundException("client not afound");
        }

        return reserveRepository.findByRoom(room.get()).stream().map(this::convertToDto).toList();
    }

    public List<ReserveDTO> getAllReserve() {
        return reserveRepository.findAll().stream().map(this::convertToDto).toList();
    }

    public void deleteReserve(int id) {
        Optional<Reserve> reserve = reserveRepository.findById(id);

        if (reserve.isEmpty()) {
            throw new EntityNotFoundException("Reserve not afound");
        }

        reserveRepository.delete(reserve.get());
    }

    public ReserveDTO updaReserve(int id, ReserveCreateDTO reserveCreateDTO) {

        if (reserveRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserve not afound");
        }

        Reserve updatedReserve = convertCreateDTO(reserveCreateDTO);
        updatedReserve.setId(id);

        List<Reserve> reserveList = reserveRepository.findByRoomAndStatusAndDateCheckInAndDateCheckOut(
                reserveCreateDTO.getRoomId(),
                ReserveStatus.RESERVED, updatedReserve.getDateCheckIn(), updatedReserve
                        .getDateCheckOut());

        if (reserveList.size() > 1) {
            throw new DateInvalid(
                    "Sorry, the selected room is already booked for the chosen dates. Please select different dates or choose another available room.");
        }

        if (reserveList.size() == 1 && reserveList.get(0).getId() != id) {
            throw new DateInvalid(
                    "Sorry, the selected room is already booked for the chosen dates. Please select different dates or choose another available room.");
        }

        return convertToDto(reserveRepository.save(updatedReserve));
    }

    private boolean checkDateValid(int idRoom, LocalDate dateCheckIn, LocalDate dateCheckout) {

        List<Reserve> reserve = reserveRepository.findByRoomAndStatusAndDateCheckInAndDateCheckOut(idRoom,
                ReserveStatus.RESERVED, dateCheckIn, dateCheckout);

        return reserve.isEmpty();
    }

    private Reserve convertCreateDTO(ReserveCreateDTO reserveCreateDTO) {
        Reserve reserve = new Reserve();

        Optional<Client> client = clientRepository.findByCpf(reserveCreateDTO.getClientCpf());
        Optional<Room> room = roomRepository.findById(reserveCreateDTO.getRoomId());
        Optional<Employee> employee = employeeRepository.findByCpf(reserveCreateDTO.getEmployeeCpf());

        if (client.isEmpty()) {
            throw new EntityNotFoundException("client not afound");
        }

        if (room.isEmpty()) {
            throw new EntityNotFoundException("room not afound");
        }

        if (employee.isEmpty()) {
            throw new EntityNotFoundException("employe not afound");
        }

        reserve.setClient(client.get());
        reserve.setRoom(room.get());
        reserve.setEmployee(employee.get());
        reserve.setDateCheckIn(LocalDate.parse(reserveCreateDTO.getDateCheckIn()));
        reserve.setDateCheckOut(LocalDate.parse(reserveCreateDTO.getDateCheckOut()));
        reserve.setStatus(ReserveStatus.RESERVED);

        long qtdDays = ChronoUnit.DAYS.between(reserve.getDateCheckIn(), reserve.getDateCheckOut());
        double totalPrice = Math.max(1, qtdDays) * reserve.getRoom().getPriceDay();
        reserve.setPrice(totalPrice);

        return reserve;
    }

    private ReserveDTO convertToDto(Reserve reserve) {
        ReserveDTO reserveDTO = new ReserveDTO();

        Employee employee = reserve.getEmployee();
        EmployeeReserveDTO employeeReserveDTO = new EmployeeReserveDTO();

        employeeReserveDTO.setId(employee.getId());
        employeeReserveDTO.setName(employee.getName());
        employeeReserveDTO.setCpf(employee.getCpf());
        employeeReserveDTO.setEmail(employee.getEmail());

        reserveDTO.setClient(clientService.convertToDTO(reserve.getClient()));
        reserveDTO.setEmployee(employeeReserveDTO);
        reserveDTO.setRoom(roomService.convertToDTO(reserve.getRoom()));

        reserveDTO.setDateCheckIn(reserve.getDateCheckIn());
        reserveDTO.setDateCheckOut(reserve.getDateCheckOut());
        reserveDTO.setPrice(reserve.getPrice());
        reserveDTO.setStatus(reserve.getStatus());
        reserveDTO.setId(reserve.getId());

        return reserveDTO;
    }

}

package com.example.ClinicaOdontologicaSpringMVC.Controller;


import com.example.ClinicaOdontologicaSpringMVC.Model.Domicilio;
import com.example.ClinicaOdontologicaSpringMVC.Model.Paciente;
import com.example.ClinicaOdontologicaSpringMVC.Service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController() {
        pacienteService= new PacienteService();
    }

  @PostMapping
  public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
      return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
  }

  @PutMapping
  public ResponseEntity<Void> actualizarPaciente(@RequestBody Paciente paciente) {
      pacienteService.actualizarPaciente(paciente);
      return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Paciente> buscarPacienteID(@PathVariable Integer id) {
      return ResponseEntity.ok(pacienteService.buscarPorID(id));
  }


  @DeleteMapping("/eliminar/{id}")
  public ResponseEntity<Void> eliminarPacienteID(@PathVariable Integer id) {
        pacienteService.eliminarPacienteID(id);
        return ResponseEntity.noContent().build();
  }

  @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
      return ResponseEntity.ok(pacienteService.listarTodos());
  }

}

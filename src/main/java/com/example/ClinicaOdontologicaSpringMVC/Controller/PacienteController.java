package com.example.ClinicaOdontologicaSpringMVC.Controller;

import com.example.ClinicaOdontologicaSpringMVC.Model.Paciente;
import com.example.ClinicaOdontologicaSpringMVC.Service.PacienteService;
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
    public Paciente guardarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
  }

  @GetMapping("/{id}")
  public Paciente buscarPacienteID(@PathVariable Integer id){
      return pacienteService.buscarPorID(id);
  }

  @GetMapping("/eliminar/{id}")
  public void eliminarPacienteID(@PathVariable Integer id){
        pacienteService.eliminarPacienteID(id);
  }

  @GetMapping
    public List<Paciente> listarTodos(){
      return pacienteService.listarTodos();
  }

/* @PutMapping
  public String actualizarPaciente(Paciente paciente){
        return pacienteService.
  }*/
}

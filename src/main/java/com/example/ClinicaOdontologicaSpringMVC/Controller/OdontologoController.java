package com.example.ClinicaOdontologicaSpringMVC.Controller;

import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import com.example.ClinicaOdontologicaSpringMVC.Service.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("/odontologos")
    public class OdontologoController {
        private OdontologoService odontologoService;

        public OdontologoController() {
            odontologoService = new OdontologoService();
        }

        @PostMapping
        public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }

        @PutMapping
        public ResponseEntity<Void> actualizarOdontologo(@RequestBody Odontologo odontologo) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si la actualización fue exitosa
    }

        @GetMapping
        public ResponseEntity<List<Odontologo>> listarTodos() {
            return ResponseEntity.ok(odontologoService.listarOdontologos());
        }

        @GetMapping("/buscar/{id}")
        public ResponseEntity<Odontologo> buscarPorId(@PathVariable Integer id) {
            return ResponseEntity.ok(odontologoService.buscarPorID(id));
        }

        @DeleteMapping("/eliminar/{id}")
        public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
            odontologoService.eliminarPorID(id);
            return ResponseEntity.noContent().build();
        }
    }


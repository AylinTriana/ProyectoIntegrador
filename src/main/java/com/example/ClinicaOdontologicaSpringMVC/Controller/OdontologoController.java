package com.example.ClinicaOdontologicaSpringMVC.Controller;

import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import com.example.ClinicaOdontologicaSpringMVC.Service.OdontologoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        public Odontologo guardarOdontologo(@RequestBody Odontologo odontologo){
            return odontologoService.guardarOdontologo(odontologo);
        }
        @PutMapping
        public void actualizarOdontologo(Odontologo odontologo){
            odontologoService.actualizarOdontologo(odontologo);
        }
        @GetMapping
        public List<Odontologo> listarTodos(){
            return odontologoService.listarOdontologos();
        }
        @GetMapping("/buscar/{id}")
        public Odontologo buscarPorId(@PathVariable Integer id){
            return odontologoService.buscarPorID(id);
        }

        @DeleteMapping("/eliminar/{id}")
        public void eliminarPorId(@PathVariable Integer id){
            odontologoService.eliminarPorID(id);
        }




    }


package com.example.ClinicaOdontologicaSpringMVC.Service;

import com.example.ClinicaOdontologicaSpringMVC.Dao.OdontologoDAOH2;
import com.example.ClinicaOdontologicaSpringMVC.Dao.iDao;
import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    public OdontologoService() {
        odontologoiDao = new OdontologoDAOH2();
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoiDao.guardar(odontologo);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoiDao.listarTodos();
    }

    public Odontologo buscarPorID(Integer id) {
        return odontologoiDao.buscarPorId(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoiDao.actualizar(odontologo);
    }

    public void eliminarPorID(Integer id) {
        odontologoiDao.eliminar(id);
    }
}

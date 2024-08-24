package com.example.ClinicaOdontologicaSpringMVC.Service;

import com.example.ClinicaOdontologicaSpringMVC.Dao.TurnoDAOLISTA;
import com.example.ClinicaOdontologicaSpringMVC.Dao.iDao;
import com.example.ClinicaOdontologicaSpringMVC.Model.Turno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {
    private iDao<Turno> turnoiDao;

    public TurnoService() {
        turnoiDao = new TurnoDAOLISTA();
    }

    public Turno guardarTurno(Turno turno) {
        return turnoiDao.guardar(turno);
    }

    public Turno buscarPorId(Integer id) {
        return turnoiDao.buscarPorId(id);
    }

    public void actualizarTurno(Turno turno) {
        turnoiDao.actualizar(turno);
    }

    public void eliminarTurno(Integer id) {
        turnoiDao.eliminar(id);
    }

    public List<Turno> listarTurnos() {
        return turnoiDao.listarTodos();
    }
}

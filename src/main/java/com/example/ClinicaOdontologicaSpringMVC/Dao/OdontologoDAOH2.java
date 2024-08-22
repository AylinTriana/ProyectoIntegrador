package com.example.ClinicaOdontologicaSpringMVC.Dao;

import com.example.ClinicaOdontologicaSpringMVC.Model.Domicilio;
import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import com.example.ClinicaOdontologicaSpringMVC.Model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo> {
    private static final Logger logger = Logger.getLogger(OdontologoDAOH2.class);
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES(?,?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM ODONTOLOGOS WHERE ID = ?";



    @Override
    public Odontologo guardar(Odontologo odontologo) {
        logger.info("iniciando las operaciones de : guardado de: " + odontologo.getNombre());
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT);
            psInsert.setInt(1, odontologo.getId());
            psInsert.setInt(2, odontologo.getNumeroMatricula());
            psInsert.setString(3, odontologo.getNombre());
            psInsert.setString(4, odontologo.getApellido());
            psInsert.execute();
            logger.info("Carga exitosa");

        } catch (Exception e) {
            logger.error("problemas con la BD" + e.getMessage());
        }
        return odontologo;
    }


    @Override
    public void actualizar(Odontologo odontologo) {

    }

    @Override
    public void eliminar(Integer id) {
        logger.info("iniciando las operaciones de : eliminado de: odontologo ID:" + id);
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement delete = connection.prepareStatement(SQL_DELETE_ONE);
            delete.setInt(1, id);
            delete.execute();
            logger.info("Eliminado exitoso");

        } catch (Exception e) {
            logger.error("problemas con la BD" + e.getMessage());
        }

    }

    @Override
    public List<Odontologo> listarTodos() {
        logger.info("iniciando las operaciones de : listar");
        Connection connection=null;
        Odontologo odontologo = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try{
            connection=BD.getConnection();
            PreparedStatement psSelect = connection.prepareStatement(SQL_SELECT);
            ResultSet rs = psSelect.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                odontologos.add(odontologo);
            }
            logger.info("Carga de Lista Odontologos existosa");

        }catch (Exception e){
            logger.error("problemas con la BD"+e.getMessage());
        }
        return odontologos;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
            logger.info("iniciando las operaciones de : busqueda  de un odontologo: "+id);
            Connection connection=null;
            Odontologo odontologo = null;
            try{
                connection=BD.getConnection();
                Statement statement= connection.createStatement();
                PreparedStatement psUpdate=connection.prepareStatement(SQL_SELECT_ONE);
                psUpdate.setInt(1,id);
                ResultSet rs= psUpdate.executeQuery();
                while (rs.next()){
                    odontologo = new Odontologo(rs.getInt(1),rs.getInt(2),rs.getString(3), rs.getString(4));
                }
                logger.info(odontologo);

            }catch (Exception e){
                logger.error("problemas con la BD"+e.getMessage());
            }
            return odontologo;
        }



    @Override
    public Odontologo buscarPorString(String string) {
        return null;
    }

}




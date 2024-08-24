package com.example.ClinicaOdontologicaSpringMVC.Dao;

import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo> {
    private static final Logger logger = Logger.getLogger(OdontologoDAOH2.class);
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS (NOMBRE, APELLIDO, MATRICULA) VALUES(?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_SELECT_ONE = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_DELETE_ONE = "DELETE FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET NOMBRE = ?, APELLIDO = ?, MATRICULA = ? WHERE ID = ?";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        logger.info("iniciando las operaciones de : guardado de: "+odontologo.getNombre());

        Connection connection=null;
        try{
            connection=BD.getConnection();
            PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, odontologo.getNombre());
            psInsert.setString(2, odontologo.getApellido());
            psInsert.setString(3, odontologo.getMatricula());
            psInsert.execute();
            ResultSet rs= psInsert.getGeneratedKeys();
            while(rs.next()){
                odontologo.setId(rs.getInt(1));
            }
            logger.info("odontologo cargado : "+odontologo.getId());

        }catch (Exception e){
            logger.error("problemas con la BD"+e.getMessage());
        }
        return odontologo;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        logger.info("iniciando las operaciones de actualizado de un odontologo");
        Connection connection = null;
        try {
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, odontologo.getNombre());
            psUpdate.setString(2, odontologo.getApellido());
            psUpdate.setString(3, odontologo.getMatricula());
            psUpdate.setInt(4, odontologo.getId());
            psUpdate.executeUpdate();
            logger.info("Odontologo actualizado correctamente");

        } catch (Exception e) {
            logger.error("Error al actualizar el odontologo" + e.getMessage());
        }
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
            logger.error("No se puede eliminar, paciente no existe" + e.getMessage());
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        logger.info("iniciando las operaciones de : listar los odontologos: ");
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologo = null;
        Connection connection=null;
        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT);
            while (rs.next()) {
                odontologo = new Odontologo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
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
                    odontologo = new Odontologo(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
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




package com.example.ClinicaOdontologicaSpringMVC.Dao;

import com.example.ClinicaOdontologicaSpringMVC.Model.Domicilio;
import com.example.ClinicaOdontologicaSpringMVC.Model.Odontologo;
import com.example.ClinicaOdontologicaSpringMVC.Model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOH2 implements iDao<Paciente>{
    private static final Logger logger= Logger.getLogger(PacienteDAOH2.class);
    private static final String SQL_INSERT="INSERT INTO PACIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_ONE="SELECT * FROM PACIENTES WHERE ID = ? ";
    private static final String SQL_SELECT_EMAIL="SELECT * FROM PACIENTES WHERE EMAIL = ?";
    private static final String SQL_UPDATE = "UPDATE PACIENTES SET NOMBRE = ?, APELLIDO = ?, CEDULA = ?, FECHA_INGRESO = ?, DOMICILIO_ID = ?, EMAIL = ? WHERE ID = ? ";
    private static final String SQL_DELETE_ONE = "DELETE FROM PACIENTES WHERE ID = ?";
    private static final String SQL_SELECT = "SELECT * FROM PACIENTES";

    @Override
    public Paciente guardar(Paciente paciente) {
        logger.info("iniciando las operaciones de guardado de un paciente");
        Connection connection= null;
        DomicilioDAOH2 daoAux= new DomicilioDAOH2();
        Domicilio domicilio= daoAux.guardar(paciente.getDomicilio());
        try{
            connection= BD.getConnection();
            PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, paciente.getNombre());
            psInsert.setString(2, paciente.getApellido());
            psInsert.setString(3, paciente.getCedula());
            psInsert.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            psInsert.setInt(5,domicilio.getId());
            psInsert.setString(6, paciente.getEmail());
            psInsert.execute();
           ResultSet rs= psInsert.getGeneratedKeys();
            while(rs.next()){
                paciente.setId(rs.getInt(1));
            }
            logger.info("paciente registrado: ID: "+paciente.getId()+ " con domicilio asignado: "+paciente.getDomicilio().getId());
        }catch (Exception e){
            logger.error("conexion fallida: "+e.getMessage());
        }
        return paciente;
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        logger.info("iniciando las operaciones de buscado de un paciente");
        Connection connection= null;
        Paciente paciente=null;
        Domicilio domicilio=null;
        try{
            connection= BD.getConnection();
            Statement statement= connection.createStatement();
            PreparedStatement psSelectOne= connection.prepareStatement(SQL_SELECT_ONE);
            psSelectOne.setInt(1,id);
            ResultSet rs= psSelectOne.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while (rs.next()){
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
            }
        }catch (Exception e){
            logger.error("conexion fallida: "+e.getMessage());
        } if(paciente != null){
            logger.info("paciente encontrado");
        }
        return paciente;
    }

    @Override
    public Paciente buscarPorString(String string) {
        logger.info("iniciando las operaciones de busqueda: "+string);
        Connection connection=null;
        Paciente paciente=null;
        Domicilio domicilio=null;
        DomicilioDAOH2 daoAux=new DomicilioDAOH2();
        try{
            connection=BD.getConnection();
            PreparedStatement psSelectEmail= connection.prepareStatement(SQL_SELECT_EMAIL);
            psSelectEmail.setString(1,string);
            ResultSet rs= psSelectEmail.executeQuery();
            while (rs.next()){
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
            }

        }catch (Exception e){
            logger.error("fallo en la conexion: "+e.getMessage());
        }if(paciente!=null){
            logger.info("paciente encontrado con exito");
        }else{
            logger.warn("error en la busqueda, paciente null");
        }
        return paciente;
    }

    @Override
    public void actualizar(Paciente paciente) {
        logger.info("iniciando las operaciones de actualizado de un paciente");
        Connection connection= null;
        Domicilio domicilio = null;
        Odontologo odontologo = null;
        DomicilioDAOH2 daoAux=new DomicilioDAOH2();
        try{
            connection = BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, paciente.getNombre());
            psUpdate.setString(2, paciente.getApellido());
            psUpdate.setString(3, paciente.getCedula());
            psUpdate.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            psUpdate.setInt(5, domicilio.getId());
            psUpdate.setString(6, paciente.getEmail());
            psUpdate.setInt(7, odontologo.getId());
            psUpdate.setInt(8, paciente.getId());
            psUpdate.executeUpdate();
            logger.info("Paciente actualizado correctamente");
        }catch (Exception e){
            logger.error("Error al actualizar el paciente"+e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) {
        logger.info("iniciando las operaciones de : eliminado de: paciente ID:" + id);
        Connection connection= null;
        try{
            connection= BD.getConnection();
            Statement statement = connection.createStatement();
            PreparedStatement delete = connection.prepareStatement(SQL_DELETE_ONE);
            delete.setInt(1, id);
            delete.execute();
            logger.info("Eliminado exitoso");

        }catch (Exception e){
            logger.error("No se puede eliminar, paciente no existe"+e.getMessage());
        }
    }

    @Override
    public List<Paciente> listarTodos() {
        logger.info("iniciando las operaciones de : listar");
        List<Paciente> pacientes = new ArrayList<>();
        Paciente paciente = null;
        Domicilio domicilio = null;
        Connection connection=null;
        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT);
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while (rs.next()) {
                domicilio= daoAux.buscarPorId(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getDate(5).toLocalDate(),domicilio,rs.getString(7));
                pacientes.add(paciente);
            }
            logger.info("Carga de Lista pacientes existosa");

        }catch (Exception e){
            logger.error("problemas con la BD"+e.getMessage());
        }
        return pacientes;
    }
}

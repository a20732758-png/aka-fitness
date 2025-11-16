package br.com.akafit.dao;

import br.com.akafit.model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Aluno.
 * (Versão Web: Propaga exceções em vez de retornar boolean)
 */
public class AlunoDAO {

    /**
     * Cadastra um novo aluno no banco de dados.
     * @param aluno O objeto Aluno preenchido.
     */
    public void cadastrar(Aluno aluno) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO aluno (nome, idade, peso, altura, endereco, email, telefone, telefone_emergencia, frequencia) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getIdade());
            ps.setDouble(3, aluno.getPeso());
            ps.setDouble(4, aluno.getAltura());
            ps.setString(5, aluno.getEndereco());
            ps.setString(6, aluno.getEmail());
            ps.setString(7, aluno.getTelefone());
            ps.setString(8, aluno.getTelefoneEmergencia());
            ps.setString(9, aluno.getFrequencia());
            ps.executeUpdate();
        }
    }

    /**
     * Atualiza os dados de um aluno existente.
     * @param aluno O objeto Aluno com o ID e os dados atualizados.
     */
    public void editar(Aluno aluno) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE aluno SET nome = ?, idade = ?, peso = ?, altura = ?, endereco = ?, " +
                     "email = ?, telefone = ?, telefone_emergencia = ?, frequencia = ? " +
                     "WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getIdade());
            ps.setDouble(3, aluno.getPeso());
            ps.setDouble(4, aluno.getAltura());
            ps.setString(5, aluno.getEndereco());
            ps.setString(6, aluno.getEmail());
            ps.setString(7, aluno.getTelefone());
            ps.setString(8, aluno.getTelefoneEmergencia());
            ps.setString(9, aluno.getFrequencia());
            ps.setInt(10, aluno.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Exclui um aluno do banco de dados.
     * @param id O ID do aluno a ser excluído.
     */
    public void excluir(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Lista todos os alunos cadastrados.
     * @return Uma lista de objetos Aluno.
     */
    public List<Aluno> listarTodos() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM aluno ORDER BY nome";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToAluno(rs));
            }
        }
        return lista;
    }
    
    /**
     * Busca um aluno específico pelo seu ID.
     * @param id O ID do aluno.
     * @return O objeto Aluno, ou null se não encontrar.
     */
    public Aluno buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        
        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAluno(rs);
                }
            }
        }
        return null;
    }
    
    // Método utilitário para não repetir código
    private Aluno mapResultSetToAluno(ResultSet rs) throws SQLException {
         return new Aluno(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getInt("idade"),
            rs.getDouble("peso"),
            rs.getDouble("altura"),
            rs.getString("endereco"),
            rs.getString("email"),
            rs.getString("telefone"),
            rs.getString("telefone_emergencia"),
            rs.getString("frequencia")
        );
    }
}
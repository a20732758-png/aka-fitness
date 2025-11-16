package br.com.akafit.dao;

import br.com.akafit.model.FichaTreino;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade FichaTreino.
 * (Versão Web: Propaga exceções)
 */
public class FichaTreinoDAO {

    /**
     * Salva ou atualiza uma lista de exercícios de uma ficha (transacional).
     */
    public void salvarFicha(List<FichaTreino> listaExercicios, int alunoId, String diaSemana) throws SQLException, ClassNotFoundException {
        // CORREÇÃO: "dia_sempre" alterado para "dia_semana"
        String sqlDelete = "DELETE FROM ficha_treino WHERE aluno_id = ? AND dia_semana = ?";
        String sqlInsert = "INSERT INTO ficha_treino (aluno_id, dia_semana, musculo, exercicio, series, repeticoes, observacao) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = ConexaoDB.getConexao();
            conexao.setAutoCommit(false); // Inicia transação

            // Etapa 1: Apagar exercícios antigos
            try (PreparedStatement psDelete = conexao.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, alunoId);
                psDelete.setString(2, diaSemana);
                psDelete.executeUpdate();
            }

            // Etapa 2: Inserir novos exercícios
            try (PreparedStatement psInsert = conexao.prepareStatement(sqlInsert)) {
                for (FichaTreino exercicio : listaExercicios) {
                    psInsert.setInt(1, alunoId);
                    psInsert.setString(2, diaSemana);
                    psInsert.setString(3, exercicio.getMusculo());
                    psInsert.setString(4, exercicio.getExercicio());
                    psInsert.setString(5, exercicio.getSeries());
                    psInsert.setString(6, exercicio.getRepeticoes());
                    psInsert.setString(7, exercicio.getObservacao());
                    psInsert.addBatch();
                }
                psInsert.executeBatch();
            }

            conexao.commit(); // Confirma a transação

        } catch (SQLException e) {
            if (conexao != null) {
                try {
                    conexao.rollback(); // Desfaz em caso de erro
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            throw e; // Propaga a exceção original
        } finally {
            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Lista todos os exercícios de um aluno.
     */
    public List<FichaTreino> listarFichaPorAluno(int alunoId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM ficha_treino WHERE aluno_id = ? ORDER BY FIELD(dia_semana, 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO'), id";
        List<FichaTreino> lista = new ArrayList<>();

        try (Connection conexao = ConexaoDB.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, alunoId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FichaTreino ficha = new FichaTreino(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getString("dia_semana"),
                        rs.getString("musculo"),
                        rs.getString("exercicio"),
                        rs.getString("series"),
                        rs.getString("repeticoes"),
                        rs.getString("observacao")
                    );
                    lista.add(ficha);
                }
            }
        }
        return lista;
    }
}
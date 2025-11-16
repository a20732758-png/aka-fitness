package br.com.akafit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para gerenciar a conexão com o banco de dados MySQL.
 * (Versão Web: Remove JOptionPane e propaga exceções)
 */
public class ConexaoDB {

    // --- Configurações do Banco de Dados ---
    private static final String URL = "jdbc:mysql://localhost:3306/akafit_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Obtém uma conexão ativa com o banco de dados.
     * @return um objeto Connection.
     * @throws SQLException se a conexão falhar.
     * @throws ClassNotFoundException se o driver não for encontrado.
     */
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        // 1. Carrega o driver JDBC
        Class.forName(DRIVER);
        // 2. Tenta estabelecer a conexão
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * Método auxiliar para fechar uma conexão.
     * @param conexao A conexão a ser fechada.
     */
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
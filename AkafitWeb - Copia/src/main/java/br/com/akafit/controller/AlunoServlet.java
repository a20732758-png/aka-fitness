/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.akafit.controller;

import br.com.akafit.dao.AlunoDAO;
import br.com.akafit.model.Aluno;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet "Front Controller" para todas as ações de Aluno (CRUD).
 * Responde pela URL "/alunos"
 */
@WebServlet(name = "AlunoServlet", urlPatterns = {"/alunos"})
public class AlunoServlet extends HttpServlet {

    private AlunoDAO alunoDAO;

    @Override
    public void init() {
        this.alunoDAO = new AlunoDAO(); // Instancia o DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Protegendo a página: verifica se o usuário está logado
        if (request.getSession(false) == null || request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listar"; // Ação padrão
        }

        try {
            switch (acao) {
                case "listar":
                    listarAlunos(request, response);
                    break;
                case "formEditar":
                    mostrarFormularioEdicao(request, response);
                    break;
                case "excluir":
                    excluirAluno(request, response);
                    break;
                case "perfil":
                    verPerfil(request, response);
                    break;
                // 'cadastrar' e 'editar' são tratados no doPost
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Erro de Banco de Dados: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Protegendo a página
        if (request.getSession(false) == null || request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // Define o encoding para UTF-8 para aceitar acentos
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");

        try {
            switch (acao) {
                case "cadastrar":
                    salvarAluno(request, response, false); // false = não é edição
                    break;
                case "editar":
                    salvarAluno(request, response, true); // true = é edição
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Erro de Banco de Dados: " + e.getMessage(), e);
        }
    }

    private void listarAlunos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        
        List<Aluno> listaAlunos = alunoDAO.listarTodos();
        
        // "Pendura" a lista na requisição para o JSP poder acessá-la
        request.setAttribute("listaAlunos", listaAlunos);
        
        // Redespacha (forward) para o JSP da página principal
        request.getRequestDispatcher("principal.jsp").forward(request, response);
    }

    private void salvarAluno(HttpServletRequest request, HttpServletResponse response, boolean edicao)
            throws SQLException, ClassNotFoundException, IOException {

        // 1. Pega os dados do formulário
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));
        double peso = Double.parseDouble(request.getParameter("peso").replace(",", "."));
        double altura = Double.parseDouble(request.getParameter("altura").replace(",", "."));
        String endereco = request.getParameter("endereco");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String telEmergencia = request.getParameter("telefoneEmergencia");
        String frequencia = request.getParameter("frequencia");

        // 2. Cria o objeto Model
        Aluno aluno = new Aluno(nome, idade, peso, altura, endereco, email, telefone, telEmergencia, frequencia);

        if (edicao) {
            // 3a. Se for edição, pega o ID
            int id = Integer.parseInt(request.getParameter("id"));
            aluno.setId(id);
            alunoDAO.editar(aluno);
        } else {
            // 3b. Se for cadastro
            alunoDAO.cadastrar(aluno);
        }

        // 4. Redireciona para a listagem (ação 'listar' do GET)
        response.sendRedirect("alunos?acao=listar");
    }

    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Aluno alunoParaEditar = alunoDAO.buscarPorId(id);
        
        // "Pendura" o aluno na requisição
        request.setAttribute("alunoParaEditar", alunoParaEditar);
        
        // Reúsa a página principal, que saberá preencher o formulário
        // Também precisamos da lista para a tabela
        listarAlunos(request, response);
    }

    private void excluirAluno(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        alunoDAO.excluir(id);
        
        // Redireciona para a listagem
        response.sendRedirect("alunos?acao=listar");
    }
    
    private void verPerfil(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException, ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Aluno aluno = alunoDAO.buscarPorId(id);
        
        request.setAttribute("aluno", aluno);
        
        // Redespacha para a página de perfil
        request.getRequestDispatcher("perfil.jsp").forward(request, response);
    }
}

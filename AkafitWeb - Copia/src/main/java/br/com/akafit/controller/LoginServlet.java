/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.akafit.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet para controlar o fluxo de Login e Logout.
 * Responde pela URL "/login" (definida no @WebServlet)
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");

        if (acao != null && acao.equals("logout")) {
            // Ação de Logout
            HttpSession session = request.getSession(false); // Pega a sessão se ela existir
            if (session != null) {
                session.invalidate(); // Invalida a sessão
            }
            response.sendRedirect("index.jsp"); // Redireciona para o login
            return; // Encerra
        }
        
        // Ação de Login (padrão do POST)
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        // Autenticação "chumbada" (igual à do Swing)
        if ("admin".equals(usuario) && "1234".equals(senha)) {
            // Sucesso!
            // Cria uma sessão para o usuário
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario); // Guarda o usuário na sessão
            
            // Redireciona para a página principal
            // Usamos o AlunoServlet com a ação 'listar' para carregar os alunos
            response.sendRedirect("alunos?acao=listar");
            
        } else {
            // Falha
            // Define uma mensagem de erro
            request.setAttribute("erroLogin", "Usuário ou senha inválidos.");
            // Redespacha (forward) de volta para a index.jsp, mantendo a requisição
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Se alguém tentar acessar /login via GET, apenas redireciona para o POST (logout)
        doPost(request, response);
    }
}
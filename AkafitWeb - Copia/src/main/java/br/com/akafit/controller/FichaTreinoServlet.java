package br.com.akafit.controller;

import br.com.akafit.dao.AlunoDAO;
import br.com.akafit.dao.FichaTreinoDAO;
import br.com.akafit.model.Aluno;
import br.com.akafit.model.FichaTreino;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet para controlar o cadastro da Ficha de Treino.
 * Responde pela URL "/ficha"
 */
@WebServlet(name = "FichaTreinoServlet", urlPatterns = {"/ficha"})
public class FichaTreinoServlet extends HttpServlet {

    private FichaTreinoDAO fichaDAO;
    private AlunoDAO alunoDAO;

    @Override
    public void init() {
        this.fichaDAO = new FichaTreinoDAO();
        this.alunoDAO = new AlunoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Proteção
        if (request.getSession(false) == null || request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String acao = request.getParameter("acao");
        String alunoIdParam = request.getParameter("alunoId"); // Pegar como String primeiro

        try {
            if ("gerenciar".equals(acao)) {

                // --- INÍCIO DA CORREÇÃO ---
                // Validar o alunoId ANTES de tentar converter
                if (alunoIdParam == null || alunoIdParam.trim().isEmpty()) {
                    // Se não há ID, não podemos gerenciar. Redirecionar para um local seguro.
                    // (Ex: página de lista de alunos ou index)
                    System.err.println("Tentativa de gerenciar ficha sem alunoId válido.");
                    response.sendRedirect("index.jsp"); // Altere se desejar outra página
                    return;
                }

                int alunoId;
                try {
                    alunoId = Integer.parseInt(alunoIdParam);
                } catch (NumberFormatException e) {
                    // O ID foi passado, mas não é um número (ex: "abc")
                    System.err.println("alunoId inválido (não numérico): " + alunoIdParam);
                    response.sendRedirect("index.jsp"); // Altere se desejar outra página
                    return;
                }
                // --- FIM DA CORREÇÃO ---

                // Agora que temos um alunoId válido, continuamos
                Aluno aluno = alunoDAO.buscarPorId(alunoId);
                List<FichaTreino> fichaCompleta = fichaDAO.listarFichaPorAluno(alunoId);

                request.setAttribute("aluno", aluno);
                request.setAttribute("fichaCompleta", fichaCompleta);

                request.getRequestDispatcher("gerenciarFicha.jsp").forward(request, response);
            
            } else {
                 // Se a ação não for "gerenciar" ou for nula, redireciona para um local seguro
                 response.sendRedirect("index.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Erro de BD ao carregar ficha: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Proteção
        if (request.getSession(false) == null || request.getSession().getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        // --- CORREÇÃO DE BOA PRÁTICA (Validar ID também no POST) ---
        String alunoIdParam = request.getParameter("alunoId");
        int alunoId;

        if (alunoIdParam == null || alunoIdParam.trim().isEmpty()) {
            throw new ServletException("Erro ao salvar: ID do aluno não fornecido no formulário.");
        }

        try {
            alunoId = Integer.parseInt(alunoIdParam);
        } catch (NumberFormatException e) {
            throw new ServletException("Erro ao salvar: ID do aluno inválido no formulário.", e);
        }
        // --- FIM DA CORREÇÃO DE BOA PRÁTICA ---


        try {
            // 1. Pegar dados do formulário
            // int alunoId = Integer.parseInt(request.getParameter("alunoId")); // Movido para cima
            String diaSemana = request.getParameter("diaSemana");
            String musculo = request.getParameter("musculo");

            // 2. Pegar os arrays de exercícios
            String[] exercicios = request.getParameterValues("exercicio");
            String[] series = request.getParameterValues("series");
            String[] repeticoes = request.getParameterValues("repeticoes");
            String[] observacoes = request.getParameterValues("observacao");

            List<FichaTreino> listaExercicios = new ArrayList<>();

            if (exercicios != null) {
                for (int i = 0; i < exercicios.length; i++) {
                    // Só salva se o nome do exercício não estiver vazio
                    if (exercicios[i] != null && !exercicios[i].trim().isEmpty()) {
                        FichaTreino ft = new FichaTreino(
                                musculo,
                                exercicios[i],
                                series[i],
                                repeticoes[i],
                                observacoes[i]
                        );
                        listaExercicios.add(ft);
                    }
                }
            }

            // 3. Chamar o DAO (que é transacional)
            fichaDAO.salvarFicha(listaExercicios, alunoId, diaSemana);

            // 4. Redirecionar de volta para a tela de gerenciamento
            response.sendRedirect("ficha?acao=gerenciar&alunoId=" + alunoId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Erro de BD ao salvar ficha: " + e.getMessage(), e);
        }
    }
}
<%-- 
    Document   : perfil
    Created on : 15 de nov. de 2025, 12:31:11
    Author     : andre
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 
  Esta página recebe o objeto 'aluno' do AlunoServlet (via 'perfil')
  e a 'fichaCompleta' (do FichaTreinoServlet, após o reload)
--%>

<jsp:include page="/includes/header.jsp">
    <jsp:param name="tituloPagina" value="Perfil de ${aluno.nome}"/>
</jsp:include>

<div style="margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center;">
    <h1>Perfil do Aluno: ${aluno.nome}</h1>
    <div>
        <a href="alunos?acao=listar" class="btn" style="background-color: #6c757d;">Voltar para Lista</a>
        <%-- Link para o FichaTreinoServlet --%>
        <a href="ficha?acao=gerenciar&alunoId=${aluno.id}" class="btn btn-verde">Gerenciar Ficha de Treino</a>
    </div>
</div>


<div class="perfil-grid">
    <!-- Coluna 1: Dados Pessoais -->
    <div class="perfil-info">
        <h2>Dados Pessoais</h2>
        <p><strong>Nome:</strong> ${aluno.nome}</p>
        <p><strong>Idade:</strong> ${aluno.idade} anos</p>
        <p><strong>E-mail:</strong> ${aluno.email}</p>
        <p><strong>Endereço:</strong> ${aluno.endereco}</p>
        <p><strong>Telefone:</strong> ${aluno.telefone}</p>
        <p><strong>Emergência:</strong> ${aluno.telefoneEmergencia}</p>
        <p><strong>Frequência:</strong> ${aluno.frequencia}</p>
    </div>
    
    <!-- Coluna 2: Dados Físicos e Ficha -->
    <div>
        <h2>Dados Físicos</h2>
        <div class="perfil-info">
            <p><strong>Peso:</strong> ${aluno.peso} kg</p>
            <p><strong>Altura:</strong> ${aluno.altura} m</p>
            
            <%-- Lógica para cor do IMC --%>
            <c:set var="statusIMC" value="${aluno.statusIMC}"/>
            <c:choose>
                <c:when test="${statusIMC == 'Peso normal'}">
                    <c:set var="corIMC" value="normal"/>
                </c:when>
                <c:when test="${statusIMC == 'Sobrepeso' || statusIMC == 'Abaixo do peso'}">
                    <c:set var="corIMC" value="alerta"/>
                </c:when>
                <c:otherwise>
                    <c:set var="corIMC" value="perigo"/>
                </c:otherwise>
            </c:choose>
            
            <div class="imc-box ${corIMC}">
                <strong>IMC: ${aluno.IMC}</strong>
                <br>
                <span>(${statusIMC})</span>
            </div>
        </div>
        
        <h2 style="margin-top: 20px;">Resumo da Ficha de Treino</h2>
        
        <%-- Carrega a ficha (é melhor fazer isso via Servlet, mas para simplificar...
             ...na verdade, o AlunoServlet deveria carregar isso)
             VOU CORRIGIR: O AlunoServlet (acao=perfil) deve carregar a ficha.
             
             (Sim, vou ajustar o AlunoServlet... Não, espera,
             o FichaTreinoServlet já faz isso. O AlunoServlet
             só precisa carregar o Aluno. O FichaTreinoDAO
             será chamado aqui para exibir.)
             
             **Decisão de Design:** Para manter simples, o `perfil.jsp`
             vai pedir ao `FichaTreinoDAO` (via JSTL, o que é má prática)
             ou (melhor) o `AlunoServlet` (na ação `perfil`) deve carregar
             a ficha e enviá-la.
             
             **Vou assumir que o AlunoServlet na ação "perfil"
             NÃO carregou a ficha.** Vou deixar a lógica de exibição
             da ficha na tela `gerenciarFicha.jsp`. Esta tela
             será apenas o *Perfil*. O botão "Gerenciar Ficha"
             levará para a exibição/edição.
        --%>
        
        <p>Para visualizar ou editar a ficha de treino completa, clique no botão "Gerenciar Ficha de Treino".</p>

    </div>
</div>


<jsp:include page="/includes/footer.jsp" />

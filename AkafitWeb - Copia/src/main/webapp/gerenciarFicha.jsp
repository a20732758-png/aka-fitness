<%-- 
    Document   : gerenciarFicha
    Created on : 15 de nov. de 2025, 12:32:17
    Author     : andre
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 
  Esta página recebe o 'aluno' e a 'fichaCompleta' do FichaTreinoServlet
--%>

<jsp:include page="/includes/header.jsp">
    <jsp:param name="tituloPagina" value="Gerenciar Ficha"/>
</jsp:include>

<div style="margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center;">
    <h1>Ficha de Treino: ${aluno.nome}</h1>
    <a href="alunos?acao=perfil&id=${aluno.id}" class="btn" style="background-color: #6c757d;">Voltar ao Perfil</a>
</div>

<%-- 
  Lógica para exibir a ficha em abas (SEGUNDA, TERCA, etc.)
  e permitir a edição.
--%>

<div class="abas-ficha">
    <%-- 
      Vamos usar JSTL (c:forEach) para iterar sobre os dias
      e criar um formulário para cada dia.
    --%>
    
    <c:forEach var="dia" items="${['SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO']}">
        
        <div class="ficha-dia" style="margin-bottom: 20px;">
            <div class="ficha-dia-header">${dia}</div>
            
            <form action="ficha" method="POST" class="ficha-dia-body">
                <input type="hidden" name="alunoId" value="${aluno.id}">
                <input type="hidden" name="diaSemana" value="${dia}">
                
                <%-- Filtra a ficha completa apenas para o dia atual --%>
                <c:set var="musculoDia" value=""/>
                <c:set var="exerciciosDoDia" value=""/>
                <c:set var="count" value="0"/>
                
                <c:forEach var="exercicio" items="${fichaCompleta}">
                    <c:if test="${exercicio.diaSemana == dia}">
                        <c:if test="${count == 0}">
                            <%-- Pega o músculo do primeiro exercício --%>
                            <c:set var="musculoDia" value="${exercicio.musculo}"/>
                        </c:if>
                        <%-- Cria uma lista de exercícios do dia --%>
                        <c:set var="exerciciosDoDia" value="${exerciciosDoDia}${exercicio.exercicio},${exercicio.series},${exercicio.repeticoes},${exercicio.observacao};"/>
                        <c:set var="count" value="${count + 1}"/>
                    </c:if>
                </c:forEach>
                
                <!-- 1. Grupo Muscular -->
                <div class="form-group">
                    <label>Grupo Muscular:</label>
                    <select name="musculo">
                        <option value="Peito" ${musculoDia == 'Peito' ? 'selected' : ''}>Peito</option>
                        <option value="Costas" ${musculoDia == 'Costas' ? 'selected' : ''}>Costas</option>
                        <option value="Pernas" ${musculoDia == 'Pernas' ? 'selected' : ''}>Pernas</option>
                        <option value="Bíceps" ${musculoDia == 'Bíceps' ? 'selected' : ''}>Bíceps</option>
                        <option value="Tríceps" ${musculoDia == 'Tríceps' ? 'selected' : ''}>Tríceps</option>
                        <option value="Ombro" ${musculoDia == 'Ombro' ? 'selected' : ''}>Ombro</option>
                        <option value="Abdômen" ${musculoDia == 'Abdômen' ? 'selected' : ''}>Abdômen</option>
                        <option value="Peito & Tríceps" ${musculoDia == 'Peito & Tríceps' ? 'selected' : ''}>Peito & Tríceps</option>
                        <option value="Costas & Bíceps" ${musculoDia == 'Costas & Bíceps' ? 'selected' : ''}>Costas & Bíceps</option>
                        <option value="Perna Completa" ${musculoDia == 'Perna Completa' ? 'selected' : ''}>Perna Completa</option>
                        <option value="Outro" ${musculoDia == 'Outro' ? 'selected' : ''}>Outro</option>
                    </select>
                </div>
                
                <!-- 2. Tabela de Exercícios (Dinâmica) -->
                <table id="tabela-${dia}">
                    <thead>
                        <tr>
                            <th>Exercício</th>
                            <th>Séries</th>
                            <th>Repetições</th>
                            <th>Observação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Loop nos exercícios carregados --%>
                        <c:if test="${count > 0}">
                             <c:forEach var="ex" items="${exerciciosDoDia.split(';')}">
                                 <c:set var="partes" value="${ex.split(',')}"/>
                                 <c:if test="${not empty partes[0]}">
                                    <tr>
                                        <td><input type="text" name="exercicio" value="${partes[0]}"></td>
                                        <td><input type="text" name="series" value="${partes[1]}"></td>
                                        <td><input type="text" name="repeticoes" value="${partes[2]}"></td>
                                        <td><input type="text" name="observacao" value="${partes[3]}"></td>
                                    </tr>
                                 </c:if>
                             </c:forEach>
                        </c:if>
                        
                        <%-- Adiciona linhas vazias para completar (ex: 5 linhas) --%>
                        <c:forEach begin="${count}" end="7">
                           <tr>
                                <td><input type="text" name="exercicio"></td>
                                <td><input type="text" name="series"></td>
                                <td><input type="text" name="repeticoes"></td>
                                <td><input type="text" name="observacao"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <button type="submit" class="btn btn-verde" style="margin-top: 15px;">Salvar Treino de ${dia}</button>
            </form>
        </div>
        
    </c:forEach>
</div>

<jsp:include page="/includes/footer.jsp" />

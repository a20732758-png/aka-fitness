<%-- 
    Document   : header
    Created on : 15 de nov. de 2025, 13:04:41
    Author     : andre
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Define a taglib 'c' (JSTL Core) para usarmos 'c:if', 'c:forEach', etc. --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.tituloPagina != null ? param.tituloPagina : 'AKAFIT Sistema'}</title>
    <%-- 
      ${pageContext.request.contextPath} é a forma segura de pegar a URL base do projeto.
      Evita problemas se o projeto rodar em 'http://localhost:8080/AkafitWeb/'
    --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.png">
</head>
<body>

<%-- 
  Verifica se o usuário está logado (atributo 'usuarioLogado' na sessão).
  O header só é exibido se ele estiver logado (exceto na tela de login).
--%>
<c:if test="${sessionScope.usuarioLogado != null}">
    <header>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo AKAFIT">
        </div>
        <div class="usuario-info">
            <span>Olá, <strong>${sessionScope.usuarioLogado}</strong>!</span>
            <form action="login" method="POST" style="display: inline; padding: 0; border: 0; background: none;">
                <input type="hidden" name="acao" value="logout">
                <button type="submit" class="logout" style="margin-left: 15px;">Sair</button>
            </form>
        </div>
    </header>
</c:if>

<main>
    <%-- O container principal será fechado no footer.jsp --%>
    <div class="container">

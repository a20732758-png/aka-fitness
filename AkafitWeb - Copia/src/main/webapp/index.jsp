<%-- 
    Document   : index
    Created on : 15 de nov. de 2025, 12:20:32
    Author     : andre
--%>

<%-- Remove o header/footer padrão, pois esta é a tela de login --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AKAFIT - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="${pageContext.request.contextPath}/img/logo.png">
</head>
<body>

    <div class="login-container">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo AKAFIT">
        <h1>Controle de Academia</h1>
        
        <form action="login" method="POST">
            <div class="form-group">
                <label for="usuario">Usuário:</label>
                <input type="text" id="usuario" name="usuario" value="admin" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" value="1234" required>
            </div>
            
            <%-- Exibe a mensagem de erro (se o LoginServlet a enviou) --%>
            <c:if test="${requestScope.erroLogin != null}">
                <p class="erro-login">${requestScope.erroLogin}</p>
            </c:if>
            
            <button type="submit" class="btn btn-azul">Entrar</button>
        </form>
    </div>

</body>
</html>

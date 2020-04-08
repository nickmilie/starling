<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 29.03.2020
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Custom Login Page</title>


    <style>
        .fail {
            color:  #ed4337; /*soft red*/
        }
    </style>
</head>
<body>
    <h3>Custom Login Page</h3>

    <form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="post">

        <%-- Check for login error --%>
        <c:if test="${param.error != null}">
            <i class="fail">Sorry! You have entered invalid username/password.</i>
        </c:if>

        <p>
            User Name: <input type="text" name="username">
        </p>

        <p>
            Password: <input type="password" name="password">
        </p>

        <input type="submit" value="Login">
    </form:form>
</body>
</html>

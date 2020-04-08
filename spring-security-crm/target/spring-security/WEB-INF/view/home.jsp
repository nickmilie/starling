<%--
  Created by IntelliJ IDEA.
  User: Nick
  Date: 29.03.2020
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <h2>Home Page</h2>
    <hr>

        <p>
            Welcome to the Home Page!
        </p>

    <hr>

        <p>
            User: <security:authentication property="principal.username"/>
            <br><br>
            Role(s): <security:authentication property="principal.authorities"/>
        </p>

    <hr>
    <p>
        <a href="${pageContext.request.contextPath}/customer/list">Customer List</a>
    </p>
    <hr>

    <security:authorize access="hasRole('MANAGER')">
        <hr>
            <p>
                <a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a>
                (Only for Manager peeps)
            </p>
        <hr>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <hr>
        <p>
            <a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
            (Only for Admin peeps)
        </p>
        <hr>
    </security:authorize>


    <form:form action="${pageContext.request.contextPath}/logout" method="post">
                    <input type="submit" value="Logout">
        </form:form>
</body>
</html>

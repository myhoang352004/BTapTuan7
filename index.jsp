<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Murach's Java Servlet</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="main.css" type="text/css"/>
</head>
<body>
<c:if test="${sqlStatement==null}">
    <c:set var="sqlStatement" value="select * from DocGia"/>
</c:if>
<h1> The SQL Gateway</h1>
<p> Enter an SQL statement and click the Excute button</p>
<h2>SQL Statement</h2>
<form action="sqlStatement" method="post">
    <textarea name="sqlStatement" cols="60" rows="8">${sqlStatement}</textarea><br>
    <input type="submit" value="Excute">
<h3>SQL Result:</h3>
${sqlResult}
</form>
</body>
</html>
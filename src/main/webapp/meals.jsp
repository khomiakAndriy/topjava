<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="1" cellpadding="8" cellspacing="0" >
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="tempMeal" items="${user_meals}">
        <jsp:useBean id="tempMeal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${tempMeal.exceed ? 'exceeded' : 'normal'}" }>
            <td>${tempMeal.dateTime.toLocalDate()}</td>
            <td>${tempMeal.description}</td>
            <td>${tempMeal.calories}</td>
            <br/>
        </tr>
    </c:forEach>


</table>


</body>
</html>
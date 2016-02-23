<%-- 
    Document   : AuthorResponse
    Created on : Feb 8, 2016, 5:29:12 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container" id="tableContent">
            <h2>Author Table!</h2>
            <table id="authorTable" class="table table-striped table-responsive">
                <tr>
                    <th>Author ID</th>
                    <th>Name</th>
                    <th>Date Added</th>
                </tr>
                <c:forEach var="a" items="${authors}">
                    <tr>

                        <td> ${a.authorId} </td>
                        <td> ${a.authorName} </td>
                        <td>
                            <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </div>
        <div class="container" id="goBackContainer">
            <button type="button" id="goBack" class="btn btn-primary"><a href="http://localhost:8080/bookWebApp2/index.html">Go Back</a></button>
        </div>

        <p>${errorMsg}</p>
        
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
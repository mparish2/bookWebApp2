<%-- 
    Document   : BookEdit
    Created on : Apr 2, 2016, 3:24:32 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDIT BOOKS</title>
        <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <h2>Edit a Book</h2>

        <div class="container">
            <form method="POST" action="BookController">
                <table id="bookTable" class="table table-striped table-responsive"> 
                    <tr>
                        <td>ID</td>
                        <td><input type="text" value="${book.bookId}" name="bookId" readonly/></td>
                    </tr>  
                    <tr>
                        <td>Title</td>
                        <td><input type="text" value="${book.title}" name="title" /></td>
                    </tr>
                    <tr>
                        <td>ISBN</td>
                        <td><input type="text" value="${book.isbn}" name="isbn" /></td>
                    </tr>
                    <tr>
                        <td>Authors</td>
                        <td>
                            <select id="authorDropDown" name="authorId">
                                <c:choose>
                                    <c:when test="${not empty book.authorId}">
                                        <option value="">None</option>
                                        <c:forEach var="author" items="${authors}">                                       
                                            <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                            <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>${author.authorName}</option>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </td>
                    </tr>

                </table>
                <input type="submit" class="btn btn-info" value="Cancel" name="taskType" />
                <input type="submit" class="btn btn-success" value="Save" name="taskType" />
            </form>
            <p id="errors">${errorMsg}</p>
        </div>


        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

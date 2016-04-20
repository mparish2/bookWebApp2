<%-- 
    Document   : BookResponse
    Created on : Apr 2, 2016, 2:58:43 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Books Management</title>
        <link rel="stylesheet" href='<%= this.getServletContext().getContextPath() + "/Content/BookWebApp.css"%>'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link href="Content/bootstrap-switch.min.css" rel="stylesheet">
    </head>
    <body>
        <h2>Books Table!</h2>

        <div class="container" id="tableContent">
            <br>

            <form method="POST" action="BookController?taskType=AEDBooks">
                <sec:csrfInput />
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <div id="deleteToggleContent" class="bootstrap-switch-container">
                        <label id="deleteLabel" for="deleteToggle">
                            Delete Type
                        </label>
                        <input type="checkbox"  id="deleteToggle" name="my-checkbox" value="true" data-size="normal" data-label-width="0" data-handle-width="auto" data-on-text="Single" data-off-text="Multiple" data-off-color="warning" checked=true>
                        <!-- <label>This Switch is Set to
                             <label id="CheckBoxValue2" value="None"></label>
                         </label -->
                    </div>
                    <div class="input-group" id="aedButtons">
                        <input type="submit" class="btn btn-success" value="Add" name="submit" >
                        <input type="submit" class="btn btn-warning" value="Edit" name="submit" />&nbsp;&nbsp;
                        <input type="submit"  class="btn btn-danger" value="Delete" name="submit" />

                    </div>
                </sec:authorize>
                <table id="bookTable" class="table table-striped table-responsive">
                    <tr >
                        <th>ID</th>
                        <th>Title</th>
                        <th>ISBN</th>
                        <th>Author</th>
                    </tr>

                    <c:forEach var="b" items="${books}">
                        <tr>
                            <td><input type="checkbox" name="bookId" value="${b.bookId}" /></td>
                            <td>${b.title}</td>
                            <td>${b.isbn}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty b.authorId}">
                                        ${b.authorId.authorName}
                                    </c:when>
                                    <c:otherwise>
                                        None
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>

                </table>

                <div class="input-group">
                    <input type="submit" class="btn btn-success" value="Add" name="submit" />
                    <input type="submit" class="btn btn-warning" value="Edit" name="submit" />&nbsp;&nbsp;
                    <input type="submit"  class="btn btn-danger" value="Delete" name="submit" /> 

                </div>

            </form>

            <!-- add a new link for user -->
             <sec:authorize access="hasAnyRole('ROLE_USER')">
                <a href='<%= this.getServletContext().getContextPath() + "/UserHomePage.jsp"%>'> <button type="button" id="goBack" class="btn btn-primary">Go Back</button></a>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
                <a href='<%= this.getServletContext().getContextPath() + "/admin/index.jsp"%>'> <button type="button" id="goBack" class="btn btn-primary">Go Back</button></a>
            </sec:authorize>
            <p id="errors">${errorMsg}</p>


        </div>

        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="Content/bootstrap-switch.min.js"></script>
        <script>
            $("[name='my-checkbox']").bootstrapSwitch();
        </script>
    </body>
</html>

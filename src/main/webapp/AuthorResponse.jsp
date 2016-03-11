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
        <title>Author Management</title>
        <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link href="Content/bootstrap-switch.min.css" rel="stylesheet">
    </head>
    <body id="authorRespBody">
        
        <div class="logoutArea container">
             <form method="POST" action="AuthorController?taskType=Logout">
                <p id="welcomeMessAuth">Welcome  ${message}</p>
                <input type="submit" class="btn btn-primary" value="Logout" id="logout" name="taskType" />
             </form>
           
        </div>
       
        <h2>Author Table!  </h2>  
        <div class="container" id="tableContent">
            
            

            <br>

            <form method="POST" action="AuthorController?taskType=AEDAuthor">
                  <div id="deleteToggleContent" class="bootstrap-switch-container">
                    <label id="deleteLabel" for="deleteToggle">
                        Delete Type
                    </label>
                    <input type="checkbox"  id="deleteToggle" name="my-checkbox" value="true" data-size="normal" data-label-width="0" data-handle-width="auto" data-on-text="Single" data-off-text="Multiple" data-off-color="warning" checked=true>
                    <!--<label>This Switch is Set to
                        <label id="CheckBoxValue2" value="None"></label>
                    </label -->
                </div>
                <div class="input-group" id="aedButtons">
                    <input type="submit" class="btn btn-success" value="Add" name="submit" >
                    <input type="submit" class="btn btn-warning" value="Edit" name="submit" />&nbsp;&nbsp;
                    <input type="submit"  class="btn btn-danger" value="Delete" name="submit" />
                    
                </div>
                  
                <table id="authorTable" class="table table-striped table-responsive">
                    <tr>
                        <th>Author ID</th>
                        <th>Name</th>
                        <th>Date Added</th>
                    </tr>
                    <c:forEach var="a" items="${authors}">
                        <tr>

                            <td> <input type="checkbox" name="authorId" value="${a.authorId}" /> </td>
                            <td> ${a.authorName} </td>
                            <td>
                                <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                                </td>
                            </tr>
                    </c:forEach>
                </table>
                <div class="input-group">
                    <input type="submit" class="btn btn-success" value="Add" name="submit" />
                    <input type="submit" class="btn btn-warning" value="Edit" name="submit" />&nbsp;&nbsp;
                    <input type="submit"  class="btn btn-danger" value="Delete" name="submit" /> 
                    
                </div>

                <br>
                <br>
               
            </form>
             <button type="button" id="goBack" class="btn btn-primary"><a href="http://localhost:8080/bookWebApp2/index.jsp">Go Back</a></button>
             <p id="errors">${errorMsg}</p>
             
              
        </div>
       

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="Content/bootstrap-switch.min.js"></script>
        <script>
            $("[name='my-checkbox']").bootstrapSwitch();
        </script>

    </body>
</html>
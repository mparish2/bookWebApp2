<%-- 
    Document   : DeleteAuthorResponse
    Created on : Feb 16, 2016, 4:53:02 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container" id="deleteContent">
            <h2>Delete a Author!</h2>
            
        <form  method="POST" action="AuthorController?taskType=deleteAuthor" id="deleteForm" name="deleteForm">
            <input type="text" name="authorId" value="" placeholder="Enter Author Id"/>
            <input class="btn btn-danger btn-sm" type="submit"  name="submit" value="Submit"/>
        </form>

        <p id="authorsDelP">Authors Deleted: ${authorIdResp}</p>
        
        </div>
        
        <div class="container" id="goBackContainer">
            <button type="button" id="goBack" class="btn btn-primary"><a href="http://localhost:8080/bookWebApp2/index.html">Go Back</a></button>
        </div>
        
        <p>${errorMsg}</p>
        
        
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

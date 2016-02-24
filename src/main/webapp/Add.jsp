<%-- 
    Document   : Add
    Created on : Feb 23, 2016, 8:15:22 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add</title>
         <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h2>Add a Author</h2>
            <table id="authorTable" class="table table-striped table-responsive"> 
                <tr>
                    <td>Name</td>
                    <td><input type="text" value="${author.authorName}" name="authorName" /></td>
                </tr>
              
            </table>
        </div>
        
        
        
        
        
        
        
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

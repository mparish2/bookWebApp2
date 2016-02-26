<%-- 
    Document   : Edit
    Created on : Feb 23, 2016, 8:15:43 PM
    Author     : Matthew_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit</title>
        <link rel="stylesheet" href="Content/BookWebApp.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        
        <div class="container">
            <h2>Edit a Author</h2>
            <form method="POST" action="AuthorController">
            <table id="authorTable" class="table table-striped table-responsive">
                <tr>
                    <td>ID</td>
                    <td><input readonly type="text" value="${author.authorId}" name="authorId" /></td>
                </tr> 
                <tr>
                    <td>Name</td>
                    <td><input type="text" value="${author.authorName}" name="authorName" /></td>
                </tr>
                <tr> 
                    <td>Date Added</td>
                    <td><input readonly type="text" value="${author.dateAdded}" name="dateAdded" /></td>
                </tr>
            </table>
                <input type="submit" class="btn btn-info" value="Cancel" name="taskType" />
                <input type="submit" class="btn btn-success" value="Save" name="taskType" />
            </form>
        </div>
         
         
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

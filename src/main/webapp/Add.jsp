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
    <body id="addBody">
        <div class="container" id="addContent">
            <h2>Add a Author</h2>
            <form method="POST" action="AuthorController">
            <table id="authorTable" class="table table-striped table-responsive"> 
                <tr>
                    <td>Name</td>
                    <td><input type="text"  name="authorName" /></td>
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

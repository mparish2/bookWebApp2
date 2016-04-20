 


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Parish-BookWebApp</title>
        <link rel="stylesheet" href='<%= this.getServletContext().getContextPath() + "/Content/BookWebApp.css"%>'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body id="HomeBody">
        <div class="container" id="content"> 
            <header>
                <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">           
                <label id="logout" class="btn btn-primary"><a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Out</a> </label>
            </sec:authorize>
                
                <div id="welcomeMessagePre">
                    <p class="welcomeMessage">${message}</p>
                </div>
                <h2 id="indexH2">Pick a Task</h2>
            </header>
                
            
                
            <div class="dropdown" id="taskButton">
                <button class="btn btn-primary" id="dLabel" data-target="#" data-toggle="dropdown" type="button"  aria-haspopup="true" aria-expanded="false">
                    Task Menu
                    <span class="caret"></span>
                </button>

                <ul id="taskMenu" class="dropdown-menu" aria-labelledby="dLabel">
                    <li><a href= '<%= this.getServletContext().getContextPath() + "/AuthorController?taskType=viewAuthors"%>'>Manage Authors <span class="glyphicon glyphicon-user" aria-hidden="true"></span></a></li>
                    <li><a href='<%= this.getServletContext().getContextPath() + "/BookController?taskType=viewBooks"%>'>Manage Books <span class="glyphicon glyphicon-book" aria-hidden="true"></span></a></li>

                </ul>
            </div>

          


          

        </div>
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="Content/BookWebApp.js" type="text/javascript"></script>
    </body>
</html>
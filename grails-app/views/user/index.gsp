<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Form</title>
	</head>
	<body>
        <div class="card logincard">
            <div class="card-body">
        <g:form  controller="User" action="loginuser" method="POST" enctype="application/x-www-form-urlencoded">
             
            <div class="form-group row">
                    <label for="userName" class="col-sm-2 col-form-label">Username</label>
                    <div class="col-sm-10">
                    <input required name="name" type="text" class="form-control" id="userName" value="">
                    </div>
                </div>
            <button class="btn btn-default" type="submit">Login</button>
        </g:form></div>
    </div>
	</body>
</html>
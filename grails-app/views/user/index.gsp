<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Form</title>
	</head>
	<body>
        <div class="card logincard" id="frmLogin">
            <div class="card-body">
        <g:form  controller="User" action="loginuser" method="POST" enctype="application/x-www-form-urlencoded">
             <div class="form-group row">
                 <label for="userNameE" class="col-sm-2 col-form-label">Sign in as </label>
                 <div class="col-sm-10">
                     <g:each var="user" in="${users}">
                         <button data-bind="click: onClickSignin" class="btn btn-default btn-block">${user.name}</button>
                     </g:each>
                 </div>
             </div>
            <div class="form-group row">
                    <label for="userName" class="col-sm-2 col-form-label">... or enter name</label>
                    <div class="col-sm-10">
                    <input required name="name" type="text" class="form-control" id="userName" value="">
                    </div>
                </div>
            <button class="btn btn-primary float-right" type="submit">Login</button>
        </g:form></div>
    </div>
	</body>
</html>
<content tag="js">        
          <asset:javascript src="signin.js" />
</content>
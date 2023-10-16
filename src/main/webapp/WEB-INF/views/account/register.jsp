<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form modelAttribute="user" action="/account/register" enctype="multipart/form-data">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-danger pull-right">${message}</div>
			<h4 class="panel-title">REGISTRATION</h4>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label>Username</label>
				<form:errors path="id"/>
				<form:input path="id" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Password</label>
				<form:errors path="password"/>
				<form:password path="password" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Confirm Password</label>
				<input name="confirm" type="password" value="${param.confirm}" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Fullname</label>
				<form:errors path="fullname"/>
				<form:input path="fullname" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Email Address</label>
				<form:errors path="email"/>
				<form:input path="email" class="form-control"/>
			</div>
			<div class="form-group">
				<label>Photo</label>
				<form:hidden path="photo"/>
				<input name="photo_file" type="file" class="form-control"/>
			</div>
		</div>
		<div class="panel-footer">
			<form:hidden path="activated"/>
			<form:hidden path="admin"/>
			<button class="btn btn-warning">
				<span class="glyphicon glyphicon-user"></span> Register
			</button>	
		</div>
	</div>
</form:form>
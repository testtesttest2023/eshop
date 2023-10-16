<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form modelAttribute="user" action="/account/edit" enctype="multipart/form-data">
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-danger pull-right">${message}</div>
			<h4 class="panel-title">EDIT PROFILE</h4>
		</div>
		<div class="panel-body">
			<div class="row">
				<!-- PHOTO -->
				<div class="col-sm-4 text-center">
					<img src="/static/images/customers/${user.photo}" style="max-width: 99%; height: 173px">
					<form:hidden path="photo"/>
					<input name="photo_file" type="file" class="form-control"/>
				</div>
				<!-- FORM -->
				<div class="col-sm-8">
					<div class="form-group">
						<label>Username</label>
						<form:input path="id" readonly="true" class="form-control"/>
						<form:errors path="id"/>
					</div>
					<div class="form-group">
						<label>Fullname</label>
						<form:input path="fullname" class="form-control"/>
						<form:errors path="fullname"/>
					</div>
					<div class="form-group">
						<label>Email Address</label>
						<form:input path="email" class="form-control"/>
						<form:errors path="email"/>
					</div>
				</div>
			</div>
		</div>
		<div class="panel-footer">
			<form:hidden path="password"/>
			<form:hidden path="activated"/>
			<form:hidden path="admin"/>
			<button class="btn btn-warning">
				<span class="glyphicon glyphicon-user"></span> Update Profile
			</button>	
		</div>
	</div>
</form:form>
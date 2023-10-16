<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<c:forEach var="p" items="${prods}">
	<div class="col-md-4 col-sm-6 nn-prod" data-pid="${p.id}">
		<div class="panel panel-success">
			<div class="panel-heading text-center">
				<h4 class="panel-title">${p.name}</h4>
			</div>
			<div class="panel-body text-center">
				<a href="/product/detail/${p.id}"><img src="/static/images/products/${p.image}"/></a>
			</div>
			<div class="panel-footer">
				<div class="row">
					<div class="col-xs-3">$${p.unitPrice}</div>
					<div class="col-xs-9 text-right">
						<%@include file="btn-prod.jsp" %>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${p.discount > 0}">
					<img src="/static/images/promo-icon.gif"/>
				</c:when>
				<c:when test="${p.available}">
					<img src="/static/images/new-icon.gif"/>
				</c:when>
			</c:choose>
		</div>
	</div>
</c:forEach>
<%@include file="dialog.jsp" %>
<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="/static/slideshow/js/modernizr.custom.63321.js"></script>
<script src="/static/slideshow/js/jquery.catslider.js"></script>
<link href="/static/slideshow/css/catslider.css" rel="stylesheet" />
<style>
    .mi-slider {
        height: 310px;
    }
    .mi-slider ul li img {
        height: 180px;
    }
</style>
<script>
    $(function () {
        showCatSlider('.mi-slider', 5000);
    });
</script>

<div class="mi-slider">
	<c:forEach var="c" items="${cate5}">
		<ul>
			<c:forEach var="p" items="${c.products}">
				<li><a href="/product/detail/${p.id}"><img src="/static/images/products/${p.image}" /><h4>${p.unitPrice}</h4></a></li>
			</c:forEach>
		</ul>
	</c:forEach>
	<nav>
		<c:forEach var="c" items="${cate5}">
			<a href="#">${c.name}</a>
		</c:forEach>
	</nav>
</div>



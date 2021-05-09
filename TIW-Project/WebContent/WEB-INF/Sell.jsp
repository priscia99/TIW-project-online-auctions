<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" />
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" media="all"
	href="/TIW-Project/css/style.css" />
<title>Online Auction - Sell</title>
<style>
#logoff-btn {
	font-size: 12px;
	padding: 5px 10px;
}

#create-popup {
	padding: 20px;
	display: none;
}

#create-popup:target {
	display: block;
}

#create-form {
	padding: 20px;
}

#create-form:target {
	display: none;
}

#price, #rise {
	width: 2em;
}

.auction-list {
	list-style: none;
	padding: 0;
	margin: 0;
	width: 100%;
}

.auction {
	border-top: 1px solid var(- -detail);
	display: grid;
	grid-template-columns: 100px 1fr auto;
	grid-template-rows: 100px;
	align-items: center;
	align-content: center;
	justify-content: center;
	grid-gap: 20px;
	padding: 20px;
}

.auction-img {
	width: 100px;
	height: 100px;
	object-fit: cover;
}

.auction-list-wrapper {
	height: calc(100vh - 250px);
	align-items: center;
	scrollbar-width: none; /* Firefox */
	-ms-overflow-style: none; /* IE 10+ */
}

.auction-list-wrapper::-webkit-scrollbar {
	display: none; /* Chromium */
}

#active-list, #closed-list {
	min-width: 400px;
	margin: 10px 10px;
}

.filter-bar {
	height: 30px;
	align-items: center;
}

.filter>li {
	width: 100px;
}

.filter:hover .filter-menu {
	display: block;
}

input.text-field {
	height: 30px;
}

#price, #rise {
	width: 80px;
}
</style>
</head>
<body>
	<div class="container navbar outer-shadow columns centered-flex">
		<div class="nav-logo">
			<img src="/TIW-Project/img/logo.svg" alt="" class="small-logo">
		</div>
		<div class="nav-links">
			<a href="home" class="text-primary-accent">Home</a> <a href="buy"
				class="text-primary-accent">Buy</a> <a href="sell"
				class="active text-primary-accent">Sell</a>
		</div>
		<div class="nav-opt">
			<button class="round primary-accent text-secondary" id="logoff-btn">Log
				Off</button>
		</div>
	</div>

	<div class="rows centered-flex w100" id="header">
		<div class="columns m-nav spaced-between-flex w80">
			<div>
				<h2 th:text="${session.user.username}">Username</h2>
				<p>Manage your auctions</p>
			</div>
			<a href="#create-popup"><button
					class="round ph20 pv10 mv50 caps-lock outer-shadow">CREATE
					NEW</button></a>
		</div>
	</div>

	<div class="columns spaced-evenly-flex" style="flex-flow: row wrap;">
		<div class="rows w40" id="active-list">
			<div class="columns w100 color-solid filter-bar" style="z-index: 0">
				<div class="ph20 text-color-background">Open Auctions</div>
			</div>
			<div class="rows w100 scrollable auction-list-wrapper inner-shadow">
				<div class="color-solid w100 h100" style="z-index: -1;">
					<ul class="auction-list color-solid-l" style="z-index: -1;"
						th:each="openAuction : ${openAuctions}">
						<li class="auction color-solid-l"><img
							th:src="${openAuction.item.image}" alt="" class="auction-img">
							<a href="">
								<h3 style="max-height: 95px; overflow: hidden"
									th:text="${openAuction.item.name}">item name</h3>
						</a>
							<ul>
								<li th:text="${openAuction.item.id}">item id</li>
								<li th:text="${openAuction.currentPrice}">current price</li>
								<li th:text="${openAuction.endTimestamp}">time left</li>
							</ul></li>
						<!-- ecc. -->
					</ul>
					<p th:if="${#lists.isEmpty(openAuctions)}" class="ph20">Nothing
						to show</p>
				</div>
			</div>
		</div>

		<div class="rows w40" id="closed-list">
			<div class="columns w100 color-solid filter-bar" style="z-index: 0">
				<div class="ph20 text-color-background">Closed Auctions</div>
			</div>
			<div class="rows w100 scrollable auction-list-wrapper inner-shadow">
				<div class="color-solid w100 h100" style="z-index: -1;">
					<ul class="auction-list color-solid-l" style="z-index: -1;"
						th:each="closeAuction : ${closeAuctions}">
						<li class="auction color-solid-l"><img
							th:src="${openAuction.item.image}" alt="" class="auction-img">
							<a href="">
								<h3 style="max-height: 95px; overflow: hidden"
									th:text="${closeAuction.item.name}">item name</h3>
						</a>
							<ul>
								<li th:text="${closeAuction.item.id}">item id</li>
								<li th:text="${openAuction.currentPrice}">current price</li>
								<li th:text="${closeAuction.endTimestamp}">time left</li>
							</ul></li>
						<!-- ecc. -->
					</ul>
					<p th:if="${#lists.isEmpty(closeAuctions)}" class="ph20">Nothing
						to show</p>
				</div>
			</div>
		</div>
	</div>

	<div class="centered container round outer-shadow " id="create-popup">
		<div class="columns">
			<a href="#">
				<button class="round ph10 pv10 color-detail">close</button>
			</a>
		</div>
		<form action="submit-auction" class="create-form rows centered-flex"
			id="create-form" method="post" enctype="multipart/form-data">
			<h3 class="caps-lock">Create new auction</h3>
			<div class="columns">
				<div class="rows mh10">
					<div class="mv10">
						<label> Name: <br> <textarea style="margin-top: 5px;"
								name="item-name" id="name"
								class="multiline-text-field round inner-shadow" cols="30"
								rows="2"></textarea>
						</label>
					</div>
					<div class="mv10">
						<label> Description (max 2000 characters): <br> <textarea
								style="margin-top: 5px;" name="item-descritpion"
								id="description" class="multiline-text-field round inner-shadow"
								cols="30" rows="10"></textarea>
						</label>
					</div>
				</div>
				<div class="rows mh10">
					<div class="mv10">
						<label> Starting price: <br> <input type="number"
							class="round inner-shadow text-field" id="price" min="1"
							value="1" name="auction-starting-price">
						</label>
					</div>
					<div class="mv10">
						<label> Minimum rise: <br> <input type="number"
							class="round inner-shadow text-field" id="rise" min="1" value="1"
							name="auction-minimum-rise">
						</label>
					</div>
					<div class="mv10">
						<label> Expires on: <br> <input
							style="margin-top: 5px;" type="datetime-local"
							class="text-field round inner-shadow" id="end"
							name="auction-end-timestamp">
						</label>
					</div>
					<div class="mv10">
						<label> Load an image <br> <input type="file"
							style="margin-top: 5px;" name="item-image" id="image">
						</label>
					</div>
				</div>
			</div>
			<div class="mv10 columns spaced-evenly-flex">
				<button type="submit"
					class=" ph20 pv10 round color-accent text-color-background outer-shadow caps-lock">Confirm</button>
				<button type="reset"
					class="ph20 pv10 round color-background text-color-accent outer-shadow caps-lock">Cancel</button>
			</div>
		</form>
	</div>

</body>
</html>
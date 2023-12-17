# Web Technologies - Project [A.Y. 2020/2021]

This project aims at developing a platform that allows users to create and manage **online auctions**. 

Key features:
- Users create auctions specifying the item name, a short description, the initial price, minimum bid increment, and expiration date, and upload an image that shows the item
- Users can search through available auctions by specifying relevant keywords, such as the item name or description.
- Once an auction is live, users can place bids provided the item remains available and the auction has not reached its expiration date. 
- Upon the conclusion of an auction, the system promptly notifies the winning bidder and displays a comprehensive list of purchased items.

To develop this project, we used **Java servlets** to handle users' requests and provide responses, **MySQL** to persist users, bids, auctions, and **Thymeleaf**, a server-side template engine, to render dynamic content such as auction details, list of bidders, item description and details.

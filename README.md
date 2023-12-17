# Web Technologies - Final Project [A.Y. 2020/2021]
This is the final project assessment regarding the course **Tecnologie Informatiche per il Web (*Web Technologies*)**, A.Y 2020/2021.
## 💡 Overview
This project aims at developing a platform that allows users to create and manage **online auctions**. 

Key features:
- Users create auctions specifying the item name, a short description, the initial price, minimum bid increment, and expiration date, and upload an image that shows the item
- Users can search through available auctions by specifying relevant keywords, such as the item name or description
- Once an auction is live, users can place bids provided the item remains available and the auction has not reached its expiration date
- Upon the conclusion of an auction, the system promptly notifies the winning bidder and displays a comprehensive list of purchased items

To develop this project, we used **Java servlets** to handle users' requests and provide responses, **MySQL** to persist users, bids, auctions, and **Thymeleaf**, a server-side template engine, to render dynamic content such as auction details, list of bidders, item description and details.

## 📚 Useful Links
- [ER Diagram](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/er.png)
- **DAO Class Diagram** [[extended](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/UML_class.pdf)] [[compact](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/UML_class_compact.pdf)]
- **Relevant sequence diagrams** (more [here](https://github.com/priscia99/TIW-project-online-auctions/tree/master/Schemi/relazione)):
  - [Login](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/login_html.png)
  - [Home Page](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/home_html.png)
  - [Bid submission](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/bid_submission_html.png)
  - [Auction creation](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/create_auction_html.png)
- [Final report](https://github.com/priscia99/TIW-project-online-auctions/blob/master/Schemi/relazione/TIW_Project_Aste_Online_Group_17.pdf) (with functional analysis, architectural choices, ER, schemas, views, IFML and sequence diagrams)

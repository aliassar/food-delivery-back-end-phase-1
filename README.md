# Loghme Food Delivery, Back End Phase 1

First phase of the Loghme food-delivery back end: a small Java web server that serves restaurants, a user wallet and a shopping cart as server-rendered pages. It was written for a university course (University of Tehran, 2020-21).

This is coursework, not a production service.

## Parts of the project

- [food-delivery-back-end-phase-1](https://github.com/aliassar/food-delivery-back-end-phase-1) - this repo
- [food-delivery-back-end-phase-2](https://github.com/aliassar/food-delivery-back-end-phase-2) - Spring REST API with MySQL, JWT login and a scheduled "food party"
- [food-delivery-front-end](https://github.com/aliassar/food-delivery-front-end) - React front end

## What it does

`src/main/java/IE/JavalinServer.java` starts a Javalin server on port 12330 with these routes:

| Route | Behaviour |
| --- | --- |
| `GET /` | list all restaurants |
| `GET /r/` | list restaurants within distance 170 of the origin (0,0) |
| `GET /r/:restaurant-id` | restaurant page with its menu; 403 if too far, 404 if unknown |
| `GET /user/`, `POST /user/` | show the user profile, add credit to the wallet (`credit` form field) |
| `GET /cart/`, `POST /cart/` | show the cart, add a food item (`name`, `restaurantName`, `price`) |
| `POST /cart/finalize` | check the cart is non-empty and the wallet covers the total, then clear the cart and deduct the price |

Pages are HTML templates in `src/main/resources/` (`restaurants.html`, `restaurant.html`, `cart.html`, `user.html`). The user and cart live in a Javalin cookie store, not in a database.

## How it works

- Restaurant data is JSON (`id`, `name`, `location`, `menu`, `logo`). On start-up `serverConfig(test)` reads it from `src/test/resources/restaurants.json` when `test` is true (as `main` does), otherwise downloads it from a course-provided HTTP endpoint, and writes it to `src/main/resources/restaurants.json`. Handlers re-read that file on each request.
- `Loghme.java` holds the business logic: adding to the cart (a cart may only contain items from one restaurant, otherwise `DifRestaurants`), finalising an order, and a popularity score (average food popularity divided by distance) used to recommend the top three restaurants.
- `Loghme.main` is also a console mode that reads commands such as `addRestaurant`, `addFood`, `getRestaurants`, `getCart` from stdin, with JSON arguments, against the JSON files in `src/main/resources/`.
- Custom exceptions in `IE/Exceptions` cover the error cases (empty cart, insufficient money, unknown restaurant or food, restaurant too far).
- Tests are in `src/test/java/IE/P1/` (JUnit 4, Mockito).

## Tech

Java 8, Maven (WAR packaging), Javalin 3.7.0, Jackson 2.9.8, Thymeleaf 3.0.9, Unirest 3.4.04, JUnit 4.13, Mockito 3.2.4.

`JavalinServer` registers Pebble templates (`JavalinPebble`), but the Pebble library is not declared in `pom.xml`. Add it if the build fails on the templates.

## Run it

```
mvn test
mvn compile exec:java -Dexec.mainClass=IE.JavalinServer   # requires the exec plugin; or run JavalinServer.main from an IDE
```

Then open http://localhost:12330/. Run it from the repo root, since file paths such as `src/main/resources/restaurants.json` are relative.

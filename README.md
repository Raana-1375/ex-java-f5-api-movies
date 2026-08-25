# API Movies

REST API for managing a movie catalog, built with Spring Boot. Developed as part of the Factoria F5 bootcamp (Spring & Spring Boot module).

## Data Model

The data model includes four entities — `Movie`, `Genre`, `Year`, and `Actor` — with the following relationships:
- **Movie ↔ Year**: many-to-one (each movie has exactly one release year)
- **Movie ↔ Genre**: many-to-many (via a `movie_genre` junction table)
- **Movie ↔ Actor**: many-to-many (via a `movie_actor` junction table)

### Chen ER Diagram
![Chen ER Diagram](assets/chen-er-diagram.png)

### Crow's Foot Diagram
![Crow's Foot Diagram](assets/crows-foot-diagram.png)
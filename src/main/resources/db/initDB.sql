DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
  id               INT(10) NOT NULL AUTO_INCREMENT,
  name             VARCHAR(100) NOT NULL,
  email            VARCHAR(100) NOT NULL,
  password         VARCHAR(100) NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL,
  PRIMARY KEY (id)
)
AUTO_INCREMENT = 100000;

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER(10) NOT NULL,
  role    VARCHAR(100),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
  user_id INTEGER(10),
  id INTEGER(10) AUTO_INCREMENT NOT NULL,
  date_time       TIMESTAMP DEFAULT now() NOT NULL,
  description VARCHAR(255) NOT NULL,
  calories    INTEGER(10),
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
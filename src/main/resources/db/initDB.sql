DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
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

CREATE TABLE meals (
  id          INTEGER(10) PRIMARY KEY AUTO_INCREMENT,
  user_id     INTEGER(10)  NOT NULL,
  date_time   TIMESTAMP NOT NULL,
  description TEXT(255)      NOT NULL,
  calories    INT(10)       NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
)
 AUTO_INCREMENT = 100000;;
CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time)
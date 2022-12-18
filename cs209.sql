DROP TABLE IF EXISTS `commit`;
CREATE TABLE `commit`
(
    commit_id int          NOT NULL AUTO_INCREMENT,
    sha       varchar(11)  NOT NULL,
    author    varchar(300) NOT NULL,
    PRIMARY KEY (commit_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `issue`;
CREATE TABLE `issue`
(
    issue_id   int         NOT NULL AUTO_INCREMENT,
    state      varchar(10) NOT NULL,
    created_at DATETIME    NOT NULL,
    updated_at DATETIME,
    closed_at  DATETIME,
    PRIMARY KEY (issue_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `releases`;
CREATE TABLE `releases`
(
    release_id   int      NOT NULL AUTO_INCREMENT,
    created_at   DATETIME NOT NULL,
    published_at DATETIME,
    PRIMARY KEY (release_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;







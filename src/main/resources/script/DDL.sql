USE nhn_academy_2;

-- -----------------------------------------------------
-- Table `nhn_academy_2`.`AUTHORITY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`AUTHORITY`
(
    `CODE`      CHAR(2)     NOT NULL,
    `NAME`      VARCHAR(45) NOT NULL,
    `CREATE_AT` DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`CODE`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`PROJECT_STATE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`PROJECT_STATE`
(
    `CODE`      CHAR(2)     NOT NULL,
    `NAME`      VARCHAR(45) NOT NULL,
    `CREATE_AT` DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`CODE`)
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`PROJECT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`PROJECT`
(
    `ID`         INT         NOT NULL AUTO_INCREMENT,
    `STATE_CODE` VARCHAR(4)  NOT NULL,
    `NAME`       VARCHAR(45) NOT NULL,
    `CREATE_AT`  DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`ID`),
    INDEX `fk_PROJECT_STATE_idx` (`STATE_CODE` ASC) VISIBLE,
    CONSTRAINT `fk_PROJECT_STATE`
        FOREIGN KEY (`STATE_CODE`)
            REFERENCES `nhn_academy_2`.`PROJECT_STATE` (`CODE`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`MILE_STONE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`MILE_STONE`
(
    `ID`         INT         NOT NULL AUTO_INCREMENT,
    `PROJECT_ID` INT         NOT NULL,
    `NAME`       VARCHAR(45) NOT NULL,
    `START_DATE` DATE        NULL,
    `END_DATE`   DATE        NULL,
    `CREATE_AT`  DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`ID`),
    INDEX `fk_PROJECT_has_MILE_STONE_PROJECT1_idx` (`PROJECT_ID` ASC) VISIBLE,
    CONSTRAINT `fk_PROJECT_has_MILE_STONE_PROJECT1`
        FOREIGN KEY (`PROJECT_ID`)
            REFERENCES `nhn_academy_2`.`PROJECT` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`TASK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`TASK`
(
    `ID`            INT         NOT NULL AUTO_INCREMENT,
    `PROJECT_ID`    INT         NOT NULL,
    `WRITER_ID`     VARCHAR(40) NOT NULL,
    `MILE_STONE_ID` INT         NULL,
    `CREATE_AT`     DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`ID`),
    INDEX `fk_TASK_PROJECT1_idx` (`PROJECT_ID` ASC) VISIBLE,
    INDEX `fk_TASK_ACCOUNT1_idx` (`WRITER_ID` ASC) VISIBLE,
    INDEX `fk_TASK_PROJECT_MILE_STONE1_idx` (`MILE_STONE_ID` ASC) VISIBLE,
    CONSTRAINT `fk_TASK_PROJECT1`
        FOREIGN KEY (`PROJECT_ID`)
            REFERENCES `nhn_academy_2`.`PROJECT` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_TASK_PROJECT_MILE_STONE1`
        FOREIGN KEY (`MILE_STONE_ID`)
            REFERENCES `nhn_academy_2`.`MILE_STONE` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`PROJECT_ACCOUNT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`PROJECT_ACCOUNT`
(
    `PROJECT_ID`     INT         NOT NULL,
    `ACCOUNT_ID`     VARCHAR(40) NOT NULL,
    `AUTHORITY_CODE` VARCHAR(4)  NOT NULL,
    `CREATE_AT`      DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`PROJECT_ID`, `ACCOUNT_ID`, `AUTHORITY_CODE`),
    INDEX `fk_PROJECT_has_ACCOUNT_ACCOUNT1_idx` (`ACCOUNT_ID` ASC) VISIBLE,
    INDEX `fk_PROJECT_has_ACCOUNT_PROJECT1_idx` (`PROJECT_ID` ASC) VISIBLE,
    INDEX `fk_PROJECT_has_ACCOUNT_AUTHORITY1_idx` (`AUTHORITY_CODE` ASC) VISIBLE,
    CONSTRAINT `fk_PROJECT_has_ACCOUNT_PROJECT1`
        FOREIGN KEY (`PROJECT_ID`)
            REFERENCES `nhn_academy_2`.`PROJECT` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_PROJECT_has_ACCOUNT_AUTHORITY1`
        FOREIGN KEY (`AUTHORITY_CODE`)
            REFERENCES `nhn_academy_2`.`AUTHORITY` (`CODE`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`TAG`
(
    `ID`         INT         NOT NULL AUTO_INCREMENT,
    `PROJECT_ID` INT         NOT NULL,
    `NAME`       VARCHAR(45) NOT NULL,
    `CREATE_AT`  DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`ID`),
    INDEX `fk_TAG_PROJECT1_idx` (`PROJECT_ID` ASC) VISIBLE,
    CONSTRAINT `fk_TAG_PROJECT1`
        FOREIGN KEY (`PROJECT_ID`)
            REFERENCES `nhn_academy_2`.`PROJECT` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`TASK_TAG`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`TASK_TAG`
(
    `TASK_ID`   INT      NOT NULL,
    `TAG_ID`    INT      NOT NULL,
    `CREATE_AT` DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`TASK_ID`, `TAG_ID`),
    INDEX `fk_TASK_has_TAG_TASK1_idx` (`TASK_ID` ASC) VISIBLE,
    INDEX `fk_TASK_TAG_TAG1_idx` (`TAG_ID` ASC) VISIBLE,
    CONSTRAINT `fk_TASK_has_TAG_TASK1`
        FOREIGN KEY (`TASK_ID`)
            REFERENCES `nhn_academy_2`.`TASK` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_TASK_TAG_TAG1`
        FOREIGN KEY (`TAG_ID`)
            REFERENCES `nhn_academy_2`.`TAG` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nhn_academy_2`.`COMMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nhn_academy_2`.`COMMENT`
(
    `ID`        INT         NOT NULL AUTO_INCREMENT,
    `PARENT_ID` INT         NULL,
    `TASK_ID`   INT         NOT NULL,
    `WRITER_ID` VARCHAR(40) NOT NULL,
    `CONTENT`   VARCHAR(45) NOT NULL,
    `CREATE_AT` DATETIME    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`ID`),
    INDEX `fk_COMMENT_TASK1_idx` (`TASK_ID` ASC) VISIBLE,
    INDEX `fk_COMMENT_ACCOUNT1_idx` (`WRITER_ID` ASC) VISIBLE,
    INDEX `fk_COMMENT_COMMENT1_idx` (`PARENT_ID` ASC) VISIBLE,
    CONSTRAINT `fk_COMMENT_TASK1`
        FOREIGN KEY (`TASK_ID`)
            REFERENCES `nhn_academy_2`.`TASK` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_COMMENT_COMMENT1`
        FOREIGN KEY (`PARENT_ID`)
            REFERENCES `nhn_academy_2`.`COMMENT` (`ID`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;
-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------

-- Indexes for primary keys have been explicitly created.

-- ---------- Table for validation queries from the connection pool -----------

DROP TABLE PingTable;
CREATE TABLE PingTable (foo CHAR(1));

-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE Event;
DROP TABLE Response;
-- --------------------------------- Event -------------------------------------
CREATE TABLE Event ( eventId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) COLLATE latin1_bin NOT NULL,
    description VARCHAR(1024) COLLATE latin1_bin,
    dateSt TIMESTAMP NOT NULL,
    dateEnd TIMESTAMP NOT NULL,
    intern BIT NOT NULL,
    address VARCHAR(255) NOT NULL,
    capacity SMALLINT NOT NULL, 
    CONSTRAINT EventPK PRIMARY KEY(eventId)) ENGINE = InnoDB;

CREATE INDEX EventIndexByEventId ON Event (eventId);

-- --------------------------------- Response ----------------------------------
CREATE TABLE Response (responseId BIGINT NOT NULL AUTO_INCREMENT,
    userId VARCHAR(255) COLLATE latin1_bin NOT NULL,
    eventId BIGINT NOT NULL REFERENCES Event(eventId),
    date TIMESTAMP NOT NULL,
    assists BIT NOT NULL, 
    CONSTRAINT ResponsePK PRIMARY KEY(responseId)) ENGINE = InnoDB;

CREATE INDEX EventIndexByResponseId ON Response (responseId);



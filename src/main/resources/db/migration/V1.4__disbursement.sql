CREATE TABLE IF NOT EXISTS SQ_DISBURSEMENT(

id               BIGINT          NOT NULL,
merchantId       BIGINT          NOT NULL,
amount           DECIMAL         NOT NULL,
year             BIGINT          NOT NULL,
week             BIGINT          NOT NULL,

CONSTRAINT PK_SQ_DISBURSEMENT PRIMARY KEY (ID)
);

ALTER TABLE SQ_DISBURSEMENT
    ADD CONSTRAINT FK_DISBURSEMENT_MERCHANTID FOREIGN KEY (merchantId) REFERENCES SQ_MERCHANT (id);

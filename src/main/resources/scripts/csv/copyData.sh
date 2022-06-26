#!/bin/bash

echo "Starting csv files import to DB"
psql postgresql://seq:seq01@localhost:5432/dev_seq -c "\copy SQ_MERCHANT from './merchants.csv' WITH DELIMITER ','"
psql postgresql://seq:seq01@localhost:5432/dev_seq -c "\copy SQ_SHOPPER from './shoppers.csv' WITH DELIMITER ','"
psql postgresql://seq:seq01@localhost:5432/dev_seq -c "\copy SQ_ORDER from './orders.csv' WITH DELIMITER ','"
echo "Finish coping csv files"
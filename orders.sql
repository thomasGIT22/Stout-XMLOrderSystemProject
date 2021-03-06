/************************************
Name: Thomas Stout
Date: 10-21-20
Author Name: Thomas Stout
Description: Creation of orders database, and stored procedures for CRUD functions
************************************/
DROP DATABASE IF EXISTS orders;
CREATE DATABASE orders;
use orders;

DROP TABLE IF EXISTS Orderz;
CREATE TABLE Orderz(
    Order_Number VARCHAR(25) NOT NULL, 
    Order_Date DATE NOT NULL, 
    Vendor_ID INT NOT NULL,
    PRIMARY KEY(Order_Number)
);

INSERT INTO Orderz(
    Order_Number,
    Order_Date,
    Vendor_ID
) VALUES (
    'BBY01-12345678',
    '2020-10-21',
    12345
);

SELECT * FROM Orderz;

DELIMITER $$
CREATE PROCEDURE sp_add_Order(
    IN p_Order_Number VARCHAR(25),
    IN p_Order_Date DATE, 
    IN p_Vendor_ID INT
)
BEGIN
    INSERT INTO Orderz(
        Order_Number,
        Order_Date,
        Vendor_ID
    )
    VALUES (
        p_Order_Number,
        p_Order_Date,
        p_Vendor_ID
    );
END$$
DELIMITER ;

CALL sp_add_Order('BBY01-12345679','2020-10-21',12346);
CALL sp_add_Order('BBY01-12345680','2020-10-21',12347);
CALL sp_add_Order('BBY01-12345681','2020-10-21',12348);
CALL sp_add_Order('BBY01-12345682','2020-10-21',12349);

SELECT Order_Number, Order_Date, Vendor_ID
FROM Orderz;

DELIMITER $$
CREATE PROCEDURE sp_get_all_Orders( 
)
BEGIN
    SELECT Order_Number, Order_Date, Vendor_ID
    FROM Orderz;
END$$
DELIMITER ;

CALL sp_get_all_Orders;

SELECT Order_Number, Order_Date, Vendor_ID
FROM Orderz
WHERE Order_Number = 'BBY01-12345678';

DELIMITER $$
CREATE PROCEDURE sp_get_Order_by_Order_Number(
    IN p_Order_Number VARCHAR(25)
) 
BEGIN
    SELECT Order_Number, Order_Date, Vendor_ID
    FROM Orderz
    WHERE Order_Number = p_Order_Number;
END$$
DELIMITER ;

CALL sp_get_Order_by_Order_Number('BBY01-12345678');

UPDATE Orderz
SET
    Order_Number = 'BBY01-12345682',
    Order_Date = '2020-10-21',
    Vendor_ID = 12335
WHERE Order_Number = 'BBY01-12345683'; 

CALL sp_get_Order_by_Order_Number('BBY01-12345682');

DELIMITER $$
CREATE PROCEDURE sp_update_Order(
    IN p_original_Order_Number VARCHAR(25),
    IN p_updated_Order_Number VARCHAR(25),
    IN p_updated_Order_Date DATE,
    IN p_updated_Vendor_ID INT
)
BEGIN
    -- Make sure the original record exists
    DECLARE var_record_count INT DEFAULT 0;
    
    SELECT COUNT(*) INTO var_record_count
	FROM Orderz
	WHERE Order_Number = p_original_Order_Number;
    
    IF var_record_count <> 1 THEN
        SIGNAL SQLSTATE '53000'
        SET MESSAGE_TEXT = 'Cannot update; no such record.';
    END IF;
    
    -- Update the record
    UPDATE Orderz
        SET Order_Number = p_updated_Order_Number,
        Order_Date = p_updated_Order_Date,
        Vendor_ID = p_updated_Vendor_ID
    WHERE Order_Number = p_original_Order_Number;
        
    SELECT ROW_COUNT() AS 'Updated';
END$$
DELIMITER ;

CALL sp_update_Order('BBY01-12345682','BBY01-12345682','2020-10-21',12245);
CALL sp_get_Order_by_Order_Number('BBY01-12345682');

DELETE FROM Orderz
WHERE Order_Number = 'BBY01-12345682'; 

DELIMITER $$
CREATE PROCEDURE sp_delete_from_Order(
    IN p_original_Order_Number VARCHAR(25)
)
BEGIN
    DELETE FROM Orderz
    WHERE Order_Number = p_original_Order_Number;
    
    SELECT ROW_COUNT() AS 'Deleted';
END$$
DELIMITER ;

CALL sp_delete_from_Order('BBY01-12345682');

CALL sp_get_all_Orders();


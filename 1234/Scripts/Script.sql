INSERT INTO MENU(menu_id,CATEGORY_ID,MENU_NAME,price,FILE_NAME)
VALUES(seq_menu.nextval,1,'아메리카노',2500,'12313132.png')

INSERT INTO MENU(menu_id,CATEGORY_ID,MENU_NAME,price,FILE_NAME)
VALUES(seq_menu.nextval,1,'카페라떼',3500,'카페라떼.png')



INSERT INTO CATEGORY(category_id,CATEGORY_NAME)
VALUES(1,'커피')


SELECT * FROM MENU
SELECT * FROM CATEGORY
SELECT * FROM STOCK
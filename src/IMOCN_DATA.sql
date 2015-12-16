--------------------------------------------------------------------------------------	BUSINESS UNIT ---------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_BU (BU_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ('BU1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BU (BU_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ('BU2', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BU (BU_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ('BU3', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BU (BU_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ('BU4', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BU (BU_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ('BU5', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

--------------------------------------------------------------------------------------	DISTRICT --------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_DISTRICT (BU_CODE, DISTRICT_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BU_CODE FROM dbo.T_BU WHERE BU_NAME='BU1'), 'DIST1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_DISTRICT (BU_CODE, DISTRICT_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BU_CODE FROM dbo.T_BU WHERE BU_NAME='BU2'), 'DIST2', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_DISTRICT (BU_CODE, DISTRICT_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BU_CODE FROM dbo.T_BU WHERE BU_NAME='BU3'), 'DIST3', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_DISTRICT (BU_CODE, DISTRICT_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BU_CODE FROM dbo.T_BU WHERE BU_NAME='BU4'), 'DIST4', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_DISTRICT (BU_CODE, DISTRICT_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BU_CODE FROM dbo.T_BU WHERE BU_NAME='BU5'), 'DIST5', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

--------------------------------------------------------------------------------------	BRANCH ----------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST1'), '0986', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST1'), '1086', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST2'), '1186', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST2'), '1286', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST3'), '2586', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST3'), '2686', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST4'), '2786', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_BRANCH (DISTRICT_CODE, BRANCH_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT DISTRICT_CODE FROM dbo.T_DISTRICT WHERE DISTRICT_NAME='DIST5'), '2886', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

--------------------------------------------------------------------------------------	CITY ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='0986'), 'SH', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='1086'), 'SZ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='1186'), 'BJ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='1286'), 'NJ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='1286'), 'NT', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='1286'), 'SU', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'GZ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'MM', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'QY', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'ZH', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'ZQ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2586'), 'ZS', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2686'), 'FS', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2786'), 'JM', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_CITY (BRANCH_CODE, CITY_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT BRANCH_CODE FROM dbo.T_BRANCH WHERE BRANCH_NAME='2886'), 'DG', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

--------------------------------------------------------------------------------------	SSC -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'SYA2', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'SPA1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'SXH1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'SBJ1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), '0000', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'FE01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'WS00', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'SPD1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SH'), 'QP01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ05', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ08', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ02', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ03', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ09', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ06', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ07', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ04', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SZ'), 'SZ99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC2', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC3', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC4', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC8', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SYA2', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC6', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC9', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSCC', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSCD', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='BJ'), 'SSC5', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='NJ'), 'NJ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='NJ'), 'PK', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='NJ'), 'XJK', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='NT'), 'NT', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'CL', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'CS', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'GJ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'JG', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'KS', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'SZ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'TC', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'WJ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'XQ', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='SU'), 'ZJG', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'HD01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'HP01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'JT01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'JT02', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'JT03', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'PY01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'XT01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'YX01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='GZ'), 'YX02', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='MM'), 'MM01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='QY'), 'QY01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='ZH'), 'ZH01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='ZQ'), 'ZQ01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='ZS'), 'ZS01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'AA08', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'AA09', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'AA10', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'AA12', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'AA14', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'FS01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'NH01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'RG01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'SD01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'SD02', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='FS'), 'XN01', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), '0000', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'EPS1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'HSS1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'JM88', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'KPS1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'TSS1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='JM'), 'XHS1', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'CA99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'CP99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'DG98', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'DG99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'GC99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'HJ99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'HM99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'QX99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'SL99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');
INSERT INTO dbo.T_SSC (CITY_CODE, SSC_NAME, CREATED_BY, CREATION_DATE, MODIFIED_BY, MODIFICATION_DATE, STATUS, TOKETN) VALUES ((SELECT CITY_CODE FROM dbo.T_CITY WHERE CITY_NAME='DG'), 'ZT99', 'SYSTEM', GETDATE(), 'SYSTEM', GETDATE(), 1, '');

--------------------------------------------------------------------------------------	Configuration Properties ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('PresenterPath','D:/IMOCN/presenter');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('AnnouncementPath','D:/IMOCN/announcement');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('HolidayPath','D:/IMOCN/holiday');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('UserPath','D:/IMOCN/USER');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('EopPath','D:/IMOCN/eop');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('InterviewPath','D:/IMOCN/Interview');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('EgreetingPath','D:/IMOCN/Egreeting');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('encryptDecryptKey','password');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('addressBookListingOffset','20');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('encryptionKeyFilePath','D:/IMOCN/encyptionkey');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('userAuthUrl','http://211.144.219.243/isp/rest/index.do?isAjax=true');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('userAuthEnvironment','internet');
INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('userAuthUrlInternet','http://211.144.219.243/isp/rest/index.do?isAjax=true');

--------------------------------------------------------------------------------------	Mail Properties ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('host','smtp.gmail.com');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('port','587');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('from','my.premieracademy@aia.com');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('username','username');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('password','password');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('auth','true');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('debug','false');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('isSSL','true');
INSERT INTO dbo.T_MAIL_PROPERTIES (MAIL_KEY, MAIL_VALUE) VALUES ('starttls','true');

INSERT INTO dbo.T_CONFIGURATION_PROPERTIES (CONFIGURATION_KEY, CONFIGURATION_VALUE) VALUES('APP_URL','http://202.150.215.194/IMOCN_CLIENT/');
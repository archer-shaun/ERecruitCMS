
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
/*
 Navicat Premium Data Transfer

 Source Server         : 10.211.55.3
 Source Server Type    : SQL Server
 Source Server Version : 9001399
 Source Host           : 10.211.55.3
 Source Database       : EMPLOYEE
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 9001399
 File Encoding         : utf-8

 Date: 08/17/2015 22:18:53 PM
*/

-- ----------------------------
--  Table structure for attract_files
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[attract_files]') AND type IN ('U'))
	DROP TABLE [dbo].[attract_files]
GO
CREATE TABLE [dbo].[attract_files] (
	[id] numeric(8,0) IDENTITY(1,1) NOT NULL,
	[name] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[path] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[contentType] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[data] image NULL,
	[resourceId] numeric(8,0) NULL,
	[ct] datetime NULL,
	[ut] datetime NULL
)
ON [PRIMARY]
TEXTIMAGE_ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for attract_individuality
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[attract_individuality]') AND type IN ('U'))
	DROP TABLE [dbo].[attract_individuality]
GO
CREATE TABLE [dbo].[attract_individuality] (
	[agentId] numeric(8,0) IDENTITY(1,1) NOT NULL,
	[sqe_num] numeric(16,0) NULL,
	[ct] datetime NULL,
	[ut] datetime NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for attract_resource
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[attract_resource]') AND type IN ('U'))
	DROP TABLE [dbo].[attract_resource]
GO
CREATE TABLE [dbo].[attract_resource] (
	[id] numeric(8,0) IDENTITY(1,1) NOT NULL,
	[title] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[content] varchar(2000) COLLATE Chinese_PRC_CI_AS NULL,
	[type] numeric(16,0) NULL,
	[fileCounts] numeric(16,0) NULL,
	[fileTypes] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[filePaths] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[fileNames] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[contentTypes] varchar(500) COLLATE Chinese_PRC_CI_AS NULL,
	[deleteFlag] int NULL,
	[ct] datetime NULL,
	[ut] datetime NULL,
	[fileIds] varchar(500) COLLATE Chinese_PRC_CI_AS NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for attract_talent
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[attract_talent]') AND type IN ('U'))
	DROP TABLE [dbo].[attract_talent]
GO
CREATE TABLE [dbo].[attract_talent] (
	[id] numeric(8,0) IDENTITY(1,1) NOT NULL,
	[name] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[sex] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[birthday] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[user_id] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[ct] datetime NULL,
	[ut] datetime NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for attract_user
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[dbo].[attract_user]') AND type IN ('U'))
	DROP TABLE [dbo].[attract_user]
GO
CREATE TABLE [dbo].[attract_user] (
	[id] numeric(8,0) IDENTITY(1,1) NOT NULL,
	[name] varchar(50) COLLATE Chinese_PRC_CI_AS NOT NULL,
	[passwd] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[salt] varchar(50) COLLATE Chinese_PRC_CI_AS NULL,
	[ct] datetime NULL,
	[ut] datetime NULL
)
ON [PRIMARY]
GO


-- ----------------------------
--  Primary key structure for table attract_files
-- ----------------------------
ALTER TABLE [dbo].[attract_files] ADD
	CONSTRAINT [PK__attract_files__239E4DCF] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table attract_individuality
-- ----------------------------
ALTER TABLE [dbo].[attract_individuality] ADD
	CONSTRAINT [PK__attract_individu__29572725] PRIMARY KEY CLUSTERED ([agentId]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table attract_resource
-- ----------------------------
ALTER TABLE [dbo].[attract_resource] ADD
	CONSTRAINT [PK__attract_resource__21B6055D] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table attract_talent
-- ----------------------------
ALTER TABLE [dbo].[attract_talent] ADD
	CONSTRAINT [PK__attract_talent__276EDEB3] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table attract_user
-- ----------------------------
ALTER TABLE [dbo].[attract_user] ADD
	CONSTRAINT [PK__attract_user__023D5A04] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Uniques structure for table attract_user
-- ----------------------------
ALTER TABLE [dbo].[attract_user] ADD
	CONSTRAINT [UQ__attract_user__03317E3D] UNIQUE NONCLUSTERED ([name] ASC) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [PRIMARY]
GO

-- ----------------------------
--  Options for table attract_files
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[attract_files]', RESEED, 53)
GO

-- ----------------------------
--  Options for table attract_individuality
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[attract_individuality]', RESEED, 1)
GO

-- ----------------------------
--  Options for table attract_resource
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[attract_resource]', RESEED, 23)
GO

-- ----------------------------
--  Options for table attract_talent
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[attract_talent]', RESEED, 1)
GO

-- ----------------------------
--  Options for table attract_user
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[attract_user]', RESEED, 3)
GO


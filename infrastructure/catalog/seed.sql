CREATE DATABASE [CATALOG]
GO

USE [CATALOG]
GO

CREATE TABLE dbo.CATALOG_TYPE (
    [GUID] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWSEQUENTIALID() ROWGUIDCOL,
    [NAME] NVARCHAR(200) NOT NULL,
    [ICON] NVARCHAR(50) NULL
)

CREATE TABLE dbo.CATALOG_BRAND (
    [GUID] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWSEQUENTIALID() ROWGUIDCOL,
    [NAME] NVARCHAR(200) NOT NULL,
    [ICON] NVARCHAR(50) NULL
)

CREATE TABLE dbo.CATALOG_ITEM (
    [GUID] UNIQUEIDENTIFIER NOT NULL PRIMARY KEY DEFAULT NEWSEQUENTIALID() ROWGUIDCOL,
    [NAME] NVARCHAR(200) NOT NULL,
    [PRICE] DECIMAL(9,2) NOT NULL,
    [DESCRIPTION] NVARCHAR(MAX) NULL,
    [IMAGE_PATH] NVARCHAR(MAX) NULL,
    [CREATED_DATE] DATETIME NOT NULL,
    [CATALOG_TYPE_GUID] UNIQUEIDENTIFIER NOT NULL,
    [CATALOG_BRAND_GUID] UNIQUEIDENTIFIER NOT NULL
)

GO

INSERT INTO [CATALOG_TYPE] ([NAME], [ICON]) VALUES ('Mug','local_cafe')
INSERT INTO [CATALOG_TYPE] ([NAME], [ICON]) VALUES ('T-Shirt','person')
INSERT INTO [CATALOG_TYPE] ([NAME], [ICON]) VALUES ('Sheet','label')
INSERT INTO [CATALOG_TYPE] ([NAME], [ICON]) VALUES ('USB Memory Stick','usb')

INSERT INTO [CATALOG_BRAND] ([NAME], [ICON]) VALUES ('Azure','cloud')
INSERT INTO [CATALOG_BRAND] ([NAME], [ICON]) VALUES ('.NET','code')
INSERT INTO [CATALOG_BRAND] ([NAME], [ICON]) VALUES ('Visual Studio','polymer')
INSERT INTO [CATALOG_BRAND] ([NAME], [ICON]) VALUES ('SQL Server','storage')
INSERT INTO [CATALOG_BRAND] ([NAME], [ICON]) VALUES ('Other','category')

INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT '.NET Bot Black Sweatshirt', 19.5, '.NET Bot Black Sweatshirt', 'Item 1.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT '.NET Black & White Mug', 8.5, '.NET Black & White Mug', 'Item 2.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'Mug'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Prism White T-Shirt', 12, 'Prism White T-Shirt', 'Item 3.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT '.NET Foundation Sweatshirt', 12, '.NET Foundation Sweatshirt', 'Item 4.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Roslyn Red Sheet', 8.5, 'Roslyn Red Sheet', 'Item 5.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'Sheet'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT '.NET Blue Sweatshirt', 12, '.NET Blue Sweatshirt', 'Item 6.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Roslyn Red T-Shirt', 12, 'Roslyn Red T-Shirt', 'Item 7.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Kudu Purple Sweatshirt', 8.5, 'Kudu Purple Sweatshirt', 'Item 8.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Cup<T> White Mug', 12, 'Cup<T> White Mug', 'Item 9.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'Mug'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT '.NET Foundation Sheet', 12, '.NET Foundation Sheet', 'Item 10.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'Sheet'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Cup<T> Sheet', 8.5, 'Cup<T> Sheet', 'Item 11.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'Sheet'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = '.NET')
INSERT INTO [CATALOG_ITEM] ([NAME],[PRICE],[DESCRIPTION],[IMAGE_PATH],[CREATED_DATE],[CATALOG_TYPE_GUID],[CATALOG_BRAND_GUID])
SELECT 'Prism White TShirt', 12, 'Prism White TShirt', 'Item 12.png', GETDATE(), (SELECT [GUID] FROM [CATALOG_TYPE] WHERE [NAME] = 'T-Shirt'), (SELECT [GUID] FROM [CATALOG_BRAND] WHERE [NAME] = 'Other')
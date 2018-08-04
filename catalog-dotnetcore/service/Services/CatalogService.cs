using System;
using System.Collections.Generic;
using Catalog.API.Models;
using Microsoft.Extensions.Logging;

namespace Catalog.API.Services
{
    public class CatalogService : ICatalogService
    {
        private readonly ILogger<CatalogService> logger;

        public CatalogService(ILogger<CatalogService> logger)
        {
            this.logger = logger;
        }

        public IList<CatalogType> GetTypes()
        {
            return new List<CatalogType>()
            {
                new CatalogType() { Id = 1, Type = "Mug", Icon = "local_cafe", IsSelected = true },
                new CatalogType() { Id = 2, Type = "T-Shirt", Icon = "person", IsSelected = true },
                new CatalogType() { Id = 3, Type = "Sheet", Icon = "label", IsSelected = true },
                new CatalogType() { Id = 4, Type = "USB Memory Stick", Icon = "usb", IsSelected = true }
            };
        }
        public IList<CatalogBrand> GetBrands()
        {
            return new List<CatalogBrand>()
            {
                new CatalogBrand() { Id = 1, Brand = "Azure", Icon = "cloud", IsSelected = true },
                new CatalogBrand() { Id = 2, Brand = ".NET", Icon = "code", IsSelected = true },
                new CatalogBrand() { Id = 3, Brand = "Visual Studio", Icon = "polymer", IsSelected = true },
                new CatalogBrand() { Id = 4, Brand = "SQL Server", Icon = "storage", IsSelected = true },
                new CatalogBrand() { Id = 5, Brand = "Other", Icon = "category", IsSelected = true }
            };
        }
        public IList<CatalogItem> GetItems()
        {
            return new List<CatalogItem>()
            {
                new CatalogItem() { Id = 1, CatalogTypeId = 2, CatalogBrandId = 2, Description = ".NET Bot Black Sweatshirt", Name = ".NET Bot Black Sweatshirt", Price = 19.5M, FileName = "Item 1.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 2, CatalogTypeId = 1, CatalogBrandId = 2, Description = ".NET Black & White Mug", Name = ".NET Black & White Mug", Price= 8.50M, FileName = "Item 2.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 3, CatalogTypeId = 2, CatalogBrandId = 5, Description = "Prism White T-Shirt", Name = "Prism White T-Shirt", Price = 12, FileName = "Item 3.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 4, CatalogTypeId = 2, CatalogBrandId = 2, Description = ".NET Foundation Sweatshirt", Name = ".NET Foundation Sweatshirt", Price = 12, FileName = "Item 4.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 5, CatalogTypeId = 3, CatalogBrandId = 5, Description = "Roslyn Red Sheet", Name = "Roslyn Red Sheet", Price = 8.5M, FileName = "Item 5.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 6, CatalogTypeId = 2, CatalogBrandId = 2, Description = ".NET Blue Sweatshirt", Name = ".NET Blue Sweatshirt", Price = 12, FileName = "Item 6.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 7, CatalogTypeId = 2, CatalogBrandId = 5, Description = "Roslyn Red T-Shirt", Name = "Roslyn Red T-Shirt", Price = 12, FileName = "Item 7.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 8, CatalogTypeId = 2, CatalogBrandId = 5, Description = "Kudu Purple Sweatshirt", Name = "Kudu Purple Sweatshirt", Price = 8.5M, FileName = "Item 8.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 9, CatalogTypeId = 1, CatalogBrandId = 5, Description = "Cup<T> White Mug", Name = "Cup<T> White Mug", Price = 12, FileName = "Item 9.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 10, CatalogTypeId = 3, CatalogBrandId = 2, Description = ".NET Foundation Sheet", Name = ".NET Foundation Sheet", Price = 12, FileName = "Item 10.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 11, CatalogTypeId = 3, CatalogBrandId = 2, Description = "Cup<T> Sheet", Name = "Cup<T> Sheet", Price = 8.5M, FileName = "Item 11.png", CreateDate = DateTime.Today },
                new CatalogItem() { Id = 12, CatalogTypeId = 2, CatalogBrandId = 5, Description = "Prism White TShirt", Name = "Prism White TShirt", Price = 12, FileName = "Item 12.png", CreateDate = DateTime.Today }
            };
        }
    }
}
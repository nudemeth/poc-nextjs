using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Cors;
using Catalog.API.Models;

namespace Catalog.API.Fakes
{
    public static class FakeCatalogItem
    {
        public static IList<CatalogType> types = new List<CatalogType>()
        {
            new CatalogType() { Id = 1, Type = "Languages & Frameworks", Icon = "code", IsSelected = true },
            new CatalogType() { Id = 2, Type = "Tools", Icon = "build", IsSelected = true },
            new CatalogType() { Id = 3, Type = "Techniques", Icon = "device_hub", IsSelected = true },
            new CatalogType() { Id = 4, Type = "Platforms", Icon = "developer_board", IsSelected = false }
        };

        public static IList<CatalogBrand> brands = new List<CatalogBrand>()
        {
            new CatalogBrand() { Id = 1, Brand = "Microsoft", Icon = "code", IsSelected = true },
            new CatalogBrand() { Id = 2, Brand = "Google", Icon = "build", IsSelected = true },
            new CatalogBrand() { Id = 3, Brand = "Apple", Icon = "device_hub", IsSelected = true },
            new CatalogBrand() { Id = 4, Brand = "Facebook", Icon = "developer_board", IsSelected = false }
        };

        public static IList<CatalogItem> items = new List<CatalogItem>()
        {
            new CatalogItem() { Id = 1, CatalogTypeId = 1, CatalogBrandId = 1, Name = "Item 1", FileName = "Item 1.png", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 2, CatalogTypeId = 2, CatalogBrandId = 3, Name = "Item 2", FileName = "Item 2.png", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 3, CatalogTypeId = 3, CatalogBrandId = 3, Name = "Item 3", FileName = "Item 3.png", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 4, CatalogTypeId = 4, CatalogBrandId = 4, Name = "Item 4", FileName = "Item 4.png", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 5, CatalogTypeId = 1, CatalogBrandId = 2, Name = "Item 5", FileName = "Item 5.png", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" }
        };
    }
}
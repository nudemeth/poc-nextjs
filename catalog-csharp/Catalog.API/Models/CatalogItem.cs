using System;

namespace Catalog.API.Models
{
    public class CatalogItem : ICatalogModel
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public decimal Price { get; set; }
        public string Description { get; set; }
        public string ImageUrl { get; set; }
        public string ImageAlt { get; set; }
        public DateTime CreatedDate { get; set; }
        public Guid CatalogBrandId { get; set; }
        public CatalogBrand CatalogBrand { get; set; }
        public Guid CatalogTypeId { get; set; }
        public CatalogType CatalogType { get; set; }
        public string ImagePath { get; set; }
    }
}
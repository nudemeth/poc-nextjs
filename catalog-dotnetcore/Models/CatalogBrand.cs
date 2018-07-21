using System;

namespace Catalog.API.Models
{
    public class CatalogBrand
    {
        public int Id { get; set; }
        public string Brand { get; set; }
        public string Icon { get; set; }
        public bool IsSelected { get; set; }
    }
}
using System;

namespace Catalog.API.Models
{
    public class CatalogBrand : ICatalogModel
    {
        public Guid Id { get; set; }
        public string Brand { get; set; }
        public string Icon { get; set; }
        public bool IsSelected { get { return true; } } //TODO: Move to user configs
    }
}
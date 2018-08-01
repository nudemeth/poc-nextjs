using System;

namespace Catalog.API.Models
{
    public class CatalogType
    {
        public int Id { get; set; }
        public string Type { get; set; }
        public string Icon { get; set; }
        public bool IsSelected { get; set; }
    }
}
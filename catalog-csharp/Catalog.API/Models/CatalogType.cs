using System;

namespace Catalog.API.Models
{
    public class CatalogType
    {
        public Guid Id { get; set; }
        public string Type { get; set; }
        public string Icon { get; set; }
        public bool IsSelected { get { return true; } } //TODO: Move to user configs
    }
}
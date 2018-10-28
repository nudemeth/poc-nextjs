using System;

namespace Catalog.API.Models
{
    public interface ICatalogModel
    {
        Guid Id { get; set; }
    }
}
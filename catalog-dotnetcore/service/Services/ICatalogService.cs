using System.Collections.Generic;
using Catalog.API.Models;

namespace Catalog.API.Services
{
    public interface ICatalogService
    {
        IList<CatalogType> GetTypes();
        IList<CatalogBrand> GetBrands();
        IList<CatalogItem> GetItems();
    }
}
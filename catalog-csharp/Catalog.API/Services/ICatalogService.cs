using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Catalog.API.Models;

namespace Catalog.API.Services
{
    public interface ICatalogService
    {
        Task<IList<CatalogType>> GetTypes();
        Task<IList<CatalogBrand>> GetBrands();
        Task<IList<CatalogItem>> GetItems();
        Task<IList<CatalogItem>> GetItems(Guid[] ids);
        Task<CatalogItem> GetItem(Guid id);
        Task<CatalogItem> GetItemByName(string name);
        Task<IList<CatalogItem>> GetItemsByTypesAndBrands(Guid[] catalogTypeIds, Guid[] catalogBrandIds);
        Task<IList<CatalogItem>> GetItemsByTypes(Guid[] catalogTypeIds);
        Task<IList<CatalogItem>> GetItemsByBrands(Guid[] catalogBrandIds);
    }
}
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public interface ICatalogItemRepository
    {
        Task<List<CatalogItem>> Get();
        Task<CatalogItem> Get(Guid id);
        Task<List<CatalogItem>> Get(Guid[] ids);
        Task<CatalogItem> GetByName(string name);
        Task<List<CatalogItem>> GetByTypesAndBrands(Guid[] typeIds, Guid[] brandIds);
        Task<List<CatalogItem>> GetByTypes(Guid[] typeIds);
        Task<List<CatalogItem>> GetByBrands(Guid[] brandIds);
        Task Insert(CatalogItem item);
        Task Update(CatalogItem item);
        Task Delete(Guid id);
        Task Save();
    }
}
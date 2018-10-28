using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public interface ICatalogBrandRepository
    {
        Task<List<CatalogBrand>> Get();
        Task<CatalogBrand> Get(Guid id);
        Task Insert(CatalogBrand brand);
        Task Update(CatalogBrand brand);
        Task Delete(Guid id);
        Task Save();
    }
}
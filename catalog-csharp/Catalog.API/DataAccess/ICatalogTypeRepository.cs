using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public interface ICatalogTypeRepository
    {
        Task<List<CatalogType>> Get();
        Task<CatalogType> Get(Guid id);
        Task Insert(CatalogType type);
        Task Update(CatalogType type);
        Task Delete(Guid id);
        Task Save();
    }
}
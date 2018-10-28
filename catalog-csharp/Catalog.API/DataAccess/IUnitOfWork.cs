using System;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public interface IUnitOfWork : IDisposable
    {
        ICatalogRepository<CatalogItem> CatalogItemRepository { get; }
        ICatalogRepository<CatalogType> CatalogTypeRepository { get; }
        ICatalogRepository<CatalogBrand> CatalogBrandRepository { get; }
        void Save();
    }
}
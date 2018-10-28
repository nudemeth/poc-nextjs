using System;

namespace Catalog.API.DataAccess
{
    public interface IUnitOfWork : IDisposable
    {
        ICatalogItemRepository CatalogItemRepository { get; }
        ICatalogTypeRepository CatalogTypeRepository { get; }
        ICatalogBrandRepository CatalogBrandRepository { get; }
        void Save();
    }
}
using System;
using Catalog.API.Infrastructure;
using Catalog.API.Models;

namespace Catalog.API.DataAccess
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly CatalogContext context;
        private ICatalogRepository<CatalogItem> catalogItemRepository;
        private ICatalogRepository<CatalogType> catalogTypeRepository;
        private ICatalogRepository<CatalogBrand> catalogBrandRepository;
        public UnitOfWork(CatalogContext context)
        {
            this.context = context;
        }
        public ICatalogRepository<CatalogItem> CatalogItemRepository
        {
            get
            {
                if (catalogItemRepository == null)
                {
                    catalogItemRepository = new CatalogRepository<CatalogItem>(context);
                }
                return catalogItemRepository;
            }
        }
        public ICatalogRepository<CatalogType> CatalogTypeRepository
        {
            get
            {
                if (catalogTypeRepository == null)
                {
                    catalogTypeRepository = new CatalogRepository<CatalogType>(context);
                }
                return catalogTypeRepository;
            }
        }
        public ICatalogRepository<CatalogBrand> CatalogBrandRepository
        {
            get
            {
                if (catalogBrandRepository == null)
                {
                    catalogBrandRepository = new CatalogRepository<CatalogBrand>(context);
                }
                return catalogBrandRepository;
            }
        }
        public void Save()
        {
            context.SaveChanges();
        }

        private bool disposed = false;
        protected virtual void Dispose(bool disposing)
        {
            if (!this.disposed)
            {
                if (disposing)
                {
                    context.Dispose();
                }
            }
            this.disposed = true;
        }
        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}
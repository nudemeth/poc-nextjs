using System;
using Catalog.API.Infrastructure;

namespace Catalog.API.DataAccess
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly CatalogContext context;
        private ICatalogItemRepository catalogItemRepository;
        private ICatalogTypeRepository catalogTypeRepository;
        private ICatalogBrandRepository catalogBrandRepository;
        public UnitOfWork(CatalogContext context)
        {
            this.context = context;
        }
        public ICatalogItemRepository CatalogItemRepository
        {
            get
            {
                if (catalogItemRepository == null)
                {
                    catalogItemRepository = new CatalogItemRepository(context);
                }
                return catalogItemRepository;
            }
        }
        public ICatalogTypeRepository CatalogTypeRepository
        {
            get
            {
                if (catalogTypeRepository == null)
                {
                    catalogTypeRepository = new CatalogTypeRepository(context);
                }
                return catalogTypeRepository;
            }
        }
        public ICatalogBrandRepository CatalogBrandRepository
        {
            get
            {
                if (catalogBrandRepository == null)
                {
                    catalogBrandRepository = new CatalogBrandRepository(context);
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
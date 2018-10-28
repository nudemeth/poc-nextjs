using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;

namespace Catalog.API.DataAccess
{
    public class CatalogBrandRepository : ICatalogBrandRepository
    {
        private readonly CatalogContext context;
        public CatalogBrandRepository(CatalogContext context)
        {
            this.context = context;
        }
        public Task Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<List<CatalogBrand>> Get()
        {
            return context.CatalogBrands.ToListAsync();
        }

        public Task<CatalogBrand> Get(Guid id)
        {
            return context.CatalogBrands.Where(i => i.Id == id).SingleOrDefaultAsync();
        }

        public Task Insert(CatalogBrand brand)
        {
            throw new NotImplementedException();
        }

        public Task Save()
        {
            throw new NotImplementedException();
        }

        public Task Update(CatalogBrand brand)
        {
            throw new NotImplementedException();
        }
    }
}
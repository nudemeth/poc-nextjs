using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;

namespace Catalog.API.DataAccess
{
    public class CatalogTypeRepository : ICatalogTypeRepository
    {
        private readonly CatalogContext context;
        public CatalogTypeRepository(CatalogContext context)
        {
            this.context = context;
        }
        public Task Delete(Guid id)
        {
            throw new NotImplementedException();
        }

        public Task<List<CatalogType>> Get()
        {
            return context.CatalogTypes.ToListAsync();
        }

        public Task<CatalogType> Get(Guid id)
        {
            return context.CatalogTypes.Where(i => i.Id == id).SingleOrDefaultAsync();
        }

        public Task Insert(CatalogType type)
        {
            throw new NotImplementedException();
        }

        public Task Save()
        {
            throw new NotImplementedException();
        }

        public Task Update(CatalogType type)
        {
            throw new NotImplementedException();
        }
    }
}
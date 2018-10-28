using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.EntityFrameworkCore;

namespace Catalog.API.DataAccess
{
    public class CatalogItemRepository : ICatalogItemRepository
    {
        private readonly CatalogContext context;
        public CatalogItemRepository(CatalogContext context)
        {
            this.context = context;
        }
        public Task Delete(Guid id)
        {
            throw new NotImplementedException();
        }
        public Task<List<CatalogItem>> Get()
        {
            return context.CatalogItems.ToListAsync();
        }
        public Task<CatalogItem> Get(Guid id)
        {
            return context.CatalogItems.Where(i => i.Id == id).SingleOrDefaultAsync();
        }
        public Task<List<CatalogItem>> Get(Guid[] ids)
        {
            return context.CatalogItems.Where(i => ids.Contains(i.Id)).ToListAsync();
        }
        public Task<List<CatalogItem>> GetByBrands(Guid[] brandIds)
        {
            return context.CatalogItems.Where(i => brandIds.Contains(i.CatalogBrandId)).ToListAsync();
        }
        public Task<CatalogItem> GetByName(string name)
        {
            return context.CatalogItems.Where(i => i.Name == name).SingleOrDefaultAsync();
        }
        public Task<List<CatalogItem>> GetByTypes(Guid[] typeIds)
        {
            return context.CatalogItems.Where(i => typeIds.Contains(i.CatalogTypeId)).ToListAsync();
        }
        public Task<List<CatalogItem>> GetByTypesAndBrands(Guid[] typeIds, Guid[] brandIds)
        {
            return context.CatalogItems.Where(i => typeIds.Contains(i.CatalogTypeId) && brandIds.Contains(i.CatalogBrandId)).ToListAsync();
        }
        public Task Insert(CatalogItem item)
        {
            throw new NotImplementedException();
        }
        public Task Save()
        {
            throw new NotImplementedException();
        }
        public Task Update(CatalogItem item)
        {
            throw new NotImplementedException();
        }
    }
}
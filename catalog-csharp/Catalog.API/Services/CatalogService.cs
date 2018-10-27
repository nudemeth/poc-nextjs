using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;

namespace Catalog.API.Services
{
    public class CatalogService : ICatalogService
    {
        private readonly ILogger<CatalogService> logger;
        private readonly CatalogContext catalogContext;

        public CatalogService(ILogger<CatalogService> logger, CatalogContext catalogContext)
        {
            this.logger = logger ?? throw new ArgumentNullException(nameof(logger));
            this.catalogContext = catalogContext ?? throw new ArgumentNullException(nameof(catalogContext));
        }

        public async Task<IList<CatalogType>> GetTypes()
        {
            return await catalogContext.CatalogTypes.ToListAsync();
        }
        public async Task<IList<CatalogBrand>> GetBrands()
        {
            return await catalogContext.CatalogBrands.ToListAsync();
        }
        public async Task<IList<CatalogItem>> GetItems()
        {
            return await catalogContext.CatalogItems.ToListAsync();
        }
        public async Task<IList<CatalogItem>> GetItems(Guid[] ids)
        {
            return await catalogContext.CatalogItems.Where(i => ids.Contains(i.Id)).ToListAsync();
        }
        public async Task<CatalogItem> GetItem(Guid id)
        {
            return await catalogContext.CatalogItems.Where(i => i.Id == id).SingleOrDefaultAsync();
        }
        public async Task<CatalogItem> GetItemByName(string name)
        {
            return await catalogContext.CatalogItems.Where(i => i.Name == name).SingleOrDefaultAsync();
        }
        public async Task<IList<CatalogItem>> GetItemsByTypesAndBrands(Guid[] catalogTypeIds, Guid[] catalogBrandIds)
        {
            return await catalogContext.CatalogItems.Where(i => catalogTypeIds.Contains(i.CatalogTypeId) && catalogBrandIds.Contains(i.CatalogBrandId)).ToListAsync();
        }
        public async Task<IList<CatalogItem>> GetItemsByTypes(Guid[] catalogTypeIds)
        {
            return await catalogContext.CatalogItems.Where(i => catalogTypeIds.Contains(i.CatalogTypeId)).ToListAsync();
        }
        public async Task<IList<CatalogItem>> GetItemsByBrands(Guid[] catalogBrandIds)
        {
            return await catalogContext.CatalogItems.Where(i => catalogBrandIds.Contains(i.CatalogBrandId)).ToListAsync();
        }
    }
}
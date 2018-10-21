using System;
using System.Collections.Generic;
using System.Linq;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.Extensions.Logging;

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

        public IList<CatalogType> GetTypes()
        {
            return catalogContext.CatalogTypes.ToList();
        }
        public IList<CatalogBrand> GetBrands()
        {
            return catalogContext.CatalogBrands.ToList();
        }
        public IList<CatalogItem> GetItems()
        {
            return catalogContext.CatalogItems.ToList();
        }
    }
}
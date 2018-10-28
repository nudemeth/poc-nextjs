using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Catalog.API.Infrastructure;
using Catalog.API.Models;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;
using Catalog.API.DataAccess;

namespace Catalog.API.Services
{
    public class CatalogService : ICatalogService
    {
        private readonly ILogger<CatalogService> logger;
        private readonly IUnitOfWork unitOfWork;

        public CatalogService(ILogger<CatalogService> logger, IUnitOfWork unitOfWork)
        {
            this.logger = logger ?? throw new ArgumentNullException(nameof(logger));
            this.unitOfWork = unitOfWork ?? throw new ArgumentNullException(nameof(unitOfWork));
        }
        public async Task<IList<CatalogType>> GetTypes()
        {
            return await unitOfWork.CatalogTypeRepository.Get();
        }
        public async Task<IList<CatalogBrand>> GetBrands()
        {
            return await unitOfWork.CatalogBrandRepository.Get();
        }
        public async Task<IList<CatalogItem>> GetItems()
        {
            return await unitOfWork.CatalogItemRepository.Get();
        }
        public async Task<IList<CatalogItem>> GetItems(Guid[] ids)
        {
            return await unitOfWork.CatalogItemRepository.Get(ids);
        }
        public async Task<CatalogItem> GetItem(Guid id)
        {
            return await unitOfWork.CatalogItemRepository.Get(id);
        }
        public async Task<CatalogItem> GetItemByName(string name)
        {
            return await unitOfWork.CatalogItemRepository.GetByName(name);
        }
        public async Task<IList<CatalogItem>> GetItemsByTypesAndBrands(Guid[] catalogTypeIds, Guid[] catalogBrandIds)
        {
            return await unitOfWork.CatalogItemRepository.GetByTypesAndBrands(catalogTypeIds, catalogBrandIds);
        }
        public async Task<IList<CatalogItem>> GetItemsByTypes(Guid[] catalogTypeIds)
        {
            return await unitOfWork.CatalogItemRepository.GetByTypes(catalogTypeIds);
        }
        public async Task<IList<CatalogItem>> GetItemsByBrands(Guid[] catalogBrandIds)
        {
            return await unitOfWork.CatalogItemRepository.GetByBrands(catalogBrandIds);
        }
    }
}
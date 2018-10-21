using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Cors;
using Catalog.API.Models;
using Catalog.API.Services;

namespace Catalog.API.Controllers
{
    [Route("api/v1/[controller]")]
    [EnableCors("APIGateway")]
    [ResponseCache(Location = ResponseCacheLocation.None, Duration = 0, VaryByHeader = "Origins, Accept-Encoding")]
    [ApiController]
    public class CatalogController : ControllerBase
    {
        private ICatalogService catalogService;
        public CatalogController(ICatalogService catalogService)
        {
            this.catalogService = catalogService;
        }

        // GET api/[controller]/items
        [HttpGet]
        [Route("items")]
        public async Task<IActionResult> GetItems()
        {
            return await Task.FromResult(Ok(catalogService.GetItems()));
        }

        // GET api/[controller]/items/[1,2,3]
        [HttpGet]
        [Route("items/{ids:minlength(1)}")]
        public async Task<IActionResult> GetItemsByIds(string ids = null)
        {
            var items = catalogService.GetItems();
            var itemIds = ids.Split(',').Select(i => new Guid(i)).ToArray();
            var result = items.Where(i => itemIds.Contains(i.Id)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/withname/{name:minlength(1)}")]
        public async Task<IActionResult> GetItemByName(string name)
        {
            var items = catalogService.GetItems();
            var result = items.Where(i => i.Name == name).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypesAndBrands(string catalogTypeIds, string catalogBrandIds)
        {
            var items = catalogService.GetItems();
            var typeIds = catalogTypeIds.Split(',').Select(i => new Guid(i)).ToArray();
            var brandIds = catalogBrandIds.Split(',').Select(i => new Guid(i)).ToArray();
            var result = items.Where(i => typeIds.Contains(i.CatalogTypeId) && brandIds.Contains(i.CatalogBrandId)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypes(string catalogTypeIds)
        {
            var items = catalogService.GetItems();
            var typeIds = catalogTypeIds.Split(',').Select(i => new Guid(i)).ToArray();
            var result = items.Where(i => typeIds.Contains(i.CatalogTypeId)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByBrands(string catalogBrandIds)
        {
            var items = catalogService.GetItems();
            var brandIds = catalogBrandIds.Split(',').Select(i => new Guid(i)).ToArray();
            var result = items.Where(i => brandIds.Contains(i.CatalogBrandId)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("catalogTypes")]
        public async Task<IActionResult> GetCatalogTypes()
        {
            return await Task.FromResult(Ok(catalogService.GetTypes()));
        }

        [HttpGet]
        [Route("catalogBrands")]
        public async Task<IActionResult> GetCatalogBrands()
        {
            return await Task.FromResult(Ok(catalogService.GetBrands()));
        }

        // POST api/[controller]
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT api/catalog/5
        [HttpPut("{id}")]
        public void Put(string id, [FromBody] string value)
        {
        }

        // DELETE api/catalog/5
        [HttpDelete("{id}")]
        public void Delete(string id)
        {
        }
    }
}

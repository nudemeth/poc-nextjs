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
            var items = await catalogService.GetItems();
            return Ok(items);
        }

        // GET api/[controller]/items/[1,2,3]
        [HttpGet]
        [Route("items/{ids:minlength(1)}")]
        public async Task<IActionResult> GetItemsByIds(string ids = null)
        {
            var itemIds = ids.Split(',').Select(i => new Guid(i)).ToArray();
            var items = await catalogService.GetItems(itemIds);
            return Ok(items);
        }

        [HttpGet]
        [Route("items/withname/{name:minlength(1)}")]
        public async Task<IActionResult> GetItemByName(string name)
        {
            var item = await catalogService.GetItemByName(name);
            return Ok(item);
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypesAndBrands(string catalogTypeIds, string catalogBrandIds)
        {
            var typeIds = catalogTypeIds.Split(',').Select(i => new Guid(i)).ToArray();
            var brandIds = catalogBrandIds.Split(',').Select(i => new Guid(i)).ToArray();
            var items = await catalogService.GetItemsByTypesAndBrands(typeIds, brandIds);
            return Ok(items);
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypes(string catalogTypeIds)
        {
            var typeIds = catalogTypeIds.Split(',').Select(i => new Guid(i)).ToArray();
            var items = await catalogService.GetItemsByTypes(typeIds);
            return Ok(items);
        }

        [HttpGet]
        [Route("items/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByBrands(string catalogBrandIds)
        {
            var brandIds = catalogBrandIds.Split(',').Select(i => new Guid(i)).ToArray();
            var items = await catalogService.GetItemsByBrands(brandIds);
            return Ok(items);
        }

        [HttpGet]
        [Route("catalogTypes")]
        public async Task<IActionResult> GetCatalogTypes()
        {
            var types = await catalogService.GetTypes();
            return Ok(types);
        }

        [HttpGet]
        [Route("catalogBrands")]
        public async Task<IActionResult> GetCatalogBrands()
        {
            var brands = await catalogService.GetBrands();
            return Ok(brands);
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

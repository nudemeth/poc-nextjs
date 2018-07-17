using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace Catalog.API.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class CatalogController : ControllerBase
    {
        // GET api/[controller]/items
        [HttpGet]
        [Route("items")]
        public async Task<IActionResult> GetItems()
        {
            return await Task.FromResult(Ok(new string[] { "value1", "value2" }));
        }

        // GET api/[controller]/items/[1,2,3]
        [HttpGet]
        [Route("items/{ids:minlength(1)}")]
        public async Task<IActionResult> GetItems(string ids = null)
        {
            var items = ids.Split(',').ToArray();
            return await Task.FromResult(Ok(items));
        }

        // GET api/[controller]/items/1
        [HttpGet]
        [Route("items/{id:int}")]
        public async Task<IActionResult> GetItemById(int id)
        {
            return await Task.FromResult(Ok("GetItemById: " + id));
        }

        [HttpGet]
        [Route("items/withname/{name:minlength(1)}")]
        public async Task<IActionResult> GetItemByName(string name)
        {
            return await Task.FromResult(Ok("GetItemByName: " + name));
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItems(string catalogTypeIds, string catalogBrandIds)
        {
            return await Task.FromResult(Ok("GetItems: typeIds=" + catalogTypeIds + ", brandIds=" + catalogBrandIds));
        }

        [HttpGet]
        [Route("catalogTypes")]
        public async Task<IActionResult> GetCatalogTypes()
        {
            return await Task.FromResult(Ok(new string[] { "Languages & Frameworks", "Tools", "Techniques", "Platforms" }));
        }

        [HttpGet]
        [Route("catalogBrands")]
        public async Task<IActionResult> GetCatalogBrands()
        {
            return await Task.FromResult(Ok(new string[] { "Microsoft", "Google", "Apple", "Facebook" }));
        }

        // POST api/[controller]
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT api/catalog/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/catalog/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}

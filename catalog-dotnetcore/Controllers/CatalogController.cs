using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Catalog.API.Models;

namespace Catalog.API.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class CatalogController : ControllerBase
    {
        private IList<CatalogType> types = new List<CatalogType>()
        {
            new CatalogType() { Id = 1, Type = "Languages & Frameworks" },
            new CatalogType() { Id = 2, Type = "Tools" },
            new CatalogType() { Id = 3, Type = "Techniques" },
            new CatalogType() { Id = 4, Type = "Platforms" }
        };

        private IList<CatalogBrand> brands = new List<CatalogBrand>()
        {
            new CatalogBrand() { Id = 1, Brand = "Microsoft" },
            new CatalogBrand() { Id = 2, Brand = "Google" },
            new CatalogBrand() { Id = 3, Brand = "Apple" },
            new CatalogBrand() { Id = 4, Brand = "Facebook" }
        };

        private IList<CatalogItem> items = new List<CatalogItem>()
        {
            new CatalogItem() { Id = 1, CatalogTypeId = 1, CatalogBrandId = 1, Name = "Item 1", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 2, CatalogTypeId = 2, CatalogBrandId = 3, Name = "Item 2", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 3, CatalogTypeId = 3, CatalogBrandId = 3, Name = "Item 3", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 4, CatalogTypeId = 4, CatalogBrandId = 4, Name = "Item 4", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" },
            new CatalogItem() { Id = 5, CatalogTypeId = 1, CatalogBrandId = 2, Name = "Item 5", Price = 800, CreateDate = DateTime.Today, Description = "", ImageUrl = "https://material-ui-next.com/static/images/cards/paella.jpg", ImageAlt = "Contemplative Reptile" }
        };

        // GET api/[controller]/items
        [HttpGet]
        [Route("items")]
        public async Task<IActionResult> GetItems()
        {
            return await Task.FromResult(Ok(items));
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
            return await Task.FromResult(Ok(types));
        }

        [HttpGet]
        [Route("catalogBrands")]
        public async Task<IActionResult> GetCatalogBrands()
        {
            return await Task.FromResult(Ok(brands));
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

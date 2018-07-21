using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Cors;
using Catalog.API.Models;

namespace Catalog.API.Controllers
{
    [Route("api/v1/[controller]")]
    [EnableCors("APIGateway")]
    [ResponseCache(Location = ResponseCacheLocation.None, Duration = 0, VaryByHeader = "Origins, Accept-Encoding")]
    [ApiController]
    public class CatalogController : ControllerBase
    {
        private IList<CatalogType> types = new List<CatalogType>()
        {
            new CatalogType() { Id = 1, Type = "Languages & Frameworks", Icon = "code", IsSelected = true },
            new CatalogType() { Id = 2, Type = "Tools", Icon = "build", IsSelected = true },
            new CatalogType() { Id = 3, Type = "Techniques", Icon = "device_hub", IsSelected = true },
            new CatalogType() { Id = 4, Type = "Platforms", Icon = "developer_board", IsSelected = false }
        };

        private IList<CatalogBrand> brands = new List<CatalogBrand>()
        {
            new CatalogBrand() { Id = 1, Brand = "Microsoft", Icon = "code", IsSelected = true },
            new CatalogBrand() { Id = 2, Brand = "Google", Icon = "build", IsSelected = true },
            new CatalogBrand() { Id = 3, Brand = "Apple", Icon = "device_hub", IsSelected = true },
            new CatalogBrand() { Id = 4, Brand = "Facebook", Icon = "developer_board", IsSelected = false }
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
            var itemIds = ids.Split(',').Select(i => Int32.Parse(i)).ToArray();
            var result = items.Where(i => itemIds.Contains(i.Id)).ToList();
            return await Task.FromResult(Ok(result));
        }

        // GET api/[controller]/items/1
        [HttpGet]
        [Route("items/{id:int}")]
        public async Task<IActionResult> GetItemById(int id)
        {
            var result = items.Where(i => i.Id == id).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/withname/{name:minlength(1)}")]
        public async Task<IActionResult> GetItemByName(string name)
        {
            var result = items.Where(i => i.Name == name).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypesAndBrands(string catalogTypeIds, string catalogBrandIds)
        {
            var typeIds = catalogTypeIds.Split(',').Select(i => Int32.Parse(i)).ToArray();
            var brandIds = catalogBrandIds.Split(',').Select(i => Int32.Parse(i)).ToArray();
            var result = items.Where(i => typeIds.Contains(i.CatalogTypeId) && brandIds.Contains(i.CatalogBrandId)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/types/{catalogTypeIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByTypes(string catalogTypeIds)
        {
            var typeIds = catalogTypeIds.Split(',').Select(i => Int32.Parse(i)).ToArray();
            var result = items.Where(i => typeIds.Contains(i.CatalogTypeId)).ToList();
            return await Task.FromResult(Ok(result));
        }

        [HttpGet]
        [Route("items/brands/{catalogBrandIds:minlength(1)}")]
        public async Task<IActionResult> GetItemsByBrands(string catalogBrandIds)
        {
            var brandIds = catalogBrandIds.Split(',').Select(i => Int32.Parse(i)).ToArray();
            var result = items.Where(i => brandIds.Contains(i.CatalogBrandId)).ToList();
            return await Task.FromResult(Ok(result));
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

using System;
using Xunit;
using Catalog.API.Controllers;
using Moq;
using Catalog.API.Services;
using System.Collections.Generic;
using Catalog.API.Models;
using System.Reflection;
using Microsoft.AspNetCore.Mvc;
using System.Linq;
using System.Threading.Tasks;

namespace ServiceTest.Controller
{
    public class CatalogControllerTest
    {
        private CatalogController controller;
        private Mock<ICatalogService> mockCatalogService = new Mock<ICatalogService>();
        private IList<CatalogItem> mockItems = new List<CatalogItem>();
        private IList<CatalogType> mockTypes = new List<CatalogType>();
        private IList<CatalogBrand> mockBrands = new List<CatalogBrand>();

        public CatalogControllerTest()
        {
            SetUpMockData();
            controller = new CatalogController(mockCatalogService.Object);
        }

        private void SetUpMockData()
        {
            mockTypes.Add(new CatalogType() { Id = Guid.NewGuid(), Type = "Mug", Icon = "local_cafe" });
            mockTypes.Add(new CatalogType() { Id = Guid.NewGuid(), Type = "T-Shirt", Icon = "person" });
            mockTypes.Add(new CatalogType() { Id = Guid.NewGuid(), Type = "Sheet", Icon = "label" });
            mockTypes.Add(new CatalogType() { Id = Guid.NewGuid(), Type = "USB Memory Stick", Icon = "usb" });

            mockBrands.Add(new CatalogBrand() { Id = Guid.NewGuid(), Brand = "Azure", Icon = "cloud" });
            mockBrands.Add(new CatalogBrand() { Id = Guid.NewGuid(), Brand = ".NET", Icon = "code" });
            mockBrands.Add(new CatalogBrand() { Id = Guid.NewGuid(), Brand = "Visual Studio", Icon = "polymer" });
            mockBrands.Add(new CatalogBrand() { Id = Guid.NewGuid(), Brand = "SQL Server", Icon = "storage" });
            mockBrands.Add(new CatalogBrand() { Id = Guid.NewGuid(), Brand = "Other", Icon = "category" });

            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = ".NET Bot Black Sweatshirt", Name = ".NET Bot Black Sweatshirt", Price = 19.5M, ImagePath = "./Images/Item 1.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "Mug").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = ".NET Black & White Mug", Name = ".NET Black & White Mug", Price= 8.50M, ImagePath = "./Images/Item 2.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Prism White T-Shirt", Name = "Prism White T-Shirt", Price = 12, ImagePath = "./Images/Item 3.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = ".NET Foundation Sweatshirt", Name = ".NET Foundation Sweatshirt", Price = 12, ImagePath = "./Images/Item 4.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "Sheet").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Roslyn Red Sheet", Name = "Roslyn Red Sheet", Price = 8.5M, ImagePath = "./Images/Item 5.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = ".NET Blue Sweatshirt", Name = ".NET Blue Sweatshirt", Price = 12, ImagePath = "./Images/Item 6.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Roslyn Red T-Shirt", Name = "Roslyn Red T-Shirt", Price = 12, ImagePath = "./Images/Item 7.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Kudu Purple Sweatshirt", Name = "Kudu Purple Sweatshirt", Price = 8.5M, ImagePath = "./Images/Item 8.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "Mug").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Cup<T> White Mug", Name = "Cup<T> White Mug", Price = 12, ImagePath = "./Images/Item 9.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "Sheet").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = ".NET Foundation Sheet", Name = ".NET Foundation Sheet", Price = 12, ImagePath = "./Images/Item 10.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "Sheet").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == ".NET").Select(c => c.Id).First(), Description = "Cup<T> Sheet", Name = "Cup<T> Sheet", Price = 8.5M, ImagePath = "./Images/Item 11.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = mockTypes.Where(c => c.Type == "T-Shirt").Select(c => c.Id).First(), CatalogBrandId = mockBrands.Where(c => c.Brand == "Other").Select(c => c.Id).First(), Description = "Prism White TShirt", Name = "Prism White TShirt", Price = 12, ImagePath = "./Images/Item 12.png", CreatedDate = DateTime.Today });
        }
        private void SetUpMock()
        {
            var mock = new Mock<ICatalogService>();
            mock.Setup(m => m.GetItems()).Returns(() => Task.FromResult(mockItems));
            mock.Setup(m => m.GetTypes()).Returns(() => Task.FromResult(mockTypes));
            mock.Setup(m => m.GetBrands()).Returns(() => Task.FromResult(mockBrands));
            this.mockCatalogService = mock;
        }

        [Fact]
        public void WhenCallGetItems_ShouldExist()
        {
            AssertMethodExists("GetItems");
        }

        [Fact]
        public void WhenCallGetItemsByIds_ShouldExist()
        {
            AssertMethodExists("GetItemsByIds");
        }

        [Fact]
        public void WhenCallGetItemByName_ShouldExist()
        {
            AssertMethodExists("GetItemByName");
        }

        [Fact]
        public void WhenCallGetItemsByTypesAndBrands_ShouldExist()
        {
            AssertMethodExists("GetItemsByTypesAndBrands");
        }

        [Fact]
        public void WhenCallGetItemsByTypes_ShouldExist()
        {
            AssertMethodExists("GetItemsByTypes");
        }

        [Fact]
        public void WhenCallGetItemsByBrands_ShouldExist()
        {
            AssertMethodExists("GetItemsByBrands");
        }

        [Fact]
        public void WhenCallGetCatalogTypes_ShouldExist()
        {
            AssertMethodExists("GetCatalogTypes");
        }

        [Fact]
        public void WhenCallGetCatalogBrands_ShouldExist()
        {
            AssertMethodExists("GetCatalogBrands");
        }

        [Fact]
        public async void WhenCallGetItems_ShouldGetAllItems()
        {
            mockCatalogService.Setup(m => m.GetItems()).Returns(() => Task.FromResult(mockItems));

            var result = await controller.GetItems() as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(mockItems, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsByIds_ShouldGetItemsFilteredByIds()
        {
            var ids = mockItems.Select(c => c.Id).Take(3).ToArray();
            var items = mockItems.Take(3).ToList() as IList<CatalogItem>;
            mockCatalogService.Setup(m => m.GetItems(ids)).Returns(() => Task.FromResult(items));
            
            var result = await controller.GetItemsByIds(String.Join(',', ids)) as OkObjectResult;
            var expected = mockItems.Where(i => ids.Contains(i.Id)).ToList();
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsById_ShouldGetItemFilteredById()
        {
            var id = mockItems.First().Id;
            var expected = mockItems.Where(i => i.Id == id).ToList() as IList<CatalogItem>;
            mockCatalogService.Setup(m => m.GetItems(new Guid[] { id })).Returns(() => Task.FromResult(expected));

            var result = await controller.GetItemsByIds(id.ToString()) as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsByName_ShouldGetItemFilteredByName()
        {
            var name = "Prism White T-Shirt";
            var expected = mockItems.Where(i => i.Name == name).First();
            mockCatalogService.Setup(m => m.GetItemByName(name)).Returns(() => Task.FromResult(expected));

            var result = await controller.GetItemByName(name) as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsByTypes_ShouldGetItemFilteredByTypes()
        {
            var ids = mockTypes.Select(c => c.Id).Take(3).ToArray();
            var expected = mockItems.Where(i => ids.Contains(i.CatalogTypeId)).ToList() as IList<CatalogItem>;
            mockCatalogService.Setup(m => m.GetItemsByTypes(ids)).Returns(() => Task.FromResult(expected));

            var result = await controller.GetItemsByTypes(String.Join(',', ids)) as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsByBrands_ShouldGetItemFilteredByBrands()
        {
            var ids = mockBrands.Select(c => c.Id).Take(3).ToArray();
            var expected = mockItems.Where(i => ids.Contains(i.CatalogBrandId)).ToList() as IList<CatalogItem>;
            mockCatalogService.Setup(m => m.GetItemsByBrands(ids)).Returns(() => Task.FromResult(expected));

            var result = await controller.GetItemsByBrands(String.Join(',', ids)) as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetItemsByTypesAndBrands_ShouldGetItemFilteredByTypesAndBrands()
        {
            var typeIds = mockTypes.Select(c => c.Id).Take(2).ToArray();
            var brandIds = mockBrands.Select(c => c.Id).Take(2).ToArray();
            var expected = mockItems.Where(i => typeIds.Contains(i.CatalogTypeId) && brandIds.Contains(i.CatalogBrandId)).ToList() as IList<CatalogItem>;

            mockCatalogService.Setup(m => m.GetItemsByTypesAndBrands(typeIds, brandIds)).Returns(() => Task.FromResult(expected));

            var result = await controller.GetItemsByTypesAndBrands(String.Join(',', typeIds), String.Join(',', brandIds)) as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(expected, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetCatalogTypes_ShouldGetCatalogTypes()
        {
            mockCatalogService.Setup(m => m.GetTypes()).Returns(() => Task.FromResult(mockTypes));

            var result = await controller.GetCatalogTypes() as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(mockTypes, result.Value);

            mockCatalogService.Reset();
        }

        [Fact]
        public async void WhenCallGetCatalogBrands_ShouldGetCatalogBrands()
        {
            mockCatalogService.Setup(m => m.GetBrands()).Returns(() => Task.FromResult(mockBrands));

            var result = await controller.GetCatalogBrands() as OkObjectResult;
            AssertResultOk(result);
            Assert.Equal(mockBrands, result.Value);

            mockCatalogService.Reset();
        }

        private void AssertResultOk(OkObjectResult result)
        {
            Assert.NotNull(result);
            Assert.Equal(200, result.StatusCode);
        }

        private void AssertMethodExists(string methodName)
        {
            var method = controller.GetType().GetMethod(methodName);
            Assert.NotNull(method);
        }
    }
}

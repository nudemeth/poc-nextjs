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
using System.IO;

namespace Catalog.API.Test.Controller
{
    public class ImageControllerTest
    {
        private ImageController controller;
        private IDictionary<string, string> config;
        private Mock<ICatalogService> mockCatalogService;
        private IList<CatalogItem> mockItems = new List<CatalogItem>();

        public ImageControllerTest()
        {
            SetUpMockData();
            SetUpMock();
            config = new Dictionary<string, string>()
            {
                { "image-path", "../../../Images" }
            };
            controller = new ImageController(mockCatalogService.Object, config);
        }

        private void SetUpMockData()
        {
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Bot Black Sweatshirt", Name = ".NET Bot Black Sweatshirt", Price = 19.5M, FileName = "Item 1.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Black & White Mug", Name = ".NET Black & White Mug", Price= 8.50M, FileName = "Item 2.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Prism White T-Shirt", Name = "Prism White T-Shirt", Price = 12, FileName = "Item 3.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Foundation Sweatshirt", Name = ".NET Foundation Sweatshirt", Price = 12, FileName = "Item 4.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Roslyn Red Sheet", Name = "Roslyn Red Sheet", Price = 8.5M, FileName = "Item 5.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Blue Sweatshirt", Name = ".NET Blue Sweatshirt", Price = 12, FileName = "Item 6.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Roslyn Red T-Shirt", Name = "Roslyn Red T-Shirt", Price = 12, FileName = "Item 7.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Kudu Purple Sweatshirt", Name = "Kudu Purple Sweatshirt", Price = 8.5M, FileName = "Item 8.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Cup<T> White Mug", Name = "Cup<T> White Mug", Price = 12, FileName = "Item 9.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Foundation Sheet", Name = ".NET Foundation Sheet", Price = 12, FileName = "Item 10.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Cup<T> Sheet", Name = "Cup<T> Sheet", Price = 8.5M, FileName = "Item 11.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Prism White TShirt", Name = "Prism White TShirt", Price = 12, FileName = "Item 12.png", CreatedDate = DateTime.Today });
        }
        private void SetUpMock()
        {
            var mock = new Mock<ICatalogService>();
            mock.Setup(m => m.GetItems()).Returns(() => mockItems);
            this.mockCatalogService = mock;
        }

        [Fact]
        public void WhenCallGetImage_ShouldExist()
        {
            var method = controller.GetType().GetMethod("GetImage");
            Assert.NotNull(method);
        }

        [Fact]
        public async void WhenGetImage_ShouldGetImageFileFromId()
        {
            var id = mockItems.First().Id.ToString();
            var result = await controller.GetImage(id) as FileContentResult;

            Assert.NotNull(result);
            
            var item = mockItems.Where(i => i.Id == new Guid(id)).SingleOrDefault();
            var path = Path.Combine(config["image-path"], item.FileName);
            var imageFileExtension = Path.GetExtension(item.FileName);
            var mimetype = "image/png";
            var buffer = System.IO.File.ReadAllBytes(path);
            var expected = controller.File(buffer, mimetype);

            Assert.Equal(expected.FileDownloadName, result.FileDownloadName);
            Assert.Equal(expected.LastModified, result.LastModified);
            Assert.Equal(expected.ContentType, result.ContentType);
        }
    }
}
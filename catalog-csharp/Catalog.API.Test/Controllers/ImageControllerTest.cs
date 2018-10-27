using System;
using Xunit;
using Catalog.API.Controllers;
using Moq;
using Catalog.API.Services;
using System.Collections.Generic;
using Catalog.API.Models;
using Microsoft.AspNetCore.Mvc;
using System.Linq;
using System.IO;
using System.Threading.Tasks;

namespace Catalog.API.Test.Controller
{
    public class ImageControllerTest
    {
        private ImageController controller;
        private Mock<ICatalogService> mockCatalogService = new Mock<ICatalogService>();
        private IList<CatalogItem> mockItems = new List<CatalogItem>();

        public ImageControllerTest()
        {
            SetUpMockData();
            controller = new ImageController(mockCatalogService.Object);
        }

        private void SetUpMockData()
        {
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Bot Black Sweatshirt", Name = ".NET Bot Black Sweatshirt", Price = 19.5M, ImagePath = "./Images/Item 1.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Black & White Mug", Name = ".NET Black & White Mug", Price= 8.50M, ImagePath = "./Images/Item 2.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Prism White T-Shirt", Name = "Prism White T-Shirt", Price = 12, ImagePath = "./Images/Item 3.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Foundation Sweatshirt", Name = ".NET Foundation Sweatshirt", Price = 12, ImagePath = "./Images/Item 4.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Roslyn Red Sheet", Name = "Roslyn Red Sheet", Price = 8.5M, ImagePath = "./Images/Item 5.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Blue Sweatshirt", Name = ".NET Blue Sweatshirt", Price = 12, ImagePath = "./Images/Item 6.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Roslyn Red T-Shirt", Name = "Roslyn Red T-Shirt", Price = 12, ImagePath = "./Images/Item 7.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Kudu Purple Sweatshirt", Name = "Kudu Purple Sweatshirt", Price = 8.5M, ImagePath = "./Images/Item 8.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Cup<T> White Mug", Name = "Cup<T> White Mug", Price = 12, ImagePath = "./Images/Item 9.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = ".NET Foundation Sheet", Name = ".NET Foundation Sheet", Price = 12, ImagePath = "./Images/Item 10.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Cup<T> Sheet", Name = "Cup<T> Sheet", Price = 8.5M, ImagePath = "./Images/Item 11.png", CreatedDate = DateTime.Today });
            mockItems.Add(new CatalogItem() { Id = Guid.NewGuid(), CatalogTypeId = Guid.NewGuid(), CatalogBrandId = Guid.NewGuid(), Description = "Prism White TShirt", Name = "Prism White TShirt", Price = 12, ImagePath = "./Images/Item 12.png", CreatedDate = DateTime.Today });
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
            var mockItem = mockItems.First();
            mockCatalogService.Setup(m => m.GetItem(mockItem.Id)).Returns(() => Task.FromResult(mockItem));
            var result = await controller.GetImage(mockItem.Id.ToString()) as FileContentResult;

            Assert.NotNull(result);
            
            var item = mockItems.Where(i => i.Id == new Guid(mockItem.Id.ToString())).SingleOrDefault();
            var mimetype = "image/png";
            var buffer = System.IO.File.ReadAllBytes(item.ImagePath);
            var expected = controller.File(buffer, mimetype);

            Assert.Equal(expected.FileDownloadName, result.FileDownloadName);
            Assert.Equal(expected.LastModified, result.LastModified);
            Assert.Equal(expected.ContentType, result.ContentType);

            mockCatalogService.Reset();
        }
    }
}
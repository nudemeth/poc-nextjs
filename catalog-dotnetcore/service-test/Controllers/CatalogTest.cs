using System;
using Xunit;
using Catalog.API.Controllers;

namespace ServiceTest.Controller
{
    public class CatalogControllerTest
    {
        private CatalogController controller;
        public CatalogControllerTest()
        {
            controller = new CatalogController();
        }

        [Fact]
        public async void GetItems()
        {
            var items = await controller.GetItems();
            Assert.True(true);
        }
    }
}
